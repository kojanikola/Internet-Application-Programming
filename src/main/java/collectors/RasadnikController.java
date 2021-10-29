/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import beans.Narudzbina;
import beans.PorukaZaRasadnik;
import beans.Proizvod;
import beans.Rasadnik;
import beans.Sadnica;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import util.dao.FirmaDAO;
import util.dao.RasadnikDAO;

/**
 *
 * @author Koja
 */
@Named
@ViewScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class RasadnikController implements Serializable {

    private Rasadnik rasadnik;

    private List<Sadnica> sadnice;

    private DashboardModel model;

    private List<Narudzbina> narudzbine;

    public RasadnikController() {
        this.rasadnik = (Rasadnik) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rasadnik");
        narudzbine = RasadnikDAO.dohvatiNarudzbine(rasadnik);
        List<Narudzbina> pomocni = new LinkedList<Narudzbina>();
        for (Narudzbina n : narudzbine) {
            if (n.getKolicina() > 0) {
                pomocni.add(n);
            }
        }
        narudzbine = pomocni;
        int i = 0;
    }

    @PostConstruct
    public void init() {
        model = new DefaultDashboardModel();

        DashboardColumn column1 = new DefaultDashboardColumn();

        model.addColumn(column1);

        column1.addWidget("KolicinaVode");
        column1.addWidget("Temperatura");
        column1.addWidget("Sadnice");

    }

    public String nazadNaPoljoprivrednika() {
        return "poljoprivrednik?faces-redirect=true";
    }

    public String prikaziSadnice() {
        return "sadnica?faces-redirect=true";
    }

    public void otkaziNarudzbinu(Narudzbina narudzbina) {
        FirmaDAO.otkaziNarudzbinu(narudzbina);
        narudzbine.remove(narudzbina);
    }

    public void povecajTemperaturu() {
        double trenutnaTemperatura = rasadnik.getTemperatura();
        trenutnaTemperatura++;
        rasadnik.setTemperatura(trenutnaTemperatura);
        RasadnikDAO.updateRasadnik(rasadnik);
        if (rasadnik.getTemperatura() > 12 && rasadnik.getVrVode() > 75) {
            RasadnikDAO.obrisiPoruku(rasadnik);
        }
    }

    public void smanjiTemperaturu() {
        double trenutnaTemperatura = rasadnik.getTemperatura();
        trenutnaTemperatura--;
        rasadnik.setTemperatura(trenutnaTemperatura);
        RasadnikDAO.updateRasadnik(rasadnik);
        if (rasadnik.getTemperatura() < 12 || rasadnik.getVrVode() < 74) {
            RasadnikDAO.dodajPoruku(rasadnik);
        }

    }

    public void povecajVodu() {
        int trenutnoVode = rasadnik.getVrVode();
        trenutnoVode++;
        rasadnik.setVrVode(trenutnoVode);
        RasadnikDAO.updateRasadnik(rasadnik);
        if (rasadnik.getTemperatura() > 12 && rasadnik.getVrVode() > 75) {
            RasadnikDAO.obrisiPoruku(rasadnik);
        }
    }

    public void smanjiVodu() {
        int trenutnoVode = rasadnik.getVrVode();
        trenutnoVode--;
        if (trenutnoVode < 0) {
            trenutnoVode = 0;
        }
        rasadnik.setVrVode(trenutnoVode);
        RasadnikDAO.updateRasadnik(rasadnik);
        if (rasadnik.getTemperatura() < 12 || rasadnik.getVrVode() < 75) {
            RasadnikDAO.dodajPoruku(rasadnik);
        }
    }

    public String otvoriProdavnicu() {
        return "prodavnica?faces-redirect=true";
    }

    public List<Narudzbina> getNarudzbine() {
        return narudzbine;
    }

    public void setNarudzbine(List<Narudzbina> narudzbine) {
        this.narudzbine = narudzbine;
    }

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }

    public Rasadnik getRasadnik() {
        return rasadnik;
    }

    public void setRasadnik(Rasadnik rasadnik) {
        this.rasadnik = rasadnik;
    }

    public List<Sadnica> getSadnice() {
        return sadnice;
    }

    public void setSadnice(List<Sadnica> sadnice) {
        this.sadnice = sadnice;
    }

}
