package ArduinoCom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * This class is ment for splitting up a string to several sub-strings, the
 * maximum amount of substrings is 11. The in-string requires an start bit 
 * "£" and an end bit "$", for seperating words "/" is used.
 * 
 * Any characters expcept "£","$", and "/" can be used in the word.
 * 
 * @author Morten
 */
public class StringSeparator
{
    public StringSeparator(){
        
    }
    
    
    /**
     * Takes in an string from the inputscanner and seperates it to substrings
     * at every "/". Most atleast contain start bit "£" and end bit "$" pluss
     * the desiered message.
     * 
     * @param stringToSplit, String from input scanner
     * @return String[], string array containing the substrings
     */
    public String[] getStringArray(String stringToSplit){
        
        String[] stringArray = new String[10];   // Array for keeping up to 11 sup-strings with index 0-10.
        int nr = 0;
        String word = "";                       // String used for building sub-string.
        boolean startRead = false;
        
        for(int i = 0; i < stringToSplit.length(); i++){        // For-loop for splitting up string to sub-strings.
            
            if ((stringToSplit.charAt(i) != '/') && (stringToSplit.charAt(i) != '£') 
                    && (stringToSplit.charAt(i) != '$') && (startRead == true)){
                    word = word + stringToSplit.charAt(i);      // Adds an character to the substring
                }
                if (stringToSplit.charAt(i) == '/' && startRead == true) {

                    stringArray[nr] = word;                     // Adds the substring to the array and increments index by one
                    word = "";
                    nr++;
                }
                if (stringToSplit.charAt(i) == '£'){
                    startRead = true;                           // Sets input to true when startbit is recived
                }
                if (stringToSplit.charAt(i) == '$' && startRead == true){
                    stringArray[nr] = word;                     // Adds the substring to the array and ends the string array
                } 
        }
        return stringArray;
    }
}
