/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porrinha;

import porrinhautils.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabe
 */
public class UpdatePeers extends Thread{
    
    @Override
    public void run(){
        while(true){
            try {
                ObjectInputStream ois = new ObjectInputStream(Porrinha.getMainserver().getInputStream());
                Porrinha.setPeers((ArrayList<Player>) ois.readObject());
                JOptionPane.showMessageDialog(Porrinha.getTelaDeJogo(), "Peer list refreshed");
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(UpdatePeers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
    

