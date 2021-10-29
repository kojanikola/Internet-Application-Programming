/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import beans.Firma;
import beans.Zahtev_reg_Firma;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import javax.validation.constraints.Email;
import util.dao.FirmaDAO;
import util.dao.KorisnikDao;
import util.dao.Registracija_FirmaDAO;

/**
 *
 * @author Koja
 */
@Named
@SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class RegistracijaFController implements Serializable {

    private String ime;
    private String username;
    private String password;
    private Date datumRodjenja;
    private String mesto;
    private String email;
    private String msg;

    public String registruj() {

        if (KorisnikDao.postojiUsername(username)) {
            this.msg = "Username je zauzet pokusajte novim!";
            return null;
        }
        Firma firma = new Firma();
        firma.setUsername(username);
        firma.setPassword(password);
        firma.setOdobreno(false);
        firma.setEmail(email);
        firma.setIme(ime);
        firma.setDatumOsnivanja(datumRodjenja);
        firma.setMesto(mesto);
        firma.setTip("firma");

        msg = "Uspesno dodat zahtev!";
        FirmaDAO.dodaj(firma);
        //Registracija_FirmaDAO.registruj(zahtev);

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

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
