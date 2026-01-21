package br.rosa.redis_ex.vo;

import br.rosa.redis_ex.domain.enumerator.SexoEnum;

import java.io.Serializable;
import java.time.LocalDate;

public class AuthorVO implements Serializable {

    private static final long serialVersionUID = 4076768870304319941L;
    
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Boolean active;
    private SexoEnum sexo;

    public AuthorVO() {
    }

    public AuthorVO(Long id, String firstName, String lastName, LocalDate birthday,
                    Boolean active, SexoEnum sexo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.active = active;
        this.sexo = sexo;
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
}
