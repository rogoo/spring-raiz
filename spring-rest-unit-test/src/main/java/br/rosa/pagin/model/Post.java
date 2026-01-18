package br.rosa.pagin.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.rosa.pagin.model.base.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Post extends BaseModel<Long> {

	private static final long serialVersionUID = -8764914429328479151L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	@Column(name = "time_created")
	private Date timeCreated;

	@Column(name = "time_disabled")
	private Date timeDisabled;

	@ManyToOne
	@JoinColumn(name = "id_author", referencedColumnName = "id")
	@JsonIgnore
	private Author author;

	@Column(name = "id_author", updatable = false, insertable = false)
	private Long idAuthor;

	@Override
	public Long getId() {
		return id;
	}

	@Override
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

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	public Date getTimeDisabled() {
		return timeDisabled;
	}

	public void setTimeDisabled(Date timeDisabled) {
		this.timeDisabled = timeDisabled;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Long getIdAuthor() {
		return idAuthor;
	}

	public void setIdAuthor(Long idAuthor) {
		this.idAuthor = idAuthor;
	}
}
