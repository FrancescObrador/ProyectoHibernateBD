package es.ua.eps.hibernate.main;

import es.ua.eps.hibernate.model.Conexion;
import es.ua.eps.hibernate.model.InformacionPublica;
import es.ua.eps.hibernate.model.Perfil;
import es.ua.eps.hibernate.model.Usuario;
import es.ua.eps.hibernate.util.HibernateUtil;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;
import org.hibernate.Session;

import java.util.*;

public class HibernateMain {

    public static final int NUM_USERS = 1000;

    public static List<Integer> CreateUsers() {

        NameGenerator generator = new NameGenerator();
        List<Name> names = generator.generateNames(NUM_USERS);

        List<Integer> createdUserIds = new LinkedList<Integer>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Random rnd = new Random();

        session.beginTransaction();
        for(Name n: names) {
            Usuario user = new Usuario();
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

        session.getTransaction().commit();

        return createdUserIds;
    }

    public static void CreatePublicInfo(List<Integer> createdUserIds) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        for (int idUser : createdUserIds) {
            System.out.println(idUser);
            InformacionPublica info = new InformacionPublica();
            Usuario curUser = session.get(Usuario.class, idUser);
            info.setUsuario(curUser);
            info.setMostrar_email(Math.random() < 0.5);
            info.setMostrar_nacido(Math.random() < 0.5);
            info.setMostrar_nombre(Math.random() < 0.5);

            Random rnd = new Random();
            info.setValoracion(rnd.nextFloat(0,10));

            session.persist(info);
        }

        session.getTransaction().commit();
    }

    private static void CreateFollowUsers(List<Integer> createdUserIds) {

        Random rnd = new Random();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        //Setting connections between users
        List<Integer> randomCreatedUserIds = new LinkedList<Integer>(createdUserIds);
        Collections.shuffle(createdUserIds);
        for(int idUser: createdUserIds) {
            Usuario curUser = session.get(Usuario.class,idUser);
            //Users follow up to 30 people
            int totalFollowing = rnd.nextInt(30) + 1;
            int startingPos = rnd.nextInt(970) +  1;
            Set<Usuario> followed = new HashSet<Usuario>();
            for(int idx: randomCreatedUserIds.subList(startingPos, startingPos+totalFollowing)) {
                Usuario fUser = session.get(Usuario.class,idx);
                followed.add(fUser);
            }
            curUser.setSeguidos(followed);
            session.persist(curUser);
        }

        session.getTransaction().commit();
    }

    private static void CreateConnections(List<Integer> createdUserIds) {

        Random rnd = new Random();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // Create connections
        for(int idUser: createdUserIds) {
            Usuario curUser = session.get(Usuario.class,idUser);
            //Users have up to 100 connections
            for(int connection = rnd.nextInt(100) + 1; connection>0; connection--) {
                Date connectionTime = new Date(-146771200000L + (Math.abs(rnd.nextLong()) % (2L * 365 * 24 * 60 * 60 * 1000)));
                Conexion con = new Conexion();
                con.setEntra(connectionTime);
                con.setUsuario(curUser);
                session.persist(con);
            }
        }

        session.getTransaction().commit();
    }

    public static void main(String[] args) {

        // Create users
        List<Integer> createdUserIds = CreateUsers();

        // Create Follow users
        CreateFollowUsers(createdUserIds);

        // Set Public info
        CreatePublicInfo(createdUserIds);

        // Create connections
        CreateConnections(createdUserIds);

        // Terminate session factory, otherwise program won't end
        HibernateUtil.getSessionFactory().close();
    }
}