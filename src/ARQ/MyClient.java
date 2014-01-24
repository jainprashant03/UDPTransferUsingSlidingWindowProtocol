/*
 * MyClient.java
 *
 * Created on November 13, 2013, 9:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ARQ;
import java.io.*;
import java.util.*;
import java.net.*;
import java.text.*;
import java.nio.*;
/**
 *
 * @author PRASHANT
 */
public class MyClient implements Serializable{
    
    private int portNumber, windowSize, maxSegSize;
    private String hostName, fileName;
    
    /** Creates a new instance of MyClient */
    public MyClient(String sHostName,String sPortNumber, String sFileName, String N, String MSS) {
        //Initializing the parameters
        hostName = sHostName;
        fileName = sFileName;
        portNumber = Integer.parseInt(sPortNumber);
        windowSize = Integer.parseInt(N);
        maxSegSize = Integer.parseInt(MSS);
    }
    
    //bean methods
    public String getHostName(){
        return hostName;
    }
    protected void setHostName(String sHostName){
        hostName = sHostName;
    }
    public int getPortNumber(){
        return portNumber;
    }
    protected void setPortNumber(int sPortNumber){
        portNumber = sPortNumber;
    }
    public String getFileName(){
        return fileName;
    }
    protected void setFileName(String sFileName){
        fileName = sFileName;
    }
    public int getWindowSize(){
        return windowSize;
    }
    protected boolean setWindowSize(int N){
        if(N <= 0){
            return false;
        }else{
            windowSize = N;
            return true;
        }        
    }
    public int getMaxSegSize(){
        return maxSegSize;
    }
    protected boolean setMaxSegSize(int MSS){
        if(MSS <= 0){
            return false;
        }else{
            maxSegSize = MSS;
            return true;
        }       
    }
    
    public void send(){
        int headerSize = 8;
        ArrayList<byte[]> readData = new ArrayList<byte[]>();
        ArrayList<byte[]> sendData = new ArrayList<byte[]>();
        
        byte[] seqNum;
        byte[] checksum;        
        byte[] dataProto = new byte[2];
        byte[] data = new byte[maxSegSize];
        byte[] payload = new byte[maxSegSize + 8];
                
        dataProto[0] = (byte)(85);
        dataProto[1] = (byte)(85);
        
        int checkTemp = 0;
        int index = 0;
        int read = 0;
        int offset = 0;
        
        
        try{
            //Creating reader for the file
            RandomAccessFile rFile = new RandomAccessFile(fileName, "r");
                        
            //rFile.r;
            readData.add(new byte[maxSegSize]);
            //first I read data from file chunk by chunk
            while ( (read = rFile.read(readData.get(index), offset, maxSegSize)) != -1) {
                if (read == maxSegSize) {
                    readData.add(new byte[maxSegSize]);
                    read = 0;
                    index++;
                }
            }
            int x = 0;
            //creating payload
            for(int i = 0; i < readData.size(); i++){
                x = i + 1;
                //obtaining sequence number
                seqNum = new byte[]{
                    (byte) (x >>>24),
                    (byte) (x >>>16),
                    (byte) (x >>>8),
                    (byte) (x)};
                
                checkTemp = 0;
                checksum = new byte[]{
                    (byte)(checkTemp >>> 8),
                    (byte)(checkTemp)};
                
                //obtaining data
                data = readData.get(index);
                
                //creating temp payload
                System.arraycopy(seqNum, 0, payload, 0, 4);
                System.arraycopy(checksum, 0, payload, 4, 2);
                System.arraycopy(dataProto, 0, payload, 6, 2);
                System.arraycopy(data, 0, payload, 8, maxSegSize);
                
                //computing checksum
                checksum = UDPChecksum.computeChecksum(payload);
                
                //forming final payload
                System.arraycopy(checksum, 0, payload, 4, 2);
                
                //Storing payloads
                sendData.add(payload);
                
            }
                
                
                //Creating Socket connection with server
                DatagramSocket clientSocket = new DatagramSocket(50001, InetAddress.getLocalHost());
                clientSocket.setReuseAddress(true);
                byte[] udpSendData;
                byte[] udpReceiveData = new byte[8];
                    
                DatagramPacket sendPacket;
                DatagramPacket receivePacket;
                
                int count = 0;
                int j = 0;
                int expectedSeqNum = 1;
                int recSeqNum = 0;
                
                byte[] receivedSeqNum = new byte[4];
                ByteBuffer temp;
                
                while(count < sendData.size()){
                    for(j = 0; j < windowSize; j++){
                        udpSendData = sendData.get(count);
                        sendPacket = new DatagramPacket(udpSendData, udpSendData.length, InetAddress.getByName(hostName), portNumber);
                        clientSocket.send(sendPacket);
                        count++;
                    }
                    while(j>=0){
                        receivePacket = new DatagramPacket(udpReceiveData, udpReceiveData.length);
                        clientSocket.receive(receivePacket);
                        
                        udpReceiveData = receivePacket.getData();
                        
                        System.arraycopy(udpReceiveData, 0, receivedSeqNum, 0, 4);
                        
                        temp = ByteBuffer.wrap(receivedSeqNum); 
                        recSeqNum = temp.getInt(); 
                        
                        if(recSeqNum == expectedSeqNum){
                            udpSendData = sendData.get(count);
                            sendPacket = new DatagramPacket(udpSendData, udpSendData.length, InetAddress.getByName(hostName), portNumber);
                            clientSocket.send(sendPacket);  
                            count++;
                            expectedSeqNum++;
                        }
                        else{
                            break;
                        }
                        j--;
                    }
                }
                
                
                
                clientSocket.close();
            
            
        }catch(Exception e){
            System.out.println("Exception while sending data to server: "+ e);
        }
        
        
    }
    
    public static void main(String[] args){
        MyClient sender = new MyClient(args[0], args[1], args[2], args[3], args[4]);
        sender.send();
    }
}
