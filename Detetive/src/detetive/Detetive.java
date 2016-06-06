/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetive;

import detetiveutils.Guess;
import detetiveutils.Jogador;
import detetiveutils.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Gabe
 */
public class Detetive {

    private static Socket mainServer;
    private static ServerSocket peerServer;
    private static ArrayList<Jogador> peersSockets = new ArrayList();
    private static ArrayList<Jogador> peersAcceptedSockets = new ArrayList();
    private static TelaInicio telaDeJogo;
    private static String name;
    private static ArrayList<Player> peers = new ArrayList();
    private static Thread t1;
    private static Thread t2;
    

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        peerServer = new ServerSocket(10120);
        Detetive.mainServer = new Socket("127.0.0.1", 4321);
        ObjectOutputStream oos = new ObjectOutputStream(Detetive.mainServer.getOutputStream());
        JFrame frame = new JFrame("pop up frame");
        Detetive.setName(JOptionPane.showInputDialog(Detetive.telaDeJogo, "Choose a username"));
        oos.writeObject(getName());
        System.out.println("connected to the server");
        telaDeJogo = new TelaInicio();
        Detetive.getTelaDeJogo().setVisible(true);
        
        UpdatePeers updatePeers = new UpdatePeers();       
        t1 = new Thread(updatePeers);
        t1.start();
        
        AcceptPeers acceptPeers = new AcceptPeers();
        t2 = new Thread(acceptPeers);
        t2.start();
        
        
        t1.join();
        t2.join();

    }
    
    
    public static void startGame() throws IOException{
       //jogo rolando
       /*
       OU SERVIDOR, OU PLAYER QUE DEU START (ADICIONAR VARIAVEL PARA CHECAR ISSO -> PREFERENCIAL)
       pegar do Guess a lista de pessoas, lugares e armas, embaralhar e dividir para os players
       DEFINIR OS TURNOS (QUEM COMEÇAR, ORDEM, ETC.)
       */
        //t1.interrupt();
        //Detetive.mainServer.close();
        connectToPeers();
        telaDeJogo.startGame();
    }

    private static void connectToPeers() throws IOException {
        //Função para pegar lista de ips e nomes e conectar com os pares
        for(Player player : peers){
            if(player.getName().equals(Detetive.name)){
            } else {
                System.out.println(player.getIp().toString().subSequence(1, player.getIp().toString().length()));
                Jogador newJogador = new Jogador(new Socket((String) player.getIp().toString().subSequence(1, player.getIp().toString().length()), 10120), player.getName());
                Detetive.peersSockets.add(newJogador);
                ObjectOutputStream oos = new ObjectOutputStream(newJogador.getSocket().getOutputStream());
                oos.writeObject(Detetive.getName());
            }           
        }
    }

    /**
     * @return the peersAcceptedSockets
     */
    public static ArrayList<Jogador> getPeersAcceptedSockets() {
        return peersAcceptedSockets;
    }

    /**
     * @param aPeersAcceptedSockets the peersAcceptedSockets to set
     */
    public static void setPeersAcceptedSockets(ArrayList<Jogador> aPeersAcceptedSockets) {
        peersAcceptedSockets = aPeersAcceptedSockets;
    }

    /**
     * @return the name
     */
    public static String getName() {
        return name;
    }

    /**
     * @param aName the name to set
     */
    public static void setName(String aName) {
        name = aName;
    }
    
    public void makeAccusation(ObjectOutputStream out) throws IOException{
        Guess guess = new Guess(Guess.Person.CoronelMostarda, Guess.Place.Biblioteca, Guess.Weapon.Cano);
        for(Jogador client : Detetive.peersSockets){
            ObjectOutputStream out1 = new ObjectOutputStream(client.getSocket().getOutputStream());
            out1.writeObject(guess);
        }
        //Se acusação certa, jogo termina
        //Se acusação errada, jogador sai do jogo
    }
    
    public void makeGuess(ObjectOutputStream out) throws IOException{
        Guess guess = new Guess(Guess.Person.CoronelMostarda, Guess.Place.Biblioteca, Guess.Weapon.Cano);
        for(Jogador client : Detetive.peersSockets){
            ObjectOutputStream out1 = new ObjectOutputStream(client.getSocket().getOutputStream());
            out1.writeObject(guess);
        }
    }


    /**
     * @return the mainServer
     */
    public static Socket getMainserver() {
        return mainServer;
    }

    /**
     * @param mainserver the mainServer to set
     */
    public static void setMainserver(Socket mainserver) {
        Detetive.mainServer = mainserver;
    }

    /**
     * @return the peerServer
     */
    public static ServerSocket getPeerserver() {
        return peerServer;
    }

    /**
     * @param peerserver the peerServer to set
     */
    public static void setPeerserver(ServerSocket peerserver) {
        Detetive.peerServer = peerserver;
    }

    /**
     * @return the peers
     */
    public static ArrayList<Player> getPeers() {
        return peers;
    }

    /**
     * @param peers the peers to set
     */
    public static  void setPeers(ArrayList<Player> peers) {
        Detetive.peers = peers;
    }

    /**
     * @return the telaDeJogo
     */
    public static TelaInicio getTelaDeJogo() {
        return telaDeJogo;
    }

    /**
     * @param telaDeJogo the telaDeJogo to set
     */
    public static void setTelaDeJogo(TelaInicio telaDeJogo) {
        Detetive.telaDeJogo = telaDeJogo;
    }

    
}
