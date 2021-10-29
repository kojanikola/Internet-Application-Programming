/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import beans.Firma;
import beans.Korisnik;
import java.io.Serializable;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import util.dao.KorisnikDao;

/**
 *
 * @author Koja
 */
@Named
@ViewScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class PromeniSifruController implements Serializable {

    Korisnik ulogovaniKorisnik;
    String sifra;
    String staraSifra;
    String novaSifra;
    String novaSifraPonovljeno;
    String msg;
    private String tipKorisnika;

    public PromeniSifruController() {
        ulogovaniKorisnik = (Korisnik) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
        sifra = ulogovaniKorisnik.getPassword();
        tipKorisnika = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipKorisnika");
    }

    public String promeni() {
        int i = 0;
        if (!staraSifra.equals(sifra)) {
            this.msg = "Neispravna sifra";
            return "";
        }
        if (sifra.equals(novaSifra)) {
            this.msg = "Stara sifra treba da se razlikuje od nove";
            return "";
        } else if (!(novaSifra.equals(novaSifraPonovljeno))) {
            this.msg = "Neuspesno ponovljena sifra";
            return "";
        }
        ulogovaniKorisnik.setPassword(novaSifra);
        KorisnikDao.zapamtiKorisnika(ulogovaniKorisnik);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    public String nazad() {
        if (tipKorisnika.equals("admin")) {
            return "admin?faces-redirect=true";
        }
        if (tipKorisnika.equals("poljoprivrednik")) {
            return "poljoprivrednik?faces-redirect=true";
        }
        return "firma?faces-redirect=true";
    }

    public String getTipKorisnika() {
        return tipKorisnika;
    }

    public void setTipKorisnika(String tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
    }

    public String getStaraSifra() {
        return staraSifra;
    }

    public void setStaraSifra(String staraSifra) {
        this.staraSifra = staraSifra;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Korisnik getUlogovaniKorisnik() {
        return ulogovaniKorisnik;
    }

    public void setUlogovaniKorisnik(Korisnik ulogovaniKorisnik) {
        this.ulogovaniKorisnik = ulogovaniKorisnik;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNovaSifra() {
        return novaSifra;
    }

    public void setNovaSifra(String novaSifra) {
        this.novaSifra = novaSifra;
    }

    public String getNovaSifraPonovljeno() {
        return novaSifraPonovljeno;
    }

    public void setNovaSifraPonovljeno(String novaSifraPonovljeno) {
        this.novaSifraPonovljeno = novaSifraPonovljeno;
    }

}
