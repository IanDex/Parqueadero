package views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.GenericController;

/**
 * Servlet implementation class GenericServlet
 */
@WebServlet("/GenericServlet")
public class GenericServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GenericController genCont = new GenericController();
    /**
     * Default constructor. 
     */
    public GenericServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 try {
	            request.getRequestDispatcher("/html/index.jsp").forward(request, response);
	            System.out.println(genCont.select());
	        } catch (IOException e) {
	            System.out.println("IOException: \n");
	            e.printStackTrace();
	        } catch (ServletException e) {
	            System.out.println("ServletException: \n");
	            e.printStackTrace();
	        } catch (Exception e) {
	            System.out.println("Exception: \n");
	            e.printStackTrace();
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String opc = request.getParameter("opc");
		String placa = request.getParameter("placa");
		String res = "";
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(opc.equals("send")) {
			System.out.println(!genCont.check(placa));
			if(!genCont.check(placa)) {
				if(genCont.insert(placa)) {
					res = "true";
					out.print(res);
				}else {
					res = "false";
					out.print(res);
				}
			}else {
				res = "rep";
				out.print(res);
			}
		}else if(opc.equals("placas")) {
			out.print(genCont.select());
		}else if(opc.equals("sale")) {
			String id = request.getParameter("id");
			out.print(genCont.sale(id));
		}else if(opc.equals("tabla")) {
			out.print(genCont.tabla());
		}
		System.out.println(res);

		out.flush();
		out.close();
	}

}
