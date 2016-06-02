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
        
        ObjectInputStream in = new ObjectInputStream(server.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
        try{
            connectToServer(in);
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //conectado com o servidor
        
    }
    
    public void connectToServer(ObjectInputStream in) throws IOException, ClassNotFoundException{
        server = new Socket("127.0.0.1", 4321);
        while(true){
            peers = (ArrayList<Socket>) in.readObject();
        }
    }
    
    public void makeAccusation(){
        
    }
    
}
