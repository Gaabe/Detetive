/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porrinhautils;

import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author Gabe
 */
public class Jogador implements Serializable{
    private Socket socket;
    private String name;
    private int port;
    
    public Jogador(Socket socket, String name, int port){
        this.socket = socket;
        this.name = name;
        this.port = port;
    }

    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * @param socket the socket to set
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }
    
}

