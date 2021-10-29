/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Komentar;
import beans.Proizvod;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

/**
 *
 * @author Koja
 */
public class ProizvodDAO {

    public static List<Komentar> dohvatiKomentare(Proizvod proizvod) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Query q = s.createQuery("from Komentar k where k.proizvod=:proizvod");
        q.setParameter("proizvod", proizvod);
        List komentari = q.list();

        s.getTransaction().commit();
        s.close();
        return komentari;
    }

    public static void ostaviKomentar(Komentar komentar) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.save(komentar);

        s.getTransaction().commit();
        s.close();
    }

    public static void zapamtiProizvod(Proizvod proizvod) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.update(proizvod);

        s.getTransaction().commit();
        s.close();
    }

}
