package es.ua.eps.hibernate.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="conexion")
public class Conexion implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    @Id
    @Column(name="momento_entrada")
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
}
