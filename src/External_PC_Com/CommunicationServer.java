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

import java.net.*;
import java.io.*;

/**
 * 
 * 
 * 
 * @author Ole Martin Hanstveit <https://github.com/OleIdole/>
 */
public class CommunicationServer implements Runnable {

    public static final int LISTENING_PORT = 5000;
    private boolean connected;
    private InputHandlerTCP inputHandler;

    public CommunicationServer(InputHandlerTCP inputHandler) {
        this.inputHandler = inputHandler;
    }
    
    @Override
    public void run() {
        // Open server socket for listening
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(LISTENING_PORT);
            System.out.println("CommunicationServer started on port " + LISTENING_PORT);
        } catch (IOException se) {
            System.err.println("Can not start listening on port " + LISTENING_PORT);
            se.printStackTrace();
            System.exit(-1);
        }

        // Start ServerDispatcher thread
        ServerDispatcher serverDispatcher = new ServerDispatcher(inputHandler);
        serverDispatcher.start();

        // Accept and handle client connections
        while (!connected) {
            try {
                Socket socket = serverSocket.accept();
                ClientInfo clientInfo = new ClientInfo();
                clientInfo.mSocket = socket;
                ClientListener clientListener
                        = new ClientListener(clientInfo, serverDispatcher);
                clientInfo.mClientListener = clientListener;
                clientListener.start();
                serverDispatcher.addClient(clientInfo);
                connected = true;
                
                /**
                 * TODO: set connected to false when connection is closed from server
                 * Fix while loop so it doesnt freeze all other threads before connected
                 */
                
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}
