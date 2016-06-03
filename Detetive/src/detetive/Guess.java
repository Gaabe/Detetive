/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetive;


/**
 *
 * @author Gabe
 */
public class Guess {
    public enum Person{
        CoronelMostarda, DonaBranca, SrMarinho, DonaVioleta, ProfessorBlack, SrtaRosa
    }
    public enum Place{
        Biblioteca, Cozinha, Hall, Escritório, Entrada , SalaDeEstar, SalaDeJantar, SalaDeMusica, SalaoDeFestas, SalaoDeJogos
    }
    public enum Weapon{
        Castical, Cano, ChaveInglesa, Corda, Punhal, Revolver, Faca
    }
    private Person person;
    private Place place;
    private Weapon weapon;
    
    public Guess(Person person, Place place, Weapon weapon){
        this.person = person;
        this.place = place;
        this.weapon = weapon;
    }
}
