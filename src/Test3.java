/*
 * Test3.java
 *
 * Created on November 23, 2013, 7:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author PRASHANT
 */
public class Test3 {
    
    /** Creates a new instance of Test3 */
    public Test3() {
    }
    
    public static void main(String[] args){
        int j = 0;
        int i = 0;
        while(j < 10){
            j++;
            i++;
            while(i == j){
                
                if(i > 5){
                    System.out.println("continue");
                    continue;
                    
                }
                System.out.println("innerloop");
            }
            System.out.println("outerloop");
        }
                
    }
    
}
