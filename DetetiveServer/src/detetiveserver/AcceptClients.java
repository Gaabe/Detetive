/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetiveserver;

import detetiveutils.Jogador;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabe
 */
public class AcceptClients extends Thread{
    private final ServerSocket server;
    private ArrayList<Jogador> clients;
    private final PipedOutputStream pos;
    private final PipedInputStream pis;
    
    AcceptClients(ServerSocket server, ArrayList<Jogador> clients, PipedOutputStream pos, PipedInputStream pis){
        this.server = server;
        this.clients = clients;
        this.pos = pos;
        this.pis = pis;
    }
    
    @Override
    public void run(){
        ObjectOutputStream oos = null;
        while(true){
            try{
                Jogador novoJogador = new Jogador(null, "", 0);
                novoJogador.setSocket(server.accept());
                ObjectInputStream clientis = new ObjectInputStream(novoJogador.getSocket().getInputStream());
                Thread.sleep(2000);
                novoJogador.setName((String) clientis.readObject());
                clients.add(novoJogador);
                oos = new ObjectOutputStream(pos);
                oos.writeObject(clients);
                System.out.println("New client connected");
            } catch (IOException e) {
                System.out.println("Accept failed: 4321");
            } catch (ClassNotFoundException | InterruptedException ex) {
                Logger.getLogger(AcceptClients.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
