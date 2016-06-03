/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetive;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
/**
 *
 * @author Gabe
 */
public class Detetive {

    private Socket server;
    private ArrayList<Socket> peers = new ArrayList();

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Detetive det = new Detetive();
        
        ObjectInputStream in = new ObjectInputStream(det.server.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(det.server.getOutputStream()); //OUTPUT TEM QUE SER 1 PRA CADA PEER
        
        try{
            det.connectToServer(in, out);
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //conectado com o servidor
        det.startGame(out);
        
        //COMO FAZER O FUNCIONAMENTO EM TURNOS
//          while !jogoAcabou
//              is my turn?
//                  MEU TURNO (criar o palpite ou acusação e lançar pra galera) [makeGuess][makeAccusation]
//                  for entre os outros jogadores pra checar se eles tem algo do palpite feito
//                  if final da lista, volte pro inicio (ArrayList ciclico?)
//                  se não incrementa posição da lista de sockets
//                  passa o turno

    }
    
    public void connectToServer(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException{
        server = new Socket("127.0.0.1", 4321);
        while(in.readObject() != "Game Start"){
            peers = (ArrayList<Socket>) in.readObject();
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
        for(Socket client : peers){
            ObjectOutputStream out1 = new ObjectOutputStream(client.getOutputStream());
            out1.writeObject(guess);
        }
        //Se acusação certa, jogo termina
        //Se acusação errada, jogador sai do jogo
    }
    
    public void makeGuess(ObjectOutputStream out) throws IOException{
        Guess guess = new Guess(Guess.Person.CoronelMostarda, Guess.Place.Biblioteca, Guess.Weapon.Cano);
        for(Socket client : peers){
            ObjectOutputStream out1 = new ObjectOutputStream(client.getOutputStream());
            out1.writeObject(guess);
        }
    }
    
}
