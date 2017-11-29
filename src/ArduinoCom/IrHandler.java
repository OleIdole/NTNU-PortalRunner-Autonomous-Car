package ArduinoCom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * This class meant for handeling inputs from the IR sensors
 * 
 * @author Morten
 */
public class IrHandler
{
    
    int sensorOneValue;
    int sensorTwoValue;
    
    public IrHandler(){
        
    }
    
    
    public void updateSensorValues(int sensorOne, int sensorTwo){
        
        this.sensorOneValue = sensorOne;
        this.sensorTwoValue = sensorTwo;
    }
    
    public int getSensorOneValue(){
        
        return sensorOneValue;
    }
    
    public int getSensorTwoValue(){
        
        return sensorTwoValue;
    }
}
