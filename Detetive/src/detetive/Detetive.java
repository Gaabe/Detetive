/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetive;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 *
 * @author Gabe
 */
public class Detetive {

    private Socket mainserver;
    private ArrayList<ServerSocket> peerserver;
    private ArrayList<Jogador> peers = new ArrayList();
    private TelaInicio telaDeJogo;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Detetive det = new Detetive();
        
        ObjectInputStream in = new ObjectInputStream(det.getMainserver().getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(det.getMainserver().getOutputStream()); //OUTPUT TEM QUE SER 1 PRA CADA PEER
        
        try{
            det.connectToServer(in, out);
        }catch(IOException | ClassNotFoundException e){
        }
        //conectado com o servidor
        TelaInicio novatela = new TelaInicio(det);
        
        //COMO FAZER O FUNCIONAMENTO EM TURNOS
//          while !jogoAcabou
//              is my turn?
//                  MEU TURNO (criar o palpite ou acusação e lançar pra galera) [makeGuess][makeAccusation]
//                  for entre os outros jogadores pra checar se eles tem algo do palpite feito
//                  if final da lista, volte pro inicio (ArrayList ciclico?)
//                  se não incrementa posição da lista de sockets
//                  passa o turno

    }
    
    public void connectToServer(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException, InterruptedException{
        setMainserver(new Socket("127.0.0.1", 4321));
        ObjectOutputStream oos = new ObjectOutputStream(getMainserver().getOutputStream());
        sendClientName(oos);
        while(in.readObject() != "Game Start"){
            setPeers((ArrayList<Jogador>) in.readObject());
            //listar na tela os pares conectados USAR SWING
//            if(/*Swing variavel*/){
//                out.writeObject("Start");
//            }
        }
    }
    
    public void startGame(ObjectOutputStream out){
       //jogo rolando
       /*
       OU SERVIDOR, OU PLAYER QUE DEU START (ADICIONAR VARIAVEL PARA CHECAR ISSO -> PREFERENCIAL)
       pegar do Guess a lista de pessoas, lugares e armas, embaralhar e dividir para os players
       DEFINIR OS TURNOS (QUEM COMEÇAR, ORDEM, ETC.)
       */
    }
    
    public void makeAccusation(ObjectOutputStream out) throws IOException{
        Guess guess = new Guess(Guess.Person.CoronelMostarda, Guess.Place.Biblioteca, Guess.Weapon.Cano);
        for(Jogador client : getPeers()){
            ObjectOutputStream out1 = new ObjectOutputStream(client.getSocket().getOutputStream());
            out1.writeObject(guess);
        }
        //Se acusação certa, jogo termina
        //Se acusação errada, jogador sai do jogo
    }
    
    public void makeGuess(ObjectOutputStream out) throws IOException{
        Guess guess = new Guess(Guess.Person.CoronelMostarda, Guess.Place.Biblioteca, Guess.Weapon.Cano);
        for(Jogador client : getPeers()){
            ObjectOutputStream out1 = new ObjectOutputStream(client.getSocket().getOutputStream());
            out1.writeObject(guess);
        }
    }

    private void sendClientName(ObjectOutputStream oos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the mainserver
     */
    public Socket getMainserver() {
        return mainserver;
    }

    /**
     * @param mainserver the mainserver to set
     */
    public void setMainserver(Socket mainserver) {
        this.mainserver = mainserver;
    }

    /**
     * @return the peerserver
     */
    public ArrayList<ServerSocket> getPeerserver() {
        return peerserver;
    }

    /**
     * @param peerserver the peerserver to set
     */
    public void setPeerserver(ArrayList<ServerSocket> peerserver) {
        this.peerserver = peerserver;
    }

    /**
     * @return the peers
     */
    public ArrayList<Jogador> getPeers() {
        return peers;
    }

    /**
     * @param peers the peers to set
     */
    public void setPeers(ArrayList<Jogador> peers) {
        this.peers = peers;
    }

    /**
     * @return the telaDeJogo
     */
    public TelaInicio getTelaDeJogo() {
        return telaDeJogo;
    }

    /**
     * @param telaDeJogo the telaDeJogo to set
     */
    public void setTelaDeJogo(TelaInicio telaDeJogo) {
        this.telaDeJogo = telaDeJogo;
    }
    
}
