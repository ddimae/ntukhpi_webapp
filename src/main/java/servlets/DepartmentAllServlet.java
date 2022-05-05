package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowTableServlet
 */
@WebServlet("/departments")
public class DepartmentAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("DepartmentAllServlet#doGET");
		// Prepare attribute for JSP
		request.setAttribute("institutes_list", hbndao.HBNDAInstitute.getNamesInstituteList());//1
		String filterInst = request.getParameter("send_select"); 
		if (filterInst==null) {
			filterInst = "any";
		}
		//System.out.println("inst_select = "+filterInst);
		System.out.println("DepartmentAllServlet#doGet#inst_select = "+filterInst);
		if ("any".equals(filterInst)) {                          //
			request.setAttribute("departments", hbndao.HBNDADepartment.getAll());
		} else {
			request.setAttribute("departments", hbndao.HBNDADepartment.getAllInInstitutes(filterInst));
		}
		//Назначаем атрибут "inst_select" (
		request.setAttribute("inst_select",filterInst); //3
		// Resend to JSP
		getServletContext().getRequestDispatcher("/views/DepartmentList.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
