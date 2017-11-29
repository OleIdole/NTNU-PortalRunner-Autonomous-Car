package ArduinoCom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * This class is meant for handeling inputs from the rfid sensor
 * 
 * @author Morten
 */
public class RfidHandler
{

    private int value;          //Value from rfid sensor
    private long timeStamp;     //Timestamp when sensor data is recived

    
    public RfidHandler() {

    }
    
    
    /**
     * Takes in the rfid sensor value and adds a timestamp from the viritual
     * machin systems clock when the value is recived.
     * 
     * @param value 
     */
    public void setValueAndStamp(int value) {

        this.value = value;
        timeStamp = System.nanoTime();
    }


    /**
     * Returns the value from the rfid sensor
     * 
     * @return int, Value of rfid sensor
     */    
    public int getRfidValue(){
        
        return this.value;
    }
    
    
    /**
     * Returns an timestamp of when the value was last updated
     * 
     * @return long, timestamp from system clock
     */
    public long getTimeStamp(){
        
        return this.timeStamp;
    }

    
    
    /**
     * Plays a specific sound depending of wich refid value that is recived.
     */
    public void playSound() {
        switch (value) {
            case 1:
                System.out.println("RFID id 1");
                break;
                
            case 2:
                System.out.println("RFID id 2");
                break;
                
            case 3:
                System.out.println("RFID id 3");
                break;
                
            case 4:
                System.out.println("RFID id 4");
                break;
                
            case 5:
                System.out.println("RFID id 5");
                break;
                
            case 6:
                System.out.println("RFID id 6");
                break;
        }
    }

}
