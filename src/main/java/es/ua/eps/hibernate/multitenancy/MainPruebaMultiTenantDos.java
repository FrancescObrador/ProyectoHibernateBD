package es.ua.eps.hibernate.multitenancy;
import org.hibernate.Session;
import es.ua.eps.hibernate.model.Usuario;
import es.ua.eps.hibernate.util.HibernateUtil;

import javax.management.Query;
import java.util.List;

public class MainPruebaMultiTenantDos {
    public static void main(String[] args) {
        // Activamos el tenant P05user1
        TenantIdentifierResolver.setTenantID("P05user1");

        // Intentamos acceder a un usuario del tenant P05user2
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        org.hibernate.query.Query query = session.createQuery(
                        "select u from Usuario u where nombre = 'Jimmy'");

        List<Usuario> users = query.list();

        if (users.size() == 0) {
            System.out.println("No se pudo a ningun usuario desde el tenant P05user1.");
        } else {
            System.out.println("Usuario o usuarios encontrados");
            System.out.println(users.getFirst().getNombre());
        }

        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
    }
}
