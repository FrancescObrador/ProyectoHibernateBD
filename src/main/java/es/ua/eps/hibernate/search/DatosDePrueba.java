package es.ua.eps.hibernate.search;

import es.ua.eps.hibernate.model.Perfil;
import es.ua.eps.hibernate.model.Usuario;
import es.ua.eps.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DatosDePrueba {

    public static void main(String[] args) {
        Random rnd = new Random();

        //Get Session
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        System.out.println("Session factory creada");
        List<Integer> createdUserIds = new LinkedList<Integer>();

        session.beginTransaction();
        Usuario user=new Usuario();
        user.setNombre("Jaime");
        user.setApellidos("García");
        user.setEmail("jaime.garcia@dlsi.ua.es");
        user.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).getBytes());
        user.setApodo(null);
        int perfil_id = rnd.nextInt(3);
        Perfil perfil = session.get(Perfil.class, perfil_id);
        user.setPerfil(perfil);
        user.setNacido(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));
        session.persist(user);

        user=new Usuario();
        user.setNombre("Jaime");
        user.setApellidos("Peris");
        user.setEmail("jaime.peris@dlsi.ua.es");
        user.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).getBytes());
        user.setApodo(null);
        perfil_id = rnd.nextInt(3);
        perfil = session.get(Perfil.class, perfil_id);
        user.setPerfil(perfil);
        user.setNacido(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));
        session.persist(user);

        user=new Usuario();
        user.setNombre("Clara");
        user.setApellidos("García");
        user.setEmail("clara.garcia@dlsi.ua.es");
        user.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).getBytes());
        user.setApodo(null);
        perfil_id = rnd.nextInt(3);
        perfil = session.get(Perfil.class, perfil_id);
        user.setPerfil(perfil);
        user.setNacido(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));
        session.persist(user);

        user=new Usuario();
        user.setNombre("Francisco");
        user.setApellidos("Gracia");
        user.setEmail("clara.garcia@dlsi.ua.es");
        user.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).getBytes());
        user.setApodo(null);
        perfil_id = rnd.nextInt(3);
        perfil = session.get(Perfil.class, perfil_id);
        user.setPerfil(perfil);
        user.setNacido(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));
        session.persist(user);

        user=new Usuario();
        user.setNombre("Helena");
        user.setApellidos("González");
        user.setEmail("clara.garcia@dlsi.ua.es");
        user.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).getBytes());
        user.setApodo(null);
        perfil_id = rnd.nextInt(3);
        perfil = session.get(Perfil.class, perfil_id);
        user.setPerfil(perfil);
        user.setNacido(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));
        session.persist(user);

        user=new Usuario();
        user.setNombre("Jaime");
        user.setApellidos("Carreras");
        user.setEmail("jaime.carreras@dlsi.ua.es");
        user.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).getBytes());
        user.setApodo(null);
        perfil_id = rnd.nextInt(3);
        perfil = session.get(Perfil.class, perfil_id);
        user.setPerfil(perfil);
        user.setNacido(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));
        session.persist(user);

        user=new Usuario();
        user.setNombre("Laura");
        user.setApellidos("Bernal");
        user.setEmail("laura.bernal@dlsi.ua.es");
        user.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).getBytes());
        user.setApodo(null);
        perfil_id = rnd.nextInt(3);
        perfil = session.get(Perfil.class, perfil_id);
        user.setPerfil(perfil);
        user.setNacido(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));
        session.persist(user);

        user=new Usuario();
        user.setNombre("Severino");
        user.setApellidos("García");
        user.setEmail("severino.garcia@dlsi.ua.es");
        user.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).getBytes());
        user.setApodo(null);
        perfil_id = rnd.nextInt(3);
        perfil = session.get(Perfil.class, perfil_id);
        user.setPerfil(perfil);
        user.setNacido(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));
        session.persist(user);

        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();

        //Generando la indexacion inicial de Lucene SolR
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        SearchSession searchSession = Search.session(session);
        try {
            Transaction tx = session.beginTransaction();
            searchSession.massIndexer()
                    .threadsToLoadObjects(1)
                    .startAndWait();
            tx.commit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.getSessionFactory().close();
        }
    }
}