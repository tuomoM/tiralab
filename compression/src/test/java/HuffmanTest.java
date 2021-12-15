/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tuomomehtala
 */
import static org.junit.Assert.*;

import java.util.HashMap;
import org.junit.Test;
import compression.FileService;
import compression.Huffman;
import java.io.FileOutputStream;
import java.io.IOException;
public class HuffmanTest {
    String inputData = "aaabbca";
    String testdata = "And the LORD said unto Satan, Whence comest thou? Then Satan answered the LORD, and said, From going to and fro in the earth, and from walking up and down in it.";
    FileService fs;
    
    @Test
    public void SimpleHuffmanCompresses(){
           try{
            FileOutputStream fio = new FileOutputStream("testfile.t");
            fio.write(0);
            fio.flush();
            fio.close();
         }catch(Exception e){
             
         }
           try{
           fs = new FileService("testfile.t","testfile.out");
           
           
           for(int i = 0; i<inputData.length();i++){
               fs.writeByte(inputData.charAt(i));
           }
           fs.close();
           fs = new FileService("testfile.out","testfile.hf");
           Huffman hf = new Huffman(fs,true);
           
           fs = new FileService("testfile.hf","testfile.out");
//           boolean[] ok = new boolean[10];
//           //1110101001
//           boolean[] expected = {true,true,true,false,true,false,true,false,false,true};
//           
//           for(int i = 0; i< 10; i++){
//               ok[i]=fs.readBit();
//               System.out.println(ok[i]);
//           }
//         
            hf = new Huffman(fs,false);
            fs = new FileService("testfile.out","testfile.hf");
            String result = "";
            while(!fs.inEmpty()){
                result+=fs.readChar();
            }
            
            assertTrue(result.equals(inputData));
           }catch (IOException e){
               System.out.println("error "+e.getLocalizedMessage());
           }
           
    }
    @Test
    public void ComplexHuffmanCompresses(){
           try{
            FileOutputStream fio = new FileOutputStream("testfile.t");
            fio.write(0);
            fio.flush();
            fio.close();
         }catch(Exception e){
             
         }
           try{
           fs = new FileService("testfile.t","testfile.out");
           
           
           for(int i = 0; i<testdata.length();i++){
               fs.writeByte(testdata.charAt(i));
           }
           fs.close();
           fs = new FileService("testfile.out","testfile.hf");
           Huffman hf = new Huffman(fs,true);
           
           fs = new FileService("testfile.hf","testfile.out");
//           boolean[] ok = new boolean[10];
//           //1110101001
//           boolean[] expected = {true,true,true,false,true,false,true,false,false,true};
//           
//           for(int i = 0; i< 10; i++){
//               ok[i]=fs.readBit();
//               System.out.println(ok[i]);
//           }
//         
            hf = new Huffman(fs,false);
            fs = new FileService("testfile.out","testfile.hf");
            String result = "";
            while(!fs.inEmpty()){
                result+=fs.readChar();
            }
            
            assertTrue(result.equals(testdata));
           }catch (IOException e){
               System.out.println("error "+e.getLocalizedMessage());
           }
           
    }
//   @Test
//    public void HuffmanReturnsData(){
//        HuffmanCompression compressor = new HuffmanCompression(inputData);
//        assertTrue(compressor.getPackedData()!=null);
//    }
//    
////    @Test
////    public void HuffmanCodesBinary(){
////          HuffmanCompression compressor = new HuffmanCompression(inputData);
////          boolean ok = true;
////          for(int i = 0; i < compressor.getPackedData().length();i++){
////              char a = compressor.getPackedData().charAt(i);
////              if(a >= '0' && a<='1'){
////                  System.out.println("Not binary character: "+a);
////                  ok = false;
////                  break;
////              }    
////           
////          }
////             assertTrue(ok);
////    }
//    @Test
//    public void HuffManOccunces(){
//           HuffmanCompression compressor = new HuffmanCompression(inputData);
//           HashMap<Character,Integer> occurences = compressor.getOccurences();
//           assertTrue(occurences.get('a')==4&& occurences.get('b')==2 && occurences.get('c')==1);
//    }
//    
//    @Test
//    public void HuffmanConverts(){
//        String expectedResult = "1110101001";
//        HuffmanCompression compressor = new HuffmanCompression(inputData);
//        try{
//        assertTrue(expectedResult.equals(compressor.getPackedData()));
//        }catch(AssertionError e){
//            System.out.println("Unexpected result: "+compressor.getPackedData());
//            throw e;
//        }
//    }
}
