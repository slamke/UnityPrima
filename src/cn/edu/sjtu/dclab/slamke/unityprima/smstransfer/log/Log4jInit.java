package cn.edu.sjtu.dclab.slamke.unityprima.smstransfer.log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInit extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -670846727380458054L;
	
	public static final String CONTENT_TYPE = "text/html; charset=utf-8";
    //Initialize global variables
    public void init() throws ServletException {
        String prefix = getServletContext().getRealPath("/");
        String file = getInitParameter("log4j-init-file");
        if(file!=null){
            PropertyConfigurator.configure(prefix+file);
        }
        System.out.println("Log4j initialized suc!");
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        {}
    }
    //Clean up resources
    public void destroy() {
    }
}
