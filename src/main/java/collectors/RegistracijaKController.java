package collectors;

import beans.Poljoprivrednik;
import beans.Zahtev_reg_Firma;
import beans.Zahtev_reg_Poljoprivrednik;
import java.io.Serializable;
import java.util.Date;
import javax.faces.annotation.FacesConfig;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import util.dao.KorisnikDao;
import util.dao.PoljoprivrednikDAO;
import util.dao.Registracija_PoljoprivrednikDAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Koja
 */
@Named
@javax.enterprise.context.SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class RegistracijaKController implements Serializable {

    private String ime;
    private String prezime;
    private String username;
    private String password;
    private Date datumRodjenja;
    private String mestoRodjenja;
    private String telefon;
    private String email;
    private String msg;

    public String registruj() {

        if (KorisnikDao.postojiUsername(username)) {
            msg = "Username je zauzet pokusajte novim!";
            return null;
        }
//        Zahtev_reg_Poljoprivrednik zahtev = new Zahtev_reg_Poljoprivrednik();
//        zahtev.setIme(ime);
//        zahtev.setPrezime(prezime);
//        zahtev.setUsername(username);
//        zahtev.setPassword(password);
//        zahtev.setDatumRodjenja(datumRodjenja);
//        zahtev.setBrojTelefona(telefon);
//        zahtev.setMestoRodjenja(mestoRodjenja);
//        zahtev.setEmail(email);

        Poljoprivrednik poljoprivrednik = new Poljoprivrednik();
        poljoprivrednik.setIme(ime);
        poljoprivrednik.setPrezime(prezime);
        poljoprivrednik.setUsername(username);
        poljoprivrednik.setPassword(password);
        poljoprivrednik.setMestoRodjenja(mestoRodjenja);
        poljoprivrednik.setDatumRodjenja(datumRodjenja);
        poljoprivrednik.setOdobreno(false);
        poljoprivrednik.setTelefon(telefon);
        poljoprivrednik.setTip("poljoprivrednik");
        poljoprivrednik.setEmail(email);

        msg = "Uspesno dodat zahtev!";

        PoljoprivrednikDAO.dodaj(poljoprivrednik);
        //Registracija_PoljoprivrednikDAO.registruj(zahtev);

        return null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
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

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getMestoRodjenja() {
        return mestoRodjenja;
    }

    public void setMestoRodjenja(String mestoRodjenja) {
        this.mestoRodjenja = mestoRodjenja;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
