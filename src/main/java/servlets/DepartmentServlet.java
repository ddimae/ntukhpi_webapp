package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hbndao.HBNDADepartment;
import hbndao.HBNDAInstitute;
import model.Department;
import model.Institute;

/**
 * Servlet implementation class AddInstituteServlet
 */
@WebServlet("/department")
public class DepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("DepartmentServlet#doGET");
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("institutes_list", hbndao.HBNDAInstitute.getNamesInstituteList());
		String filterInst = request.getParameter("send_select"); // Запомнили институт из комбобокса с именами
																	// институтов
		request.setAttribute("inst_select", filterInst);
		// System.out.println("inst_select "+ filterInst);
		String errorString = null;
		String nameInstIn;
		LocalDate dateStartIn;
		// id = -1 - for new Institute
		// other case - for edit exist Institute
		Department department = null;
		String titleDepartment = null;
		if (id == -1) {
			department = new Department();
			department.setIdDep(-1); // -1 - устанавливается для перехода в режим новой записи
			nameInstIn = filterInst;
			dateStartIn = null;
			titleDepartment = "Add Departments ";
		} else {
			department = HBNDADepartment.getByID(id);
			Entry<Institute, LocalDate> lastInst = department.getLastInstitute();
			if (lastInst != null) {
				nameInstIn = lastInst.getKey().getShortNameInstitute();
				dateStartIn = lastInst.getValue();
			} else {
				nameInstIn = "any";
				dateStartIn = null;
			}
			titleDepartment = "Edit departments";
		}
		request.setAttribute("errorString", errorString);
		request.setAttribute("nameInstIn", nameInstIn);
		// System.out.println("nameInstIn "+ nameInstIn);
		request.setAttribute("dateStartIn", dateStartIn);
		request.setAttribute("department", department);
		request.setAttribute("titleDepartment", titleDepartment);

		getServletContext().getRequestDispatcher("/views/Department.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("DepartmentServlet#doPOST");
		// Processing info for insert new Institute
		// Read Parameters from Institute.jsp
		// это нужно для подготовки объекта с введенными данными и его записи
		// (редактированию) в БД
		// Длинное выражение - устранение крокозябров при записи
		String name = new String(request.getParameter("nameD").getBytes("ISO-8859-1"), "utf-8");
		//String name = request.getParameter("nameD");
		System.out.println("nameD = " + name);
		String nameS = new String(request.getParameter("nameSD").getBytes("ISO-8859-1"), "utf-8");
		System.out.println("nameSD = " + nameS);
		int cod = Integer.parseInt(request.getParameter("codD"));
		System.out.println("codD = " + cod);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		String dateCreate = request.getParameter("datecreateD");
		LocalDate data = null;
		if (!dateCreate.isEmpty()) {
			data = LocalDate.parse(request.getParameter("datecreateD"));
			System.out.println("datecreateD = " + data.format(formatter));
		}
		Department newDep = new Department(0, cod, name, nameS, data);

		// New!!! With Institute
		System.out.println("Info about Institute:");
		String nameInstIn = new String(request.getParameter("send_select").getBytes("ISO-8859-1"), "utf-8");
		System.out.println("nameInstIn = " + nameInstIn);
		String dateStartInStr = request.getParameter("dateStartIn");
		System.out.println("dateStartIn = " + dateStartInStr);
		Institute inst = null;
		LocalDate dateStartIn = null;
		if (!nameInstIn.equals("any") && dateStartInStr.length()>0) {
			System.out.println("Present full info about Institute In!!!");
			dateStartIn = LocalDate.parse(dateStartInStr);
			System.out.println("dateStartIn = " + dateStartIn.format(formatter));
			inst = HBNDAInstitute.findInstBySName(nameInstIn);
			newDep.addInst(inst, dateStartIn);
		}
		
		// Новые данные готовы
		System.out.println(newDep.toStringWithInst());
		int id = Integer.parseInt(request.getParameter("id"));
		String titleDepartment = null;
		String errorString = null;
		// Try to add or edit
		if (id == -1) { // -1 ==> ЗАПИСЬ НОВАЯ
			boolean okAddDepartment = HBNDADepartment.insert(newDep);
			if (!okAddDepartment) {
				errorString = "Під час додавання виникла помилка на рівні DBMS\n";
				newDep = new Department();
				titleDepartment = "Add Departments";
			}
		} else { // ==> РЕДАКТИРОВАНИЕ
			boolean okEditDepartment = HBNDADepartment.update(id, newDep);
			if (!okEditDepartment) {
				errorString = "Під час редагування виникла помилка на рівні DBMS\n";
				newDep = HBNDADepartment.getByID(id);
				titleDepartment = "Edit Institutes";
			}
		}

		// If error, forward to Edit page.
		if (errorString != null) {
			// Prepare attributes to back to input or edit data
			request.setAttribute("errorString", errorString);
			request.setAttribute("department", newDep);
			System.out.println(newDep);
			Entry<Institute, LocalDate> lastInst = newDep.getLastInstitute();
			if (lastInst != null) {
				nameInstIn = lastInst.getKey().getShortNameInstitute();
				dateStartIn = lastInst.getValue();
			} else {
				nameInstIn = "any";
				dateStartIn = null;
			}
			request.setAttribute("nameInstIn", nameInstIn);
			request.setAttribute("dateStartIn", dateStartIn);
			request.setAttribute("titleDepartment", titleDepartment);
			// Параметры фильтра и данных про институт
			request.getServletContext().getRequestDispatcher("/views/Department.jsp").forward(request, response);
		} else {
			// If everything nice.
			// Redirect to the InstituteList page
			String passDeps = new String((request.getContextPath() + "/departments?send_select=" + nameInstIn).getBytes("utf-8"), "ISO-8859-1");
			
			//response.sendRedirect(request.getContextPath() + "/departments?send_select=" + nameInstIn);
			System.out.println("passDeps="+passDeps);
			response.sendRedirect(passDeps);
		}

	}

}
