package br.rosa.redis_ex.domain;

import br.rosa.redis_ex.domain.converter.SexoConverter;
import br.rosa.redis_ex.domain.converter.SimNaoBooleanConverter;
import br.rosa.redis_ex.domain.enumerator.SexoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "author")
public class Author implements Serializable {

    private static final long serialVersionUID = 7092489470123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private LocalDate birthday;

    @Convert(converter = SimNaoBooleanConverter.class)
    private Boolean active;

    @Convert(converter = SexoConverter.class)
    @Column(name = "gender")
    private SexoEnum sexo;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Post> listaPost;

    @Transient
    private boolean naoPossuiPosts;

    public Author() {
        super();
    }

    public Author(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public List<Post> getListaPost() {
        return listaPost;
    }

    public void setListaPost(List<Post> listaPost) {
        this.listaPost = listaPost;
    }

    public boolean isNaoPossuiPosts() {
        return naoPossuiPosts;
    }

    public void setNaoPossuiPosts(boolean naoPossuiPosts) {
        this.naoPossuiPosts = naoPossuiPosts;
    }
}
