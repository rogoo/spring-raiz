package br.rosa.redis_ex.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = 7092489470123456739L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private Author author;

    @Column(name = "time_created")
    private LocalDateTime timeCreated;

    @Column(name = "time_disabled")
    private LocalDateTime timeDisabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeDisabled() {
        return timeDisabled;
    }

    public void setTimeDisabled(LocalDateTime timeDisabled) {
        this.timeDisabled = timeDisabled;
    }
}
