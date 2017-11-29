package ArduinoCom;

/*
 * This code is for a group project called "Portal Runner".
 * The purpose is to get a car to navigate through checkpoints
 * and fetch an object before returning to start location.
 * 
 * The system consists of an Odroid connected with an Arduino Uno
 * that runs the car, while an external computer allow for navigation
 * control and monitoring.
 */


import com.fazecast.jSerialComm.SerialPort;
import java.io.PrintWriter;

/**
 *
 * @author Morten
 * 
 */
public class SerialCommunication
{
    
    /**
     * @param args the command line arguments
     */
 
    
    private SerialPort chosenport;
    private final String desiredPort; // The port we want to connect to // ttyUSB0
//    private final String desiredPort = "COM4"; // The port we want to connect to // ttyUSB0
    private final int BAUD_RATE = 9600;
    private boolean connected = false;
    
    public SerialCommunication (String desiredPort){
        this.desiredPort = desiredPort;
    }

    /**
     * Searching for avilable com ports by iterating over the computers comports
     * then listing every avilable port. Then it checks if the port we desire is
     * one of these ports and if so, the desiered port is set to chosenPort.
     */
    private void searchAndSetPort() {
        SerialPort[] port = SerialPort.getCommPorts();
        for (int i = 0; i < port.length; i++) {
            System.out.println("Ports avilable for use " + port[i].getDescriptivePortName());
            if (port[i].getSystemPortName().equals(this.desiredPort)) {
                this.chosenport = port[i];
            }
        }
        System.out.println(this.chosenport.getSystemPortName() + " is chosen");
    }

    /**
     * Method for conectting to the port, setting Baud Rate and Timeout, this
     * method also creats the output strem.
     */
    private void connectToPort() {
        //if(!chosenport.isOpen()){
        this.chosenport.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        this.chosenport.setBaudRate(BAUD_RATE);
        if (this.chosenport.openPort()) {
            System.out.println("connected to: " + this.chosenport.getSystemPortName());
            this.connected = true;
            
        } else {
            System.out.println("Could not open port");
        }
    }

    
    public SerialPort getChosenPort(){
        return chosenport;
    }
    /**
     * Closese the output stream and terminates the connection.
     */
    public void closeConnection() {
        this.chosenport.closePort();
        System.out.println("Conection terminated");
    }
    
    /**
     * Initialise the serial port for comunication
     */
    public void init(){
        this.searchAndSetPort();
        this.connectToPort();
    }
    
}
