package ArduinoCom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class is ment for handeling inputs and deligate tasks regarding inputs.
 * 
 * @author Morten
 */
public class ArduinoInputHandler implements Runnable
{
    
    Event inputRecivedEvent;                // Event telleing when input is recived
    private StringSeparator seperator;      // Object for seperating a string
    private ReciveSerial input;             // Object for reciving input
    private String dataString;                          // Input from input scanner
    private String[] stringArray = new String[10];      // String array holding substrings
    
    public ArduinoInputHandler (SensorHandler sensorHandeler, SerialCommunication com){
        
        inputRecivedEvent = new Event();
        input = new ReciveSerial(inputRecivedEvent, com);
        seperator = new StringSeparator();
    
    }

    @Override
    public void run() {
        try {
            while(true){
            input.eventListener();
            inputRecivedEvent.await(Event.EventState.UP);       // Wait until input is recived
            inputRecivedEvent.reset();                          // Resets event to DOWN
            dataString = input.getDataString();                  
//            System.out.println(dataString);
            
            sendToseperate(dataString);
            this.stringArray = seperator.getStringArray(dataString);  // sends string to seperation
//            System.out.println(stringArray[0]);
//            System.out.println(stringArray[1]);
//            System.out.println(stringArray[2]);
           }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(ArduinoInputHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * sends string to be splitted up inot sub-strings. Can seperate string  into
     *  as much as 11 substrings. 
     * 
     * @param stringToSeperate, String that shal be seperated
     */
    private void sendToseperate(String stringToSeperate){
        
        this.stringArray = seperator.getStringArray(stringToSeperate);
    }

    
    
}
    
