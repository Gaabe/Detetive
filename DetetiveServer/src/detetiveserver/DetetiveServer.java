/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetiveserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Gabe
 */
public class DetetiveServer {
    ServerSocket server;
    Socket client;
            

  public void listenSocket(){
    try{
        server = new ServerSocket(4321); 
    } catch (IOException e) {
        System.out.println("Could not listen on port 4321");
        System.exit(-1);
    }

    try{
        client = server.accept();
    } catch (IOException e) {
        System.out.println("Accept failed: 4321");
        System.exit(-1);
    }
    BufferedReader in = null;
    PrintWriter out = null;
    try{
        int a = 0;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    } catch (IOException e) {
        System.out.println("Read failed");
        System.exit(-1);
    }

    while(true){
        try{
            String line = in.readLine();
            //Tratar lista de clients aqui!
                 
        } catch (IOException e) {
            System.out.println("Read failed");
            System.exit(-1);
        }
    }
  }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
