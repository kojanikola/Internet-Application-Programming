/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Koja
 */
@Entity
public class PorukaZaRasadnik {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String poruka;
    @ManyToOne
    @JoinColumn
    private Poljoprivrednik vlasnik;

    @OneToOne
    @JoinColumn
    private Rasadnik rasadnik;

    public Rasadnik getRasadnik() {
        return rasadnik;
    }

    public void setRasadnik(Rasadnik rasadnik) {
        this.rasadnik = rasadnik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public Poljoprivrednik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Poljoprivrednik vlasnik) {
        this.vlasnik = vlasnik;
    }

}
