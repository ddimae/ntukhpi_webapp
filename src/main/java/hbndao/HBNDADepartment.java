package hbndao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import hbn.HibernateUtil5;
import model.Department;
import model.Institute;

/**
 * @author Dvukhhlavov D.E.
 */
public class HBNDADepartment {
	// https://www.javaguides.net/2019/02/hibernate-5-create-read-update-and-delete-operations-example.html
	// Метод получения ArrayList<> объктов из таблицы
	public static List<Department> getAll() {

		List<Department> departments = new ArrayList<>();
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {

			departments = session.createQuery("from Department", model.Department.class).list();

		} catch (Exception e) {
			System.err.println("=== Department#getAll - get all records ===");
			e.printStackTrace();
		}
		return departments;
	}

	// Метод получения ArrayList<> объктов из таблицы
	public static List<Department> getAllInInstitutes(String instName) {

		List<Department> departments = new ArrayList<>();
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {

			departments = session.createQuery("from Department", model.Department.class).list();
			departments = departments.stream()
					.filter((d) -> d.getLastInstitute() != null
							&& d.getLastInstitute().getKey().getShortNameInstitute().equals(instName))
					.collect(Collectors.toList());
		} catch (Exception e) {
			System.err.println("=== Department#getAllInInstitutes - get all records ===");
			e.printStackTrace();
		}
		return departments;
	}

	// Метод добавления Insert
	public static boolean insert(Department newDep) {
		boolean res = false;
		Transaction transaction = null;
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			System.out.println(newDep);

			session.save(newDep);

			// commit transaction
			transaction.commit();
			res = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("=== Department#insert - problem ===");
			res = false;
			// e.printStackTrace();
		}
		return res;
	}

	public static long findBySName(String shortNameDepartment) {
		List<Department> departments = new ArrayList<>();
		long id = -1;
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {
			String testHQL = "from Department where shortNameDepartment = '" + shortNameDepartment + "'";
			// System.out.println("SQL "+testHQL);
			departments = session.createQuery(testHQL, model.Department.class).list();
			// System.out.println(departments);
			if (!departments.isEmpty()) {
				id = departments.get(0).getIdDep();
			}
		} catch (Exception e) {
			System.err.println("=== Department#findBySName - find id by shortNameDepartment  ===");
			e.printStackTrace();
		}

		return id;
	}

	public static Department getByID(long id) {
		System.out.println("getByID: id to find = " + id);
		Department department = null;
		Transaction transaction = null;
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// get Department entity using get() method
			department = session.get(Department.class, (int) id);

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("=== Department#getByID - find Object by id  ===");
			e.printStackTrace();
		}
		return department;
	}

	// Метод изменения формирует Update
	public static boolean update(long id, Department newDep) {
		boolean res = false;
		// Изначально предполагается, что іd принадлежит одной из записей!!!
		// Это проверяется где-то в другом месте перед вызовом метода
		System.out.println("ID for update = " + id);
		Department department = null;
		Transaction transaction = null;
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// get Institute entity using get() method
			department = session.get(Department.class, (int) id);
			System.out.println("OLD " + department.toStringWithInst());
			System.out.println("NEW " + newDep.toStringWithInst());

			// update Department Обновляем все, кроме id
			department.setCodDepartment(newDep.getCodDepartment());
			department.setNameDepartment(newDep.getNameDepartment());
			department.setShortNameDepartment(newDep.getShortNameDepartment());
			department.setYearCreate(newDep.getYearCreate());
			// БРЕД, НО.... ПРОСТО put - или обновиться , или добавится
			// В newDep мапа придет с одним элементом или пустая
			Map<Institute, LocalDate> instituteList = newDep.getInstsOfDep();
			if (instituteList.size() > 0) {
				Entry<Institute, LocalDate> inst = (Entry<Institute, LocalDate>) newDep.getInstsOfDep().entrySet()
						.toArray()[0];
				department.addInst(inst.getKey(), inst.getValue());
			} else {
				department.delInst();
			}

			// Save changes into database
			session.update(department);

			// commit transaction
			transaction.commit();
			res = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("=== Department#update - problem  ===");
			e.printStackTrace();
			res = false;
		}
		return res;
	}

	public static boolean delete(long id) {
		boolean res = false;
		// Изначально предполагается, что іd принадлежит одной из записей!!!
		// Это проверяется где-то в другом месте перед вызовом метода
		System.out.println("ID for delete = " + id);
		Department department = null;
		Transaction transaction = null;
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// get Institute entity using get() method
			department = session.get(Department.class, (int) id);

			// Delete from database
			session.delete(department);

			// commit transaction
			transaction.commit();
			res = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("=== Department#delete - problem  ===");
			System.err.println(e.getLocalizedMessage());
			res = false;
		}
		return res;
	}

}
