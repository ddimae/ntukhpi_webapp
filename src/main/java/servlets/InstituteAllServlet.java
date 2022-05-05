package servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Institute;

/**
 * Servlet implementation class ShowTableServlet
 */
@WebServlet("/institutes")
public class InstituteAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("InstituteAllServlet#doGET");
		//Prepare attribute for JSP
		String numSortStr = request.getParameter("sortInst");
		int numSort = 0;
		if (numSortStr!=null) numSort = Integer.parseInt(numSortStr);
		List<Institute> instListToShow = hbndao.HBNDAInstitute.getAll();
		System.out.println(instListToShow);
		switch (numSort) {
		//by Cod
		case 1: instListToShow = instListToShow.stream()
				.sorted((i1,i2)->i1.getCodInstitute()-i2.getCodInstitute())
				.collect(Collectors.toList());
			break;
	    //by shortName
		case 2: instListToShow = instListToShow.stream()
				.sorted((i1,i2)->i1.getShortNameInstitute().compareTo(i2.getShortNameInstitute()))
				.collect(Collectors.toList());
			break;
		//by Date Create
		case 3:
			instListToShow = instListToShow.stream()
			.sorted((i1,i2)->i1.getYearCreate().compareTo(i2.getYearCreate()))
			.collect(Collectors.toList());
			break;
		}
		request.setAttribute("institutes", instListToShow);
		//Resend to JSP
		getServletContext().getRequestDispatcher("/views/InstituteList.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
