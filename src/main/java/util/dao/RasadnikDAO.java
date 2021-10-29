package util.dao;

import beans.Korisnik;
import beans.MestoURasadniku;
import beans.Narudzbina;
import beans.Poljoprivrednik;
import beans.PorukaZaRasadnik;
import beans.Proizvod;
import beans.Rasadnik;
import beans.Sadnica;
import beans.ZasadjenaSadnica;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
public class RasadnikDAO {

    public static List<Sadnica> sadnice(Rasadnik rasadnik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Criteria crit = s.createCriteria(Sadnica.class);
        crit.add(Restrictions.eq("rasadnik", rasadnik));
        List lista = crit.list();

        s.getTransaction().commit();
        s.close();
        return lista;
    }

    public static List<Narudzbina> dohvatiSadnice(Rasadnik rasadnik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Query q = s.createQuery("from Narudzbina n where n.rasadnik=:rasadnik and isporuceno=1 and n.proizvod.tip='sadnica'");
        q.setParameter("rasadnik", rasadnik);
        List lista = q.list();

        s.getTransaction().commit();
        s.close();
        return lista;
    }

    public static List<Proizvod> dohvatiProizvode(Rasadnik rasadnik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Criteria crit = s.createCriteria(Proizvod.class);
        crit.add(Restrictions.eq("rasadnik", rasadnik));
        List lista = crit.list();

        s.getTransaction().commit();
        s.close();
        return lista;
    }

    public static List<Narudzbina> dohvatiNarudzbine(Rasadnik rasadnik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Criteria crit = s.createCriteria(Narudzbina.class);
        crit.add(Restrictions.eq("rasadnik", rasadnik));
        List lista = crit.list();

        s.getTransaction().commit();
        s.close();
        return lista;
    }

    public static void updateRasadnik(Rasadnik rasadnik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.update(rasadnik);

        s.getTransaction().commit();
        s.close();
    }

    public static void umanji() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Query q = s.createQuery("from Rasadnik");
        List<Rasadnik> rasadnici = q.list();
        for (Rasadnik r : rasadnici) {
            double trenutnaTemperatura = r.getTemperatura();
            trenutnaTemperatura -= 0.5;
            int trenutnoVode = r.getVrVode();
            trenutnoVode -= 1;
            r.setVrVode(trenutnoVode);
            r.setTemperatura(trenutnaTemperatura);
            if (trenutnoVode <= 75 || trenutnaTemperatura <= 12) {
                Criteria criteria = s.createCriteria(PorukaZaRasadnik.class);
                criteria.add(Restrictions.eq("rasadnik", r));
                PorukaZaRasadnik por = (PorukaZaRasadnik) criteria.uniqueResult();
                if (por == null) {
                    Criteria crit = s.createCriteria(Korisnik.class);
                    crit.add(Restrictions.eq("username", r.getVlasnik().getUsername()));
                    Poljoprivrednik polj = (Poljoprivrednik) crit.uniqueResult();
                    PorukaZaRasadnik p = new PorukaZaRasadnik();
                    p.setPoruka("Rasadnik " + r.getNaziv() + " zahteva odrzavanje");
                    p.setVlasnik(polj);
                    p.setRasadnik(r);
                    s.save(p);
                }
            }

        }

        s.getTransaction().commit();
        s.close();
    }

    public static List<ZasadjenaSadnica> dohvatiZasadjeneSadnice(Rasadnik rasadnik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Criteria crit = s.createCriteria(ZasadjenaSadnica.class);
        crit.add(Restrictions.eq("rasadnik", rasadnik));
        List lista = crit.list();

        s.getTransaction().commit();
        s.close();
        return lista;
    }

    public static void zasadiSadnicu(ZasadjenaSadnica sadnica) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.save(sadnica);

        s.getTransaction().commit();
        s.close();
    }

    public static void updateNarudzbine(Narudzbina odabranaSadnica) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.update(odabranaSadnica);

        s.getTransaction().commit();
        s.close();

    }

    public static void updateZasadjenuSadnicu(ZasadjenaSadnica sadnicaZaPrikaz) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.update(sadnicaZaPrikaz);

        s.getTransaction().commit();
        s.close();
    }

    public static List<MestoURasadniku> dohvatiMesta(Rasadnik rasadnik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Criteria crit = s.createCriteria(MestoURasadniku.class);
        crit.add(Restrictions.eq("rasadnik", rasadnik));
        List lista = crit.list();

        s.getTransaction().commit();
        s.close();
        return lista;
    }

    public static void zapamtiMestoURasadniku(MestoURasadniku m) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        s.save(m);

        s.getTransaction().commit();
        s.close();
    }

    public static void obrisiPoruku(Rasadnik rasadnik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Criteria crit = s.createCriteria(PorukaZaRasadnik.class);
        crit.add(Restrictions.eq("rasadnik", rasadnik));
        PorukaZaRasadnik p = (PorukaZaRasadnik) crit.uniqueResult();
        s.delete(p);

        s.getTransaction().commit();
        s.close();
    }

    public static void dodajPoruku(Rasadnik rasadnik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Criteria criteria = s.createCriteria(PorukaZaRasadnik.class);
        criteria.add(Restrictions.eq("rasadnik", rasadnik));
        PorukaZaRasadnik por = (PorukaZaRasadnik) criteria.uniqueResult();
        if (por == null) {
            Criteria crit = s.createCriteria(Korisnik.class);
            crit.add(Restrictions.eq("username", rasadnik.getVlasnik().getUsername()));
            Poljoprivrednik polj = (Poljoprivrednik) crit.uniqueResult();
            PorukaZaRasadnik p = new PorukaZaRasadnik();
            p.setPoruka("Rasadnik " + rasadnik.getNaziv() + " zahteva odrzavanje");
            p.setVlasnik(polj);
            p.setRasadnik(rasadnik);
            s.save(p);
        }

        s.getTransaction().commit();
        s.close();
    }

    public static void obrisiMestoURasadniku(MestoURasadniku m) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        s.delete(m);
        s.getTransaction().commit();
        s.close();
    }

}
