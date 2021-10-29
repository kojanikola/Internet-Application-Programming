/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Admin;
import beans.Firma;
import beans.Kurir;
import beans.Poljoprivrednik;
import beans.Zahtev_reg_Firma;
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
public class AdminDAO {
    
    public static List<Firma> zahteviFirmi() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        Query q = s.createQuery("from Firma f where f.odobreno=0");
        List firme = q.list();
        
        s.getTransaction().commit();
        s.close();
        return firme;
    }
    
    public static List<Poljoprivrednik> zahteviPoljoprvrednika() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        Query q = s.createQuery("from Poljoprivrednik p where p.odobreno=0");
        List poljoprivrednici = q.list();
        
        s.getTransaction().commit();
        s.close();
        return poljoprivrednici;
    }
    
    public static void izbaciFirmu(Firma selectedFirma) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

//        String pom=selectedFirma.getUsername();
//        Query q= s.createQuery("delete from Firma f where f.username= pom");
//        q.executeUpdate();
        s.delete(selectedFirma);
        
        s.getTransaction().commit();
        s.close();
    }
    
    public static void izbaciPoljoprivrednika(Poljoprivrednik selectedPoljoprivrednik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

//        String pom=selectedFirma.getUsername();
//        Query q= s.createQuery("delete from Firma f where f.username= pom");
//        q.executeUpdate();
        s.delete(selectedPoljoprivrednik);
        
        s.getTransaction().commit();
        s.close();
    }
    
    public static void dodajFirmu(Firma firma) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        s.save(firma);
        
        s.getTransaction().commit();
        s.close();
    }
    
    public static void dodajPolj(Poljoprivrednik polj) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        s.save(polj);
        
        s.getTransaction().commit();
        s.close();
    }
    
    public static void dodajAdmina(Admin admin) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        s.save(admin);
        
        s.getTransaction().commit();
        s.close();
    }
    
    public static void prihvatiFirmu(Firma selectedFirma) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        Query q = s.createQuery("Update Firma f set f.odobreno=1 where f.username=:username");
        q.setParameter("username", selectedFirma.getUsername());
        q.executeUpdate();
        
        s.getTransaction().commit();
        s.close();
    }
    
    public static List<Firma> dohvatiFirme() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        Query q = s.createQuery("from Firma");
        List<Firma> lista = q.list();
        
        s.getTransaction().commit();
        s.close();
        return lista;
    }
    
    public static void prihvatiPoljoprivrednika(Poljoprivrednik selectedPoljoprivrednik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        Query q = s.createQuery("Update Poljoprivrednik p set p.odobreno=1 where p.username=:username");
        q.setParameter("username", selectedPoljoprivrednik.getUsername());
        q.executeUpdate();
        
        s.getTransaction().commit();
        s.close();
    }
    
    public static void zapamtiFirmu(Firma firma) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        s.update(firma);
        
        s.getTransaction().commit();
        s.close();
    }
    
    public static void zapamtiPoljoprivrednika(Poljoprivrednik poljoprivrednik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        s.update(poljoprivrednik);
        
        s.getTransaction().commit();
        s.close();
    }
    
    public static List<Poljoprivrednik> dohvatiPoljoprivrednike() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        Query q = s.createQuery("from Poljoprivrednik");
        List<Poljoprivrednik> lista = q.list();
        
        s.getTransaction().commit();
        s.close();
        return lista;
    }
    
    public static void zapamtiFirme(List<Firma> firme) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        for (Firma f : firme) {
            s.update(f);
        }
        
        s.getTransaction().commit();
        s.close();
        
    }
    
    public static void zapamtiPoljoprivrednike(Poljoprivrednik p) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        s.update(p);
        
        s.getTransaction().commit();
        s.close();
        
    }
    
    public static void dodajKurira(Kurir k) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        s.save(k);
        
        s.getTransaction().commit();
        s.close();
    }
}
