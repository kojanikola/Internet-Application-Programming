/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

/**
 *
 * @author Koja
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Korisnik {

    @Id
    private String username;
    private String password;
    private String email;
    private String tip;
    private boolean odobreno;
    @Transient
    private boolean izmeni;

    public boolean isOdobreno() {
        return odobreno;
    }

    public boolean isIzmeni() {
        return izmeni;
    }

    public void setIzmeni(boolean izmeni) {
        this.izmeni = izmeni;
    }

    public boolean getOdobreno() {
        return odobreno;
    }

    public void setOdobreno(boolean odobreno) {
        this.odobreno = odobreno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

}
