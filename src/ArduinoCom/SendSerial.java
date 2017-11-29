package ArduinoCom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.fazecast.jSerialComm.SerialPort;
import java.io.PrintWriter;

/**
 * This class is ment for sending output commands to an arduino unit 
 * 
 * @author Morten
 */
public class SendSerial implements Runnable
{
    
    SerialCommunication com;
    SerialPort port;
    protected PrintWriter sendCommand;
    
    public SendSerial(SerialCommunication com){
        
        this.com = com;
        this.port = com.getChosenPort();
        
    }
    
    /**
     * Creating outputstream
     */
    protected void creatOutputStream(){
        sendCommand = new PrintWriter(port.getOutputStream());
            System.out.println("OutPutStream created");
        }
    
    
    /**
     * Closing outputstream 
     */
    protected void closeOutputStream(){
        sendCommand.close();
        System.out.println("Output stream closed");
    }
    
    /**
     * Send a single command of type char to the arduino wich will initialize
     * a state on the arduino
     * 
     * @param charCommand, a command of type char
     */
    public void sendCharCommand(Character charCommand){
        
        sendCommand.write(charCommand);
        sendCommand.flush();
//        System.out.println("Sending: " + charCommand);
    }
    
    /**
     * Send a single command of type int to the arduino wich will initialize
     * a state on the arduino
     * 
     * @param intCommand, a command of type int. 
     */
    public void sendIntCommand(int intCommand){
        
        sendCommand=new PrintWriter(port.getOutputStream());
        
        sendCommand.write('1');
        sendCommand.flush();
//        System.out.println("Sending: " + intCommand);
    }
 
    
    /**
     * Sends tree commands to the arduino over serial communication. each command
     * word is seperated by "/" (Forward slash). 
     * 
     * £ - is used as start bit.
     * $ - is used as stop bit.
     * 
     * @param switchCase, String - choose which case to go to on the arduino.
     * @param varOne, int - a number between 0-255, used for first variabel within case.
     * @param varTwo, int - a number between 0-255, used for second variabel within case.
     */
    public void sendStringCommand(String switchCase, String varOne, String varTwo){
        
//        if(!chosenport.openPort()){
//            super.init();           
//        }
        
        String stringCommand = switchCase + "/" + varOne + "/" + varTwo + "/";
        sendCommand=new PrintWriter(port.getOutputStream());
        
        sendCommand.write(stringCommand);
        sendCommand.flush();
//        System.out.println("Sending: " + stringCommand);
        
    }
    
    @Override
    public void run() {
        
    }
    
}
