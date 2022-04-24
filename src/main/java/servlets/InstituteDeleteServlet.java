package servlets;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hbndao.HBNDAInstitute;
import model.Institute;

/**
 * Servlet implementation class InstituteDelete
 */
@WebServlet("/institute_delete")
public class InstituteDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("InstituteDeleteServlet#doGET");
		int id = Integer.parseInt(request.getParameter("id"));

		String textToConfirm = null;

		Institute institute = HBNDAInstitute.getByID(id);
		StringBuilder sb = new StringBuilder("Ви плануєти вилучити наступний запис:").append("<br>");
		sb.append(institute.getNameInstitute()).append("<br>");
		sb.append(institute.getShortNameInstitute()).append("<br>");
		sb.append(institute.getCodInstitute()).append("<br>");
		if (institute.getYearCreate() != null)
			sb.append(institute.getYearCreate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).append("<br>");
		sb.append("<br>").append("<br>");
		sb.append("Ви підтверджуєте вилучення?");
		sb.append("<br>").append("<br>");
		textToConfirm = sb.toString();

		request.setAttribute("id", id);
		request.setAttribute("textToConfirm", textToConfirm);
		getServletContext().getRequestDispatcher("/views/InstituteDelConfirm.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("InstituteDeleteServlet#doPOST");
		int id = Integer.parseInt(request.getParameter("id"));
		String errorString = null;
		boolean okDelInstitute = HBNDAInstitute.delete(id);
		if (okDelInstitute) {
			// If everything nice.
			// Redirect to the InstituteList page.
			System.out.println("Запись по Institute удалена");
			response.sendRedirect(request.getContextPath() + "/institutes");
		} else {
			errorString = "Під час вилучення виникла помилка на рівні DBMS\n";
			// Сохранить информацию в request attribute перед тем как forward к views.
			request.setAttribute("errorString", errorString);
			request.getServletContext().getRequestDispatcher("/views/InstituteDelConfirm.jsp").forward(request,
					response);
		}
	}

}
