/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tuomomehtala
 */
import static org.junit.Assert.*;
import compression.HuffmanCompression;
import compression.HuffmanNode;
import java.util.HashMap;
import org.junit.Test;
public class HuffmanTest {
    String inputData = "aaabbca";
   
   @Test
    public void HuffmanReturnsData(){
        HuffmanCompression compressor = new HuffmanCompression(inputData);
        assertTrue(compressor.getPackedData()!=null);
    }
    
    @Test
    public void HuffmanCodesBinary(){
          HuffmanCompression compressor = new HuffmanCompression(inputData);
          boolean fail = false;
          for(int i = 0; i < compressor.getPackedData().length();i++){
              char a = compressor.getPackedData().charAt(i);
              if(a!=0 || a!=1){
                  fail = true;
                  break;
              }    
           
          }
             assertFalse(fail);
    }
    @Test
    public void HuffManOccunces(){
           HuffmanCompression compressor = new HuffmanCompression(inputData);
           HashMap<Character,Integer> occurences = compressor.getOccurences();
           assertTrue(occurences.get('a')==4);
    }
    
}
