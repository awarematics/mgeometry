
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name = "GetAndPostExample", urlPatterns = {"/GetAndPostExample"})
public class GetAndPostExample extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public GetAndPostExample() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql = null;
		String res = null;
		sql = (String) request.getParameter("submit");

		System.out.println("data:\t" + sql);
		try {
			res = OperationRefines.Postgresql(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(res);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(res);
		out.flush();
		out.close();
	}

}