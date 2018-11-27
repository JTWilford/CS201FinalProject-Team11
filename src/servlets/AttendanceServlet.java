package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.fabric.RangeShardMapping;
import com.sun.beans.editors.IntegerEditor;
import services.ResponseSetup;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "AttendanceServlet",
        description = "Handles requests for attendance",
        urlPatterns = {"/Attendance"}
)

public class AttendanceServlet extends HttpServlet {
	public static Classroom[] classes = {
	                                 	  new Classroom(34.021002, 34.021502, -118.288463, -118.287279),
	                                      new Classroom(34.019222, 34.019731, -118.289793, -118.289082),
	                                      new Classroom(34.019941, 34.020470, -118.286028, -118.285410)
	};
	private static final String sqlusername = "root";
	private static final String sqlpassword = "root";

	private static Connection conn = null;
	private static ResultSet rs = null;
	private static PreparedStatement ps = null;
	
	private Gson gson = new Gson();
    /*
    * Post Request:
    *   Expected Format:
    *       studentID: the unique user identifier
    *       classID: the unique class identifier (1, 2, or 3) (to determine class location)
    *       latitude: the latitude number
    *       longitude: the longitude number
    *
    *   Method:
    *       Will add a new entry to the attendance database showing that the user has checked into class on time (check the time first)
    *
    *   Response:
    *       success: a boolean stating whether or not the user checked in
    *
    * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("[AttendanceServlet] In Post");
    	long studentID = Long.parseLong(request.getParameter("uscID"));
    	int classID = Integer.parseInt(request.getParameter("classID"));
    	double latitude = Double.parseDouble(request.getParameter("latitude"));
    	double longitude = Double.parseDouble(request.getParameter("longitude"));
    	PrintWriter pw = response.getWriter();
		//Add the access control header to the response
		ResponseSetup.fixOptions(response);
    	boolean success = false;
//    	if (classes[classID - 1].x1 < latitude && classes[classID - 1].x2 > latitude && classes[classID - 1].y1 < longitude && classes[classID - 1].y2 > longitude) {
    	if(true) {
			success = true;
    		connect();
    		System.out.println("[AttendanceServlet] Connected to database");
    		try {
    			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				ps = conn.prepareStatement("INSERT INTO Attendance (uscID, date, lecturePeriod) VALUES (?, ?, ?)");
				ps.setLong(1, studentID);
				ps.setString(2, dtf.format(LocalDate.now()));
				ps.setInt(3, classID);
				System.out.println("[AttendanceServlet] Set parameters");
				ps.execute();
				System.out.println("[AttendanceServlet] Executed statement");
			} catch (SQLException sqle) {
				// TODO: handle exception
				System.out.println("sqle: " + sqle.getMessage());
				success = false;

			} finally {
				closeResultStatementSet();
				closeConnection();
			}
    		DataWrapper wrapper = new DataWrapper();
    		if (!success) {
    			wrapper.error = "Failed to check in";
    		}
    		pw.println(gson.toJson(wrapper));
    		// pw.println(System.currentTimeMillis());
    	}
    	else {
    		System.out.println("[AttendanceServlet] User is not within bounds!");
    		DataWrapper wrap = new DataWrapper();
    		wrap.error = "You are not within the bounds of the classroom";
    		pw.println(gson.toJson(wrap));
		}
 
    }

    /*
    * Get Request:
    *   Expected Format:
    *       studentID: a unique user identifier
    *
    *   Method:
    *       Will look up the requested user's attendance records and send them back in the response as a JSON object
    *
    *   Response:
    *       err: a string that will contain an error message (if there is one, otherwise will be null)
    *       data: a JSON object containing the requested user's attendance data in the following format:
    *           date: the date of the class attended (MM-DD-YYYY)
    *           classID: the unique class identifier
    *
    * */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[AttendanceServlet] In Get");
    	long studentID = Long.parseLong(request.getParameter("uscID"));
    	List<Record> records = new ArrayList<>();
    	PrintWriter pw = response.getWriter();
    	connect();

		//Add the access control header to the response
//		response.setHeader("Access-Control-Allow-Origin", "*");
		ResponseSetup.fixOptions(response);
    	
    	try {
			ps = conn.prepareStatement("SELECT date, lecturePeriod FROM Attendance WHERE uscID=?");
			ps.setLong(1, studentID);
			rs = ps.executeQuery();
			while (rs.next()) {
				Record record = new Record();
				record.date = rs.getString("date");
				record.lecturePeriod = rs.getInt("lecturePeriod");
				records.add(record);
			}
			DataWrapper<Record> recordWrap = new DataWrapper<>();
			if (records.isEmpty()) {
				recordWrap.error = "There is no such student or the student does not attend any lectures,";
				pw.println(gson.toJson(recordWrap));
			} else {
				recordWrap.data = records;
				pw.println(gson.toJson(recordWrap));
			}
		} catch (SQLException sqle) {
			// TODO: handle exception
			System.out.println("sqle: " + sqle.getMessage());
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

	//for Preflight
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
    	System.out.println("[AttendanceServlet] In Options");
		ResponseSetup.fixOptions(resp);
	}
}

class Classroom {
	public double x1;
	public double x2;
	public double y1;
	public double y2;
	public Classroom(double _x1, double _x2, double _y1, double _y2) {
        this.x1 = _x1;
        this.x2 = _x2;
        this.y1 = _y1;
        this.y2 = _y2;
    }
}

class Record {
	public String date;
	public int lecturePeriod;
}
	