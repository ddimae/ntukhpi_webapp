package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.*;

/**
 * The persistent class for the institute database table.
 * 
 */
@Entity
public class Institute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_inst")
	private int idInst;

	private int codInstitute;

	private String nameInstitute;

	private String shortNameInstitute;
	
	private LocalDate yearCreate;

	public Institute() {
	}
	
	public Institute(int idInst, int codInstitute, String nameInstitute, String shortNameInstitute, LocalDate date) {
		super();
		this.idInst = idInst;
		this.codInstitute = codInstitute;
		this.nameInstitute = nameInstitute;
		this.shortNameInstitute = shortNameInstitute;
		this.yearCreate = date;
	}

	public int getIdInst() {
		return this.idInst;
	}

	public void setIdInst(int idInst) {
		this.idInst = idInst;
	}

	public int getCodInstitute() {
		return this.codInstitute;
	}

	public void setCodInstitute(int codInstitute) {
		this.codInstitute = codInstitute;
	}

	public String getNameInstitute() {
		return this.nameInstitute;
	}

	public void setNameInstitute(String nameInstitute) {
		this.nameInstitute = nameInstitute;
	}

	public String getShortNameInstitute() {
		return this.shortNameInstitute;
	}

	public void setShortNameInstitute(String shortNameInstitute) {
		this.shortNameInstitute = shortNameInstitute;
	}

	public LocalDate getYearCreate() {
		return this.yearCreate;
	}

	public void setYearCreate(LocalDate yearCreate) {
		this.yearCreate = yearCreate;
	}

	@Override
	public String toString() {
		return "Institute [idInst=" + idInst + ", codInstitute=" + codInstitute + ", nameInstitute=" + nameInstitute
				+ ", shortNameInstitute=" + shortNameInstitute + ", yearCreate=" + yearCreate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(shortNameInstitute);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Institute other = (Institute) obj;
		return Objects.equals(shortNameInstitute, other.shortNameInstitute);
	}

}