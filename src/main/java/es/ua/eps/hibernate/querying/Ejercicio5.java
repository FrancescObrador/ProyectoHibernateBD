package es.ua.eps.hibernate.querying;

import es.ua.eps.hibernate.model.Conexion;
import es.ua.eps.hibernate.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Ejercicio5 {

    private static int id = 2001;

    private static void setup(){
        id = new Random().nextInt(2001, 3000);
    }


    private static void Q2(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q2 (E5) ----------");

        List<Date> entradas = session.getNamedQuery("Q2")
                .setParameter("id", id)
                .list();

        System.out.println(entradas);

        session.getTransaction().rollback();
    }

    private static void Q11(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q11 (E5) ----------");
        List<Date> entradas = session.getNamedQuery("Q11")
                .setParameter("perfil", "Premium")
                .list();

        System.out.println(entradas);

        session.getTransaction().rollback();
    }

    private static void Q13(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q13 (E5) ----------");
        int deleteCount = session.getNamedQuery("Q13").setParameter("perfil", "Básico").executeUpdate();

        System.out.println(deleteCount);

        session.getTransaction().rollback();
    }

    public static void main(String[] args) {
        setup();
        Q2();
        Q11();
        Q13();

    }
}
