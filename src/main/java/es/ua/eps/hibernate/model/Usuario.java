package es.ua.eps.hibernate.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_usuario", nullable=false)
    private int id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="apellidos")
    private String apellidos;

    @Column(name="email", nullable=false)
    private String email;

    @Column(name="password")
    private byte[] password;

    @Column(name="nacido", nullable=false)
    private Date nacido;

    @ManyToOne
    @JoinColumn(name="id_perfil", nullable=false)
    private Perfil perfil;

    @OneToMany
    @JoinColumn(name="id_usuario")
    private List<Conexion> conexiones;

    @Column(name="apodo")
    private String apodo;

    @OneToOne(mappedBy="usuario")
    private InformacionPublica informacionPublica;

    public int getUsuId(){
        return id;
    }

    public void setUsuId(int id){
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public Date getNacido() {
        return nacido;
    }

    public void setNacido(Date nacido) {
        this.nacido = nacido;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }
}
