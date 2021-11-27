/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package compression;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.nio.ByteBuffer;
/**
 *
 * @author tuomomehtala
 */
public class HuffmanCompression {
    private HashMap<Character,String> keys;
    private HuffmanNode nodes;
    private HashMap<Character,Integer> occurences;
    private String data;
    private String dataPacked;
    private int strLen;
    private FileService fs;
    public HuffmanCompression(FileService fs, boolean compress){
        this.fs = fs;
        this.data = "";
        char a;
        this.occurences = new HashMap<>();
        this.keys = new HashMap<>();
        if(compress){
            while(!fs.inEmpty()){
                try{
                a=  (char)(fs.readByteAsInt()& 0xff);
                  this.data+=a;        
                this.occurences.put(a, this.occurences.getOrDefault(a, 0)+1);
                  System.out.println("char: "+a);
                }catch(Exception e){
                    System.out.println("eof");
                }
              
              
                
            }
            System.out.println("data read succesfully");
            generateNodes();
            generateKeys(this.nodes,"");
            generatePacked();
        }else{ // extract
            
            this.nodes = decodeNode();
            try{
            while(!fs.inEmpty()){
                System.out.println("decoding");
                decodeData(this.nodes);
            }
            } catch(Exception e){
                System.out.println(e.getLocalizedMessage());
                System.out.println("EOF - decoding");
            }
            
        }
        System.out.println("constructor complete");
    }
    /**
     * Method saves the uncompressed data to file.
     * 
     */
    public void saveData(){
        for(int i = 0; i< this.data.length();i++){
            fs.writeInt((int)this.data.charAt(i));
        }
        
    }
    /**
     * Method saves the compressed file. This includes the saved huffman tree for purposes of decoding.
     */
    public void saveCompressed(){
        encodeNode(this.nodes);
        encodeData();
    }
    /**
     * This is a constructor that can be used to test the encoding.
     * @param data the unpacked data 
     */
    public HuffmanCompression(String data){
       this.data = data.trim();
        this.keys = new HashMap<>();
        HashMap<Character,Integer> occurences = new HashMap<>();
        
        for(int i = 0 ; i < data.length();i++){
            occurences.put(data.charAt(i), occurences.getOrDefault(data.charAt(i), 0)+1);
        }
        this.occurences = occurences;
        generateNodes();
        generateKeys(this.nodes,"");
        generatePacked();
        System.out.println("Packed data in hfc: "+this.dataPacked);
    }
    
   /**
    * Method generates the huffman tree from original data
    */
    private void generateNodes(){
        
        HuffmanNode node1,node2;
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        for(char a : occurences.keySet()){
            queue.add(new HuffmanNode(a, occurences.get(a)));
            
        }
        while(!queue.isEmpty()){
            node1 = queue.poll();
            if(queue.isEmpty()){
                this.nodes = node1;
            }else{
                node2 = queue.poll();
                queue.add(new HuffmanNode(node1,node2));
            }
            
        }
    }
    /**
     * Generates the pairs character and binarycoding. This is a recursive function that generates the key while going to the leaf and when at leaf, saves the generated key.
     * @param node current node
     * @param key the binary code for being generated.
     */
    private void generateKeys(HuffmanNode node,String key){
        if(node.isLeaf()){
            this.keys.put(node.getLeaf(), key);
            return;
        }
        generateKeys(node.getLeft(),key+0);
        generateKeys(node.getRight(),key+1);
    }
    /**
     * Generates the packed data using the character key pairs generated in generateKey method.
     */
    private void generatePacked(){
        this.dataPacked = "";
        for(int i = 0; i<this.data.length();i++){
            char a = data.charAt(i);
            dataPacked = dataPacked+this.keys.get(a);
        }
    }
    public String getPackedData(){
        return this.dataPacked;
    }
    public String getData(){
        return this.data;
    }
    public HashMap<Character,Integer> getOccurences(){
        return this.occurences;
    }
    public HashMap<Character,String> getKeys(){
        return this.keys;
    }
    /**
     * generates the huffman tree from the file. This is a recursive function. The indicator for a leaf is the bit 1 and following 8 bits should represent the character.
     * @return 
     */
    private HuffmanNode decodeNode(){
        try{
        if(fs.readBit()){
            System.out.println("found char ");
            return new HuffmanNode((char)(fs.readByteAsInt()& 0xff),5);
          
        }else{
            HuffmanNode left = decodeNode();
            HuffmanNode right = decodeNode();
            return new HuffmanNode(left,right);
        }
        }catch(Exception e){
            System.out.println("EOF");
        }
        return null;
    }
    /**
     * Writes the packed data to file. The writing is done bit by bit.
     * FileService class takes care of the buffering.
     */
    private void encodeData(){
        for(int i = 0; i<this.dataPacked.length();i++){
            if(this.dataPacked.charAt(i)==1){
                fs.writeBoolean(true);
            }else{
                fs.writeBoolean(false);
            }
            
        }
    }
    /**
     * Writes the huffman tree to file. 0 presents a non leaf node and 1 presents a leaf. After each 1 bit representing a leaf, the 8 bit character is written.
     * This is a recursive method that goes through the huffman tree.
     * @param node Current node.
     */
    private void encodeNode(HuffmanNode node){
      
        if(node.isLeaf()){
            fs.writeBoolean(true);
            fs.writeInt((int)node.getLeaf());
        }else{
            fs.writeBoolean(false);
            encodeNode(node.getLeft());
            encodeNode(node.getRight());
        }
        
    }
    /**
     * reads a single character from file using huffmantree coding and writes it to non packed data. This will crash and burn... auts.
     * @param node current node. 
     */
    private void decodeData(HuffmanNode node){
        if(node.isLeaf()){
            this.data+=node.getLeaf();
            System.out.println("dataa kertyy: "+this.data);
            return;
        }else{
             try{
            if(fs.readBit()){
                decodeData(node.getLeft());
            }else{
                decodeData(node.getRight());
            }
            }catch(Exception e){
            // enf of file. Now this only works if no leaves are in range of 0,00,... 0000000
                 System.out.println("end of file");
            return;
        }
        }
    }
    
}
