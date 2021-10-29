/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import beans.Admin;
import beans.Poljoprivrednik;
import beans.PorukaZaRasadnik;
import beans.Proizvod;
import beans.Rasadnik;
import java.io.Serializable;
import java.util.List;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import util.dao.PoljoprivrednikDAO;

/**
 *
 * @author Koja
 */
@Named
@ViewScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class PoljoprivrednikController implements Serializable {

    private Poljoprivrednik logovaniPoljoprivrednik;

    private Rasadnik dodajRasadnik;

    private List<Rasadnik> rasadnici;

    private List<PorukaZaRasadnik> upozorenja;

    public PoljoprivrednikController() {
        logovaniPoljoprivrednik = (Poljoprivrednik) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tipKorisnika", "poljoprivrednik");
        rasadnici = PoljoprivrednikDAO.dohvatiRasadnike(logovaniPoljoprivrednik);
        upozorenja = PoljoprivrednikDAO.dohvatiPoruke(logovaniPoljoprivrednik);
        FacesContext context = FacesContext.getCurrentInstance();
        for (PorukaZaRasadnik p : upozorenja) {
            context.addMessage(null, new FacesMessage("Upozorenje", p.getPoruka()));
        }
        dodajRasadnik = new Rasadnik();

    }

    public String promeniSifru() {
        return "promeniSifru?faces-redirect=true";
    }

    public String otvoriProdavnicu() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tipKorisnika", "poljoprivrednik");
        return "prodavnica?faces-redirect=true";
    }

    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    public String otvoriRasadnik(Rasadnik rasadnik) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rasadnik", rasadnik);
        return "rasadnik?faces-redirect=true";
    }

    public String dodajRasadnikF() {
        this.dodajRasadnik.setVlasnik(logovaniPoljoprivrednik);
        this.dodajRasadnik.setBrMesta(dodajRasadnik.getSirina() * dodajRasadnik.getDuzina());
        this.dodajRasadnik.setBrSlobMesta(dodajRasadnik.getSirina() * dodajRasadnik.getDuzina());
        this.dodajRasadnik.setTemperatura(18);
        this.dodajRasadnik.setVrVode(200);

        PoljoprivrednikDAO.dodajRasadnik(dodajRasadnik);

        rasadnici = PoljoprivrednikDAO.dohvatiRasadnike(logovaniPoljoprivrednik);

        return "poljoprivrednik?faces-redirect=true";

    }

    public List<PorukaZaRasadnik> getUpozorenja() {
        return upozorenja;
    }

    public void setUpozorenja(List<PorukaZaRasadnik> upozorenja) {
        this.upozorenja = upozorenja;
    }

    public Rasadnik getDodajRasadnik() {
        return dodajRasadnik;
    }

    public void setDodajRasadnik(Rasadnik dodajRasadnik) {
        this.dodajRasadnik = dodajRasadnik;
    }

    public Poljoprivrednik getLogovaniPoljoprivrednik() {
        return logovaniPoljoprivrednik;
    }

    public void setLogovaniPoljoprivrednik(Poljoprivrednik logovaniPoljoprivrednik) {
        this.logovaniPoljoprivrednik = logovaniPoljoprivrednik;
    }

    public List<Rasadnik> getRasadnici() {
        return rasadnici;
    }

    public void setRasadnici(List<Rasadnik> rasadnici) {
        this.rasadnici = rasadnici;
    }

}
