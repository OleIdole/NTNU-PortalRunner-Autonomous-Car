/*
 * This code is for a group project called "Portal Runner".
 * The purpose is to get a car to navigate through checkpoints
 * and fetch an object before returning to start location.
 * 
 * The system consists of an Odroid connected with an Arduino Uno
 * that runs the car, while an external computer allow for navigation
 * control and monitoring.
 */
package Main;

import java.util.concurrent.*;
import ArduinoCom.*;
import External_PC_Com.CommunicationServer;
import External_PC_Com.InputHandlerTCP;
import External_PC_Com.VideoStreamServer;
import ImgRecog.ColorDetection;
import ImgRecog.ImageProcessor;
import ImgRecog.WebCamHandler;

/**
 * This is the main class that runs the program.
 * 
 * @author Ole Martin Hanstveit <https://github.com/OleIdole/>
 */
public class Application {
    public static void main(String[] args) {
        
        // Establish a serial connection between Odroid and Arduino
        SerialCommunication arduinoCom = new SerialCommunication("ttyUSB0"); // In-parameter specifies port
        arduinoCom.init();

        // Handling sensor data
//        SensorHandler sensorhandler = new SensorHandler();
        
        // Handling input from Arduino
//        InputHandler inputHandler = new InputHandler(sensorhandler, com);
//        inputHandler.run();
        
        // Scheduler for running threads at a fixed rate
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        // Thread for handling the webcam
        WebCamHandler webCamHandler = new WebCamHandler();
        webCamHandler.setDaemon(true);
        
        // Class for color detection
        ColorDetection colorDetection = new ColorDetection();
        
        // Processes messages from external PC
        InputHandlerTCP inputHandlerTCP = new InputHandlerTCP(colorDetection);
        
        // Establish connection between Odroid and external PC
        CommunicationServer externalCom = new CommunicationServer(inputHandlerTCP);
        externalCom.run();
        
        // Thread for handling image processing
        int framesPerSec = 30; // Define frames per second
        int imgProcRate = 1000/framesPerSec; // Schedule rate for ImageProcessor, based on specified fps.
        SendSerial arduinoSender = new SendSerial(arduinoCom);
        ImageProcessor imgProcessor = new ImageProcessor(webCamHandler, colorDetection, arduinoSender);
        scheduler.scheduleAtFixedRate(imgProcessor, 0, imgProcRate, TimeUnit.MILLISECONDS);
        
        // UDP output videostream
        VideoStreamServer vidOutStream = new VideoStreamServer(webCamHandler);
        scheduler.scheduleAtFixedRate(vidOutStream, 0, imgProcRate, TimeUnit.MILLISECONDS);
    }
}
