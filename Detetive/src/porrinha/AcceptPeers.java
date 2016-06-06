/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porrinha;

import porrinhautils.Jogador;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabe
 */
class AcceptPeers extends Thread {
    public static boolean gameStarted = false;
    @Override
    public void run(){
        while(true){
            try {
                Jogador newJogador = new Jogador(Porrinha.getPeerserver().accept(), "", 0);
                System.out.println("Player accepted");
                Porrinha.getPeersAcceptedSockets().add(newJogador);
                ObjectInputStream ois = new ObjectInputStream(newJogador.getSocket().getInputStream());
                newJogador.setName((String) ois.readObject());
                newJogador.setPort((int) ois.readObject());
                System.out.println("Player name included");
                if(!gameStarted){
                    Porrinha.startGame();
                    gameStarted = true;
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(AcceptPeers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
