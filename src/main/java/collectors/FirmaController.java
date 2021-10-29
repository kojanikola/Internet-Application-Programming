/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import beans.Firma;
import beans.Narudzbina;
import beans.Proizvod;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import util.dao.FirmaDAO;
import util.dao.ProdavnicaDAO;

/**
 *
 * @author Koja
 */
@Named
@ViewScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class FirmaController implements Serializable {

    private Firma logovanaFirma;
    private List<Narudzbina> narudzbine;
    private List<Narudzbina> narudzbineZaMesec;
    private String korak;
    private int korakID;
    private Proizvod noviProizvod;
    private List<Proizvod> sviProizvodi;
    private Proizvod selectedProizvod;
    private Narudzbina selectedNarudzbina;
    private int isporuceno;
    private int odbijeno;
    private int poslato;
    //private List<Kurir> kuriri;
    private boolean nemaKurira;
    private int max;

    public FirmaController() {
        logovanaFirma = (Firma) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tipKorisnika", "firma");
        korak = "naziv";
        korakID = 0;
        noviProizvod = new Proizvod();
        sviProizvodi = FirmaDAO.dohvatiProizvode(logovanaFirma);
        narudzbine = FirmaDAO.dohvatiNarudzbine(logovanaFirma);
        if (logovanaFirma.getBrSlobodnihKurira() == 0) {
            nemaKurira = true;
        }

        narudzbineZaMesec = new LinkedList<>();
        List<Narudzbina> pom = FirmaDAO.dohvatiSveNaruzbine(logovanaFirma);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime kraj = now.minusDays(30);
        for (Narudzbina n : pom) {
            if (n.getDatum().isAfter(kraj)) {
                narudzbineZaMesec.add(n);
            }
        }
        isporuceno = 0;
        poslato = 0;
        odbijeno = 0;
        for (Narudzbina n : narudzbineZaMesec) {
            if (n.isIsporuceno()) {
                isporuceno++;
            }
            if (n.isOdbijeno()) {
                odbijeno++;
            }
            if (n.isPoslato()) {
                poslato++;
            }
        }
        max = odbijeno;
        if (isporuceno > max) {
            max = isporuceno;
        }
        if (poslato > max) {
            max = poslato;
        }
    }

//    @PostConstruct
//    public void init() {
//        odbijeno = new HashMap();
//        isporuceno = new HashMap();
//        poslato = new HashMap();
//        LocalDate now = LocalDate.now();
//        LocalDate kraj = now.minusDays(30);
//        for (LocalDate datum = now; datum.isAfter(kraj); datum.minusDays(1)) {
//            datumi.add(datum);
//        }
//    }
    public String promeniSifru() {
        return "promeniSifru?faces-redirect=true";
    }

    public void prihvatiNarudzbinu() {
        selectedNarudzbina.setPoslato(true);
        FirmaDAO.prihvatiNarudzbinu(selectedNarudzbina);
        int i = logovanaFirma.getBrSlobodnihKurira();
        logovanaFirma.setBrSlobodnihKurira(i - 1);
        if (logovanaFirma.getBrSlobodnihKurira() <= 0) {
            logovanaFirma.setBrSlobodnihKurira(0);
            nemaKurira = true;
        }
        FirmaDAO.updateFirma(logovanaFirma);
        List<Narudzbina> pom = FirmaDAO.dohvatiSveNaruzbine(logovanaFirma);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime kraj = now.minusDays(30);
        for (Narudzbina n : pom) {
            if (n.getDatum().isAfter(kraj)) {
                narudzbineZaMesec.add(n);
            }
        }
        isporuceno = 0;
        poslato = 0;
        odbijeno = 0;
        for (Narudzbina n : narudzbineZaMesec) {
            if (n.isIsporuceno()) {
                isporuceno++;
            }
            if (n.isOdbijeno()) {
                odbijeno++;
            }
            if (n.isPoslato()) {
                poslato++;
            }
        }
        max = odbijeno;
        if (isporuceno > max) {
            max = isporuceno;
        }
        if (poslato > max) {
            max = poslato;
        }
    }

    public List<Narudzbina> getNarudzbineZaMesec() {
        return narudzbineZaMesec;
    }

    public void setNarudzbineZaMesec(List<Narudzbina> narudzbineZaMesec) {
        this.narudzbineZaMesec = narudzbineZaMesec;
    }

    public String prikaziDatum(LocalDate datum) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LLLL.yyyy HH:mm");
        String formattedString = datum.format(formatter);
        return formattedString;
    }

    public void isporuciNarudzbinu() {

        selectedNarudzbina.setIsporuceno(true);
        selectedNarudzbina.setPoslato(true);
        FirmaDAO.prihvatiNarudzbinu(selectedNarudzbina);
        int i = logovanaFirma.getBrSlobodnihKurira();
        logovanaFirma.setBrSlobodnihKurira(i + 1);
        if (logovanaFirma.getBrSlobodnihKurira() > 0) {
            nemaKurira = false;
        }
        FirmaDAO.updateFirma(logovanaFirma);
        narudzbine.remove(selectedNarudzbina);
        List<Narudzbina> pom = FirmaDAO.dohvatiSveNaruzbine(logovanaFirma);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime kraj = now.minusDays(30);
        for (Narudzbina n : pom) {
            if (n.getDatum().isAfter(kraj)) {
                narudzbineZaMesec.add(n);
            }
        }
        isporuceno = 0;
        poslato = 0;
        odbijeno = 0;
        for (Narudzbina n : narudzbineZaMesec) {
            if (n.isIsporuceno()) {
                isporuceno++;
            }
            if (n.isOdbijeno()) {
                odbijeno++;
            }
            if (n.isPoslato()) {
                poslato++;
            }
        }
        max = odbijeno;
        if (isporuceno > max) {
            max = isporuceno;
        }
        if (poslato > max) {
            max = poslato;
        }
    }

    public void odbaciNarudzbinu() {
        selectedNarudzbina.setOdbijeno(true);
        ProdavnicaDAO.odbaciNarudzbinu(selectedNarudzbina);
        narudzbine.remove(selectedNarudzbina);
        List<Narudzbina> pom = FirmaDAO.dohvatiSveNaruzbine(logovanaFirma);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime kraj = now.minusDays(30);
        for (Narudzbina n : pom) {
            if (n.getDatum().isAfter(kraj)) {
                narudzbineZaMesec.add(n);
            }
        }
        isporuceno = 0;
        poslato = 0;
        odbijeno = 0;
        for (Narudzbina n : narudzbineZaMesec) {
            if (n.isIsporuceno()) {
                isporuceno++;
            }
            if (n.isOdbijeno()) {
                odbijeno++;
            }
            if (n.isPoslato()) {
                poslato++;
            }
        }
        max = odbijeno;
        if (isporuceno > max) {
            max = isporuceno;
        }
        if (poslato > max) {
            max = poslato;
        }
    }

    public void obrisiNarudzbinu() {
        ProdavnicaDAO.otkaziNarudzbinu(selectedNarudzbina);
        narudzbine = FirmaDAO.dohvatiNarudzbine(logovanaFirma);
    }

    public String prikaziProizvod() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("proizvod", selectedProizvod);
        return "proizvod?faces-redirect=true";
    }

    public String otvoriProdavnicu() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tipKorisnika", "firma");
        return "prodavnica?faces-redirect=true";
    }

    public void obrisiProizvod() {
        FirmaDAO.obrisiProizvod(selectedProizvod);
        sviProizvodi = FirmaDAO.dohvatiProizvode(logovanaFirma);
    }

    public void promeniDostupnost() {
        boolean pom = selectedProizvod.isDostupan();
        selectedProizvod.setDostupan(!pom);
        FirmaDAO.promeniDostupnost(selectedProizvod);
        sviProizvodi = FirmaDAO.dohvatiProizvode(logovanaFirma);
    }

    public Proizvod getSelectedProizvod() {
        return selectedProizvod;
    }

    public void setSelectedProizvod(Proizvod selectedProizvod) {
        this.selectedProizvod = selectedProizvod;
    }

    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    public String dodajProizvod() {
        noviProizvod.setProizvodjac(logovanaFirma);
        noviProizvod.setDostupan(true);
        FirmaDAO.dodajProizvod(noviProizvod);
        return "firma?faces-redirect=true";

    }

    public boolean isNemaKurira() {
        return nemaKurira;
    }

    public void setNemaKurira(boolean nemaKurira) {
        this.nemaKurira = nemaKurira;
    }

    public Narudzbina getSelectedNarudzbina() {
        return selectedNarudzbina;
    }

    public void setSelectedNarudzbina(Narudzbina selectedNarudzbina) {
        this.selectedNarudzbina = selectedNarudzbina;
    }

    public void sledeciKorak() {
        korakID += 1;
    }

    public void prethodniKorak() {
        korakID -= 1;
    }

    public List<Proizvod> getSviProizvodi() {
        return sviProizvodi;
    }

    public void setSviProizvodi(List<Proizvod> sviProizvodi) {
        this.sviProizvodi = sviProizvodi;
    }

    public void postaviKorak(int i, String k) {
        this.korakID = i;
        this.korak = k;
    }

    public List<Narudzbina> getNarudzbine() {
        return narudzbine;
    }

    public void setNarudzbine(List<Narudzbina> narudzbine) {
        this.narudzbine = narudzbine;
    }

    public Firma getLogovanaFirma() {
        return logovanaFirma;
    }

    public void setLogovanaFirma(Firma logovanaFirma) {
        this.logovanaFirma = logovanaFirma;
    }

    public String getKorak() {
        return korak;
    }

    public void setKorak(String korak) {
        this.korak = korak;
    }

    public int getKorakID() {
        return korakID;
    }

    public void setKorakID(int korakID) {
        this.korakID = korakID;
    }

    public Proizvod getNoviProizvod() {
        return noviProizvod;
    }

    public void setNoviProizvod(Proizvod noviProizvod) {
        this.noviProizvod = noviProizvod;
    }

//    public List<Kurir> getKuriri() {
//        return kuriri;
//    }
//
//    public void setKuriri(List<Kurir> kuriri) {
//        this.kuriri = kuriri;
//    }
    public int getIsporuceno() {
        return isporuceno;
    }

    public void setIsporuceno(int isporuceno) {
        this.isporuceno = isporuceno;
    }

    public int getOdbijeno() {
        return odbijeno;
    }

    public void setOdbijeno(int odbijeno) {
        this.odbijeno = odbijeno;
    }

    public int getPoslato() {
        return poslato;
    }

    public void setPoslato(int poslato) {
        this.poslato = poslato;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

}
