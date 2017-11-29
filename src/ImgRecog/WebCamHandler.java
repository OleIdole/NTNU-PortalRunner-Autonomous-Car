/*
 * This code is for a group project called "Portal Runner".
 * The purpose is to get a car to navigate through checkpoints
 * and fetch an object before returning to start location.
 * 
 * The system consists of an Odroid connected with an Arduino Uno
 * that runs the car, while an external computer allow for navigation
 * control and monitoring.
 */
package ImgRecog;
  
import java.awt.image.BufferedImage;  
import org.opencv.core.Mat;  
import org.opencv.videoio.VideoCapture;  
import org.opencv.videoio.Videoio;  
  

/**
 * This class is ment for handling the webcam.
 * 
 * @author Ole Martin Hanstveit <https://github.com/OleIdole/>
 */

public class WebCamHandler extends Thread{  
 
    private VideoCapture camera = null;   
    
    // Initializing the camera
    public WebCamHandler() {  
        System.load("/home/odroid/Downloads/opencv-package-xu4/libopencv_java310.so"); // Load the OpenCV library
//        System.load("C:/Users/Olema/Downloads/opencv/build/java/x64/opencv_java310.dll"); // Load the OpenCV library
        
        camera = new VideoCapture(0); // Start capturing
        
        // Specify image resolution
        camera.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 320); // 640
        camera.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 240); // 480
        
        // Verify that camera initialized successfully
        if(!camera.isOpened()){    
            throw new IllegalStateException("Cannot open camera");  
        }    
    }
      
    // Reads current image and return its BufferedImage
    // Synchronized to prevent overwriting the image while getting the BufferedImage
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public synchronized BufferedImage getBufferedImage() {  
        Mat frame = new Mat();    
        if (camera.read(frame)){    
            return convertMatToBufferedImage(frame);
        }  
        return null;  
    }  
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    // Reads current image and return it as a Mat
    // Synchronized to prevent another method call that potentially could
    // overwrite the Mat during another operation
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public synchronized Mat getMat()
    {
        Mat frame = new Mat();    
        if (camera.read(frame)){  
            return frame;  
        }  
        return null;  
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

    // Converts a Mat into a BufferedImage
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static BufferedImage convertMatToBufferedImage(Mat mat) {    
        byte[] data = new byte[mat.width() * mat.height() * (int)mat.elemSize()];    
        int type;    
        mat.get(0, 0, data);    
        switch (mat.channels()) {      
            case 1:      
                type = BufferedImage.TYPE_BYTE_GRAY;      
            break;      
            case 3:      
                type = BufferedImage.TYPE_3BYTE_BGR;      
            byte b;      
                for(int i=0; i<data.length; i=i+3) {      
                    b = data[i];      
                    data[i] = data[i+2];      
                    data[i+2] = b;      
                }      
            break;      
            default:      
                throw new IllegalStateException("Unsupported number of channels");    
        }      
        BufferedImage out = new BufferedImage(mat.width(), mat.height(), type);    
        out.getRaster().setDataElements(0, 0, mat.width(), mat.height(), data);   
        return out;    
    }    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

    // Debug function for checking the frames per second of the camera
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public void checkFPS()
//    {
//        Mat mat = new Mat();
//        long time = System.currentTimeMillis();
//        for(int i = 0; i<120 ; i++)
//        {
//            camera.read(mat);
//        }
//        float timeSpent = System.currentTimeMillis() - time;
//        float fps = 120/(timeSpent/1000);
//        System.out.println(fps);
//    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}