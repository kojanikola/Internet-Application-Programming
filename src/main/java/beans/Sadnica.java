/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Koja
 */
@Entity
public class Sadnica extends Proizvod {

    @ManyToOne
    private Rasadnik rasadnik;
    private int x;
    private int y;
    private int proteklo;

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

}
