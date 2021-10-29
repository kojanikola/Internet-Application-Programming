/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Narudzbina;
import beans.Poljoprivrednik;
import beans.Proizvod;
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
public class ProdavnicaDAO {

    public static List<Proizvod> dohvatiProizvode() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Query q = s.createQuery("from Proizvod p where p.dostupan=1");
        List lista = q.list();

        s.getTransaction().commit();
        s.close();
        return lista;
    }

    public static void updateProizvod(Proizvod p) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.update(p);

        s.getTransaction().commit();
        s.close();
    }

    public static Proizvod dohvatiOdredjeniProizvod(Proizvod p) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Criteria crit = s.createCriteria(Proizvod.class);
        crit.add(Restrictions.eq("id", p.getId()));
        Proizvod proizvod = (Proizvod) crit.uniqueResult();

        s.getTransaction().commit();
        s.close();
        return proizvod;
    }

    public static void otkaziNarudzbinu(Narudzbina n) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.delete(n);

        s.getTransaction().commit();
        s.close();
    }
    

    public static boolean jeNarucivao(Proizvod proizvod, Poljoprivrednik poljoprivrednik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Query q = s.createQuery("from Narudzbina f where f.proizvod=:proizvod and f.rasadnik.vlasnik=:poljoprivrednik and f.isporuceno=1");
        q.setParameter("proizvod", proizvod);
        q.setParameter("poljoprivrednik", poljoprivrednik);
        List lista = q.list();

        s.getTransaction().commit();
        s.close();
        if (lista.isEmpty()) {
            return false;
        }
        return true;

    }

    public static void odbaciNarudzbinu(Narudzbina selectedNarudzbina) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        try{
            s.beginTransaction();
            s.saveOrUpdate(selectedNarudzbina);
            s.getTransaction().commit();
        }finally{
            s.close();
        }
    }

}
