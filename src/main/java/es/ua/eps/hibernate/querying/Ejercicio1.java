package es.ua.eps.hibernate.querying;

import es.ua.eps.hibernate.model.Conexion;
import es.ua.eps.hibernate.model.Usuario;
import es.ua.eps.hibernate.util.HibernateUtil;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Ejercicio1 {

    private static int id = 34991;

    private static void Q1(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q1 ----------");

        Usuario usuario = session.get(Usuario.class, id);
        List<Conexion> conexiones = usuario.getConexiones();
        for(Conexion conexion : conexiones){
            System.out.println(conexion.getEntra());
        }

        session.getTransaction().commit();
    }

    private static void Q2(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q2 ----------");

        List<Object> momentoEntradas = session.createNativeQuery(
                        "SELECT momento_entrada from conexion WHERE id_usuario = :id")
                .setParameter("id", id)
                .list();

        for(Object entrada : momentoEntradas){
            System.out.println(entrada.toString());
        }

        session.getTransaction().commit();
    }

    private static void Q3(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q3 ----------");

        // select de todas las conexiones entre el 1965-02-01 y hoy.
        List<Conexion> conexiones = session.createNativeQuery(
                "SELECT * FROM conexion " +
                        "WHERE  momento_entrada BETWEEN '1965-02-01' AND GETDATE() ", Conexion.class)
                .list();

        for(Conexion conexion : conexiones){
            Usuario usuario = conexion.getUsuario();
            System.out.println("Nombre: " + usuario.getNombre() + " Apellido: " + usuario.getApellidos() + " Perfil: " + usuario.getPerfil().getDescripcion() + " Conexion: " + conexion.getEntra());
        }

        session.getTransaction().commit();
    }

    private static void Q4(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q4 ----------");

        org.hibernate.query.Query query = session.createQuery(
                "select c.momentoEntrada from Conexion c where c.usuario.id = :id order by c.momentoEntrada desc")
                .setParameter("id", id);

        List<Date> result = query.list();

        for(Date conexion : result){
            System.out.println(conexion);
        }


        session.getTransaction().commit();
    }

    private static void Q5(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q5 ----------");

        Timestamp fechaEntrada = Timestamp.valueOf("1966-03-01 00:00:00");

        org.hibernate.query.Query query = session.createQuery(
                        "select c from Conexion c where c.momentoEntrada > :fecha and c.usuario.id = :id ")
                .setParameter("fecha", fechaEntrada)
                .setParameter("id", id);

        List<Conexion> result = query.list();

        for(Conexion conexion : result){
            System.out.println(conexion.getEntra());
        }

        session.getTransaction().commit();
    }

    private static void Q6(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q6 ----------");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);

        Root<Conexion> root = query.from(Conexion.class);
        Path<Date> fecha = root.get("momentoEntrada");

        query.multiselect(fecha);
        query.where(builder.equal(root.get("usuario").get("id"), id));

        List<Tuple> fechas = session.createQuery(query).list();

        for (Tuple tuple : fechas) {
            System.out.println(tuple.get(fecha));
        }


        session.getTransaction().commit();
    }

    private static void Q7(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q7 ----------");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Conexion> criteria = builder.createQuery(Conexion.class);
        Root<Conexion> root = criteria.from(Conexion.class);

        criteria.select(root);

        criteria.where(builder.equal(root.get("usuario").get("id"), id));

        List<Conexion> conexiones = session.createQuery(criteria).list();

        for(Conexion conexion : conexiones){
            System.out.println(conexion.getEntra());
        }

        session.getTransaction().commit();
    }

    public static void main(String[] args) {
        Q1();
        Q2();
        Q3();
        Q4();
        Q5();
        Q6();
        Q7();
    }
}
