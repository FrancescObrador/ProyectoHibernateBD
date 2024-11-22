package es.ua.eps.hibernate.search;

import es.ua.eps.hibernate.model.Usuario;
import es.ua.eps.hibernate.util.HibernateUtil;
import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;

import java.util.List;

public class Query1 {
    public static void main(String[] args) {
        // Devuelva todos los usuarios con nombre Laura
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        SearchSession searchSession = Search.session(session);
        Transaction transaction = session.beginTransaction();

        List<Usuario> result = searchSession.search(Usuario.class)
                .where(f -> f.match()
                        .field("nombre")
                        .matching("Laura")
                ).fetchAllHits();
        transaction.commit();

        for (Usuario usuario : result) {
            System.out.println(usuario);
        }

        session.close();
        HibernateUtil.getSessionFactory().close();

    }
}
