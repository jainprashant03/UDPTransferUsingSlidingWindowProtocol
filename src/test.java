import java.io.*;
import java.util.*;
 
public class test {
 
    public static void main(String a[]){
        try {
            String fileName = "C:\\Users\\PRASHANT\\IPProject\\RFCs\\DEL.txt";
        RandomAccessFile rFile = new RandomAccessFile(fileName, "r");
        ArrayList<byte[]> readData = new ArrayList<byte[]>();
        int index = 0;
        int read = 0;
        int offset = 0;
        //rFile.r;
            readData.add(new byte[1000]);
            //first I read data from file chunk by chunk
            while ( (read = rFile.read(readData.get(index), 0, 1000)) != -1) {
                if (read == 1000) {
                    readData.add(new byte[1000]);
                    read = 0;
                    index++;
                    offset += 1000;
                }
            }
        //byte[] content = str.getBytes();
        InputStream is = null;
        BufferedReader bfReader = null;
        String temp = null;
        
            for(int i = 0; i <=index; i++){
            is = new ByteArrayInputStream(readData.get(i));
            
            bfReader = new BufferedReader(new InputStreamReader(is));
            temp = null;
            while((temp = bfReader.readLine()) != null){
                System.out.println(temp);
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
         
    }
}