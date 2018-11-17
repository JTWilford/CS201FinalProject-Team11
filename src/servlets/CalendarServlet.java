package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
@WebServlet(
        name = "CalendarServlet",
        description = "Handles Requests for retrieving or updating calendar information",
        urlPatterns = {"/Calendar"}
)
public class CalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String sqlusername = "root";
	private static final String sqlpassword = "Yty712719#";

	private static Connection conn = null;
	private static ResultSet rs = null;
	private static PreparedStatement ps = null;

	private Gson gson = new Gson();

	private void Debug(String msg) {
		System.out.println("[DataServlet] " + msg);
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Debug("This is a test. We're in GET");
		connect();
		PrintWriter pw = response.getWriter();
		//Add the access control header to the response
		response.setHeader("Access-Control-Allow-Origin", "*");
	
		try {
			List<event> events = new ArrayList<>();
			ps = conn.prepareStatement("SELECT title, date, startTime, endTime FROM Calendar");
			rs = ps.executeQuery();
			while(rs.next())
			{
				event Event = new event();
				Event.title = rs.getString("title");
				Event.date = rs.getString("date");
				Event.startTime = rs.getString("startTime");
				Event.endTime = rs.getString("endTime");
				if (Event.title == null) {
					//pw.println("Error: please specify a file type");
					//Add an error in a wrapper
					DataWrapper wrap = new DataWrapper();
					wrap.error = "Please specify event title";
					//Send the wrapper as JSON
					pw.println(gson.toJson(wrap));
					return;
				}
				if(Event.date == null){
					DataWrapper wrap = new DataWrapper();
					wrap.error = "Please specify event date";
					//Send the wrapper as JSON
					pw.println(gson.toJson(wrap));
					return;
				}
				System.out.println(Event.title);
				System.out.println(Event.date);
				System.out.println(Event.startTime);
				System.out.println(Event.endTime);
				events.add(Event);
			}
			DataWrapper<event> eventWrap = new DataWrapper<>();
			eventWrap.data = events;
			pw.println(gson.toJson(eventWrap));
		}catch (SQLException sqle) {
			// TODO: handle exception
			System.out.println("sqle: " + sqle.getMessage());
			//Send the error to the frontend
			DataWrapper wrap = new DataWrapper();
			wrap.error = "SQL Exception: " + sqle.getMessage();
			pw.println(gson.toJson(wrap));
		} finally {
			closeResultStatementSet();
			closeConnection();
		}
    }
    
	public static void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Data?user=" + sqlusername + "&password="
					+ sqlpassword + "&useSSL=false");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // function to connect to the server

	public static void closeResultStatementSet() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps = null;
			}
		} catch (SQLException sqle) {
			System.out.println("result-statement-set close error");
			sqle.printStackTrace();
		}
	} // function to disconnect from the server

	public static void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException sqle) {
			System.out.print("connection close error");
			sqle.printStackTrace();
		}
	}
}

class event
{
	String title;// title of the event
	String date;//date of the event
	String startTime;//start time of the event
	String endTime;//end time of the event
}