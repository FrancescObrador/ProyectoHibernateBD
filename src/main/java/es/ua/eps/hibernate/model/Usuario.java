package es.ua.eps.hibernate.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="usuario")
@Table(name="usuario")
public class Usuario implements Serializable {

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

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private List<Conexion> conexiones;

    @Column(name="apodo")
    private String apodo;

    @OneToOne(mappedBy="usuario")
    private InformacionPublica informacionPublica;

    @ManyToMany
    @JoinTable(name = "sigue",
            joinColumns = @JoinColumn(name = "id_seguidor"),
            inverseJoinColumns = @JoinColumn(name="id_seguido"))
    private Set<Usuario> seguidos = new HashSet<>();

    @ManyToMany(mappedBy = "seguidos")
    private Set<Usuario> seguidores = new HashSet<>();

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

    public Set<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(Set<Usuario> seguidores) {
        this.seguidores = seguidores;
    }

    public Set<Usuario> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(Set<Usuario> seguidos) {
        this.seguidos = seguidos;
    }

    public List<Conexion> getConexiones() {
        return conexiones;
    }

    public void setConexiones(List<Conexion> conexiones) {
        this.conexiones = conexiones;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", perfil=" + perfil +
                '}';
    }
}
