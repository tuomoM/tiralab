/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 
 
import static org.junit.Assert.*;

import java.util.HashMap;
import org.junit.Test;
import compression.FileService;
import compression.Lz;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 *
 * @author tuomomehtala
 */
public class LZWTest {
    String inputData = "aaabbca";
    String testdata = "And the LORD said unto Satan, Whence comest thou? Then Satan answered the LORD, and said, From going to and fro in the earth, and from walking up and down in it.";
    FileService fs;
    
    
    
        
    @Test
    public void SimpleLZWCompresses(){
           try{
            FileOutputStream fio = new FileOutputStream("testfilelz.t");
            fio.write(0);
            fio.flush();
            fio.close();
         }catch(Exception e){
             
         }
           try{
           fs = new FileService("testfilelz.t","testfilelz.out");
           
           
           for(int i = 0; i<inputData.length();i++){
               fs.writeByte(inputData.charAt(i));
           }
           fs.close();
           fs = new FileService("testfilelz.out","testfilelz.lz");
           Lz lz = new Lz(fs);
           lz.compress();
           
           fs = new FileService("testfilelz.lz","testfilelz.out");
//           boolean[] ok = new boolean[10];
//           //1110101001
//           boolean[] expected = {true,true,true,false,true,false,true,false,false,true};
//           
//           for(int i = 0; i< 10; i++){
//               ok[i]=fs.readBit();
//               System.out.println(ok[i]);
//           }
//         
            lz = new Lz(fs);
            lz.extract();
            
            fs = new FileService("testfilelz.out","testfilelz.lz");
            String result = "";
            while(!fs.inEmpty()){
                result+=fs.readChar();
            }
               System.out.println("inputData: "+inputData);
            for(int i = 0; i< result.length();i++){
                System.out.println("result: " +result.charAt(i)+" target : "+inputData.charAt(i));
            }
            assertTrue(result.equals(inputData));
           }catch (IOException e){
               System.out.println("error "+e.getLocalizedMessage());
           }
           
    }
      @Test
    public void complexLZWCompresses(){
           try{
            FileOutputStream fio = new FileOutputStream("testfilelz.t");
            fio.write(0);
            fio.flush();
            fio.close();
         }catch(Exception e){
             
         }
           try{
           fs = new FileService("testfilelz.t","testfilelz.out");
           
           
           for(int i = 0; i<testdata.length();i++){
               fs.writeByte(testdata.charAt(i));
           }
           fs.close();
           fs = new FileService("testfilelz.out","testfilelz.lz");
           Lz lz = new Lz(fs);
           lz.compress();
           
           fs = new FileService("testfilelz.lz","testfilelz.out");
//           boolean[] ok = new boolean[10];
//           //1110101001
//           boolean[] expected = {true,true,true,false,true,false,true,false,false,true};
//           
//           for(int i = 0; i< 10; i++){
//               ok[i]=fs.readBit();
//               System.out.println(ok[i]);
//           }
//         
            lz = new Lz(fs);
            lz.extract();
            
            fs = new FileService("testfilelz.out","testfilelz.lz");
            String result = "";
            while(!fs.inEmpty()){
                result+=fs.readChar();
            }
               System.out.println("inputData: "+testdata);
            for(int i = 0; i< result.length();i++){
                System.out.println("result: " +result.charAt(i)+" target : "+testdata.charAt(i));
            }
            assertTrue(result.equals(testdata));
           }catch (IOException e){
               System.out.println("error "+e.getLocalizedMessage());
           }
           
    }
    
}
