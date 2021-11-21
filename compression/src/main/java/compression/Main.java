/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package compression;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.nio.ByteBuffer;
import java.util.ArrayList;
/**
 *
 * @author tuomomehtala
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         HuffmanCompression hfComp;
         BufferedInputStream ioIn ;
         BufferedOutputStream ioOut;
         String inputData = "";
         
         
        // TODO code application logic here
       if(args[0]=="-hf"){ //huffman
           if(args[1]=="-c" && args[2]!=null){ //compress
               try{
               ioIn = new BufferedInputStream(new FileInputStream(args[2]));
             
               inputData = new String(readFile(ioIn));
               }catch(Exception e){
                   System.out.println("Exception:"+e.getLocalizedMessage());
               }
               hfComp = new HuffmanCompression(inputData);
               String outputData = hfComp.getPackedData();
               int strLen = inputData.length();
               int index = 0;
               try{
                    for(int i = 0;i<args[2].length();i++){
                        if(args[2].charAt(i)=='.') index = i;
                    }
                    ioOut = new BufferedOutputStream(new FileOutputStream(args[2].substring(0, index)+"hf"));
                    HashMap<Character,Integer> occurences  = hfComp.getOccurences();
                    writeHeader(strLen,occurences,ioOut);
                    writeBinaryString(hfComp.getPackedData(),ioOut);
                    ioOut.flush();
                    ioOut.close();
               } catch(Exception e){
                   System.out.println("Exception:"+e.getLocalizedMessage());
               }
           }else if(args[1]== "-e" && args[2]!=null){ //extract
               try{
                    ioIn = new BufferedInputStream(new FileInputStream(args[2]));
                    byte[] data = readFile(ioIn);
                    
               }catch (Exception e){
                   System.out.println("Exception:"+e.getLocalizedMessage());
               }
            
           }else System.out.println("Illegal arguments");
       }
      
      
    }
    
    private static void writeBinaryString(String data, BufferedOutputStream ioOut){
        int n = 0;
        int buffer = 0;
        for (int i = 0; i<data.length();i++){
            buffer <<= 1;
            if(data.charAt(i)=='1') buffer |=1;
            n++;
            if(n==8){
                try{
                    ioOut.write(buffer);
                    buffer = 0;
                    n=0;
                }catch(Exception e){
                    System.out.println("Exception:"+e.getLocalizedMessage());
                }
            }
        }
        if(n!=0){
            buffer <<= (8-n);
            try{
                ioOut.write(buffer);
            }catch (Exception e){
                System.out.println("Exception"+e.getLocalizedMessage());
            }
        }
    }
    private static void writeHeader(int strL, HashMap<Character,Integer> occurences, BufferedOutputStream ioOut){
        byte[] largeInt;
        largeInt = ByteBuffer.allocate(4).putInt(strL).array();
        
        try{
         
            ioOut.write(largeInt);
            
            largeInt = ByteBuffer.allocate(4).putInt(occurences.size()).array();
            ioOut.write(largeInt);
          
            int count = 0;
            for(char a:occurences.keySet()){
                ioOut.write(a);
                ioOut.write(ByteBuffer.allocate(4).putInt(occurences.get(a)).array());
                
                
            }
            
        }catch(Exception e){
            System.out.println("Exception:"+e.getLocalizedMessage());
        }

    }
    private static byte[] readFile(BufferedInputStream in){
        byte[] data = null;
        try{
            data = in.readAllBytes();
        }catch(IOException e){
            System.out.println("Exception:"+e.getLocalizedMessage());
        }
      
        return data;
    }
    
}
