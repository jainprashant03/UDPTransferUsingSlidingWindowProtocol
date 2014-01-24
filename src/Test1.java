/*
 * Test1.java
 *
 * Created on November 21, 2013, 8:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author PRASHANT
 */
public class Test1 {
    
    /** Creates a new instance of Test1 */
    public Test1() {
    }
    public static void main(String args[]){
        int i = 27;
        //byte b = ((Integer)i).toBinaryString();
        byte b = ((Integer)i).byteValue();
        
        byte [] sBPort = new byte[]{
            (byte)(i >>> 8),
            (byte)(i)};
        
        byte by = (((Integer)(5 ^ 4)).byteValue());
        
        System.out.println(Integer.toBinaryString(i));
        System.out.println(String.format("%02X", by));
        
        byte x = (byte)127;
        byte y = (byte)127;
        
        int a = x + y;
        
        byte [] t = new byte[]{
            (byte)(a >>> 8),
            (byte)(a)};
        
        
        System.out.println(a);
    }
}
