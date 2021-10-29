/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Korisnik;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

/**
 *
 * @author Koja
 */
public class KorisnikDao {
    
    public static boolean postojiUsername(String username) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        Korisnik k = null;
        
        k = (Korisnik) s.get(Korisnik.class, username);
        
        s.getTransaction().commit();
        s.close();
        
        if (k != null) {
            return true;
        }
        return false;
    }
    
    public static void zapamtiKorisnika(Korisnik ulogovaniKorisnik) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();
        
        s.update(ulogovaniKorisnik);
        
        s.getTransaction().commit();
        s.close();
        
    }
}
