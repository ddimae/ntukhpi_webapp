package model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

/**
 * The persistent class for the deps_in_inst database table.
 * 
 */
@Entity
@Table(name="deps_in_inst")
@NamedQuery(name="DepsInInst.findAll", query="SELECT d FROM DepsInInst d")
public class DepsInInst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_dep_inst")
	private int idDepInst;

	private LocalDate yearStartIn;

	@OneToOne
	@JoinColumn(name="id_inst") //name of column in
	private Institute insitute;

	@OneToOne
	@JoinColumn(name="id_dep")
	private Department department;

	
	public DepsInInst() {
	}

	public int getIdDepInst() {
		return this.idDepInst;
	}

	public void setIdDepInst(int idDepInst) {
		this.idDepInst = idDepInst;
	}

	public LocalDate getYearStartIn() {
		return this.yearStartIn;
	}

	public void setYearStartIn(LocalDate yearStartIn) {
		this.yearStartIn = yearStartIn;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void Department(Department department) {
		this.department = department;
	}

	public Institute getInstitute() {
		return this.insitute;
	}

	public void setInsitute(Institute insitute) {
		this.insitute = insitute;
	}

	@Override
	public String toString() {
		return "department " + department.getShortNameDepartment() 
		+ " in institute " + insitute.getShortNameInstitute()+" from "	+yearStartIn;
	}
	
	

}