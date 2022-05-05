package consoletest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import hbndao.HBNDADepartment;
import hbndao.HBNDAInstitute;
import model.Institute;
import model.Department;

public class ConsoleTestingNewHBN {

	public static void main(String[] args) {
		System.out.println("\nInstitutes Test Set");
				System.out.println("Fisrt state");
		Institute inst = new Institute(100, 333, "Інститут підготовки за стандартами ЕС",
				"ІПСЕС", LocalDate.of(2021, 1 , 1));
		System.out.println(inst);
		
		//Map<Department,LocalDate> deps =  new HashMap<>();
		System.out.println("Upgraded structure");
		Department dep1 = new Department(1,101,"Кафедра комп\'ютерной графики"
                ,"КГ",LocalDate.of(1998, 1 , 1));
		Department dep2 = new model.Department(2,102,"Кафедра іноземних мов"
                 ,"ІМ",LocalDate.of(1988, 1 , 1));
		//
		System.out.println("Add");
		inst.addDep(dep1,LocalDate.of(2021, 1 , 2));
		dep1.addInst(inst, LocalDate.of(2021, 1 , 2));
		inst.addDep(dep2,LocalDate.of(2021, 1 , 2));
		dep2.addInst(inst, LocalDate.of(2021, 1 , 2));
		System.out.println(inst);
		System.out.println(dep1.toStringWithInst());
		System.out.println(dep2.toStringWithInst());
		
		//
		System.out.println("Delete");
		inst.delDep(dep1);
		//dep1.setInstitute(null);
		System.out.println(inst);
		
		//
		inst.addDep(dep1,LocalDate.of(2021, 1 , 3));
		//dep1.setInstitute(inst);
		System.out.println("Add again");
		System.out.println(inst);
		
		//Получить обект из БД
		System.out.println("\nLets Work with DB");
		inst = HBNDAInstitute.getByID(2);
		System.out.println("Find by ID");
		System.out.println(inst);
		
		//Получить список институтов
		System.out.println("\nInstitutes from DB full list");
		List<Institute> institutes = HBNDAInstitute.getAll();
		institutes.stream().forEach(System.out::println);
		
		//Получить список кафедр с указанием институтов, в которые она входиила
	    System.out.println("\nDepartments from DB full list");
	    List<Department> departments = HBNDADepartment.getAll();
	    departments.stream().forEach((dep)->System.out.println(dep.toStringWithInsts()));
	    
        //Получить список кафедр с указанием института, в который она входит сейчас (с последней датой входа)
	    System.out.println("\nDepartments with Institute");
	    departments.stream().forEach((dep)->System.out.println(dep.toStringWithInst()));
		
        //Получить список институтов для спадаючого списка
	    System.out.println("\nInstitute ShortNameList");
	    List<String> shortNamesInstitute = HBNDAInstitute.getNamesInstituteList();
	    shortNamesInstitute.stream().forEach(System.out::println);
	    
	    //Получить список кафедр по имени для спадаючого списка
	    String instNameToFind = shortNamesInstitute.get(4);
	    System.out.println("\nDepartment of Institute "+ instNameToFind);
	    Set<Department> depsInstitute = HBNDAInstitute.findInstBySName(instNameToFind).getDepsInst().keySet();
	    depsInstitute.stream().forEach(System.out::println);
	    
	    System.out.println("\nDepartment of Institute VAR2 "+ instNameToFind);
	    List<Department> depsInstituteList = HBNDADepartment.getAllInInstitutes(instNameToFind);
	    depsInstituteList.stream().forEach(System.out::println);
	    
	    
	}

}
