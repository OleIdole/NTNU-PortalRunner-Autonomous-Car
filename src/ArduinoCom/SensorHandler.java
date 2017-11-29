package ArduinoCom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * This class is ment for handeling sensor data and put it to the right place,
 * make sure that whoever needs the data gets it when requested if there is new
 * data.
 *
 * @author Morten
 */
public class SensorHandler
{
    Event rfidEvent;            //Tells when the rfid sesnor vlue have been updated
    RfidHandler rfidHandler;    //Contains the rfid sensor value and a timestamp when its recived
    IrHandler irHandler;        //Contains the IRsensors value
    long lastTimeStamp = 0;     //Keeps track of the last timestamp
    
    public SensorHandler(){
        
        rfidEvent = new Event();
        rfidEvent.reset();
        rfidHandler = new RfidHandler();
        irHandler = new IrHandler();
        
    }
    
    
    
    /**
     * Returns the rfidEvent object
     * 
     * @return Event, returns the rfidEvent object.
     */
    public Event getRfidEventHandler(){
        
        return this.rfidEvent;
    }
    
    public synchronized int getIrValue(){
        
        return irHandler.getSensorOneValue();
    }
    
    
    /**
     * Returns the value of the rfid sensor
     * 
     * @return int, value of the rfid sensors
     */
    public synchronized int  getRfidValue(){
        
        return rfidHandler.getRfidValue();
    }
    
    
    /**
     * Sorts the sensor data by sensor type, RFID data goes to one handler and
     * IR data goes to another hendler.
     * 
     * @param stringArray, StringArray gotten from the input.
     */
    public synchronized void sortSensorValues(String[] stringArray){
        
        if(stringArray[0].equals("RFID")){
            
            int rfidvalue = Integer.parseInt(stringArray[1]);
            rfidHandler.setValueAndStamp(rfidvalue);
            rfidEvent.set();
        }
        else if(stringArray[0].equals("IR")){
            
            int sensorOneValue = Integer.parseInt(stringArray[1]);
            int sensorTwoValue = Integer.parseInt(stringArray[2]);
            irHandler.updateSensorValues(sensorOneValue, sensorTwoValue);
        }
        else{
            
            System.out.println("no valied sensor");
        }
        
    }
}
