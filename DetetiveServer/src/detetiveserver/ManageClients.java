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
import java.util.ArrayList;

/**
 *
 * @author Gabe
 */
public class ManageClients implements Runnable{
    private ServerSocket server;
    private ArrayList<Socket> clients;
   
    ManageClients(ServerSocket server, ArrayList<Socket> clients){
        this.server = server;
        this.clients = clients;
    }
    
    @Override
    public void run(){
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(clients.get(0).getInputStream()));
            //out = new PrintWriter(game.getClients().getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Buffer/Writer failed");
            System.exit(-1);
        }
        while(true){
            /*try{
                //Tratar lista de clients aqui!
            } catch (IOException e) {
                System.out.println("Read failed");
                System.exit(-1);
            }*/
        }
    }
}
