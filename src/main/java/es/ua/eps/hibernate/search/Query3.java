package es.ua.eps.hibernate.search;

import es.ua.eps.hibernate.model.Usuario;
import es.ua.eps.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;

import java.util.List;

public class Query3 {
    public static void main(String[] args) {

        // Devuelva todos los usuarios con variaciones del apellido García con hasta 2 ediciones a nivel de carácter
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        SearchSession searchSession = Search.session(session);
        Transaction transaction = session.beginTransaction();

        List<Usuario> result = searchSession.search(Usuario.class)
                .where(f -> f.match()
                                .field("apellidos")
                                .matching("García")
                        .fuzzy(2)
                ).fetchAllHits();


        transaction.commit();

        for (Usuario usuario : result) {
            System.out.println(usuario);
        }

        session.close();
        HibernateUtil.getSessionFactory().close();
    }
}
