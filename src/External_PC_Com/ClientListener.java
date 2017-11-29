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
 
import java.io.*;
import java.net.*;
 
/**
 * 
 * 
 * @author Ole Martin Hanstveit <https://github.com/OleIdole/>
 */
public class ClientListener extends Thread
{
    private ServerDispatcher mServerDispatcher;
    private ClientInfo mClientInfo;
    private BufferedReader mIn;
 
    public ClientListener(ClientInfo aClientInfo, ServerDispatcher aServerDispatcher)
    throws IOException
    {
        mClientInfo = aClientInfo;
        mServerDispatcher = aServerDispatcher;
        Socket socket = aClientInfo.mSocket;
        mIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
 
    /**
     * Until interrupted, reads messages from the client socket, forwards them
     * to the server dispatcher's queue and notifies the server dispatcher.
     */
    public void run()
    {
        try {
           while (!isInterrupted()) {
               String message = mIn.readLine();
               if (message == null)
                   break;
               mServerDispatcher.dispatchMessage(mClientInfo, message);
           }
        } catch (IOException ioex) {
           // Problem reading from socket (communication is broken)
        }
 
        // Communication is broken. Interrupt listener thread
        mServerDispatcher.deleteClient(mClientInfo);
    }
 
}