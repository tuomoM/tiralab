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

public class HuffmanTest {
    String inputData = "aaabbca";
   
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
