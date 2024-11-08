package es.ua.eps.hibernate.querying;

import es.ua.eps.hibernate.model.Conexion;
import es.ua.eps.hibernate.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class Ejercicio2 {

    private static int id = 34991;

    private static void Q8(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q8 ----------");


        org.hibernate.query.Query query = session.createQuery(
                "select Year(c.momentoEntrada) as y, count(c) from Conexion c " +
                        "where c.usuario.id = :id group by y"
        ).setParameter("id", id);

        List<Object[]> results = query.list();

        for (Object[] o : results) {
            System.out.println(o[0] + " " + o[1]);
        }

        session.getTransaction().commit();

    }

    private static void Q9(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q9 ----------");

        org.hibernate.query.Query query = session.createQuery(
                "select count(c) from Conexion c where c.usuario.id = :id"
        ).setParameter("id", id);

        float count = (Long)query.uniqueResult();

        System.out.println(count);

        session.getTransaction().commit();

    }

    public static void main(String[] args) {
        Q8();
        Q9();
    }
}
