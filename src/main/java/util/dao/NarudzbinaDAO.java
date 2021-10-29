/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Narudzbina;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

/**
 *
 * @author Koja
 */
public class NarudzbinaDAO {

    public static void dodajNarudzbine(List<Narudzbina> korpa) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        for (Narudzbina n : korpa) {
            s.save(n);
        }

        s.getTransaction().commit();
        s.close();
    }

    public static void otkaziKorpu(List<Narudzbina> korpa) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        for (Narudzbina n : korpa) {
            s.delete(n);
        }

        s.getTransaction().commit();
        s.close();
    }

    public static void dodajNarudzbinu(Narudzbina novaNarudzbina) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.save(novaNarudzbina);

        s.getTransaction().commit();
        s.close();
    }

    public static void povecajKolicinu(Narudzbina n) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.update(n);

        s.getTransaction().commit();
        s.close();
    }

}
