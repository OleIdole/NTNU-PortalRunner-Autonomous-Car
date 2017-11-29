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

import static org.opencv.core.Core.inRange;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * This class is ment for recognizing the part of a picture with a set range of
 * HSV values.
 * 
 * @author Ole Martin Hanstveit <https://github.com/OleIdole/>
 */
public class ColorDetection {
    
    // Color parameters:
        // Hue (refers to which pure color it resembles):
        double hueLow =  1; //75;//105;
        double hueHigh = 0; //130;//123;

        // Saturation (describes how white the color is. Pure red is fully saturated, with saturation value of 1, where 1 = 255 here):
        double satLow = 1; //36; //67;
        double satHigh = 0; //255;

        // Value (lightness of a color, describes how dark the color is. A value of 0 is black, with increasing lightness moving away from black):
        double valLow = 1; //55;
        double valHigh = 0; //255;
    
        
    // Detect specified color values of a mat
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Mat detectColor(Mat mat)
    {
        Mat imgHSV = new Mat(); // Mat used for HSV color convertion
        // Convert mat from BGR to HSV for better detection accuracy of colors
        Imgproc.cvtColor(mat, imgHSV, Imgproc.COLOR_BGR2HSV); //Convert the captured frame from BGR to HSV
        
        Mat imgThresholded = new Mat();
        // Thresholds the image, finding everything in the picture that is in range of the values.
        // This paints the new Mat imgThresholded white where correct color is detected and black everywhere else.
        inRange(imgHSV, new Scalar(hueLow, satLow, valLow), new Scalar(hueHigh, satHigh, valHigh), imgThresholded);
        
        // Morphological opening (remove small objects from the foreground)
        Imgproc.erode(imgThresholded, imgThresholded, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));
        Imgproc.dilate(imgThresholded, imgThresholded, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));
        
        // Morphological closing (fill small holes in the foreground)
        Imgproc.dilate(imgThresholded, imgThresholded, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));
        Imgproc.erode(imgThresholded, imgThresholded, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5)));
        
        //Imgproc.Canny(imgThresholded, imgThresholded, 0, 255); // Finds edges of objects
        
        // Release the Mat to avoid filling the memory with old data
        imgHSV.release();
        
        return imgThresholded;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    // Setters for HSV values:
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setHueLow(double hueLow) {
        this.hueLow = hueLow;
    }

    public void setHueHigh(double hueHigh) {
        this.hueHigh = hueHigh;
    }

    public void setSatLow(double satLow) {
        this.satLow = satLow;
    }

    public void setSatHigh(double satHigh) {
        this.satHigh = satHigh;
    }

    public void setValLow(double valLow) {
        this.valLow = valLow;
    }

    public void setValHigh(double valHigh) {
        this.valHigh = valHigh;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
