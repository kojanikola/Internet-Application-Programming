/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import beans.Application;
import beans.Korisnik;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import util.dao.LoginDAO;

/**
 *
 * @author Koja
 */
@Named
@SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class LoginController implements Serializable{

    private Korisnik korisnik = new Korisnik();
    private String message;
 
    
    public String login() {
        Korisnik pom = LoginDAO.proveri(korisnik.getUsername(), korisnik.getPassword());
        if (pom == null) {
            message = "Pogresni podaci";
            return null;
        }
        if (pom.getTip().equals("admin")) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("korisnik", pom);
            return "faces/admin.xhtml";
        } else if (pom.getTip().equals("firma")) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("korisnik", pom);
            return "faces/firma.xhtml";
        } else if (pom.getTip().equals("poljoprivrednik")) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("korisnik", pom);
            return "faces/poljoprivrednik.xhtml";
        }
        return null;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
