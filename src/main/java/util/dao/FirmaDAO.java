/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Firma;
import beans.Kurir;
import beans.Narudzbina;
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
public class FirmaDAO {

    public static void dodaj(Firma firma) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        try {
            s.beginTransaction();

            s.save(firma);
            s.getTransaction().commit();

        } finally {
            s.close();
        }
    }

    public static void dodajProizvod(Proizvod p) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        try {
            s.beginTransaction();

            s.save(p);
            s.getTransaction().commit();
        } finally {

            s.close();
        }
    }

    public static List<Proizvod> dohvatiProizvode(Firma logovanaFirma) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        List lista = null;
        try {
            s.beginTransaction();

            Criteria crit = s.createCriteria(Proizvod.class);
            crit.add(Restrictions.eq("proizvodjac", logovanaFirma));
            lista = crit.list();
            s.getTransaction().commit();
        } finally {

            s.close();
        }
        return lista;
    }

    public static void povuciProizvod(Proizvod selectedProizvod) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        try {
            s.beginTransaction();

            Query q = s.createQuery("Update Proizvod p set p.dostupan=0 where p.id=:id");
            q.setParameter("id", selectedProizvod.getId());
            q.executeUpdate();
            s.getTransaction().commit();
        } finally {

            s.close();
        }
    }

    public static void obrisiProizvod(Proizvod selectedProizvod) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        try {
            s.beginTransaction();

            s.delete(selectedProizvod);
            s.getTransaction().commit();
        } finally {

            s.close();
        }
    }

    public static void promeniDostupnost(Proizvod selectedProizvod) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        try {
            s.beginTransaction();

            s.update(selectedProizvod);
            s.getTransaction().commit();

        } finally {
            s.close();
        }
    }

    public static List<Narudzbina> dohvatiNarudzbine(Firma logovanaFirma) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        List lista = null;
        try {
            s.beginTransaction();

            Query q = s.createQuery("from Narudzbina n where n.proizvod.proizvodjac=:logovanaFirma and n.isporuceno=false");
            q.setParameter("logovanaFirma", logovanaFirma);
            lista = q.list();
            s.getTransaction().commit();
        } finally {

            s.close();
        }
        return lista;
    }

    public static List<Kurir> dohvatiKurire(Firma logovanaFirma) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        List lista = null;
        try {
            s.beginTransaction();

            Query q = s.createQuery("from Kurir k where k.firma=:logovanaFirma");
            q.setParameter("logovanaFirma", logovanaFirma);
            lista = q.list();
            s.getTransaction().commit();
        } finally {

            s.close();
        }
        return lista;
    }

    public static void prihvatiNarudzbinu(Narudzbina n) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        try {
            s.beginTransaction();

            s.update(n);
            s.getTransaction().commit();

        } finally {
            s.close();
        }
    }

    public static void otkaziNarudzbinu(Narudzbina narudzbina) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        try {
            s.beginTransaction();
            s.delete(narudzbina);
            s.getTransaction().commit();

        } finally {
            s.close();
        }
    }

    public static void updateFirma(Firma logovanaFirma) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();

        try {
            s.beginTransaction();
            s.update(logovanaFirma);
            s.getTransaction().commit();
        } finally {

            s.close();
        }
    }

    public static List<Narudzbina> dohvatiSveNaruzbine(Firma logovanaFirma) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        List lista = null;
        try {
            s.beginTransaction();

            Query q = s.createQuery("from Narudzbina n where n.proizvod.proizvodjac=:logovanaFirma");
            q.setParameter("logovanaFirma", logovanaFirma);
            lista = q.list();
            s.getTransaction().commit();
        } finally {

            s.close();
        }
        return lista;
    }
}
