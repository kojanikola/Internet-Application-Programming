package util.dao;

import beans.Zahtev_reg_Firma;
import beans.Zahtev_reg_Poljoprivrednik;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Koja
 */
public class Registracija_PoljoprivrednikDAO {
    
     public static boolean registruj(Zahtev_reg_Poljoprivrednik zahtev) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.save(zahtev);

        s.getTransaction().commit();
        s.close();

        return true;
    }
    
}
