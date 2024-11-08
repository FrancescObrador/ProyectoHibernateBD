package es.ua.eps.hibernate.querying;

import es.ua.eps.hibernate.model.Conexion;
import es.ua.eps.hibernate.model.Perfil;
import es.ua.eps.hibernate.model.Usuario;
import es.ua.eps.hibernate.util.HibernateUtil;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class Ejercicio3 {

    private static void Q10(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q10 ----------");

        List<Conexion> conexiones = session.createNativeQuery(
                "select c.* from conexion c join usuario u on c.id_usuario = u.id_usuario join perfil p on u.id_perfil = p.id_perfil where p.descripcion = 'Premium'", Conexion.class
        ).list();

        for(Conexion conexion : conexiones){
            System.out.println(conexion.getEntra());
        }

        session.getTransaction().commit();

    }

    private static void Q11(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q11 ----------");

        org.hibernate.query.Query query =
                session.createQuery("select c.momentoEntrada from Conexion c where c.usuario.perfil.descripcion = 'Premium'");

        List<Date> conexiones = query.list();

        for(Date conexion : conexiones){
            System.out.println(conexion);
        }

        session.getTransaction().commit();

    }

    private static void Q12(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        System.out.println("---------- Q12 ----------");

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Conexion> criteria = builder.createQuery(Conexion.class);

        Root<Conexion> root = criteria.from(Conexion.class);
        Join<Conexion, Usuario> join_usuario = root.join("usuario", JoinType.INNER);
        Join<Usuario, Perfil> join_perfil = join_usuario.join("perfil", JoinType.INNER);

        criteria.select(root).where(builder.equal(join_perfil.get("descripcion"), "Premium"));

        List<Conexion> conexiones = session.createQuery(criteria).list();

        for(Conexion conexion : conexiones){
            System.out.println(conexion.getEntra());
        }

        session.getTransaction().commit();

    }

    public static void main(String[] args) {
        Q10();
        Q11();
        Q12();
    }
}
