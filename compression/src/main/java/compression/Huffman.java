/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compression;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.math.BigInteger;
/**
 *
 * @author tuomomehtala
 */
public class Huffman {
    private FileService fs;
    private HashMap<Character,String> keys;
    private HashMap<Character, BigInteger> occurences;
    private Node root;
    
    
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
    private void compress(){
        String data = "";
        int lenght = 0;
        PriorityQueue<Node> queue;
        queue = new PriorityQueue<>(); 
        Node node1;
        Node node2;
        char b;
        while(!fs.inEmpty()){
            try{
            b =  (char)(fs.readByteAsInt()&0xff);  
            data+=b;
           
            lenght++;
            }catch(Exception e){
                System.out.println("Eof - reading the file");
            }
        }
        
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
        fs.close();
        
        
    }
    private void extract(){
        
        this.root = decodeTree();
        extractData();
        fs.close();
        
        
        
    }
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
    
    private void generateKeys(Node node, String key){
        if(node.isLeaf){
            this.keys.put(node.leaf, key);
            return;
        }else{
            generateKeys(node.left,key+'0');
            generateKeys(node.right,key+'1');
        }
    }
    private Node decodeTree(){
        if(fs.readBit()){
           return new Node( (char)( fs.readByteAsInt() & 0xff),BigInteger.ONE);
        }else{
            Node left = decodeTree();
            Node right = decodeTree();
            
            return new Node(left,right);
        }
        
        
    }
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
