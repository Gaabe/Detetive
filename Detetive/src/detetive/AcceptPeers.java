/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetive;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabe
 */
class AcceptPeers extends Thread {
    @Override
    public void run(){
        while(true){
            try {
                Jogador newJogador = new Jogador(Detetive.getPeerserver().accept(), "");
                Detetive.getPeersAcceptedSockets().add(newJogador);
                ObjectInputStream ois = new ObjectInputStream(newJogador.getSocket().getInputStream());
                newJogador.setName((String) ois.readObject());
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(AcceptPeers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
