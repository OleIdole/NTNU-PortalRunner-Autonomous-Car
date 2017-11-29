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

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 * @author Ole Martin Hanstveit <https://github.com/OleIdole/>
 */
public class PixelScanner {

    int leftAmount;
    int rightAmount;
    
    
    // Scan left and right half of a Mat to find the amount of white pixels on each side
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void scanLeftandRight(Mat mat)
    {
        // Create a rectangle to use for each half of the Mat
        Rect left = new Rect(0, 0, 160, 240);
        Rect right = new Rect(160, 0, 160, 240);
        
        // Split the image into a left and right half
        Mat leftHalf = new Mat(mat, left);
        Mat rightHalf = new Mat(mat, right);

        // Count white pixels in each half of the image
        leftAmount = Core.countNonZero(leftHalf);
        rightAmount = Core.countNonZero(rightHalf);
        
        // Release the Mat to avoid filling the memory with old data
        leftHalf.release();
        rightHalf.release();
        mat.release();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    // Getters for pixel amounts:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int getLeftAmount() {
        return leftAmount;
    }

    public int getRightAmount() {
        return rightAmount;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
