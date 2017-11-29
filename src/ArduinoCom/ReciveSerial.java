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
import com.fazecast.jSerialComm.SerialPortDataListener;
import java.io.InputStream;
import java.util.Scanner;

/**
 * This class is ment for reciving input from the serial port on the computer.
 *
 * @author Morten
 */
public class ReciveSerial 

{

    InputStream in;
    private final boolean debug = false;
    Scanner data;                   // Input scanner
    boolean inputDetected;          //
    String dataString;              // String with input
    Event inputEvent;               // Event telleing when input is recived
    SerialCommunication com;
    private SerialPort port;

    public ReciveSerial(Event event, SerialCommunication com) {

        this.inputEvent = event;
        this.inputEvent.reset();
        this.com = com;
        this.port = com.getChosenPort();
        creatInputStream();

        //readInputStream();
        inputDetected = false;

    }

    /**
     * Creats an inputstream and scanner.
     */
    private void creatInputStream() {

        if (!port.openPort()) {
            com.init();
        }

        data = new Scanner(port.getInputStream());
        System.out.println("InPutStream created");

    }

    /**
     * Reads the inputstream and creats it to a string, when the messages is
     * read the inputEvent will be set to UP.
     */
    public void readInputStream() {

        boolean dataRecived = false;

        while (data.hasNext() && dataRecived == false) {
            dataString = data.nextLine();
//            System.out.println(dataString);
            dataRecived = true;
//            if(dataRecived == true){
//              //  break;
//            }
        }

        inputEvent.set();

    }

    /**
     * Returns the string from the input scanner.
     *
     * @return String, inputString from the scanner.
     */
    public String getDataString() {
        return dataString;
    }



    /**
     * 
     */
    public void eventListener() {

        port.addDataListener(new SerialPortDataListener(){

            @Override
            public int getListeningEvents() {
                return port.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(com.fazecast.jSerialComm.SerialPortEvent event) {
                if (event.getEventType() != port.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }

                readInputStream();

            }
        });
    }
    
}

    