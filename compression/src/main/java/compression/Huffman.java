/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compression;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.math.BigInteger;
/**
 * implementation of huffman compression
 * @author tuomomehtala
 */
public class Huffman {
    private FileService fs;
    private HashMap<Character,String> keys;
    private HashMap<Character, BigInteger> occurences;
    private Node root;
    
    /**
     * Internal class implementation for huffman node
     */
    private static class Node implements Comparable{
        Node left;
        Node right;
        boolean isLeaf;
        char leaf;
        BigInteger value;
        public Node(char a,BigInteger value){
            this.leaf = a;
            this.value = value;
            this.isLeaf = true;
        }
        public Node( Node left, Node right){
            this.value = left.value.add(right.value);
            this.left = left;
            this.right = right;
            this.isLeaf= false;
        }
        @Override
        public int compareTo(Object o) {
            if(o instanceof Node){      
                return this.value.compareTo(((Node) o).value);
            }
            return 0;
        }
        
    }
    /**
     * Depending on parameter compress, either compresses or expands
     * @param fs FileSerice object with files for reading and writing.
     * @param compress 
     */
    public Huffman(FileService fs, boolean compress){
        this.keys = new HashMap<>();
        this.occurences = new HashMap<>();
        this.fs = fs;
        if(compress){
            System.out.println("compressing");
            compress();
            
        }else{
            extract();
        }
        
    }
    /**
     * starts the compression
     */
    private void compress(){
        String data = "";
        int lenght = 0;
        PriorityQueue<Node> queue;
        queue = new PriorityQueue<>(); 
        Node node1;
        Node node2;
        char b;
        data = fs.readFile();
        lenght = data.length();

        
        for(char a:data.toCharArray()){
            occurences.put(a,BigInteger.ONE.add(occurences.getOrDefault(a, BigInteger.ZERO)));
        }
        
        for(char a: occurences.keySet()){
            queue.add(new Node(a,occurences.get(a)));
        }
        while(!queue.isEmpty()){
            node1 = queue.poll();
            if(queue.isEmpty()){
                this.root = node1;
                
            }else{
                node2 = queue.poll();
                queue.add(new Node(node1,node2));
            }
        }
        //Tree is complete, now build the keys.
        generateKeys(root,"");
        //write out the tree
        encodeTree(root);
        
        //write out the lenght of the input 
        fs.writeInt(lenght);
        //write out the data
        String buffer = "";
        for(char a:data.toCharArray()){
            buffer = keys.get(a);
            for(char c:buffer.toCharArray()){
                if(c=='1'){
                    fs.writeBoolean(true);
                }else{
                    fs.writeBoolean(false);
                }
            }
        }
        //close files
       // System.out.println("closing at: "+fs.getOutCounter());
        fs.close();
        
        
    }
    /**
     * starts the extraction process
     */
    private void extract(){
        
        this.root = decodeTree();
        System.out.println("Tree decoded");
        extractData();
        fs.close();
        
        
        
    }
    /**
     * Internal method to extract the content of the message
     */
    private void extractData(){
        int lenght = fs.readInt();
        boolean bit = false;
        Node node = root;
        while(!fs.inEmpty()&& lenght > 0){
            if(node.isLeaf){
                fs.writeByte(node.leaf);
                node = root;
                lenght--;
            }else{
                try{
                if(fs.readBit()){
                    node = node.right;
                }else{
                    node = node.left;
                }
                }catch (Exception e){
                    System.out.println("Exception at extraction - data - "+e.getLocalizedMessage());
                    break;
                }
            }
        }
    }
    /**
     * recursive method to generate keys for each character
     * @param node current node
     * @param key key so far
     */
    private void generateKeys(Node node, String key){
        if(node.isLeaf){
            this.keys.put(node.leaf, key);
            return;
        }else{
            generateKeys(node.left,key+'0');
            generateKeys(node.right,key+'1');
        }
    }
    /**
     * Reads the tree from the file
     * @return 
     */
    private Node decodeTree(){
        if(fs.readBit()){
           return new Node( fs.readChar(),BigInteger.ONE);
        }else{
            Node left = decodeTree();
            Node right = decodeTree();
            
            return new Node(left,right);
        }
        
        
    }
    /**
     * Writes the tree to file
     * @param node 
     */
    private void encodeTree(Node node){
        if(node.isLeaf){
            fs.writeBoolean(true);
            fs.writeByte(node.leaf);
        }else{
            fs.writeBoolean(false);
            encodeTree(node.left);
            encodeTree(node.right);
        }
    }
    
    
}
