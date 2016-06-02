/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetiveserver;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 *
 * @author Gabe
 */
public class DetetiveServer {
    private ServerSocket server;
    private ArrayList<Socket> clients;
            
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        DetetiveServer newGame = new DetetiveServer();
        try{
            newGame.setServer(new ServerSocket(4321));
            newGame.setClients(new ArrayList());
        } catch (IOException e) {
            System.out.println("Could not listen on port 4321");
            System.exit(-1);
        }
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream(pis);
        ManageClients manageClients = new ManageClients(newGame.server, newGame.clients, pos, pis);
        AcceptClients acceptClients = new AcceptClients(newGame.server, newGame.clients, pos, pis);
        Thread t1 = new Thread(manageClients);
        Thread t2 = new Thread(acceptClients);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    /**
     * @return the server
     */
    public ServerSocket getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(ServerSocket server) {
        this.server = server;
    }

    /**
     * @return the clients
     */
    public ArrayList<Socket> getClients() {
        return clients;
    }

    /**
     * @param clients the clients to set
     */
    public void setClients(ArrayList<Socket> clients) {
        this.clients = clients;
    }
    
}
