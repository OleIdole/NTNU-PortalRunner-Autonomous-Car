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

import org.opencv.core.Mat;
import ArduinoCom.SendSerial;

/**
 * This class handles the image processing.
 * 
 * @author Ole Martin Hanstveit <https://github.com/OleIdole/>
 */
public class ImageProcessor implements Runnable {

    private final WebCamHandler webCamHandler;
    private final ColorDetection colorDetection;
    private final MovementHandler movement;

    public ImageProcessor(WebCamHandler webCamHandler, ColorDetection colorDetection, SendSerial arduinoSender) {
         this.webCamHandler = webCamHandler;
         this.colorDetection = colorDetection;
         this.movement = new MovementHandler(arduinoSender);
    }


    @Override
    public void run() {
            Mat camMat = webCamHandler.getMat(); // Fetch Mat of the webcam image
        
        // Color recognition
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Mat imgThresholded = colorDetection.detectColor(camMat); // Create a black/white where the white is where the color is detected.
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            
        // Pixel scanning left half vs right half of image
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            PixelScanner pixelScanner = new PixelScanner();
            pixelScanner.scanLeftandRight(imgThresholded);
        
            int leftAmount = pixelScanner.getLeftAmount();
            int rightAmount = pixelScanner.getRightAmount();
//            System.out.println("Left: " + leftAmount + ", Right: " + rightAmount + ", Total: " + (leftAmount+rightAmount));
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        
        // Movement proccessing
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            movement.processInput(leftAmount, rightAmount);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        // Free memory from old data
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            camMat.release();
            imgThresholded.release();
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}