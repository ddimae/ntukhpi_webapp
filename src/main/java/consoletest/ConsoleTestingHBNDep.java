package consoletest;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;

import hbn.HibernateUtil5;
import hbndao.HBNDADepartment;
import hbndao.HBNDAInstitute;
import model.Department;

public class ConsoleTestingHBNDep {

	public static void main(String[] args) {

		// Подготовка коллекции для ображения
		java.util.List<Department> listDepartment = null;
		boolean resOp;

		// Тестируется работа SQL для вывода всех записей
		System.out.println("\nТест SQL для вывода всех записей");
		listDepartment = HBNDADepartment.getAll();
		System.out.println("\nDepartments (HIBERNATE)");
		listDepartment.stream().forEach(System.out::println); // Show in console

		if (listDepartment.size() > 0) {
			System.out.println("Тест SQL для вывода всех записей .... ОК");
		} else {
			System.err.println("Тест SQL для вывода всех записей .... PROBLEM");
			System.exit(100);
		}

		// Тестируем добавление нового объекта
		// ДО РАЗРАБОТКИ МЕТОДА УДАЛЕНИЯ НЕОБХОДИМО УДАЛЯТЬ ЗАПИСЬ ИЗ БД РУЧКАМИ!!!!
		System.out.println("\nТест добавления записей");
		Department departmentNew = new Department(0, 777, "Новая кафедра", "НК", LocalDate.of(2022, 4, 1));
		System.out.println("New record - " + departmentNew);

		// Промежуточный поиск - найти такой объект в БД
		// Если есть, то запись будет с ошибкой и исключением, тогда 1) записывать не
		// надо или 2) удалить...
		// ===> нужен метод, который отыскивает объект в БД и возвращает или все (*) или
		// id
		// А для этого нужно задать equals и hashcod
		if (!listDepartment.contains(departmentNew)) {

			resOp = HBNDADepartment.insert(departmentNew);
			if (resOp) {
				listDepartment = HBNDADepartment.getAll();
				listDepartment.stream().forEach(System.out::println); // Show in console
				System.out.println("Тест добавления записей .... ОК");
			} else {
				System.err.println("Новые записи не добавлены");
				System.err.println("Тест добавления записей .... PROBLEM");
				System.exit(200);
			}
		} else {
			System.out.println("Тест добавления записей .... НЕ ПРОВОДИЛСЯ");
		}
		
		// Тестируем повторное добавление
		System.out.println("\nТест повторное добавление");
		System.out.println("New record - " + departmentNew);

		if (!listDepartment.contains(departmentNew)) {

			resOp = HBNDADepartment.insert(departmentNew);
			if (resOp) {
				listDepartment = HBNDADepartment.getAll();
				listDepartment.stream().forEach(System.out::println); // Show in console
				System.err.println("Тест повторное добавление .... PROBLEM");
				System.exit(201);
			} else {
				System.err.println("Новые записи не добавлены");
				System.err.println("Тест повторное добавление .... PROBLEM");
				System.exit(202);
			}
		} else {
			System.out.println("Тест повторное добавление .... НЕ ПРОВОДИЛСЯ == OK");
		}
		

		// Тестируем поиск id по ключевым полям
		System.out.println("\nТест поиск id по ключевым полям");
		// Определяем по сокращенному названию
		String shortNameDepartment = departmentNew.getShortNameDepartment();
		System.out.println("ShortName Department to find = " + shortNameDepartment);
		// Department departmentFindID = new Department(0, 111, "Test", shortNameDepartment,
		// LocalDate.of(2000, 1, 1));
		// departmentFindID.setShortNameDepartment("SGI");
		long id = HBNDADepartment.findBySName(shortNameDepartment);
		long idNewDep = -1; // для будущих проверок
		if (id != -1) {
			System.out.println("Found! " + id);
			System.out.println("\nТест поиск id по ключевым полям .... OK");
			// Сохраняем id для следующих проверок редактирования, удаления и поиска
			idNewDep = id;
		} else {
			System.err.println("Problem ===> Such id not found!");
			System.exit(300);
		}

		// Тестируем отсутствие при поиске id по ключевым полям
		System.out.println("\nТест поиск id по ключевым полям (absence)");
		shortNameDepartment = "Super " + departmentNew.getShortNameDepartment();
		System.out.println("ShortName Institute to find = " + shortNameDepartment);
		// departmentFindID = new Institute(0, 111, "Test", shortNameDepartment,
		// LocalDate.of(2000, 1, 1));
		id = HBNDADepartment.findBySName(shortNameDepartment);
		if (id != -1L) {
			System.err.println("Problem ===> Found! " + id);
			System.exit(400);
		} else {
			System.out.println("Such id not found!");
			System.out.println("Тест поиск id по ключевым полям (absence).... OK");
		}

		// Тестируем поиск по id
		System.out.println("\nТест поиск по id");
		// HBNDAInstitute.getByID1(31);
		// в idNewInst значение ключа записи, которая точно существует
		Department findDep = HBNDADepartment.getByID(idNewDep);
		if (findDep != null) {
			System.out.println(findDep);
			System.out.println("Тест поиск по id ... OK");
		} else {
			System.err.println("Such object not found!");
			System.err.println("Тест поиск по id ... PROBLEM");
			System.exit(500);
		}

		// Тестируем поиск по id (absence)
		System.out.println("\nТест поиск по id (absent)");
		// в idNewInst значение ключа записи, которая точно НЕ существует
		// HBNDAInstitute.getByID1(32);

		findDep = HBNDADepartment.getByID((int) idNewDep + 1);
		if (findDep == null) {
			System.out.println("Such object not found!");
			System.out.println("Тест поиск по id (absent) ... OK");
		} else {
			System.err.println(findDep);
			System.err.println("Тест Тест поиск по id (absent) ... PROBLEM");
			System.exit(505);
		}

		// Тестируем редактирование
		// ДО РАЗРАБОТКИ МЕТОДА УДАЛЕНИЯ НЕОБХОДИМО УДАЛЯТЬ ОТРЕДАКТИРОВАННУЮ ЗАПИСЬ ИЗ БД РУЧКАМИ!!!!
		System.out.println("\nТест редактирование");
		// Редактируется только что добавленная запись
		long idForUpdate = idNewDep;
		Department depForUpdate = new Department(departmentNew.getIdDep(), 333, "Кафедра підготовки за стандартами ЕС",
				"КПСЕС", departmentNew.getYearCreate());
		// Если вносятся данные, которые уже есть, то запрос не имеет смысла - данные
		// записаны не будут
		// Для этого были настроены equals + hashcode для Institute
		if (!listDepartment.contains(depForUpdate)) {
			resOp = HBNDADepartment.update(idForUpdate, depForUpdate);
			if (resOp) {
				listDepartment = HBNDADepartment.getAll();
				listDepartment.stream().forEach(System.out::println); // Show in console
				System.out.println("Тест редактирование .... ОК");
			} else {
				System.err.println("Редактирование не выполнено");
				System.err.println("Тест редактирование .... PROBLEM");
				System.exit(700);
			}
		} else {
			System.err.println("Тест редактирование .... ОТМЕНА (объект присутстует в БД)");
		}
		//Вывод!!!! Если в таблице несколько ключевых полей (в данном случае один первичный и три уникальных поля),
		//нужна более сложная проверка, которую можно или в equals , или оператором

		// Тестируем редактирование записи, которая уже есть
		System.out.println("\nТест редактирование (новое Короткое название и существующий код)");
		// Редактируется только что отредактированная запись
		// Попытка поменять скорочену назву ІПСЕС ==> ІПСЄС - такого нет
		// А код введем для существующего - 221, код кафедр ПІІТУ
		// На уровне проверки методами Equals пройдет, на уровне БД будет ошибка, так
		// как код и полное имя не меняются
		idForUpdate = idNewDep;
		depForUpdate = new Department(departmentNew.getIdDep(), 221, "Інститут підготовки за стандартами ЕС", "ІПСЄС",
				departmentNew.getYearCreate());
		if (!listDepartment.contains(depForUpdate)) {
			resOp = HBNDADepartment.update(idForUpdate, depForUpdate);
			if (resOp) {
				listDepartment = HBNDADepartment.getAll();
				listDepartment.stream().forEach(System.out::println); // Show in console
				System.err.println("Тест редактирование (новое Короткое название и существующий код) ... PROBLEM");
				System.exit(701);
			} else {
				System.out.println("Редактирование не выполнено");
				System.out.println("Тест редактирование (новое Короткое название и существующий код) .... OK");
				
			}
		} else {
			System.err.println(
					"Тест редактирование (новое Короткое название и существующий код) .... ОТМЕНА (объект присутстует в БД)");
		}

		// Тестируем редактирование записи, которая уже есть 2
		System.out.println("\nТест редактирование, которая уже есть (Код и Название)");
		// Редактируется только что отредактированная запись
		// Попытка поменять полную назву и код
		// На редактирование зайти не должно
		idForUpdate = idNewDep;
		depForUpdate = new Department(departmentNew.getIdDep(), 555, "Кафедра підготовки за стандартами ЄС", "КПСЄС",
				departmentNew.getYearCreate());

		//Чтобы проверить, есть ли аналогичные объекты, можем применить StreamAPI
		final Department finstForUpdate = depForUpdate; //надо для работы filter()
		long ammountRep = listDepartment.stream()
				.filter(
						inst->(inst.getCodDepartment()==finstForUpdate.getCodDepartment()
						&&inst.getNameDepartment().equals(finstForUpdate.getNameDepartment())
						&&inst.getShortNameDepartment().equals(finstForUpdate.getShortNameDepartment())
						))
				.count();
		if (ammountRep>0) {
			resOp = HBNDADepartment.update(idForUpdate, depForUpdate);
			if (resOp) {
				listDepartment = HBNDADepartment.getAll();
				listDepartment.stream().forEach(System.out::println); // Show in console
				System.err.println("Тест редактирование, которая уже есть (Код и Название) ... PROBLEM");
				System.exit(702);
			} else {
				System.err.println("Редактирование не выполнено");
				System.err.println("Тест редактирование, которая уже есть (Код и Название) ... PROBLEM");
				System.exit(703);
			}
		} else {
			System.out.println(
					"Тест редактирование, которая уже есть (Код и Название) .... ОТМЕНА ==> OK");
		}
		//ВЫВОД! Данная реализация позволяет более корректно отследить "одинаковость" записей в БД на стороне клиента
		

		// Тестируем удаление
		System.out.println("\nТест удаление");
		// Удаляется запись, которую вставили и редактировали в ходе самого первого редактирования
		// Две другие попытки были неудачны
		long idForDelete = idNewDep;
		Department instForDelete = new Department(departmentNew.getIdDep(), 333, "Кафедра підготовки за стандартами ЕС",
				"КПСЕС", departmentNew.getYearCreate());;
		// Удаление имеет смысл, если такой объект есть
		if (listDepartment.contains(instForDelete)) {
			resOp = HBNDADepartment.delete(idForDelete);
			if (resOp) {
				listDepartment = HBNDADepartment.getAll();
				listDepartment.stream().forEach(System.out::println); // Show in console
				System.out.println("Тест удаление .... ОК");
			} else {
				System.err.println("Удаление не выполнено");
				System.err.println("Тест удаление .... PROBLEM");
				System.exit(800);
			}
		} else {
			System.err.println("Тест удаление .... ОТМЕНА (объект отсутствует в БД)");
		}

		// Тестируем пустой вывод после удаления
		System.out.println("\nТест повторное удаление");
		// Удаляется запись, которую вставили и редактировали выше в коде
		// Но он только что был удален, поєтому правильный вариант - отказ от удаления
		// Удаление имеет смысл, если такой объект есть
		if (listDepartment.contains(instForDelete)) {
			resOp = HBNDAInstitute.delete(idForDelete);
			if (resOp) {
				listDepartment = HBNDADepartment.getAll();
				listDepartment.stream().forEach(System.out::println); // Show in console
				System.err.println("Тест удаление .... ОК");
				System.exit(801);
			} else {
				System.err.println("Удаление не выполнено");
				System.err.println("Тест удаление .... PROBLEM");
				System.exit(802);
			}
		} else {
			System.out.println("Тест удаление .... ОТМЕНА (объект отсутствует в БД) ==> OK");
		}

		// Завершение работы соединения
		HibernateUtil5.shutdown();
		System.out.println("\n\n\nthe END!!!");
	}

}
