package es.ua.eps.hibernate.multitenancy;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;
import org.hibernate.Session;

import es.ua.eps.hibernate.model.Perfil;
import es.ua.eps.hibernate.model.Usuario;
import es.ua.eps.hibernate.util.HibernateUtil;

public class MainPruebaMultiTenancy {

    public static void main(String[] args) {

        NameGenerator generator = new NameGenerator();
        List<Name> names = generator.generateNames( 10 );
        Random rnd = new Random();
        List<Integer> createdUserIds = new LinkedList<Integer>();

        /**************** Activamos el tenant P05user1 *******************/
        TenantIdentifierResolver.setTenantID("P05user1");

        //Añadimos 10 nuevos usuarios
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for(Name n: names) {
            Usuario user=new Usuario();
            user.setNombre(n.getFirstName());
            user.setApellidos(n.getLastName());
            user.setEmail(n.getFirstName().toLowerCase()+"."+n.getLastName().toLowerCase()+"@dlsi.ua.es");
            user.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).getBytes());
            user.setApodo(null);
            int perfil_id = rnd.nextInt(3);
            Perfil perfil = session.get(Perfil.class, perfil_id);
            user.setPerfil(perfil);
            user.setNacido(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));
            session.persist(user);
            createdUserIds.add(user.getUsuId());
        }
        //Forcing to write to the DB and cleaning 1st level cache
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();

        //Volvemos a abrir la sesión y leemos los ID de los usuarios que hemos creado
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        //Consultamos los 10 usuarios que hemos creado
        for(int idUser: createdUserIds) {
            Usuario curUser = session.get(Usuario.class,idUser);
            System.out.println(curUser.getUsuId());
        }

        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();



        /**************** Activamos el tenant P05user2 *******************/
        TenantIdentifierResolver.setTenantID("P05user2");
        createdUserIds.clear();

        //Añadimos otros 10 usuarios nuevos
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for(Name n: names) {
            Usuario user=new Usuario();
            user.setNombre(n.getFirstName());
            user.setApellidos(n.getLastName());
            user.setEmail(n.getFirstName().toLowerCase()+"."+n.getLastName().toLowerCase()+"@dlsi.ua.es");
            user.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).getBytes());
            user.setApodo(null);
            int perfil_id = rnd.nextInt(3);
            Perfil perfil = session.get(Perfil.class, perfil_id);
            user.setPerfil(perfil);
            user.setNacido(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));
            session.persist(user);
            createdUserIds.add(user.getUsuId());
        }

        //Forcing to write to the DB and cleaning 1st level cache
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();

        //Volvemos a abrir la sesión y leemos los ID de los usuarios que hemos creado
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        //Consultamos los 10 usuarios que hemos creado
        for(int idUser: createdUserIds) {
            Usuario curUser = session.get(Usuario.class,idUser);
            System.out.println(curUser.getUsuId());
        }
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();
    }
}