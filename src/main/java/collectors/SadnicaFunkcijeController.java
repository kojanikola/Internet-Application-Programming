/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import beans.Narudzbina;
import beans.Rasadnik;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Koja
 */
@Named
@ViewScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class SadnicaFunkcijeController implements Serializable {

    private List<Narudzbina> sadnice;
    private Rasadnik rasadnik;
    private boolean postojiSadnica;

    public SadnicaFunkcijeController() {
        this.rasadnik = (Rasadnik) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rasadnik");
        sadnice = new LinkedList<Narudzbina>();
        
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
