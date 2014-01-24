/*
 * Checksum.java
 *
 * Created on November 21, 2013, 8:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


/*Computing checksum code is inspired from the following online link:
         *stackoverflow.com/questions/4113890/how-to-calculate-the-internet-checksum-from-a-byte-in-java*/

package ARQ;

/**
 *
 * @author PRASHANT
 */
public class UDPChecksum {
         
     
    public static byte[] computeChecksum(byte[] payload){
                
        int len = payload.length;
        int index = 0;

        int sum = 0;
        int data;

        // Handle all pairs
        while (len > 1) {
            data = (((payload[index] << 8) & 0xFF00) | ((payload[index + 1]) & 0xFF));
            sum += data;
            // 1's complement carry bit correction in 16-bits (detecting sign extension)
            if ((sum & 0xFFFF0000) > 0) {
                sum = sum & 0xFFFF;
                sum += 1;
            }

            index += 2;
            len -= 2;
        }

        
        // Final 1's complement value correction to 16-bits
        sum = ~sum;
        sum = sum & 0xFFFF;
            
        byte[] checksum = new byte[]{
            (byte)(sum >>> 8),
            (byte)(sum)
        };
        
        return checksum;
    }
    
    public static boolean verifyChecksum(byte[] payload){
                
        int len = payload.length;
        int index = 0;

        int sum = 0;
        int data;

        // Handle all pairs
        while (len > 1) {
            data = (((payload[index] << 8) & 0xFF00) | ((payload[index + 1]) & 0xFF));
            sum += data;
            // 1's complement carry bit correction in 16-bits (detecting sign extension)
            if ((sum & 0xFFFF0000) > 0) {
                sum = sum & 0xFFFF;
                sum += 1;
            }

            index += 2;
            len -= 2;
        }

        
        // Final 1's complement value correction to 16-bits
        sum = ~sum;
        sum = sum & 0xFFFF;
        
        //Verifying Checksum
        if(sum == 0){
            return true;
        }else{
            return false;
        }
    }
    
}
