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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.lang.StringBuilder;
/**
 * Class to handle file operations for compression.
 * Provides methods for writing and reading binary data
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
    private byte[] outBufferall;
    private int inCounter,outCounter;
    
    /**
     * Obsolote constructor 
     * @param in
     * @param out 
     */
    public FileService(BufferedInputStream in, BufferedOutputStream out){
        this.ioIn = in;
        this.ioOut = out;
        this.index = 0;
        this.buffer = 0;
        this.outBuffer = 0;
        this.active = true;
        fillInBuffer();
       
        
    }
    /**
     * Constructor
     * @param inputFile name of the file to be read 
     * @param outputFile name of the file to be written
     * @throws IOException 
     */
    public FileService(String inputFile,String outputFile) throws IOException{
           int k  = 64;
               ioIn = new BufferedInputStream(new FileInputStream(inputFile),(1024*k));
           
              ioOut = new BufferedOutputStream(new FileOutputStream(outputFile),(1024*k));
               this.index = 0;
        this.buffer = 0;
        this.outBuffer = 0;
        this.active = true;
        fillInBuffer();
       
    }
    /**
     * 
     * @return value true if file is empty 
     */
    public boolean inEmpty(){
        return index == -1;
    }
    /**
     * Internal method for filling the 8 bit buffer
     */
    private void fillInBuffer(){
        if(buffer == 0 && index > -1){
            try{
                
                this.buffer = ioIn.read();
             //   System.out.println("read byte:"+buffer);
                if(buffer == -1){
                    index = -1;
                    buffer= 0;        
                }else{
                index = 8;
                inCounter++;
                }
            }catch(Exception e){
                System.out.println("EOF: "+e.getLocalizedMessage());
                index = -1;
            }
        }
        
    }
    /**
     * Reads one bit from file
     * @return return true for 1 and false for 0
     */
    public boolean readBit(){ 
        if(!active) throw new IllegalStateException("No active input/output streams");
       
        if(inEmpty()) throw new NoSuchElementException("Eof at: "+inCounter);
        
        index --;
   
        
        boolean value = ((buffer >> index)& 1)==1;
        if(index <= 0) {
            index = 0;
            buffer = 0;
            fillInBuffer();
        }
        return value;
        
      
    }
    /**
     * Reads the whole file into a String with transformation of 1 byte = 1 character
     * @return String value of all bytes
     */
    public String readFile(){
        String data = "";
        
        try{
        byte[] bytes = ioIn.readAllBytes();
        char[] table = new char[bytes.length];
        StringBuilder strB = new StringBuilder(bytes.length);
           
            //System.out.println("all bytes read");
        strB.append((char)(buffer & 0xff));    
        for(int i = 0; i < bytes.length;i++){
           
           strB.append((char)(bytes[i] & 0xff));
  
        }
        data = strB.toString();

        }catch(IOException e){
            System.out.println("Error reading file "+e.getLocalizedMessage());
        }
        index = 8;
        buffer = 0;
        fillInBuffer();
        return data;
    }
    /**
     * Read one byte char from file
     * @return byte as char
     */
    public char readChar(){
       
        if(!active) throw new IllegalStateException("No active input/output streams");
        int data = 0;
      
        if(index == 8){
      
            data = buffer;
            index = 0;
            buffer = 0;
            fillInBuffer();
          //  System.out.println("returning char: "+data);
            return (char)(data & 0xff);
        }else{
            data = buffer;
            int localIndex = index;
            buffer = 0;
            index = 0;
            fillInBuffer();
            if(inEmpty()) throw new NoSuchElementException("Eof");
          //  System.out.println("moving : " +(8-localIndex)+" bits");
            data <<= (8-localIndex);
            index = localIndex;
            data |= (buffer >>> index);
           //   System.out.println("returning char: "+data);
            return (char)(data & 0xff) ; // takes 256 (the lowest byte)
            
            
            
            
        }
        
        
        
    }
    /**
     * read one byte as int 
     * @return byte value as int.
     */
    public int readByteAsInt(){
        if(!active) throw new IllegalStateException("No active input/output streams");
        int data = 0;
      
        if(index == 8){
      
            data = buffer;
            index = 0;
            buffer = 0;
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
            return data%256;
            
            
            
            
        }
      
        
    }
    public int getInCounter(){
        return this.inCounter;
    }
    public int getOutCounter(){
        return this.outCounter;
    }
    /**
     * Internal method to empty program out buffer
     */
    private void writeOutput(){
        try{
            ioOut.write(outBuffer);
        
          //  System.out.println("wrote byte: "+outBuffer);
            outBuffer = 0;
            outIndex = 0;
            outCounter++;
            
        }catch(IOException e){
            System.out.println("Exception in writing to file: "+e.getLocalizedMessage() );
        }
    }
    /**
     * Write one bit to file
     * @param bit true for 1 and false for 0
     */
    public void writeBoolean(boolean bit){
        if(!active) throw new IllegalStateException("No active input/output streams");
        outBuffer <<= 1; //add one bit to right
        if(bit) outBuffer |= 1;
         //System.out.println("wrote out: "+bit);
        outIndex++;
        if(outIndex >= 8) writeOutput();
    }
    /**
     * Write byte size int to file
     * @param value to be written
     */
    public void writeByte(int value){
        if(!active) throw new IllegalStateException("No active input/output streams");    
        if(outIndex == 0){
           // System.out.println("writing a full byte");
            outBuffer = value;
            outIndex = 8;
            writeOutput();
        }else{
          //  System.out.println("writing byte bit by bit");
              for (int i = 0; i < 8; i++) {
            boolean bit = ((value >>> (8 - i - 1)) & 1) == 1;
            writeBoolean(bit);
                 
        }
//            for(int i = 7; i<=0;i--){
//                System.out.println("adding individual bits: "+i);
//                 if(value << ~i<0){
//                     writeBoolean(true);
//                     System.out.println("true");
//                 }else{
//                     writeBoolean(false);
//                     System.out.println("false");
//                 }
//            }
        }
    }
    /**
     * Writes integer with the predefined length as bits out.
     * Will throw exception if the length is larger than max int length 32
     * @param value the value to be written
     * @param length the amount of bits to be used
     */
    public void writeInt(int value, int length){
        if(length == 32 ){
            writeInt(value);
        }
        if(length < 1 || length > 32) throw new IllegalStateException("illegal int length");
        for(int i = 0; i<length;i++){
               boolean bit = ((value >>> (length - i - 1)) & 1) == 1;
               writeBoolean(bit);
        }
    }
    /**
     * Reads a specific length integer from file
     * @param length length in bits.
     * @return 
     */
    public int readInt(int length){
        if(length == 32) return readInt();
        if(length < 1 || length > 32) throw new IllegalStateException("illegal int length");
        int result = 0;
        for(int i = 0; i< length;i++){
            boolean bit = readBit();
            result <<= 1;
            if(bit){
                result |= 1;
            }
        }
        
        
        return result;
    }
    /**
     * Writes integer to file (4 bytes)
     * @param value to be written
     */
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
    /**
     * Writes the buffer out and padds it with 0s before closing the file.
     */
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
