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
import java.util.Scanner;
import java.io.File;

/**
 *
 * @author tuomomehtala
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private final static String version = "1.0";
    
    public static void main(String[] args) {

         Huffman hfc;
         BufferedInputStream ioIn ;
         BufferedOutputStream ioOut;
         boolean hf = false;
         boolean lzw = false;
         boolean compress = false;
         FileService fs;
         String fileName;
         String outputFileName;
         String extension;
         Lz lz;
         if(args[0].equals("--h") || args[0].equals("--help")){
             printHelp();
             return;
         }
         
   
       
        
       if(args.length<3){
           System.out.println("Too few arguments, for help use '--h'");
           return;
       }
       if(args[0].equalsIgnoreCase("-hf")){ //huffman
           System.out.println("Huffman");
           hf = true;
           
       } else if(args[0].equalsIgnoreCase("-lz")){
          
       lzw = true;
       }else{
           System.out.println("Illegal arguments:" + args[0]);
           return;
       }
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
             
              fs = new FileService(fileName,outputFileName);
              hfc = new Huffman(fs,compress);
          }catch(IOException e){
              System.out.println("Exception : "+e.getLocalizedMessage());
          }
         
          
      }else if(lzw){
              if(args.length == 4){
              extension = args[3];
          }else{
              if(compress){
                  extension = ".lz";
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
              fs = new FileService(fileName,outputFileName);
       
              lz = new Lz(fs);
              if(compress){
                  lz.compress();
              }else{
                  lz.extract();
              }
             
          }catch(IOException e){
              System.out.println("Exception : "+e.getLocalizedMessage());
          }
          
      }
       
       
    }
    
 static void printHelp(){
     System.out.println("");
     System.out.println("*******************");
     System.out.println("Compression/Extraction program with Huffman and LZW options version: "+version);
     System.out.println("Tiralab project 2021 - Tuomo Mehtälä");
     System.out.println("It is strongly advised to not use this software for anything");
     System.out.println("******************");
     System.out.println("");
     System.out.println("switches to be used:");
     System.out.println(" -hf/lz, -c/e, filename, target externsion(optional)");
     System.out.println("hf is huffman and lz is LZW");
     System.out.println("c is for compression and e for extract");
     System.out.println("");
     System.out.println("*****************");
     System.out.println("");
     System.out.println("filename is the file to be processed, should be at directory :"+System.getProperty("user.dir"));
     System.out.println("If no optional extension is defined, the target for compressed file is either .hf/.lz and for extraction .txt");
     System.out.println("If you want for example '.exe' extension, you should add parameter with value '.exe' ");
 }
    
}
