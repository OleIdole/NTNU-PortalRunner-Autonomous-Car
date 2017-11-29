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

import ArduinoCom.StringSeparator;
import ImgRecog.ColorDetection;

/**
 * 
 * 
 * 
 * @author Ole Martin Hanstveit <https://github.com/OleIdole/>
 */
public class InputHandlerTCP {
    private ColorDetection colorDetection;

    public InputHandlerTCP(ColorDetection colorDetection) {
        this.colorDetection = colorDetection;
    }
    
    /**
     * Process the message by splitting it into words, check description
     * of the words and handle them.
     * 
     * @param message A message composed by a start symbol, end symbol and
     * search parameters for the car. Words are separated by a "/".
     */
    public void processMessage(String message)
    {
        StringSeparator stringSeparator = new StringSeparator();
        String[] messageWords = stringSeparator.getStringArray(message);
        if (messageWords[0].equals("Stop"))
        {
            setStop();
        }
        else
        {
            if (messageWords[0].equals("Blue"))
            {
                setBlue();
            }
            else if (messageWords[0].equals("Orange"))
            {
                setOrange();
            }
            else if (messageWords[0].equals("Red"))
            {
                setRed();
            }
            else if (messageWords[0].equals("Green"))
            {
                setGreen();
            }
            else if (messageWords[0].equals("Yellow"))
            {
                setYellow();
            }
//            // Iterate through the message for color parameter to portals 1-3
//            for(int i = 0; i<3; i++)
//            {
//                System.out.println(messageWords[i]);
//            }
        }
    }
    
    
    // Set the color parameters for the ColorDetection class
    private void setColors(Double hueL, Double hueH, Double satL, Double satH,
                            Double valL, Double valH)
    {
        colorDetection.setHueLow(hueL);
        colorDetection.setHueHigh(hueH);
        colorDetection.setSatLow(satL);
        colorDetection.setSatHigh(satH);
        colorDetection.setValLow(valL);
        colorDetection.setValHigh(valH);
    }
    
    // Set color parameters for blue color
    private void setBlue()
    {
        setColors(94.0,108.0,40.0,180.0,55.0,175.0);
    }
    
    // Set color parameters for orange color
    private void setOrange()
    {
        setColors(2.0,3.0,4.0,5.0,3.0,4.0);
    }
    
    // Set color parameters for red color
    private void setRed()
    {
        setColors(2.0,3.0,4.0,5.0,3.0,4.0);
    }
    
    // Set color parameters for green color
    private void setGreen()
    {
        setColors(30.0,46.0,87.0,177.0,20.0,150.0);
    }
    
    // Set color parameters for yellow color
    private void setYellow()
    {
        setColors(2.0,3.0,4.0,5.0,3.0,4.0);
    }
    
    // Stops the car by setting full color specter as parameters
    // Handled by Movement to recognize this as a stop indication
    private void setStop()
    {
        setColors(0.0,255.0,0.0,255.0,0.0,255.0);
    }
}
