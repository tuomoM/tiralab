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
import java.nio.ByteBuffer;
import java.lang.Integer;

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
        this.index = 0;
        this.buffer = 0;
        this.active = true;
        fillInBuffer();
       
        
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
            ioOut.flush();
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
        if(outIndex == 8) writeOutput();
    }
    public void writeByte(int value){
        if(!active) throw new IllegalStateException("No active input/output streams");    
        if(outIndex == 0){
            outBuffer = value;
            outIndex = 8;
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
    public void writeInt(int value){
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();
        for(int i = 0; i<4;i++){
            writeByte(b[i]);
        }
        
    }
    /**
     * Reads 4 bytes from disk and returns it as integer
     * @return integer value of 4 next bytes
     */
    public int readInt(){
        byte[] b = new byte[4];
        for(int i = 0; i<4;i++){
            int x = readByteAsInt();
            b[i] =(byte) (x & 0xff);
        }
        return ByteBuffer.wrap(b).getInt();
        
    }
    public void close(){
        if(!active) throw new IllegalStateException("No active input/output streams");
        this.active = false;
        if(outIndex>0){
            outBuffer <<= (8-outIndex); // pad last byte with zeroes to end.
            writeOutput();
        }
        try{
            ioOut.flush();
            ioIn.close();
            ioOut.close();
        }catch(IOException e){
            System.out.println("Exception in closing file inteface: "+e.getLocalizedMessage());
        }
    }  
    
    
}
