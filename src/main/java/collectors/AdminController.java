/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import beans.Admin;
import beans.Firma;
import beans.Poljoprivrednik;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import util.dao.AdminDAO;
import util.dao.FirmaDAO;
import util.dao.KorisnikDao;
import util.dao.PoljoprivrednikDAO;

/**
 *
 * @author Koja
 */
@Named
@ViewScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class AdminController implements Serializable {

    // public static List<Firma> zahteviFirmi2 = AdminDAO.zahteviFirmi();
    private Admin logovaniAdmin;
    private Admin dodajAdmin;
    private Firma dodajFirma;
    private Poljoprivrednik dodajPolj;

    private String msgFirma;
    private String msgPolj;
    private String msgAdmin;
    private String msgPoljZaIzmenu;
    private String msgFirmaZaIzmenu;

    private List<Firma> zahteviFirmi;
    private List<Poljoprivrednik> zahteviPoljoprivrednika;

    private List<Firma> firme;
    private List<Poljoprivrednik> poljoprivrednici;

    private Firma selectedFirma;
    private Poljoprivrednik selectedPoljoprivrednik;

    private Firma firmaZaIzmenu;
    private Poljoprivrednik poljoprivrednikZaIzmenu;

    private boolean zapamtiDugmeZaFirmu;
    private boolean zapamtiDugmeZaPolj;

    public AdminController() {

        logovaniAdmin = (Admin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("korisnik");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tipKorisnika", "admin");
        zahteviFirmi = AdminDAO.zahteviFirmi();
        zahteviPoljoprivrednika = AdminDAO.zahteviPoljoprvrednika();
        dodajFirma = new Firma();
        dodajPolj = new Poljoprivrednik();
        dodajAdmin = new Admin();
        firme = AdminDAO.dohvatiFirme();
        poljoprivrednici = AdminDAO.dohvatiPoljoprivrednike();
        zapamtiDugmeZaFirmu = false;
        zapamtiDugmeZaPolj = false;

    }

    public String promeniSifru() {
        return "promeniSifru?faces-redirect=true";
    }

    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    public void zapamtiFirmu() {

        if (this.firmaZaIzmenu == null) {
            this.msgFirmaZaIzmenu = "Izaberite firmu prvo";
            return;
        }
        AdminDAO.zapamtiFirmu(firmaZaIzmenu);
        firmaZaIzmenu.setIzmeni(false);
        firme = AdminDAO.dohvatiFirme();
    }

    public void zapamtiFirme() {
        AdminDAO.zapamtiFirme(firme);
        for (Firma f : firme) {
            f.setIzmeni(false);
        }
    }

    public void zapamtiPoljoprivrednika() {
        if (poljoprivrednikZaIzmenu == null) {
            this.msgPoljZaIzmenu = "Izaberite poljoprivrendika prvo";
            return;
        }
        AdminDAO.zapamtiPoljoprivrednike(poljoprivrednikZaIzmenu);
        poljoprivrednikZaIzmenu.setIzmeni(false);
        poljoprivrednici = AdminDAO.dohvatiPoljoprivrednike();
    }

    public void izmeniFirmu(Firma firma) {
        firma.setIzmeni(true);
        firmaZaIzmenu = firma;
        zapamtiDugmeZaFirmu = true;
    }

    public void izmeniPoljoprivrednika(Poljoprivrednik poljoprivrednik) {
        poljoprivrednik.setIzmeni(true);
        poljoprivrednikZaIzmenu = poljoprivrednik;
        zapamtiDugmeZaPolj = true;
    }

    public void zapamtiPoljoprivrednika(Poljoprivrednik poljoprivrednik) {
        AdminDAO.zapamtiPoljoprivrednika(poljoprivrednik);
        poljoprivrednik.setIzmeni(false);
        poljoprivrednici = AdminDAO.dohvatiPoljoprivrednike();
    }

    public void prihvatiFirmu() {
        AdminDAO.prihvatiFirmu(selectedFirma);
        zahteviFirmi.remove(selectedFirma);
    }

    public void prihvatiPoljoprivrednika() {
        AdminDAO.prihvatiPoljoprivrednika(selectedPoljoprivrednik);
        zahteviPoljoprivrednika.remove(selectedPoljoprivrednik);
    }

    public void dodajFirmu() {
        dodajFirma.setOdobreno(true);
        dodajFirma.setTip("firma");
        if (KorisnikDao.postojiUsername(dodajFirma.getUsername())) {
            msgFirma = "Username je zauzet, pokusajte drugi";
            return;
        }

        FirmaDAO.dodaj(dodajFirma);
//        for (int i = 0; i < 5; i++) {
//            Kurir k = new Kurir();
//            k.setFirma(dodajFirma);
//            AdminDAO.dodajKurira(k);
//        }
        dodajFirma.setBrSlobodnihKurira(5);
        msgFirma = "Uspesno dodata firma!";
        firme = AdminDAO.dohvatiFirme();
    }

    public void dodajPoljoprivrednika() {
        dodajPolj.setOdobreno(true);
        dodajPolj.setTip("poljoprivrednik");
        if (KorisnikDao.postojiUsername(dodajPolj.getUsername())) {
            msgPolj = "Username je zauzet, pokusajte drugi";
        }
        PoljoprivrednikDAO.dodaj(dodajPolj);
        msgPolj = "Uspesno dodat poljoprivrednik!";
        poljoprivrednici = AdminDAO.dohvatiPoljoprivrednike();
    }

    public void izbaciFirmu() {
        zahteviFirmi.remove(selectedFirma);
        AdminDAO.izbaciFirmu(selectedFirma);
    }

    public void dodajAdmina() {
        dodajAdmin.setOdobreno(true);
        dodajAdmin.setTip("admin");
        if (KorisnikDao.postojiUsername(dodajAdmin.getUsername())) {
            msgFirma = "Username je zauzet, pokusajte drugi";
            return;
        }
        AdminDAO.dodajAdmina(dodajAdmin);
        msgAdmin = "Uspesno dodat administrator!";
    }

    public List<Poljoprivrednik> getPoljoprivrednici() {
        return poljoprivrednici;
    }

    public void setPoljoprivrednici(List<Poljoprivrednik> poljoprivrednici) {
        this.poljoprivrednici = poljoprivrednici;
    }

    public List<Firma> getFirme() {
        return firme;
    }

    public void setFirme(List<Firma> firme) {
        this.firme = firme;
    }

    public Admin getLogovaniAdmin() {
        return logovaniAdmin;
    }

    public void setLogovaniAdmin(Admin logovaniAdmin) {
        this.logovaniAdmin = logovaniAdmin;
    }

    public String getMsgAdmin() {
        return msgAdmin;
    }

    public void setMsgAdmin(String msgAdmin) {
        this.msgAdmin = msgAdmin;
    }

    public void izbaciPoljoprivrednika() {
        zahteviPoljoprivrednika.remove(selectedPoljoprivrednik);
        AdminDAO.izbaciPoljoprivrednika(selectedPoljoprivrednik);
    }

    public Poljoprivrednik getDodajPolj() {
        return dodajPolj;
    }

    public void setDodajPolj(Poljoprivrednik dodajPolj) {
        this.dodajPolj = dodajPolj;
    }

    public String getMsgPolj() {
        return msgPolj;
    }

    public void setMsgPolj(String msgPolj) {
        this.msgPolj = msgPolj;
    }

    public String getMsgFirma() {
        return msgFirma;
    }

    public void setMsgFirma(String msgFirma) {
        this.msgFirma = msgFirma;
    }

    public Admin getDodajAdmin() {
        return dodajAdmin;
    }

    public void setDodajAdmin(Admin dodajAdmin) {
        this.dodajAdmin = dodajAdmin;
    }

    public Firma getDodajFirma() {
        return dodajFirma;
    }

    public void setDodajFirma(Firma dodajFirma) {
        this.dodajFirma = dodajFirma;
    }

    public Firma getSelectedFirma() {
        return selectedFirma;
    }

    public void setSelectedFirma(Firma selectedFirma) {
        this.selectedFirma = selectedFirma;
    }

    public Poljoprivrednik getSelectedPoljoprivrednik() {
        return selectedPoljoprivrednik;
    }

    public void setSelectedPoljoprivrednik(Poljoprivrednik selectedPoljoprivrednik) {
        this.selectedPoljoprivrednik = selectedPoljoprivrednik;
    }

    public List<Firma> getZahteviFirmi() {
        return zahteviFirmi;
    }

    public void setZahteviFirmi(List<Firma> zahteviFirmi) {
        this.zahteviFirmi = zahteviFirmi;
    }

    public List<Poljoprivrednik> getZahteviPoljoprivrednika() {
        return zahteviPoljoprivrednika;
    }

    public void setZahteviPoljoprivrednika(List<Poljoprivrednik> zahteviPoljoprivrednika) {
        this.zahteviPoljoprivrednika = zahteviPoljoprivrednika;
    }

    public Firma getFirmaZaIzmenu() {
        return firmaZaIzmenu;
    }

    public void setFirmaZaIzmenu(Firma firmaZaIzmenu) {
        this.firmaZaIzmenu = firmaZaIzmenu;
    }

    public Poljoprivrednik getPoljoprivrednikZaIzmenu() {
        return poljoprivrednikZaIzmenu;
    }

    public void setPoljoprivrednikZaIzmenu(Poljoprivrednik poljoprivrednikZaIzmenu) {
        this.poljoprivrednikZaIzmenu = poljoprivrednikZaIzmenu;
    }

    public String getMsgPoljZaIzmenu() {
        return msgPoljZaIzmenu;
    }

    public void setMsgPoljZaIzmenu(String msgPoljZaIzmenu) {
        this.msgPoljZaIzmenu = msgPoljZaIzmenu;
    }

    public String getMsgFirmaZaIzmenu() {
        return msgFirmaZaIzmenu;
    }

    public void setMsgFirmaZaIzmenu(String msgFirmaZaIzmenu) {
        this.msgFirmaZaIzmenu = msgFirmaZaIzmenu;
    }

    public boolean isZapamtiDugmeZaFirmu() {
        return zapamtiDugmeZaFirmu;
    }

    public void setZapamtiDugmeZaFirmu(boolean zapamtiDugmeZaFirmu) {
        this.zapamtiDugmeZaFirmu = zapamtiDugmeZaFirmu;
    }

    public boolean isZapamtiDugmeZaPolj() {
        return zapamtiDugmeZaPolj;
    }

    public void setZapamtiDugmeZaPolj(boolean zapamtiDugmeZaPolj) {
        this.zapamtiDugmeZaPolj = zapamtiDugmeZaPolj;
    }

}
