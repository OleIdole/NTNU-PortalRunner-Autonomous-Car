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

import ArduinoCom.SendSerial;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class will handle all movement processing.
 *
 * @author Ole Martin Hanstveit <https://github.com/OleIdole/>
 */
public class MovementHandler {

    private final int deviation = 50; // Percentage deviation left/right movement
    private final int minimumAmount = 50; // Least amount of pixels required to detect object
    private final boolean debugMode = false; // Prints statuses, used for debugging
    private final SendSerial arduinoSender;

    private final String motorSpeed = "120"; // Speed used for motors of the car, can be modified.

    public MovementHandler(SendSerial arduinoSender) {
        this.arduinoSender = arduinoSender;
    }

    
    // Process the pixel amount for left and right half of the image and determine how to handle movement of the car
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void processInput(int leftAmount, int rightAmount) {
        int totalAmount = leftAmount + rightAmount; // Calculate total amount of white pixels in the image
        // If target is not in sight, perform a search by rotating right until target is found
        if (totalAmount < minimumAmount) {
            searchForTarget();
        } 

        // If more pixel percentage in right half than left, percentage is given by deviation
        else if (100 * (rightAmount - leftAmount) / totalAmount > deviation) {
            turnRight();
        }
        
        // If more pixel percentage in left half than right, percentage is given by deviation
        else if (100 * (leftAmount - rightAmount) / totalAmount > deviation) {
            turnLeft();
        } 
            
        // If entire image is detected as colors, indicates that car should stop
        else if (totalAmount > 76700) // Image is 320x240, total pixels will then be 76800
        {
            stopCar();
        }
        
        // Object is close and straight ahead, drive forward for a short while to pass
        // through the portal
        else if (30000 < totalAmount && totalAmount < 76700)
        {
            driveForward();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(MovementHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Object is straight ahead, drive forward
        else { 
            driveForward();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Commands the Arduino to turn right by sending motor speed for each side in addition to a command word
    // Similar to turnRight() method, but made a new method for it in consideration of future implementations
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void searchForTarget() {
            if (debugMode) {
                System.out.println("Target not found, turning right until found!");
            } else {
                arduinoSender.sendStringCommand("turnRight", motorSpeed, motorSpeed);
        }
    }
    
    
    // Commands the Arduino to turn right by sending motor speed for each side in addition to a command word
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void turnRight() {
            if (debugMode) {
                // Turn right
                System.out.println("Turning right"); // Command + left motor speed + right motor speed
            } else {
                arduinoSender.sendStringCommand("turnRight", motorSpeed, motorSpeed);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    // Commands the Arduino to turn left by sending motor speed for each side in addition to a command word
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void turnLeft() {
            if (debugMode) {
                System.out.println("Turning left"); // Command + left motor speed + right motor speed
            } else {
                arduinoSender.sendStringCommand("turnLeft", motorSpeed, motorSpeed);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    // Commands the Arduino to drive forward by sending motor speed for each side  in addition to a command word
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void driveForward() {
        if (debugMode) {
                System.out.println("Driving forward"); // Command + left motor speed + right motor speed
        }
        else {
                arduinoSender.sendStringCommand("driveForward", motorSpeed, motorSpeed);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    // Commands the Arduino to stop by sending motor speed for each side to zero
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void stopCar() {
        if (debugMode) {
                System.out.println("Stopping car"); // Command + left motor speed + right motor speed
        }
        
        else {
                arduinoSender.sendStringCommand("driveForward", "0", "0");
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
