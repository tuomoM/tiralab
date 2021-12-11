/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compression;

/**
 * Ternary Search Trie
 * @author tuomomehtala
 * @param <Value> type of key value to be used
 */
public class TST<Value> {
    private Node<Value> root;
    public TST(){
        
    }
    
    private static class Node<Value>{
        private char c;
        private Node<Value> left,mid,right;
        private Value value;
    }
    
   public void put(String key, Value value){
      if(key==null)System.out.println("error");
       if(key.length()>0){
           this.root = put(root,key,value,0);
       }
   } 
   
   private Node<Value> put(Node<Value> node, String key, Value val, int d){
       //System.out.println("putting "+key + " at depth: "+d + " value "+val);
       char c = key.charAt(d);
       if(node == null){
           node = new Node<Value>();
           node.c = c;
       }
       if       (c<node.c) node.left = put(node.left, key, val,d);
       else if  (c> node.c) node.right = put(node.right,key,val,d);
       else if  (d< key.length()-1) node.mid = put(node.mid, key,val,d+1);
       else                         node.value = val;
    
       return node;
   }
   public Value get(String key){
       
       return get(root,key,0).value;
   }

   private Node<Value> get(Node<Value> node, String key, int d){
       if(node == null) return null;
       char c = key.charAt(d);
       if (c> node.c) return get(node.left,key,d);
       else if(c<node.c) return get(node.right,key,d);
       else if(d<key.length()-1) return get(node.mid,key,d+1);
       else return node;
       
   }
   public String longestSubstring(String s){
       
       Node<Value> node = root;
       System.out.println("rootnode: "+root.c);
      
       int d = 0;
       int length = 0;
       while(node != null && d < s.length()){
        char c = s.charAt(d);  
        if(c> node.c) {
           node = node.left; 
        }
            else if(c < node.c)node = node.right;
            else {
               System.out.println("mid haara");
               d++;
               node = node.mid;
           if(node.value == null){
              length = d;  
              
           }  
           
           
       }
       }
       System.out.println("length:"+length);
       return s.substring(0, length);
   }
}
