/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetive;

import com.sun.corba.se.pept.encoding.OutputObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 *
 * @author Gabe
 */
public class Detetive {

    private Socket client;
    private ArrayList<Socket> peers = new ArrayList();

    /**
     * @param args the command line arguments
     */
    public void main(String[] args) throws IOException {
        // TODO code application logic here
        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        try{
            connectToServer(in);
        }catch(Exception e){
            e.printStackTrace();
        }
        //conectado com o servidor
        
    }
    
    public void connectToServer(ObjectInputStream in) throws IOException, ClassNotFoundException{
        client = new Socket("127.0.0.1", 4321);
        while(true){
            peers = (ArrayList<Socket>) in.readObject();
        }
    }
    
    public void makeAccusation(){
        
    }
    
}
