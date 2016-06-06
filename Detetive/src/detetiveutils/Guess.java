/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetiveutils;

import java.io.Serializable;


/**
 *
 * @author Gabe
 */
public class Guess implements Serializable {
    
    private String name;
    private int chute;
    private int porrinhas;
    
    public Guess(String name, int chute, int porrinhas){
        this.name = name;
        this.chute = chute;
        this.porrinhas = porrinhas;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the chute
     */
    public int getChute() {
        return chute;
    }

    /**
     * @param chute the chute to set
     */
    public void setChute(int chute) {
        this.chute = chute;
    }

    /**
     * @return the porrinhas
     */
    public int getPorrinhas() {
        return porrinhas;
    }

    /**
     * @param porrinhas the porrinhas to set
     */
    public void setPorrinhas(int porrinhas) {
        this.porrinhas = porrinhas;
    }
}
