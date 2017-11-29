/*
 * This code is for a group project called "Portal Runner".
 * The purpose is to get a car to navigate through checkpoints
 * and fetch an object before returning to start location.
 * 
 * The system consists of an Odroid connected with an Arduino Uno
 * that runs the car, while an external computer allow for navigation
 * control and monitoring.
 */
package External_PC_Com;

import java.net.Socket;
 
/**
* 
* 
 * @author Ole Martin Hanstveit <https://github.com/OleIdole/>
 */
public class ClientInfo
{
    public Socket mSocket = null;
    public ClientListener mClientListener = null;
}