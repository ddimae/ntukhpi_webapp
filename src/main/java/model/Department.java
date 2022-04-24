package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.*;

/**
 * The persistent class for the department database table.
 * 
 */
@Entity
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_dep")
	private int idDep;

	private int codDepartment;

	private String nameDepartment;

	private String shortNameDepartment;
	
	private LocalDate yearCreate;

	public Department() {
	}
	
	public Department(int idDep, int codDepartment, String nameDepartment, String shortNameDepartment, LocalDate date) {
		super();
		this.idDep = idDep;
		this.codDepartment = codDepartment;
		this.nameDepartment = nameDepartment;
		this.shortNameDepartment = shortNameDepartment;
		this.yearCreate = date;
	}

	public int getIdDep() {
		return this.idDep;
	}

	public void setIdDep(int idDep) {
		this.idDep = idDep;
	}

	public int getCodDepartment() {
		return this.codDepartment;
	}

	public void setCodDepartment(int codDepartment) {
		this.codDepartment = codDepartment;
	}

	public String getNameDepartment() {
		return this.nameDepartment;
	}

	public void setNameDepartment(String nameDepartment) {
		this.nameDepartment = nameDepartment;
	}

	public String getShortNameDepartment() {
		return this.shortNameDepartment;
	}

	public void setShortNameDepartment(String shortNameDepartment) {
		this.shortNameDepartment = shortNameDepartment;
	}

	public LocalDate getYearCreate() {
		return this.yearCreate;
	}

	public void setYearCreate(LocalDate yearCreate) {
		this.yearCreate = yearCreate;
	}

	@Override
	public String toString() {
		return "Department [idDep=" + idDep + ", codDepartment=" + codDepartment + ", nameDepartment=" + nameDepartment
				+ ", shortNameDepartment=" + shortNameDepartment + ", yearCreate=" + yearCreate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(shortNameDepartment);
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
		Department other = (Department) obj;
		return Objects.equals(shortNameDepartment, other.shortNameDepartment);
	}

}