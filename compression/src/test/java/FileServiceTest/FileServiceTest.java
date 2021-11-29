/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileServiceTest;
import org.junit.Test;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import compression.FileService;
import java.io.FileNotFoundException;
import static org.junit.Assert.*;
/**
 *
 * @author tuomomehtala
 */
public class FileServiceTest {
    
    FileService fs;
    
    
    
     @Test
     public void writeBitTest(){
         try{
            FileOutputStream fio = new FileOutputStream("testfile.t");
            fio.write(0);
            fio.flush();
            fio.close();
         }catch(Exception e){
             
         }
         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.t")));
         fs.writeBoolean(true);
         fs.close();
         }catch(Exception e){
             System.out.println("Error"+e.getLocalizedMessage());
         }

         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.t")));
      
         
         assertTrue(fs.readBit());
         fs.close();
         }catch(FileNotFoundException e){
             System.out.println("Error 2");
             
         }
     }
    
}
