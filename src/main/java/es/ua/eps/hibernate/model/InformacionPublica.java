package es.ua.eps.hibernate.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

@Entity
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="informacionPublica")
@Table(name="informacion_publica")
public class InformacionPublica implements Serializable {
    //@Id
    //@Column(name="id_usuario")
    //private int id;
    @Id
    @OneToOne
    @JoinColumn(name="id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "mostrar_email")
    private boolean mostrarEmail;

    @Column(name = "mostrar_nacido")
    private boolean mostrarNacido;

    @Column(name = "mostrar_nombre")
    private boolean mostrarNombre;

    @Column(name = "valoracion")
    private float valoracion;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean getMostrarEmail() {
        return mostrarEmail;
    }

    public void setMostrar_email(boolean mostrarEmail) {
        this.mostrarEmail = mostrarEmail;
    }

    public boolean getMostrarNacido() {
        return mostrarNacido;
    }

    public void setMostrar_nacido(boolean mostrarNacido) {
        this.mostrarNacido = mostrarNacido;
    }

    public boolean getMostrarNombre() {
        return mostrarNombre;
    }

    public void setMostrar_nombre(boolean mostrar_nombre) {
        this.mostrarNombre = mostrar_nombre;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }
}
