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

         Huffman hfc;
         BufferedInputStream ioIn ;
         BufferedOutputStream ioOut;
         boolean hf;
         boolean compress;
         FileService fs;
         String fileName;
         String outputFileName;
         String extension;
         
         
        System.out.println("Compression start");  
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
       if(args.length<3){
           System.out.println("Too few arguments");
           return;
       }
       if(args[0].equalsIgnoreCase("-hf")){ //huffman
           System.out.println("Huffman");
           hf = true;
           if(args[1].equalsIgnoreCase("-c") && args[2]!=null){ //compress
               System.out.println("Compress");
               compress = true;
                fileName = args[2];
               int index = 0;
               for(int i = 0;i<args[2].length();i++){
                        if(args[2].charAt(i)=='.') index = i;
                }
               String outPutName = args[2].substring(0,index)+".hf";
               
               
           
             
           }else if(args[1].equals("-e") && args[2]!=null){ //extract
             compress = false;
            
           }else{ 
               System.out.println("Illegal arguments");
               return;
           }
       }else{
           System.out.println("Illegal arguments:" + args[0]);
           return;
       }
      if(hf){
          if(args.length == 4){
              extension = args[3];
          }else{
              if(compress){
                  extension = ".hf";
              }else{
                  extension = ".txt";
              }
              
          }
          int index = 0;
          for(int i = 0; i< args[2].length();i++){
              if(args[2].charAt(i)=='.')index = i;
          }
          fileName = args[2];
          outputFileName = args[2].substring(0, index)+extension;
          try{
              ioIn = new BufferedInputStream(new FileInputStream(fileName));
              ioOut = new BufferedOutputStream(new FileOutputStream(outputFileName));
              fs = new FileService(ioIn,ioOut);
              /**
              hfComp = new HuffmanCompression(fs,compress);
              if(compress){
                  hfComp.saveCompressed();
              }else{
                  hfComp.saveData();
              }
              fs.close();
              **/
              hfc = new Huffman(fs,compress);
          }catch(IOException e){
              System.out.println("Exception : "+e.getLocalizedMessage());
          }
         
          
      }else{
          
      }
       
        System.out.println("Compression done");
    }
    
 
    
}
