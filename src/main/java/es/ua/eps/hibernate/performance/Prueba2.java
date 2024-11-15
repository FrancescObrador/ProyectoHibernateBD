package es.ua.eps.hibernate.performance;

import es.ua.eps.hibernate.model.Conexion;
import es.ua.eps.hibernate.model.Usuario;
import es.ua.eps.hibernate.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class Prueba2 {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        System.out.println("Session factory creada");

        session.beginTransaction();

        org.hibernate.query.Query query = session.createQuery(
                "select u from Usuario u where u.id between 29690 and 29700"
        );


        List<Usuario> usuarios = query.list();

        //
        System.out.println("-----------------------");
        for (Usuario u : usuarios){
            for(Conexion c : u.getConexiones()){
                c.getEntra();
            }
        }

        session.getTransaction().commit();
        //terminate session factory, otherwise program won't end
        HibernateUtil.getSessionFactory().close();
    }
}
