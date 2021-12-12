/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compression;

/**
 * Implementation of the LZW compression.
 * @author tuomomehtala
 */
public class Lz {
    
    private final int W = 12;
    private final int Max = 4095;
    private FileService fs;
    
    public Lz(FileService fs){
    this.fs = fs;    
    }
    public void compress(){
        TST2 tst = new TST2<Integer>();
        String input = "";
        int  i;
        //initialize dictionary
        System.out.println("Compressing");
        tst.put(""+(char)135, 135);
        for(i=0;i<256;i++){
            if(i==135)continue;
            tst.put(""+(char)(i), i);
            
        }
     
        while(!fs.inEmpty()){
            input+=fs.readChar();
        }
        while(input.length()>0){
            String sub = tst.longestPrefix(input);
            if(sub.length()==0){
             if(input.length()==1)break;
            }
            fs.writeInt((int)tst.get(sub), W);
            if(i<Max && sub.length()<input.length()){
                i++;
                tst.put(input.substring(0, sub.length()+1), i);
            }
            input = input.substring(sub.length());
        }
        System.out.println("compression done, closing");
        fs.close();
        
    }
    public void extract(){
        String[] dict = new String[Max];
        int i,key;
        String value;
        for(i=0; i< 256;i++){
            dict[i]=""+(char)i;
        }
        key = fs.readInt(W);
        if(fs.inEmpty()) return; // could be that one character inputs break...
        
        while(!fs.inEmpty()){
            value = dict[key];
            for(int j = 0; j<value.length();j++){
                fs.writeByte(value.charAt(j));
            }
            key = fs.readInt(W);
            if(i<Max){
                i++;
                dict[i]=value+dict[key];
            }
            
        }
        fs.close();
        
       
    }
    
}
