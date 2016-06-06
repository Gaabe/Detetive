/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porrinhaserver;

import porrinhautils.Jogador;
import porrinhautils.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;



/**
 *
 * @author Gabe
 */
public class PorrinhaServer {
    private static ServerSocket server;
    private static ArrayList<Jogador> clients;
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        System.out.println("Server running");
        try{
            PorrinhaServer.server = new ServerSocket(4321);
            PorrinhaServer.clients = new ArrayList();
        } catch (IOException e) {
            System.out.println("Could not listen on port 4321");
        }
        System.out.println("Server is ready");
        while(true){
            try{
                Jogador novoJogador = new Jogador(null, "", 0);
                novoJogador.setSocket(PorrinhaServer.server.accept());
                System.out.println("client accepted");
                ObjectInputStream clientis = new ObjectInputStream(novoJogador.getSocket().getInputStream());
                novoJogador.setName((String) clientis.readObject());
                novoJogador.setPort((int)clientis.readObject());
                PorrinhaServer.clients.add(novoJogador);
                System.out.println("New client connected");
                PorrinhaServer.informClients();
                System.out.println("Clients refreshed with peer list");
            }catch(IOException | ClassNotFoundException ex){
                ex.printStackTrace();
            }
                
        }
        
        
        
        
        /*
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
        */
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
    public ArrayList<Jogador> getClients() {
        return clients;
    }

    /**
     * @param clients the clients to set
     */
    public void setClients(ArrayList<Jogador> clients) {
        this.clients = clients;
    }
    
    public static void informClients() throws IOException, SocketException{
        ArrayList<Player> clientsip = new ArrayList();
        for(Jogador client : clients){
            Player newPlayer = new Player();
            newPlayer.setIp(client.getSocket().getInetAddress());
            newPlayer.setName(client.getName());
            newPlayer.setPort(client.getPort());
            clientsip.add(newPlayer);
        }
        for(Jogador client : clients){
            ObjectOutputStream out = new ObjectOutputStream(client.getSocket().getOutputStream());
            out.writeObject(clientsip);
        }
    }
    
}
