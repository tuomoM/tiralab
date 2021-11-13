/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package compression;
import java.util.HashMap;
import java.util.PriorityQueue;
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
    
    public HuffmanCompression(String data){
       this.data = data;
        this.keys = new HashMap<>();
        HashMap<Character,Integer> occurences = new HashMap<>();
        
        for(int i = 0 ; i < data.length();i++){
            occurences.put(data.charAt(i), occurences.getOrDefault(data.charAt(i), 0)+1);
        }
        this.occurences = occurences;
        generateNodes(occurences);
        generateKeys(this.nodes,"");
        generatePacked();
    }
    public HuffmanCompression(String packedData,HashMap<Character,Integer> occurences){
        this.dataPacked = packedData;
        generateNodes(occurences);
        generateData(0);
    }
    private void generateData(int index){
        HuffmanNode node = this.nodes;
        while(!node.isLeaf()){
            if(this.dataPacked.charAt(index)==0){
                node = node.getLeft();
            }else{
                node = node.getRight();
            }
            index++;
                
        }
        this.data = this.data+node.getLeaf();
        if(index<this.dataPacked.length()){
            generateData(index);
        }
    }
    private void generateNodes(HashMap<Character,Integer> occurences){
        HuffmanNode node1,node2;
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        for(char a : occurences.keySet()){
            queue.add(new HuffmanNode(a, occurences.get(a)));
            
        }
        while(!queue.isEmpty()){
            node1 = queue.poll();
            if(queue.isEmpty()){
                nodes = node1;
            }else{
                node2 = queue.poll();
                queue.add(new HuffmanNode(node1,node2));
            }
            
        }
    }
    private void generateKeys(HuffmanNode node,String key){
        if(node.isLeaf()){
            this.keys.put(node.getLeaf(), key);
            return;
        }
        generateKeys(node.getLeft(),key+0);
        generateKeys(node.getRight(),key+1);
    }
    private void generatePacked(){
        for(int i = 0; i<this.data.length();i++){
            char a = data.charAt(i);
            dataPacked = dataPacked+a;
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
    
}
