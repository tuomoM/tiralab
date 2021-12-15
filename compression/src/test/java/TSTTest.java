/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tuomomehtala
 */
import org.junit.Test;
import compression.TST2;
import static org.junit.Assert.*;
public class TSTTest {
    @Test
    public void singleCharsAdd(){
        TST2 tst = new TST2<Integer>();
        tst.put(""+'a', 1);
        tst.put(""+'b', 2);
        assertTrue((int)tst.get(""+'a')==1);
    }
    @Test
    public void findPrefix(){
          TST2 tst = new TST2<Integer>();
          tst.put(""+'g', 1);
          tst.put(""+'a', 2);
          tst.put(""+'o', 3);
          tst.put("omega", 4);
          assertTrue(tst.longestPrefix("omni", false).equals("om"));
    }
    @Test
    public void findIdofString(){
           TST2 tst = new TST2<Integer>();
          tst.put(""+'g', 1);
          tst.put(""+'a', 2);
          tst.put(""+'o', 3);
          tst.put("omega", 7);
          tst.put("alpha", 8);
          assertTrue((int)tst.get("omega")==7);
    }
    @Test
    public void searchForNonExistent(){
            TST2 tst = new TST2<Integer>();
          tst.put(""+'g', 1);
          tst.put(""+'a', 2);
          tst.put(""+'o', 3);
          tst.put("omega", 7);
          tst.put("alpha", 8);
          tst.put(null, 9); // also lets try to add empty
          assertTrue(tst.get("terror")==null);
          
    }
}
