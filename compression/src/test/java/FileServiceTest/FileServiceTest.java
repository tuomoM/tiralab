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
         fs.writeBoolean(false);
         fs.close();
         }catch(Exception e){
             System.out.println("Error"+e.getLocalizedMessage());
         }

         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.te")));
      
         
         assertTrue(!fs.readBit());
         fs.close();
         }catch(FileNotFoundException e){
             System.out.println("Error 2");
             
         }
     }
     
     @Test
     public void writeByte(){
             try{
            FileOutputStream fio = new FileOutputStream("testfile.t");
            fio.write(0);
            fio.flush();
            fio.close();
         }catch(Exception e){
             
         }
         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.t")));
         fs.writeByte(101);
         fs.close();
         }catch(Exception e){
             System.out.println("Error"+e.getLocalizedMessage());
         }

         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.te")));
      
         
         assertTrue(fs.readByteAsInt()==101);
         fs.close();
         }catch(FileNotFoundException e){
             System.out.println("Error 2");
             
         }
         
         
         
         
         
     }
        @Test
        public void writeInteger(){
                       try{
            FileOutputStream fio = new FileOutputStream("testfile.t");
            fio.write(0);
            fio.flush();
            fio.close();
         }catch(Exception e){
             
         }
         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.t")));
         fs.writeInt(134111);
        
         fs.close();
         }catch(Exception e){
             System.out.println("Error"+e.getLocalizedMessage());
         }

         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.te")));
      
         
         assertTrue(fs.readInt()==134111);
         fs.close();
         }catch(FileNotFoundException e){
             System.out.println("Error 2");
             
         } 
        }
        @Test
        public void writeShortInt(){
                           try{
            FileOutputStream fio = new FileOutputStream("testfile.t");
            fio.write(0);
            fio.flush();
            fio.close();
         }catch(Exception e){
             
         }
         try{
         fs = new FileService("testfile.t", "testfile.t");
         fs.writeInt(130,12);
        
         fs.close();
         }catch(Exception e){
             System.out.println("Error"+e.getLocalizedMessage());
         }

         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.te")));
      
         
         assertTrue(fs.readInt(12)==130);
         fs.close();
         }catch(FileNotFoundException e){
             System.out.println("Error 2");
             
         }
        }
        @Test
        public void writeChar(){
                           try{
            FileOutputStream fio = new FileOutputStream("testfile.t");
            fio.write(0);
            fio.flush();
            fio.close();
         }catch(Exception e){
             
         }
         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.t")));
         fs.writeByte('a');
         fs.close();
         }catch(Exception e){
             System.out.println("Error"+e.getLocalizedMessage());
         }

         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.te")));
      
         
         assertTrue(fs.readChar() == 'a');
         fs.close();
         }catch(FileNotFoundException e){
             System.out.println("Error 2");
             
         }
        
        }
        @Test
        public void testBitByteSeq(){
         try{
            FileOutputStream fio = new FileOutputStream("testfile.t");
            fio.write(0);
            fio.flush();
            fio.close();
         }catch(Exception e){
             
         }
         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.t")));
         fs.writeBoolean(false);
         fs.writeByte('a');
         fs.writeBoolean(true);
         fs.writeByte('b');
         fs.close();
         }catch(Exception e){
             System.out.println("Error"+e.getLocalizedMessage());
         }

         try{
         fs = new FileService(new BufferedInputStream(new FileInputStream("testfile.t")), new BufferedOutputStream(new FileOutputStream("testfile.te")));
      
         
         assertTrue(!fs.readBit()&&fs.readChar()=='a');
         fs.close();
         }catch(FileNotFoundException e){
             System.out.println("Error 2");
             
         }            
            
        }
        
    
}
