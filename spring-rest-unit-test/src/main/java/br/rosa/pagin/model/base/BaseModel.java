package br.rosa.pagin.model.base;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseModel<T> implements Serializable {

	private static final long serialVersionUID = 1427268532314526005L;

//	@Column(name = "time_created", updatable = false)
//	private Date timeCreated;
//
//	@Column(name = "time_modification")
//	private Date timeModification;
//
//	@Version
//	private int version;

	public abstract T getId();

	public abstract void setId(T id);

//	public Date getTimeCreated() {
//		return timeCreated;
//	}
//
//	public Date getTimeModification() {
//		return timeModification;
//	}
//
//	public int getVersion() {
//		return version;
//	}
//
//	@PreUpdate
//	public final void preUpdate() {
//		timeModification = new Date();
//	}
//
//	@PrePersist
//	public final void prePersist() {
//		timeCreated = new Date();
//		timeModification = new Date();
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (this == null || getClass() != obj.getClass()) {
			return false;
		}

		@SuppressWarnings("unchecked")
		BaseModel<T> base = (BaseModel<T>) obj;

		return this.getId() == base.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
