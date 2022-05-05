package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.persistence.*;

/**
 * The persistent class for the institute database table.
 * 
 */
@Entity
@Table(name = "institutes")
public class Institute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_inst")
	private int idInst;

	@Column(nullable = false)
	private int codInstitute;

	@Column(nullable = false)
	private String nameInstitute;

	@Column(nullable = false)
	private String shortNameInstitute;

	private LocalDate yearCreate;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "deps_in_inst", joinColumns = @JoinColumn(name = "id_inst"))
	@MapKeyJoinColumn(name = "id_dep")
	@Column(name = "YearStartIn", nullable = true) 
	private Map<Department, LocalDate> depsInst = new HashMap<>();

	public Institute() {
	}

	public Institute(int idInst, int codInstitute, String nameInstitute, String shortNameInstitute, LocalDate date) {
		super();
		this.idInst = idInst;
		this.codInstitute = codInstitute;
		this.nameInstitute = nameInstitute;
		this.shortNameInstitute = shortNameInstitute;
		this.yearCreate = date;
		// this.depsInst -initialized above
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

	//Для Map c подчиненными кафедрами
	public Map<Department, LocalDate> getDepsInst() {
		return Collections.unmodifiableMap(depsInst);
	}

	public void addDep(Department dep, LocalDate dateIn) {
		depsInst.put(dep,dateIn);
	}
	
	public void delDep(Department dep) {
		depsInst.remove(dep);		
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(System.lineSeparator());
		sb.append(codInstitute + " ").append(nameInstitute + " ").append("(" + shortNameInstitute + ")")
				.append(System.lineSeparator());
		if (yearCreate!=null) {
		sb.append("Founded at ").append(yearCreate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
				.append(System.lineSeparator());
		} else {
			sb.append("Date of establishment is unknown").append(System.lineSeparator());
		}
		if (depsInst.size() > 0) {
			sb.append("Deps of Institute").append(System.lineSeparator());
			for (Entry<Department,LocalDate> deps: depsInst.entrySet()) {
				sb.append("From ").append(deps.getValue()).append(": ");
				sb.append(deps.getKey().toString());
			}
		} else {
			sb.append("Deps in the institute struture not found!");
		}
		return sb.toString();
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