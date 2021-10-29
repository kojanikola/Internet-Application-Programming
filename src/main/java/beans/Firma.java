/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Koja
 */
@Entity
public class Firma extends Korisnik {

    private String ime;
    @Temporal(TemporalType.DATE)
    private Date datumOsnivanja;
    private String mesto;

    private int brSlobodnihKurira;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Proizvod> proizvodi;

    
    public String getIme() {
        return ime;
    }

    public List<Proizvod> getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(List<Proizvod> proizvodi) {
        this.proizvodi = proizvodi;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Date getDatumOsnivanja() {
        return datumOsnivanja;
    }

    public void setDatumOsnivanja(Date datumOsnivanja) {
        this.datumOsnivanja = datumOsnivanja;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public int getBrSlobodnihKurira() {
        return brSlobodnihKurira;
    }

    public void setBrSlobodnihKurira(int brSlobodnihKurira) {
        this.brSlobodnihKurira = brSlobodnihKurira;
    }

}
