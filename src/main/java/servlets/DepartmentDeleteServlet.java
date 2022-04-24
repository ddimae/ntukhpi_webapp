package servlets;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hbndao.HBNDADepartment;
import model.Department;

/**
 * Servlet implementation class Department Delete
 */
@WebServlet("/department_delete")
public class DepartmentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("DepartmentDeleteServlet#doGET");
		int id = Integer.parseInt(request.getParameter("id"));

		String textToConfirm = null;

		Department department = HBNDADepartment.getByID(id);
		StringBuilder sb = new StringBuilder("Ви плануєти вилучити наступний запис:").append("<br>");
		sb.append(department.getNameDepartment()).append("<br>");
		sb.append(department.getShortNameDepartment()).append("<br>");
		sb.append(department.getCodDepartment()).append("<br>");
		if (department.getYearCreate() != null)
			sb.append(department.getYearCreate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).append("<br>");
		sb.append("<br>").append("<br>");
		sb.append("Ви підтверджуєте вилучення?");
		sb.append("<br>").append("<br>");
		textToConfirm = sb.toString();

		request.setAttribute("id", id);
		request.setAttribute("textToConfirm", textToConfirm);
		getServletContext().getRequestDispatcher("/views/DepartmentDelConfirm.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("DepartmentDeleteServlet#doPOST");
		int id = Integer.parseInt(request.getParameter("id"));
		String errorString = null;
		boolean okDelDepartment = HBNDADepartment.delete(id);
		if (okDelDepartment) {
			// If everything nice.
			// Redirect to the DepartmentList page.
			System.out.println("Запись по Department удалена");
			response.sendRedirect(request.getContextPath() + "/departments");
		} else {
			errorString = "Під час вилучення виникла помилка на рівні DBMS\n";
			// Сохранить информацию в request attribute перед тем как forward к views.
			request.setAttribute("errorString", errorString);
			request.getServletContext().getRequestDispatcher("/views/DepartmentDelConfirm.jsp").forward(request,
					response);
		}
	}

}
