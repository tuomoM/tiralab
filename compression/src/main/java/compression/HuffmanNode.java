/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compression;

/**
 *
 * @author tuomomehtala
 */
public class HuffmanNode implements Comparable {
    private HuffmanNode left;
    private HuffmanNode right;
    private int value;
    private char leaf;
    private boolean isLeaf;
    
    public HuffmanNode(HuffmanNode left, HuffmanNode right){
        this.left = left;
        this.right = right;
        this.value = right.getValue() + left.getValue();
        this.isLeaf = false;
    }
    public HuffmanNode(char leaf, int value){
        this.value = value;
        this.leaf = leaf;
        this.isLeaf = true;
    }
    public int getValue(){
        return this.value;
    }
    public boolean isLeaf(){
        return this.isLeaf;
    }
    public char getLeaf(){
        return this.leaf;
    }
    public HuffmanNode getLeft(){
        return this.left;
    }
    public HuffmanNode getRight(){
        return this.right;
    }
    @Override
    public int compareTo(Object o) {
        if(o instanceof HuffmanNode){
            HuffmanNode node = (HuffmanNode)o;
            if (this.value > node.getValue()) return 1;
            if(this.value < node.getValue()) return -1;
            return 0;
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
