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
    private final int Max = 4096;
    private final int eof = 256;
    private FileService fs;
    
    public Lz(FileService fs){
    this.fs = fs;    
    }
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

        
        
        i++; //reserve 256 for eof
        input = fs.readFile();
        System.out.println("data read into string");
//        while(!fs.inEmpty()){
//            debugI++;
//            input+=fs.readChar();
//            if(debugI%1000000==0)System.out.println("million bytes read: "+(anotherDI++));
//        }
        System.out.println("input length: "+ input.length());
        int index = 0;
        while(input.length()>0){
            index ++;
            String sub = tst.longestPrefix(input,debug);
            if(sub.length()==0){
             if(input.length()<=1)break;
            }
            try{
           int keycode = (int)tst.get(sub);
            fs.writeInt(keycode, W);
            }catch (java.lang.NullPointerException e){
                debug = true;
                System.out.println("problem with : "+sub + " input len: "+ input.length());
           
            }
            //if(index%5000==0)System.out.println("5000 iterations");
            if(i<Max && sub.length()<input.length()){
            
                
                tst.put(input.substring(0, sub.length()+1), i);
                i++;
            }
            input = input.substring(sub.length());
        }
        fs.writeInt(eof, W);
        System.out.println("compression done, closing");
        fs.close();
        
    }
    public void extract(){
        String[] dict = new String[Max];
        int i,key,index;
        String value = "";
        for(i=0; i< 256;i++){
            dict[i]=""+(char)i;
        }
         // 256 = eof;
        dict[i]="";
        index = 0;
        key = fs.readInt(W);
        //System.out.println(key);
        if (key == eof) return; // empty string.
        if(fs.inEmpty()) return; // could be that one character inputs break...
        value = dict[key];
        //System.out.println("value"+ value + "key: "+key + "value first char code: "+(int)value.charAt(0));
        while(!fs.inEmpty()){
          // if(value.isBlank()) System.out.println("Error i: "+i + "key: "+key + "value in dict"+dict[key]);
           index++;
           
            for(int j = 0; j<value.length();j++){
                fs.writeByte(value.charAt(j));
            
            }
            key = fs.readInt(W);
           // System.out.println(key);
            if(key == eof) break;
           //if(index < 500) System.out.println(key + " dict: "+dict[key] + " |current value: "+value);
            String s = dict[key];
            //if(index < 200) System.out.println("s: "+s +" at index "+index+ "current i: "+i);
            if(i==key) s = value + value.charAt(0); //strange case
            if(i<(Max-1)){
                i++;
                try{
                dict[i]=value+s.charAt(0);
                }catch (NullPointerException e){
                    System.out.println("Error: "+ key + "dict at key: "+dict[key] + " i: "+i);
                }
               
            }
            value = s;
            
        }

        System.out.println("Extraction done, closing.");
        fs.close();
        
       
    }
    
}
