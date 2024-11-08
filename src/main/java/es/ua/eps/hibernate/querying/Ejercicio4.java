package es.ua.eps.hibernate.querying;

import es.ua.eps.hibernate.model.Conexion;
import es.ua.eps.hibernate.model.Perfil;
import es.ua.eps.hibernate.model.Usuario;
import es.ua.eps.hibernate.util.HibernateUtil;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class Ejercicio4 {

    private static void Q13(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q13 ----------");

        org.hibernate.query.Query query =
                session.createQuery("delete from Conexion c where c.usuario.perfil.descripcion = 'Básico'");

        int count = query.executeUpdate();

        System.out.println(count);

        session.getTransaction().rollback();

    }

    private static void Q14(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q14 ----------");

        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaDelete<Conexion> deleteConexiones = builder.createCriteriaDelete(Conexion.class);

        Root<Conexion> root = deleteConexiones.from(Conexion.class);

        deleteConexiones.where((builder.equal(root.get("usuario").get("perfil").get("descripcion"), "Básico")));

        int deleteCount = session.createQuery(deleteConexiones).executeUpdate();

        System.out.println(deleteCount);

        session.getTransaction().rollback();

    }
    public static void main(String[] args) {
        //Q13();
        Q14();
    }
}
