package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hbndao.HBNDAInstitute;
import model.Institute;

/**
 * Servlet implementation class AddInstituteServlet
 */
@WebServlet("/institute")
public class InstituteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("InstituteServlet#doGET");
		int id = Integer.parseInt(request.getParameter("id"));
		String errorString = null;
		// id = -1 - for new Institute
		// other case - for edit exist Institute
		Institute institute = null;
		String titleInstitute = null;
		if (id == -1) {
			institute = new Institute();
			institute.setIdInst(-1); // -1 - устанавливается для перехода в режим новой записи
			titleInstitute = "Add Institutes";
		} else {
			institute = HBNDAInstitute.getByID(id);
			titleInstitute = "Edit Institutes";
		}
		request.setAttribute("errorString", errorString);
		request.setAttribute("institute", institute);
		request.setAttribute("titleInstitute", titleInstitute);
		getServletContext().getRequestDispatcher("/views/Institute.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("InstituteServlet#doPOST");
		// Processing info for insert new Institute
		// Read Parameters from Institute.jsp
		// это нужно для подготовки объекта с введенными данными и его записи
		// (редактированию) в БД
		// Длинное выражение - устранение крокозябров при записи
		String name = new String(request.getParameter("nameI").getBytes("ISO-8859-1"), "utf-8");
		System.out.println("nameI = " + name);
		String nameS = new String(request.getParameter("nameSI").getBytes("ISO-8859-1"), "utf-8");
		System.out.println("nameSI = " + nameS);
		int cod = Integer.parseInt(request.getParameter("codI"));
		System.out.println("codI = " + cod);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		String dateCreate = request.getParameter("datecreateI");
		LocalDate data = null;
		if (!dateCreate.isEmpty()) {
			data = LocalDate.parse(request.getParameter("datecreateI"));
			System.out.println("data = " + data.format(formatter));
		}
		Institute newInst = new Institute(0, cod, name, nameS, data);
		System.out.println(newInst);
		int id = Integer.parseInt(request.getParameter("id"));
		String titleInstitute = null;

		String errorString = null;
		// Try to add or edit
		if (id == -1) { // -1 ==> ЗАПИСЬ НОВАЯ
			boolean okAddInstitute = HBNDAInstitute.insert(newInst);
			if (!okAddInstitute) {
				errorString = "Під час додавання виникла помилка на рівні DBMS\n";
				newInst = new Institute();
				titleInstitute = "Add Institutes";
			}
		} else { // ==> РЕДАКТИРОВАНИЕ
			boolean okEditInstitute = HBNDAInstitute.update(id, newInst);
			if (!okEditInstitute) {
				errorString = "Під час редагування виникла помилка на рівні DBMS\n";
				newInst = HBNDAInstitute.getByID(id);
				titleInstitute = "Edit Institutes";
			}
		}

		// If error, forward to Edit page.
		if (errorString != null) {
			// Prepare attributes to back to input or edit data
			request.setAttribute("errorString", errorString);
			request.setAttribute("institute", newInst);
			request.setAttribute("titleInstitute", titleInstitute);
			request.getServletContext().getRequestDispatcher("/views/Institute.jsp").forward(request, response);
		} else {
			// If everything nice.
			// Redirect to the InstituteList page.
			response.sendRedirect(request.getContextPath() + "/institutes");
		}

	}

}
