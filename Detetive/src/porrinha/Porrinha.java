/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porrinha;

import porrinhautils.Guess;
import porrinhautils.Jogador;
import porrinhautils.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Integer.parseInt;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static java.lang.Integer.parseInt;
/**
 *
 * @author Gabe
 */
public class Porrinha {

    private static Socket mainServer;
    private static ServerSocket peerServer;
    private static ArrayList<Jogador> peersSockets = new ArrayList();
    private static ArrayList<Jogador> peersAcceptedSockets = new ArrayList();
    private static TelaInicio telaDeJogo;
    private static String name;
    private static ArrayList<Player> peers = new ArrayList();
    private static Thread t1;
    private static Thread t2;
    private static int chute = 0;
    private static int turn = 0;
    private static int myTurn;
    private static int porrinhas = -1;
    private static int totalPorrinhas = 0;
    private static final int port = 2000 + (int) (Math.random() * 5000);
    

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        peerServer = new ServerSocket(port);
        Porrinha.mainServer = new Socket("191.40.89.18", 4321);
        ObjectOutputStream oos = new ObjectOutputStream(Porrinha.mainServer.getOutputStream());
        JFrame frame = new JFrame("pop up frame");
        Porrinha.setName(JOptionPane.showInputDialog(Porrinha.telaDeJogo, "Choose a username"));
        oos.writeObject(getName());
        oos.writeObject(port);
        System.out.println("connected to the server");
        telaDeJogo = new TelaInicio();
        Porrinha.getTelaDeJogo().setVisible(true);
        
        UpdatePeers updatePeers = new UpdatePeers();       
        t1 = new Thread(updatePeers);
        t1.start();
        
        AcceptPeers acceptPeers = new AcceptPeers();
        t2 = new Thread(acceptPeers);
        t2.start();
        
        
        t1.join();
        t2.join();

    }
    
    
    public static void startGame() throws IOException, ClassNotFoundException{
        connectToPeers();
        telaDeJogo.startGame();
        Porrinha.checkTurn();
        while(porrinhas < 0 || porrinhas > 3){
            porrinhas = parseInt(JOptionPane.showInputDialog("Bote entre 0 e 3 porrinhas:"));
        }
        while(turn < Porrinha.getPeersAcceptedSockets().size()){
            if(turn == myTurn){
                Porrinha.guess();
            }else{
                Porrinha.listen();     
            }
        }
        if(totalPorrinhas == chute){
            JOptionPane.showMessageDialog(telaDeJogo, "Você ganhou!");
        }else{
            JOptionPane.showMessageDialog(telaDeJogo, "Você perdeu seu porra!");
        }
    }

    private static void connectToPeers() {
        //Função para pegar lista de ips e nomes e conectar com os pares
        for(Player player : peers){
            if(player.getName().equals(Porrinha.name)){
            } else {
                try{
                    System.out.println(player.getIp().toString().subSequence(1, player.getIp().toString().length()));
                    System.out.println(player.getPort());
                    Jogador newJogador = new Jogador(new Socket((String) player.getIp().toString().subSequence(1, player.getIp().toString().length()), player.getPort()), player.getName(), player.getPort());
                    Porrinha.peersSockets.add(newJogador);
                    ObjectOutputStream oos = new ObjectOutputStream(newJogador.getSocket().getOutputStream());
                    oos.writeObject(Porrinha.getName());
                    oos.writeObject(player.getPort());
                }catch(IOException e){
                    e.printStackTrace();
                }
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

    private static void checkTurn() {
        for(int i = 0; i<Porrinha.getPeers().size(); i++){
            if (Porrinha.getPeers().get(i).equals(Porrinha.getName())){
                myTurn = i;
            }
        }
    }

    private static void guess() throws IOException {
        Porrinha.chute = parseInt(JOptionPane.showInputDialog("Chute um numero:"));
        Guess guess = new Guess(Porrinha.name, chute, porrinhas);
        for(Jogador jogador : Porrinha.peersSockets){
            ObjectOutputStream oos = new ObjectOutputStream(jogador.getSocket().getOutputStream());
            oos.writeObject(guess);
        }
        turn++;
    }

    private static void listen() throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(Porrinha.peersAcceptedSockets.get(turn).getSocket().getInputStream());
        telaDeJogo.showGuess((Guess) ois.readObject());
        totalPorrinhas += ((Guess) ois.readObject()).getPorrinhas();
        turn++;
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
        Porrinha.mainServer = mainserver;
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
        Porrinha.peerServer = peerserver;
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
        Porrinha.peers = peers;
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
        Porrinha.telaDeJogo = telaDeJogo;
    }

    
}
