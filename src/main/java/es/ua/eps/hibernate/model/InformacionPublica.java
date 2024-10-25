package es.ua.eps.hibernate.model;

import jakarta.persistence.*;

@Entity
@Table(name="informacion_publica")
public class InformacionPublica {
    @Id
    @Column(name="id_usuario")
    private int id;

    @OneToOne
    @JoinColumn(name="id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "mostrar_email")
    private boolean mostrarEmail;

    @Column(name = "mostrar_nacido")
    private boolean mostrarNacido;

    @Column(name = "mostar_nombre")
    private boolean mostarNombre;

    @Column(name = "valoracion")
    private float valoracion;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean getMostrar_email() {
        return mostrarEmail;
    }

    public void setMostrar_email(boolean mostrarEmail) {
        this.mostrarEmail = mostrarEmail;
    }

    public boolean getMostar_nacido() {
        return mostrarNacido;
    }

    public void setMostrar_nacido(boolean mostrarNacido) {
        this.mostrarNacido = mostrarNacido;
    }

    public boolean getMostrar_nombre() {
        return mostarNombre;
    }

    public void setMostrar_nombre(boolean mostrar_nombre) {
        this.mostarNombre = mostrar_nombre;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }
}
