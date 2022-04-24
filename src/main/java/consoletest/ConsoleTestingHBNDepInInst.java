package consoletest;

import java.util.List;

import hbn.HibernateUtil5;
import hbndao.HBNDADepartment;
import model.DepsInInst;

public class ConsoleTestingHBNDepInInst {

	public static void main(String[] args) {

		// Подготовка коллекции для ображения
		//List<Department> listDepartment = null;
		List<DepsInInst> list = null;
		
		// Тестируется работа SQL для вывода всех кафедр
		System.out.println("\nТест SQL для вывода всех записей из DepsInInst");
		list = HBNDADepartment.getAllDeps();
		//DepartmentList.getInstance() = HBNDADepartment.getAllDeps();
		System.out.println("\nDepartments in Institutes (HIBERNATE)");
		list.stream().forEach(System.out::println); // Show in console

		if (list.size() > 0 ) {
			System.out.println("Тест SQL для вывода всех записей из DepsInInst .... ОК");
		} else {
			System.err.println("Тест SQL для вывода всех записей из DepsInInst .... PROBLEM");
			System.exit(1001);
		}
		
		// Тестируется работа ManyToOne
		
		

		// Завершение работы соединения
		HibernateUtil5.shutdown();
		System.out.println("\n\n\nthe END!!!");
	}

}
