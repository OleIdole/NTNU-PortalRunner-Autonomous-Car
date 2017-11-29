/*
 * This code is for a group project called "Portal Runner".
 * The purpose is to get a car to navigate through checkpoints
 * and fetch an object before returning to start location.
 * 
 * The system consists of an Odroid connected with an Arduino Uno
 * that runs the car, while an external computer allow for navigation
 * control and monitoring.
 */
package External_PC_Com;

import ImgRecog.WebCamHandler;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * This class is meant for sending a continous stream om UDP DatagramPackages of
 * images to make an video stream in realtime for the reciver. 
 * 
 * @author Morten
 */
public class VideoStreamServer implements Runnable
{
    private String host = "192.168.1.5"; // Specify IP (localhost for testing on same computer)
//    private String host = "localhost"; // Specify IP (localhost for testing on same computer)
    private int portNumber = 50685;    // Specify port used for transmission
    private InetAddress ipAdress;
    private DatagramSocket videoStreamSocket;
    private WebCamHandler webCamHandler;
    
    public VideoStreamServer(WebCamHandler webCamHandler) {
        // Acquire IP address of the target client and set up a DatagramSocket.
        try {
            ipAdress = InetAddress.getByName(host);
            videoStreamSocket = new DatagramSocket();
            this.webCamHandler = webCamHandler;
        } catch (UnknownHostException | SocketException ex) {
            Logger.getLogger(VideoStreamServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
        @Override
    public void run() {
        Mat img = webCamHandler.getMat();
        sendMatAsBufImg(img);
        img.release();
    }
    
    /**
     * 
     * @param mat 
     */
    public void sendMatAsBufImg(Mat mat)
    {
        // Converts the frame to a BufferedImage before sending.
        try {
            BufferedImage image = convertMatToBufImg(mat);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            
            byte[] sendData = baos.toByteArray(); 
            
            // Setting up the DatagramPackege which will be sent.
            DatagramPacket videoPacket = new DatagramPacket(sendData, sendData.length,
                                                     this.ipAdress, this.portNumber);
            
            // Sending DatagramPacket 
            videoStreamSocket.send(videoPacket);
                
        } catch (IOException ex) {
            Logger.getLogger(VideoStreamServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Converts frame of type Mat to BufferedImage.
     * 
     * @param m, object of type Mat to be converted
     * @return BufferedImage.
     * @throws IOException 
     */
    private BufferedImage convertMatToBufImg(Mat m) throws IOException{
        MatOfByte matOfByte = new MatOfByte();
        
        Imgcodecs.imencode(".jpg", m, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        
        InputStream in = new ByteArrayInputStream(byteArray);
        return ImageIO.read(in);
    } 
    
}
