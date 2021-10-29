/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import beans.Narudzbina;
import beans.Rasadnik;
import beans.ZasadjenaSadnica;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import beans.MestoURasadniku;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import util.dao.RasadnikDAO;

/**
 *
 * @author Koja
 */
@Named
@ViewScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class SadnicaController implements Serializable {

    private List<Narudzbina> narudzbine;
    private List<Narudzbina> sadnice;
    private List<ZasadjenaSadnica> zasadjeneSadnice;
    private Rasadnik rasadnik;
    private boolean postojiSadnica;
    private ZasadjenaSadnica sadnicaZaPrikaz;
    private boolean prikaziProzor;
    private Narudzbina odabranaSadnica;
    private int trenX;
    private int trenY;
    private List<Narudzbina> preparati;
    private int progres;
    private boolean spremnaZaVadjenje;
    private List<MestoURasadniku> mestaURasadniku;
    private boolean spremnoZaSadnju;

    @PostConstruct
    public void init() {
        this.rasadnik = (Rasadnik) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rasadnik");
        sadnice = new LinkedList<Narudzbina>();
        preparati = new LinkedList<Narudzbina>();
        mestaURasadniku = new LinkedList<>();
        mestaURasadniku = RasadnikDAO.dohvatiMesta(rasadnik);
        narudzbine = RasadnikDAO.dohvatiNarudzbine(rasadnik);

        for (Narudzbina n : narudzbine) {
            if ((n.getProizvod()).getTip().equals("sadnica") && n.isIsporuceno() == true && n.getKolicina() > 0) {
                sadnice.add(n);
            }
            if ((n.getProizvod()).getTip().equals("preparat") && n.isIsporuceno() == true && n.getKolicina() > 0) {
                preparati.add(n);
            }
        }
        prikaziProzor = false;
        zasadjeneSadnice = RasadnikDAO.dohvatiZasadjeneSadnice(rasadnik);
    }

    public void prikazi(int i, int j) {
        postojiSadnica = false;
        spremnaZaVadjenje = false;
        spremnoZaSadnju = true;
        for (ZasadjenaSadnica s : zasadjeneSadnice) {
            if (s.getX() == i && s.getY() == j && !s.isIzvadjena()) {
                sadnicaZaPrikaz = s;
                postojiSadnica = true;
                spremnoZaSadnju = false;
                if (sadnicaZaPrikaz.getPotrebno() <= sadnicaZaPrikaz.getProteklo()) {
                    spremnaZaVadjenje = true;
                }
            }
        }
        trenX = i;
        trenY = j;
        prikaziProzor = true;
        LocalDate now = LocalDate.now();
        if (postojiSadnica) {

            LocalDate pom = Instant.ofEpochMilli(sadnicaZaPrikaz.getDatumKadJeZasadjena().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            // Period p = Period.between(pom, now);
            int brDana = (int) ChronoUnit.DAYS.between(pom, now);
            sadnicaZaPrikaz.setProteklo(brDana);
            RasadnikDAO.updateZasadjenuSadnicu(sadnicaZaPrikaz);
            if (sadnicaZaPrikaz.getPotrebno() > 0) {
                progres = (sadnicaZaPrikaz.getProteklo() * 100) / sadnicaZaPrikaz.getPotrebno();
                if (progres >= 100) {
                    progres = 100;
                }
            } else {
                progres = 100;
            }
        }

        if (!postojiSadnica) {
            MestoURasadniku mestoZaBrisanje = null;
            for (MestoURasadniku m : mestaURasadniku) {
                Date datum = m.getDatum();
                LocalDate datumVadjenja = Instant.ofEpochMilli(datum.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                Period p = Period.between(datumVadjenja, now);
                int brDana = (int) ChronoUnit.DAYS.between(datumVadjenja, now);
                int k = p.getDays();
                if (p.getDays() < 1 && m.getX() == trenX && m.getY() == trenY) {
                    spremnoZaSadnju = false;
                }
                if (p.getDays() >= 1 && m.getX() == trenX && m.getY() == trenY) {
                    mestoZaBrisanje = m;
                }
            }
            if (mestoZaBrisanje != null) {
                RasadnikDAO.obrisiMestoURasadniku(mestoZaBrisanje);
            }
        }

    }

    public void izvadiSadnicu() {
        MestoURasadniku m = new MestoURasadniku();
        m.setX(trenX);
        m.setY(trenY);
        m.setRasadnik(rasadnik);
        Date datum = new Date();
        m.setDatum(datum);
        RasadnikDAO.zapamtiMestoURasadniku(m);
        sadnicaZaPrikaz.setIzvadjena(true);
        RasadnikDAO.updateZasadjenuSadnicu(sadnicaZaPrikaz);
        zasadjeneSadnice = RasadnikDAO.dohvatiZasadjeneSadnice(rasadnik);
        mestaURasadniku = RasadnikDAO.dohvatiMesta(rasadnik);
        prikaziProzor = false;
    }

    public void zasadiSadnicu(Narudzbina sadnica) {

        ZasadjenaSadnica s = new ZasadjenaSadnica();
        int i = sadnica.getKolicina();
        sadnica.setKolicina(i - 1);
        if (sadnica.getKolicina() == 0) {
            sadnice.remove(sadnica);
        }
        s.setPotrebno(sadnica.getProizvod().getBrojDana());
        s.setProteklo(0);
        s.setRasadnik(rasadnik);
        s.setSadnica(sadnica.getProizvod());
        s.setX(trenX);
        s.setY(trenY);
        Date pomDatum = new Date();
        s.setDatumKadJeZasadjena(pomDatum);

        int j = this.rasadnik.getBrSlobMesta();
        rasadnik.setBrSlobMesta(j - 1);
        j = this.rasadnik.getBrSadnica();
        rasadnik.setBrSadnica(j + 1);

        RasadnikDAO.updateRasadnik(rasadnik);
        RasadnikDAO.updateNarudzbine(sadnica);
        RasadnikDAO.zasadiSadnicu(s);
        zasadjeneSadnice = RasadnikDAO.dohvatiZasadjeneSadnice(rasadnik);
        prikaziProzor = false;
    }

    public List<MestoURasadniku> getMestaURasadniku() {
        return mestaURasadniku;
    }

    public void setMestaURasadniku(List<MestoURasadniku> mestaURasadniku) {
        this.mestaURasadniku = mestaURasadniku;
    }

    public void primeniPreparat(Narudzbina preparat) {
        int i = sadnicaZaPrikaz.getPotrebno();
        i = i - preparat.getProizvod().getBrojDana();
        if (i < 0) {
            i = 0;
        }
        sadnicaZaPrikaz.setPotrebno(i);
        RasadnikDAO.updateZasadjenuSadnicu(sadnicaZaPrikaz);
        if (sadnicaZaPrikaz.getPotrebno() <= sadnicaZaPrikaz.getProteklo()) {
            spremnaZaVadjenje = true;
        }
        int j = preparat.getKolicina();
        preparat.setKolicina(j - 1);
        if (preparat.getKolicina() == 0) {
            preparati.remove(preparat);
        }
        RasadnikDAO.updateNarudzbine(preparat);
        if (sadnicaZaPrikaz.getPotrebno() > 0) {
            progres = (sadnicaZaPrikaz.getProteklo() * 100) / sadnicaZaPrikaz.getPotrebno();
        } else {
            progres = 100;
        }
    }

    public boolean isSpremnoZaSadnju() {
        return spremnoZaSadnju;
    }

    public void setSpremnoZaSadnju(boolean spremnoZaSadnju) {
        this.spremnoZaSadnju = spremnoZaSadnju;
    }

    public boolean isSpremnaZaVadjenje() {
        return spremnaZaVadjenje;
    }

    public void setSpremnaZaVadjenje(boolean spremnaZaVadjenje) {
        this.spremnaZaVadjenje = spremnaZaVadjenje;
    }

    public int getProgres() {
        return progres;
    }

    public void setProgres(int progres) {
        this.progres = progres;
    }

    public List<Narudzbina> getPreparati() {
        return preparati;
    }

    public void setPreparati(List<Narudzbina> preparati) {
        this.preparati = preparati;
    }

    public int getTrenX() {
        return trenX;
    }

    public void setTrenX(int trenX) {
        this.trenX = trenX;
    }

    public int getTrenY() {
        return trenY;
    }

    public void setTrenY(int trenY) {
        this.trenY = trenY;
    }

    public Narudzbina getOdabranaSadnica() {
        return odabranaSadnica;
    }

    public void setOdabranaSadnica(Narudzbina odabranaSadnica) {
        this.odabranaSadnica = odabranaSadnica;
    }

    public List<Narudzbina> getNarudzbine() {
        return narudzbine;
    }

    public void setNarudzbine(List<Narudzbina> narudzbine) {
        this.narudzbine = narudzbine;
    }

    public boolean isPrikaziProzor() {
        return prikaziProzor;
    }

    public void setPrikaziProzor(boolean prikaziProzor) {
        this.prikaziProzor = prikaziProzor;
    }

    public boolean isPostojiSadnica() {
        return postojiSadnica;
    }

    public void setPostojiSadnica(boolean postojiSadnica) {
        this.postojiSadnica = postojiSadnica;
    }

    public List<ZasadjenaSadnica> getZasadjeneSadnice() {
        return zasadjeneSadnice;
    }

    public void setZasadjeneSadnice(List<ZasadjenaSadnica> zasadjeneSadnice) {
        this.zasadjeneSadnice = zasadjeneSadnice;
    }

    public ZasadjenaSadnica getSadnicaZaPrikaz() {
        return sadnicaZaPrikaz;
    }

    public void setSadnicaZaPrikaz(ZasadjenaSadnica sadnicaZaPrikaz) {
        this.sadnicaZaPrikaz = sadnicaZaPrikaz;
    }

    public String nazadNaRasadnik() {
        return "rasadnik?faces-redirect=true";
    }

    public List<Narudzbina> getSadnice() {
        return sadnice;
    }

    public void setSadnice(List<Narudzbina> sadnice) {
        this.sadnice = sadnice;
    }

    public Rasadnik getRasadnik() {
        return rasadnik;
    }

    public void setRasadnik(Rasadnik rasadnik) {
        this.rasadnik = rasadnik;
    }

}
