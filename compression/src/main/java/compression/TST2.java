/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compression;
import java.lang.StringBuilder;
/**
 * Implementation of Ternary Search Trie
 * @author tuomomehtala
 */
class Node<Value>{
    private Value value;
    private char c;
    private Node left,right,mid;
    public Node(){
        
    }
    public char getChar(){
        return c;
    }
    public void setChar(char c){
        this.c=c;
        
    }
    public void setValue(Value val){
        this.value = val;
        
    }
    public void setLeft(Node<Value> left){
        this.left= left;
    }
    public Node<Value> getLeft(){
        return this.left;
    }
    public void setRight(Node<Value> right){
        this.right = right;
    }
    public Node<Value> getRight(){
        return this.right;
    }
    public void setMid(Node<Value> mid){
        this.mid = mid;
    }
    public Node<Value> getMid(){
        return this.mid;
    }
    public Value getValue(){
        return this.value;
    }
}
public class TST2<Value> {
    private Node<Value> root;
    
    public  TST2(){
    //do nothing
    }
    public void put(String key, Value val){
        if(key==null) return;
        root = put(root,key,val,0);
        
    }
    private Node<Value> put(Node<Value> x, String key, Value val, int d){
        char c = key.charAt(d);
        if(x==null){
            x = new Node<Value>();
            x.setChar(c);
           // System.out.println("creating new node: "+c);
        }
        if(c>x.getChar()) x.setLeft(put(x.getLeft(),key,val,d));
        else if(c<x.getChar()) x.setRight(put(x.getRight(),key,val,d));
        else if(d<key.length()-1) x.setMid(put(x.getMid(),key,val,d+1));
        else x.setValue(val);
        
        return x;
    }
    /**
     * Find the key for a given String
     * @param key String
     * @return 
     */
    public Value get(String key){
       if(key.length()==0) return null;
       
        Node<Value> node= get(root,key,0);
        if(node == null) return null;
        return node.getValue();
    }
    private Node<Value> get(Node<Value> x, String key, int d){
        char c = key.charAt(d);
        if(x == null) return null;
        if(c>x.getChar()) return get(x.getLeft(),key,d);
        if(c<x.getChar()) return get(x.getRight(), key,d);
        else if(d<key.length()-1) return get(x.getMid(),key, d+1);
        else return x;
    }
    public String longestPrefix(String key,boolean debug, int start){
      //  if(key.charAt(0)==(char)10)System.out.println("ERROR"+"key: "+key);
        int d = 0;
        StringBuilder str = new StringBuilder(1000);
        int strLength = key.length()-start;
     //   System.out.println("Strlen in beginning: "+ strLength);
        int length = 0;
        Node<Value> x = root;
        if(x==null) return "";
        while(x!=null && d<strLength){
         
             char c = key.charAt(start+d);
       //      System.out.println("char c at :"+d+ " value "+c);
            // if(debug) System.out.println("searching for: "+c+" key length: "+key.length());
            if(c>x.getChar()) x = x.getLeft();
            else if(c<x.getChar()) x = x.getRight();
            else{
                d++;
                str.append(c);
                x= x.getMid();
            }
                if(x==null || d == strLength) {
                  //  System.out.println("found d: "+d);
                    length = d;
                }
            //if(debug) System.out.println("d: "+d);
        }
        if(d==0)System.out.println("searching for: "+key.substring(start, key.length()));
      //  if(debug) System.out.println("returning lenght: "+length);
        //System.out.println("returngin found string :"+str.toString() + " value of d: "+d);
        return str.toString();
    }
}
