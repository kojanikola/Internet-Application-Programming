/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Koja
 */
@Entity
public class Rasadnik {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Poljoprivrednik vlasnik;
    private String mesto;
    private String naziv;
    private int brSadnica;
    private int brSlobMesta;
    private int sirina;
    private int duzina;
    private int brMesta;
    private int vrVode;
    private double temperatura;
    @OneToMany(mappedBy = "rasadnik")
    private List<Sadnica> sadnice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Poljoprivrednik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Poljoprivrednik vlasnik) {
        this.vlasnik = vlasnik;
    }

    public int getBrMesta() {
        return brMesta;
    }

    public void setBrMesta(int brMesta) {
        this.brMesta = brMesta;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrSadnica() {
        return brSadnica;
    }

    public void setBrSadnica(int brSadnica) {
        this.brSadnica = brSadnica;
    }

    public int getBrSlobMesta() {
        return brSlobMesta;
    }

    public void setBrSlobMesta(int brSlobMesta) {
        this.brSlobMesta = brSlobMesta;
    }

    public int getVrVode() {
        return vrVode;
    }

    public void setVrVode(int vrVode) {
        this.vrVode = vrVode;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public int getSirina() {
        return sirina;
    }

    public void setSirina(int sirina) {
        this.sirina = sirina;
    }

    public int getDuzina() {
        return duzina;
    }

    public void setDuzina(int duzina) {
        this.duzina = duzina;
    }

}
