/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import beans.JeNarucioLista;
import beans.Korisnik;
import beans.Narudzbina;
import beans.Poljoprivrednik;
import beans.Proizvod;
import beans.Rasadnik;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import util.dao.NarudzbinaDAO;
import util.dao.PoljoprivrednikDAO;
import util.dao.ProdavnicaDAO;

/**
 *
 * @author Koja
 */
@Named
@ViewScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class ProdavnicaController implements Serializable {

    private List<Proizvod> proizvodi;
    private Proizvod selectedProizvod;
    private String tipKorisnika;
    private List<Narudzbina> korpa;
    private Korisnik logovaniKorisnik;
    private Rasadnik selectedRasadnik;

    public ProdavnicaController() {
        proizvodi = ProdavnicaDAO.dohvatiProizvode();
        korpa = new LinkedList<>();
        tipKorisnika = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipKorisnika");
        logovaniKorisnik = (Korisnik) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
        if (tipKorisnika.equals("poljoprivrednik")) {
            selectedRasadnik = (Rasadnik) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rasadnik");
        }
    }

    public String nazad() {
        if (tipKorisnika == "poljoprivrednik") {
            return "rasadnik?faces-redirect=true";
        }
        return "firma?faces-redirect=true";
    }

    public String naruci() {
        NarudzbinaDAO.dodajNarudzbine(korpa);
        return "prodavnica?faces-redirect=true";

    }

    public String otkaziKorpu() {
        NarudzbinaDAO.otkaziKorpu(korpa);
        return "prodavnica?faces-redirect=true";
    }

    public String ostaviKomentar(Proizvod p) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("proizvod", p);
        return "proizvod?faces-redirect=true";
    }

    public void dodajProizvod(Proizvod p) {
        boolean postoji = false;
        Narudzbina novaNarudzbina = null;

        for (Narudzbina n : korpa) {
            if (n.getProizvod().getId() == p.getId()) {
                int i = n.getKolicina();
                n.setKolicina(i + 1);
                postoji = true;
                NarudzbinaDAO.povecajKolicinu(n);
            }
        }
        if (!postoji) {
            novaNarudzbina = new Narudzbina();
            LocalDateTime locald = LocalDateTime.now();
            novaNarudzbina.setDatum(locald);
            novaNarudzbina.setProizvod(p);
            novaNarudzbina.setRasadnik(selectedRasadnik);
            novaNarudzbina.setKolicina(1);
            novaNarudzbina.setIsporuceno(false);
            korpa.add(novaNarudzbina);
        }
        int kol = p.getKolicina();
        p.setKolicina(kol - 1);
        if (p.getKolicina() <= 0) {
            proizvodi.remove(p);
            p.setDostupan(false);
        }
        ProdavnicaDAO.updateProizvod(p);
        proizvodi = ProdavnicaDAO.dohvatiProizvode();
        if (novaNarudzbina != null) {
            NarudzbinaDAO.dodajNarudzbinu(novaNarudzbina);
        }
    }

    public void otkazi(Narudzbina n) {
        korpa.remove(n);
        ProdavnicaDAO.otkaziNarudzbinu(n);
        Proizvod p = ProdavnicaDAO.dohvatiOdredjeniProizvod(n.getProizvod());
        int i = n.getKolicina();
        int j = p.getKolicina();
        int k = i + j;
        p.setKolicina(k);
        ProdavnicaDAO.updateProizvod(p);
        proizvodi = ProdavnicaDAO.dohvatiProizvode();
    }

    public Rasadnik getSelectedRasadnik() {
        return selectedRasadnik;
    }

    public void setSelectedRasadnik(Rasadnik selectedRasadnik) {
        this.selectedRasadnik = selectedRasadnik;
    }

    public List<Narudzbina> getKorpa() {
        return korpa;
    }

    public void setKorpa(List<Narudzbina> korpa) {
        this.korpa = korpa;
    }

    public String getTipKorisnika() {
        return tipKorisnika;
    }

    public void setTipKorisnika(String tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
    }

    public Korisnik getLogovaniKorisnik() {
        return logovaniKorisnik;
    }

    public void setLogovaniKorisnik(Korisnik logovaniKorisnik) {
        this.logovaniKorisnik = logovaniKorisnik;
    }

    public List<Proizvod> getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(List<Proizvod> proizvodi) {
        this.proizvodi = proizvodi;
    }

    public Proizvod getSelectedProizvod() {
        return selectedProizvod;
    }

    public void setSelectedProizvod(Proizvod selectedProizvod) {
        this.selectedProizvod = selectedProizvod;
    }

}
