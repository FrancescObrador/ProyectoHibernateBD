package es.ua.eps.hibernate.main;

import es.ua.eps.hibernate.model.InformacionPublica;
import es.ua.eps.hibernate.model.Perfil;
import es.ua.eps.hibernate.model.Usuario;
import es.ua.eps.hibernate.util.HibernateUtil;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;
import org.hibernate.Session;
import org.hibernate.validator.*;
import jakarta.validation.*;

import java.util.*;

public class ValidationMain {

    public static void main(String[] args) {

        NameGenerator generator = new NameGenerator();
        List<Name> names = generator.generateNames(10);

        List<Integer> createdUserIds = new LinkedList<Integer>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Random rnd = new Random();

        session.beginTransaction();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

        for(Name n: names) {
            Usuario user = new Usuario();
            user.setNombre(n.getFirstName());
            user.setApellidos(n.getLastName());
            user.setEmail(n.getFirstName().toLowerCase()+"."+n.getLastName().toLowerCase()+"dlsi.ua.es");
            user.setPassword(Long.toHexString(Double.doubleToLongBits(Math.random())).getBytes());
            user.setApodo(null);
            int perfil_id = rnd.nextInt(3);
            Perfil perfil = session.get(Perfil.class, perfil_id);
            user.setPerfil(perfil);
            user.setNacido(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));

            Set<ConstraintViolation<Usuario>> violationsUsuario = validator.validate(user);
            // error para todos los usuarios pues: apodo es nulo y email está mal
            System.out.println(violationsUsuario);

            if(violationsUsuario.isEmpty()) {
                session.persist(user);
            }
            createdUserIds.add(user.getUsuId());

        }

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        for (int idUser : createdUserIds) {
            System.out.println(idUser);
            InformacionPublica info = new InformacionPublica();
            Usuario curUser = session.get(Usuario.class, idUser);
            info.setUsuario(curUser);


            rnd = new Random();
            // Asi algunos darán error de que no puede ser 0 o menos
            info.setValoracion(rnd.nextFloat(-10,10));

            Set<ConstraintViolation<InformacionPublica>> violationsInfoUsu = validator.validate(info);
            System.out.println(violationsInfoUsu);

            if(violationsInfoUsu.isEmpty()) {
                session.persist(info);
            }
        }

        session.getTransaction().rollback();
    }
}
