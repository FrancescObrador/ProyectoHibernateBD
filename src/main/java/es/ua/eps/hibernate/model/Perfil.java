package es.ua.eps.hibernate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="perfil")
public class Perfil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_perfil")
    @NotNull(message = "id cannot be null")
    private int id;

    // Mapped by usa el nombre de variable del otro POJO?
    @OneToMany(mappedBy = "perfil")
    @NotEmpty(message = "perfil cannot be empty")
    private Set<Usuario> usuarios = new HashSet<>();

    @Column(name="descripcion", length=20)
    @Max(value = 20, message = "description cannot be larger than 20 characters")
    private String descripcion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
