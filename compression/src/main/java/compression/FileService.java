/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compression;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.NoSuchElementException;
import java.io.IOException;

/**
 *
 * @author tuomomehtala
 */
public class FileService {
    private BufferedInputStream ioIn;
    private BufferedOutputStream ioOut;
    private int buffer;
    private int index;
    private int outBuffer;
    private int outIndex;  
    private boolean active;
    
    public FileService(BufferedInputStream in, BufferedOutputStream out){
        this.ioIn = in;
        this.ioOut = out;
        fillInBuffer();
        active = true;
        
    }
    public boolean inEmpty(){
        return index == -1;
    }
    private void fillInBuffer(){
        if(buffer == 0 && index > -1){
            try{
                buffer = ioIn.read();
                if(buffer == -1){
                    index = -1;
                    buffer= 0;        
                }else{
                index = 8;
                }
            }catch(Exception e){
                System.out.println("EOF: "+e.getLocalizedMessage());
                index = -1;
            }
        }
        
    }
    public boolean readBit(){ 
        if(!active) throw new IllegalStateException("No active input/output streams");
       
        if(inEmpty()) throw new NoSuchElementException("Eof");
        
        index --;
   
        
        boolean value = ((buffer >> index)& 1)==1;
        if(index == 0) fillInBuffer();
        return value;
        
      
    }
    public int readByteAsInt(){
        if(!active) throw new IllegalStateException("No active input/output streams");
        int data = 0;
        if(index == 8){
            data = buffer;
            index = 0;
            fillInBuffer();
            return data;
        }else{
            data = buffer;
            int localIndex = index;
            buffer = 0;
            index = 0;
            fillInBuffer();
            if(inEmpty()) throw new NoSuchElementException("Eof");
            data <<= (8-localIndex);
            index = localIndex;
            data |= (buffer >>> index);
            return data;
            
            
            
            
        }
      
        
    }
    private void writeOutput(){
        try{
            ioOut.write(outBuffer);
            outBuffer = 0;
            outIndex = 0;
            
        }catch(IOException e){
            System.out.println("Exception in writing to file: "+e.getLocalizedMessage() );
        }
    }
    public void writeBoolean(boolean bit){
        if(!active) throw new IllegalStateException("No active input/output streams");
        outBuffer <<= 1; //add one bit to right
        if(bit) outBuffer |= 1;
        outIndex++;
        if(outIndex == 7) writeOutput();
    }
    public void writeInt(int value){
        if(!active) throw new IllegalStateException("No active input/output streams");    
        if(outIndex == 0){
            outBuffer = value;
            outIndex = 7;
            writeOutput();
        }else{
            for(int i = 7; i<=0;i--){
                 if(value << ~i<0){
                     writeBoolean(true);
                 }else{
                     writeBoolean(false);
                 }
            }
        }
    }
    public void close(){
        if(!active) throw new IllegalStateException("No active input/output streams");
        this.active = false;
        if(outBuffer>0){
            outBuffer <<= (7-index); // pad last byte with zeroes to end.
            writeOutput();
        }
        try{
            ioIn.close();
            ioOut.close();
        }catch(IOException e){
            System.out.println("Exception in closing file inteface: "+e.getLocalizedMessage());
        }
    }  
    
    
}
