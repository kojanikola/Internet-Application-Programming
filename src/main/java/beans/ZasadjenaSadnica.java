/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Koja
 */
@Entity
public class ZasadjenaSadnica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Rasadnik rasadnik;
    private int x;
    private int y;
    private int proteklo;
    @ManyToOne
    @JoinColumn
    private Proizvod sadnica;
    private int potrebno;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date datumKadJeZasadjena;

    private boolean izvadjena;

    public boolean isIzvadjena() {
        return izvadjena;
    }

    public void setIzvadjena(boolean izvadjena) {
        this.izvadjena = izvadjena;
    }

    public Date getDatumKadJeZasadjena() {
        return datumKadJeZasadjena;
    }

    public void setDatumKadJeZasadjena(Date datumKadJeZasadjena) {
        this.datumKadJeZasadjena = datumKadJeZasadjena;
    }

    public int getPotrebno() {
        return potrebno;
    }

    public void setPotrebno(int potrebno) {
        this.potrebno = potrebno;
    }

    public Rasadnik getRasadnik() {
        return rasadnik;
    }

    public void setRasadnik(Rasadnik rasadnik) {
        this.rasadnik = rasadnik;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getProteklo() {
        return proteklo;
    }

    public void setProteklo(int proteklo) {
        this.proteklo = proteklo;
    }

    public Proizvod getSadnica() {
        return sadnica;
    }

    public void setSadnica(Proizvod sadnica) {
        this.sadnica = sadnica;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
