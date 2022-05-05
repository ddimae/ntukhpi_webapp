package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.Map.Entry;

import javax.persistence.*;

/**
 * The persistent class for the department database table.
 * 
 */
//@Embeddable
@Entity
@Table(name = "departments")
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_dep")
	private int idDep;

	@Column(nullable = false)
	private int codDepartment;

	@Column(nullable = false)
	private String nameDepartment;

	@Column(nullable = false)
	private String shortNameDepartment;

	@Column(nullable = false)
	private LocalDate yearCreate;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "deps_in_inst", joinColumns = @JoinColumn(name = "id_dep"))
	@MapKeyJoinColumn(name = "id_inst")
	@Column(name = "YearStartIn", nullable = true)
	private Map<Institute, LocalDate> instsOfDep = new HashMap<>();

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

	// Для Map c подчиненными кафедрами
	public Map<Institute, LocalDate> getInstsOfDep() {
		return Collections.unmodifiableMap(instsOfDep);
	}

	public void addInst(Institute inst, LocalDate dateIn) {
		instsOfDep.put(inst, dateIn);
	}

	//Удаление = очищение истории института
	public void delInst() {
		instsOfDep.clear();
	}
	
	public Entry<Institute,LocalDate> getLastInstitute() {
		Entry<Institute,LocalDate> lastInst = null;
		if (instsOfDep.size()>0) {
			Stream<Entry<Institute,LocalDate>> sortedInst = instsOfDep.entrySet()
					.stream().sorted((e1,e2)->e2.getValue().compareTo(e1.getValue())).limit(1);
			lastInst = sortedInst.findFirst().get();
 		}
		return lastInst;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(nameDepartment + " (");
		sb.append(codDepartment + ",").append(shortNameDepartment + ", founded at ");
		if (yearCreate != null) {
			sb.append(yearCreate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).append(")");
		} else {
			sb.append(codDepartment + "unknown");
		}
		sb.append(")").append(System.lineSeparator());
		return sb.toString();
	}

	public String toStringWithInsts() {
		StringBuilder sb = new StringBuilder();
		sb.append(nameDepartment + " (");
		sb.append(codDepartment + ",").append(shortNameDepartment + ", founded at ");
		if (yearCreate != null) {
			sb.append(yearCreate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).append(")");
		} else {
			sb.append(codDepartment + "unknown");
		}
		sb.append(")").append(System.lineSeparator());
		if (instsOfDep.size() > 0) {
			sb.append("History of department:").append(System.lineSeparator());
			for (Entry<Institute,LocalDate> inst: instsOfDep.entrySet()) {
				sb.append("from ").append(inst.getValue()).append(": ");
				sb.append(inst.getKey().getNameInstitute()).append(System.lineSeparator());
			}
		} else {
			sb.append("Department was not included in any institute!").append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	public String toStringWithInst() {
		StringBuilder sb = new StringBuilder();
		sb.append(shortNameDepartment + " (");
		sb.append(codDepartment).append(", founded at ");
		if (yearCreate != null) {
			sb.append(yearCreate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).append(")");
		} else {
			sb.append(codDepartment + "unknown");
		}
		sb.append(")");
		Entry<Institute, LocalDate> instLast = getLastInstitute(); 
		if (instLast!=null) {
			sb.append(" - ").append(instLast.getKey().getShortNameInstitute());
		} 
		sb.append(System.lineSeparator());
		return sb.toString();
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