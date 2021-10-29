/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import beans.Korisnik;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

/**
 *
 * @author Koja
 */
public class LoginDAO {

    public static Korisnik proveri(String username, String password) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        s.beginTransaction();

        Query q = s.createQuery("from Korisnik k where k.username=:username and k.password=:password and k.odobreno=1");
        q.setParameter("username", username);
        q.setParameter("password",password);
        Korisnik korisnik = (Korisnik) q.uniqueResult();

        s.getTransaction().commit();
        s.close();

        return korisnik;
    }

}
