/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Zahtev_reg_Firma;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

/**
 *
 * @author Koja
 */
public class Registracija_FirmaDAO {

    public static boolean registruj(Zahtev_reg_Firma zahtev) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.save(zahtev);

        s.getTransaction().commit();
        s.close();

        return true;
    }

}
