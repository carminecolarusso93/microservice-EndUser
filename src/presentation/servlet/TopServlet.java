package presentation.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import application.TestEJBLocal;
import data.dataModel.Intersection;
import com.google.gson.Gson;
import util.*;


/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TopServlet")
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	TestEJBLocal testEJB;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int top = Integer.parseInt(request.getParameter("top"));
		ArrayList<Intersection> resp = testEJB.getTopCriticalNodes(top);
		
		Gson gson = new Gson();
		response.getWriter().append(gson.toJson(resp));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
