/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Poljoprivrednik;
import beans.PorukaZaRasadnik;
import beans.Proizvod;
import beans.Rasadnik;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

/**
 *
 * @author Koja
 */
public class PoljoprivrednikDAO {

    public static void dodaj(Poljoprivrednik poljoprivrednik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.save(poljoprivrednik);

        s.getTransaction().commit();
        s.close();
    }

    public static void dodajRasadnik(Rasadnik dodajRasadnik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.save(dodajRasadnik);

        s.getTransaction().commit();
        s.close();
    }

    public static List<Rasadnik> dohvatiRasadnike(Poljoprivrednik poljoprivrednik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Criteria crit = s.createCriteria(Rasadnik.class);
        crit.add(Restrictions.eq("vlasnik", poljoprivrednik));
        List lista = crit.list();

        s.getTransaction().commit();
        s.close();
        return lista;
    }

    public static List<Proizvod> dohvatiProizvode(Poljoprivrednik logovaniPoljoprivrednik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Criteria crit = s.createCriteria(Rasadnik.class);
        crit.add(Restrictions.eq("vlasnik", logovaniPoljoprivrednik));
        List lista = crit.list();

        s.getTransaction().commit();
        s.close();
        return lista;
    }

    public static List<PorukaZaRasadnik> dohvatiPoruke(Poljoprivrednik logovaniPoljoprivrednik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Criteria crit = s.createCriteria(PorukaZaRasadnik.class);
        crit.add(Restrictions.eq("vlasnik", logovaniPoljoprivrednik));
        List lista = crit.list();

        s.getTransaction().commit();
        s.close();
        return lista;
    }

}
