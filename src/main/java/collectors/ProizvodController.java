/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import beans.Komentar;
import beans.Poljoprivrednik;
import beans.Proizvod;
import beans.Rasadnik;
import java.io.Serializable;
import java.util.List;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import util.dao.ProdavnicaDAO;
import util.dao.ProizvodDAO;

/**
 *
 * @author Koja
 */
@Named
@ViewScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class ProizvodController implements Serializable {

    private Proizvod proizvod;
    private String tipKorisnika;
    private Poljoprivrednik poljoprivrednik;
    private boolean komentar;
    private String tekstKomentara;
    private int ocena;

    private List<Komentar> komentari;

    public ProizvodController() {
        proizvod = (Proizvod) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("proizvod");

        tipKorisnika = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipKorisnika");

        if (tipKorisnika.equals("poljoprivrednik")) {
            poljoprivrednik = (Poljoprivrednik) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
            komentar = ProdavnicaDAO.jeNarucivao(proizvod, poljoprivrednik);
        }

        komentari = ProizvodDAO.dohvatiKomentare(proizvod);
    }

    public void ostaviKomentar() {
        Komentar komentarPom = new Komentar();
        komentarPom.setKomentar(tekstKomentara);
        komentarPom.setKorisnik(poljoprivrednik);
        komentarPom.setOcena(ocena);
        komentarPom.setProizvod(proizvod);
        int brOcena = proizvod.getBrojOcena();
        brOcena += 1;
        proizvod.setBrojOcena(brOcena);
        ProizvodDAO.ostaviKomentar(komentarPom);
        komentari = ProizvodDAO.dohvatiKomentare(proizvod);
        int ukupnaOcena = 0;
        for (Komentar k : komentari) {
            ukupnaOcena += k.getOcena();
        }
        proizvod.setProsecnaOcena(ukupnaOcena / brOcena);
        ProizvodDAO.zapamtiProizvod(proizvod);

    }

    public String nazad() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("proizvod");
        if (tipKorisnika == "poljoprivrednik") {
            return "prodavnica?faces-redirect=true";
        }
        return "firma?faces-redirect=true";

    }

    public List<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(List<Komentar> komentari) {
        this.komentari = komentari;
    }

    public String getTekstKomentara() {
        return tekstKomentara;
    }

    public void setTekstKomentara(String tekstKomentara) {
        this.tekstKomentara = tekstKomentara;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public boolean isKomentar() {
        return komentar;
    }

    public void setKomentar(boolean komentar) {
        this.komentar = komentar;
    }

    public Proizvod getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvod proizvod) {
        this.proizvod = proizvod;
    }

    public String getTipKorisnika() {
        return tipKorisnika;
    }

    public void setTipKorisnika(String tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
    }

    public Poljoprivrednik getPoljoprivrednik() {
        return poljoprivrednik;
    }

    public void setPoljoprivrednik(Poljoprivrednik poljoprivrednik) {
        this.poljoprivrednik = poljoprivrednik;
    }

}
