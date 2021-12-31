/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compression;
import java.lang.StringBuilder;
/**
 * Implementation of the LZW compression.
 * @author tuomomehtala
 */
public class Lz {
    
    private final int W = 12;
    private final int Max = 4096;
    private final int eof = 256;
    private FileService fs;
    
    public Lz(FileService fs){
    this.fs = fs;    
    }
    /**
     * Starts the compression using fileService in and out
     */
    public void compress(){
        TST2 tst = new TST2<Integer>();
        String input = "";
        int  i;
        int debugI = 0;
        int anotherDI = 0;
        boolean debug = false;
        //initialize dictionary
        System.out.println("Compressing");
        tst.put(""+(char)135, 135);
        for(i=0;i<256;i++){
            if(i==135)continue;
            tst.put(""+(char)(i), i);
            
        }

        int prevKey = 0;
        
        i++; //reserve 256 for eof
        input = fs.readFile();
        int last = input.length()-1;

        int index = 0;
        while(index<=last){
            
            String sub = tst.longestPrefix(input,debug,index);
            int subLength = sub.length();
            if(subLength==0){
             //
                System.out.println("should not go here");
            }
            try{
           int keycode = (int)tst.get(sub);
           
           prevKey = keycode;
            fs.writeInt(keycode, W);
            }catch (java.lang.NullPointerException e){
                debug = true;
                System.out.println("problem with : "+sub + " input index: "+index);
           
            }
            if(i<Max && subLength<(last-index-1)){ // this will be done only once / dictionary entry
            //   System.out.println("creating entry; "+sub + " with indexes start:"+index);
                tst.put(input.substring(index, index+sub.length()+1), i);
                i++;
            }
            index = index+subLength;
            //input = input.substring(sub.length());
        }
        fs.writeInt(eof, W);
        System.out.println("compression done, closing");
        fs.close();
        
    }
    /**
     * Starts the extraction
     */
    public void extract(){
        String[] dict = new String[Max];
        int i,key,index;
        //boolean debug = false;
       // int debugI = 0;
        String value = "";
        for(i=0; i< 256;i++){
            dict[i]=""+(char)i;
        }
         // 256 = eof;
        dict[i]="";
        index = 0;
        int prevKey = 0;
        key = fs.readInt(W);
    
        if (key == eof) return; // empty string.
        if(fs.inEmpty()) return; // could be that one character inputs break...
        value = dict[key];

        while(!fs.inEmpty()){
           index++;
            for(int j = 0; j<value.length();j++){
                fs.writeByte(value.charAt(j));    
            }
            prevKey = key;
            key = fs.readInt(W);
            if(key == eof) break;
            String s = dict[key];
    
            if(i==(key-1)){ // for some reason we are referencing to same key we are building
                s = value + value.charAt(0);
            }
            if(i<(Max-1)){
                i++;
                try{
                dict[i]=value+s.charAt(0);
                }catch (NullPointerException e){
                    System.out.println("Error: "+ key + "dict at key: "+dict[key] + " i: "+i);
                }
               
            }
            value = s;
            //debugI ++;
            
        }

        System.out.println("Extraction done, closing.");
        fs.close();
        
       
    }
    
}
