/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Koja
 */
@Entity
public class Narudzbina {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn
    private Proizvod proizvod;

    @ManyToOne
    @JoinColumn
    private Rasadnik rasadnik;

    private LocalDateTime datum;

    private boolean poslato;
    private boolean isporuceno;
    private boolean odbijeno;
    private int kolicina;

    public boolean isOdbijeno() {
        return odbijeno;
    }

    public void setOdbijeno(boolean odbijeno) {
        this.odbijeno = odbijeno;
    }

    public boolean isPoslato() {
        return poslato;
    }

    public void setPoslato(boolean poslato) {
        this.poslato = poslato;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsporuceno() {
        return isporuceno;
    }

    public void setIsporuceno(boolean isporuceno) {
        this.isporuceno = isporuceno;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvodi) {
        this.proizvod = proizvodi;
    }

    public Rasadnik getRasadnik() {
        return rasadnik;
    }

    public void setRasadnik(Rasadnik rasadnik) {
        this.rasadnik = rasadnik;
    }

}
