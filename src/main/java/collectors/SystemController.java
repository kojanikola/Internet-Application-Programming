/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collectors;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import util.dao.RasadnikDAO;

/**
 *
 * @author Koja
 */
@ApplicationScoped
@ManagedBean(eager = true)
public class SystemController {

    Thread tr = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000 * 60 * 60);
                    RasadnikDAO.umanji();
                } catch (InterruptedException ie) {
                }
            }
        }
    };

    @PostConstruct
    public void init() {
        tr.start();
    }

}
