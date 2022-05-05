package hbndao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;

import hbn.HibernateUtil5;
import model.Institute;

/**
 * @author Dvukhhlavov D.E.
 */
public class HBNDAInstitute {
	// https://www.javaguides.net/2019/02/hibernate-5-create-read-update-and-delete-operations-example.html
	// Метод получения ArrayList<> объектов из таблицы
	public static List<Institute> getAll() {

		List<Institute> instituties = new ArrayList<>();
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {

			instituties = session.createQuery("from Institute", model.Institute.class).list();

		} catch (Exception e) {
			System.err.println("=== Institute#getAll - get all records ===");
			e.printStackTrace();
		}
		return instituties;
	}
	
	public static List<String> getNamesInstituteList() {

		List<Institute> instituties = new ArrayList<>();
		List<String> names = new ArrayList<>();
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {

			instituties = session.createQuery("from Institute", model.Institute.class).list();
			names = instituties.stream()
					.map((inst)->inst.getShortNameInstitute()).sorted()
					.collect(Collectors.toList());
		} catch (Exception e) {
			System.err.println("=== Institute#getShortNamesInstituteList - get all records ===");
			e.printStackTrace();
		}
		return names;
	}

	// Метод добавления Insert
	public static boolean insert(Institute newInst) {
		boolean res = false;
		Transaction transaction = null;
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			System.out.println(newInst);

			session.save(newInst);

			// commit transaction
			transaction.commit();
			res = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("=== Institute#insert - problem ===");
			res = false;
			// e.printStackTrace();
		}
		return res;
	}

	public static long findIdInstBySName(String shortNameInstitute) {
		List<Institute> instituties = new ArrayList<>();
		long id = -1;
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {
			String testHQL = "from Institute where shortNameInstitute = '" + shortNameInstitute + "'";
			// System.out.println("SQL "+testHQL);
			instituties = session.createQuery(testHQL, model.Institute.class).list();
			// System.out.println(instituties);
			if (!instituties.isEmpty()) {
				id = instituties.get(0).getIdInst();
			}
		} catch (Exception e) {
			System.err.println("=== Institute#findIdInstBySName - find id by shortNameInstitute  ===");
			e.printStackTrace();
		}

		return id;
	}
	
	public static Institute findInstBySName(String shortNameInstitute) {
		List<Institute> instituties = new ArrayList<>();
		Institute institute = null;
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {
			String testHQL = "from Institute where shortNameInstitute = '" + shortNameInstitute + "'";
			// System.out.println("SQL "+testHQL);
			instituties = session.createQuery(testHQL, model.Institute.class).list();
			// System.out.println(instituties);
			if (!instituties.isEmpty()) {
				institute = instituties.get(0);
			}
		} catch (Exception e) {
			System.err.println("=== Institute#findInstBySName - find id by shortNameInstitute  ===");
			e.printStackTrace();
		}

		return institute;
	}

	public static Institute getByID(long id) {
		System.out.println("getByID: id to find = " + id);
		Institute institute = null;
		Transaction transaction = null;
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// get Institute entity using get() method
			institute = session.get(Institute.class, (int)id);

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("=== Institute#getByID - find Object by id  ===");
			e.printStackTrace();
		}
		return institute;
	}

	// Метод изменения формирует Update
	public static boolean update(long id, Institute newInst) {
		boolean res = false;
		//Изначально предполагается, что іd принадлежит одной из записей!!!
		//Это проверяется где-то в другом месте перед вызовом метода
		System.out.println("ID for update = " + id);
		Institute institute = null;
		Transaction transaction = null;
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// get Institute entity using get() method
			institute = session.get(Institute.class, (int)id);
			System.out.println("OLD "+institute);
			System.out.println("NEW "+newInst);
			
			//update Institute Обновляем все, кроме id
			institute.setCodInstitute(newInst.getCodInstitute());
			institute.setNameInstitute(newInst.getNameInstitute());
			institute.setShortNameInstitute(newInst.getShortNameInstitute());
			institute.setYearCreate(newInst.getYearCreate());
			
			//Save changes into database
			session.update(institute);
			
			// commit transaction
			transaction.commit();
			res = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("=== Institute#update - problem  ===");
			e.printStackTrace();
			res = false;
		}
		return res;
	}

	public static boolean delete(long id) {
		boolean res = false;
		//Изначально предполагается, что іd принадлежит одной из записей!!!
		//Это проверяется где-то в другом месте перед вызовом метода
		System.out.println("ID for delete = " + id);
		Institute institute = null;
		Transaction transaction = null;
		try (Session session = HibernateUtil5.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// get Institute entity using get() method
			institute = session.get(Institute.class, (int)id);
			
			
			//Delete from database
			session.delete(institute);
			
			// commit transaction
			transaction.commit();
			res = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.err.println("=== Institute#delete - problem  ===");
			System.err.println(e.getLocalizedMessage());
			res = false;
		}
		return res;
	}
	
	

}
