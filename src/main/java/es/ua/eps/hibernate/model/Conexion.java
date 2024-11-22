package es.ua.eps.hibernate.model;

import es.ua.eps.hibernate.util.HibernateUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.Date;

@Entity
@NamedNativeQuery(
        name="Q2", // Native Query
        query = "SELECT momento_entrada FROM conexion WHERE id_usuario = :id"
)
@NamedQuery(
        name = "Q11", // HQL
        query = "select c.momentoEntrada from Conexion c where c.usuario.perfil.descripcion = :perfil"
)
@NamedQuery(
        name = "Q13", // HQL
        query = "DELETE FROM Conexion c WHERE c.usuario.perfil.descripcion = :perfil"
)
@Table(name="conexion")
public class Conexion implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="id_usuario")
    //@NotNull(message = "usuario cannot be null")
    private Usuario usuario;

    @Id
    @Column(name="momento_entrada")
    @PastOrPresent(message = "momentoEntrada cannot be in the future")
    private Date momentoEntrada;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getEntra() {
        return momentoEntrada;
    }

    public void setEntra(Date momentoEntrada) {
        this.momentoEntrada = momentoEntrada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}