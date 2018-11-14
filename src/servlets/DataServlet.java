package servlets;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
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
		name = "DataServlet",
		description = "Handles requests for data on the site to populate tables",
		urlPatterns = {"/Data"}
)
public class DataServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final String sqlusername = "root";
	private static final String sqlpassword = "root";

	private static Connection conn = null;
	private static ResultSet rs = null;
	private static PreparedStatement ps = null;

	private Gson gson = new Gson();

	private void Debug(String msg) {
		System.out.println("[DataServlet] " + msg);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Debug("This is a test. We're in POST");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Debug("This is a test. We're in GET");
		String FileType = request.getParameter("FileType");
		connect();
		PrintWriter pw = response.getWriter();
		//Add the access control header to the response
		response.setHeader("Access-Control-Allow-Origin", "*");

		if (FileType == null) {
			//pw.println("Error: please specify a file type");
			//Add an error in a wrapper
			DataWrapper wrap = new DataWrapper();
			wrap.error = "Please specify a file type";
			//Send the wrapper as JSON
			pw.println(gson.toJson(wrap));
			return;
		}
		try {
			switch (FileType) {
				case "assignment":	// Retrieves all the assignment information in the Assignment database
					List<Assignment> assignments = new ArrayList<>();
					ps = conn.prepareStatement("SELECT number, title, gradePercent, assignedDate, dueDate, pdfLink, additionalFiles, solutionLink FROM Assignments");
					rs = ps.executeQuery();
					while (rs.next()) {
						Assignment assignment = new Assignment();
						assignment.number = rs.getInt("number");
						assignment.title = rs.getString("title");
						assignment.gradePercent = rs.getFloat("gradePercent");
						assignment.assignedDate = rs.getString("assignedDate");
						assignment.dueDate = rs.getString("dueDate");
						assignment.pdfLink = rs.getInt("pdfLink");
						assignment.additionalFiles = rs.getBoolean("additionalFiles");
						assignment.solutionLink = rs.getInt("solutionLink");
						assignments.add(assignment);
					}
					//Insert into the data wrapper
					DataWrapper<Assignment> assignmentWrap = new DataWrapper<>();
					assignmentWrap.data = assignments;
					pw.println(gson.toJson(assignmentWrap));
					break;
				case "lab":		// Retrieves all the lab information in the Labs database
					List<Lab> labs = new ArrayList<>();
					ps = conn.prepareStatement("SELECT number, labDate, labTopics, pdfLink, additionalFiles FROM Labs");
					rs = ps.executeQuery();
					while (rs.next()) {
						Lab lab = new Lab();
						lab.number = rs.getInt("number");
						lab.labDate = rs.getString("labDate");
						lab.labTopics = rs.getString("labTopics");
						lab.pdfLink = rs.getInt("pdfLink");
						lab.additionalFiles = rs.getBoolean("additionalFiles");
						labs.add(lab);
					}
					//Insert into the data wrapper
					DataWrapper<Lab> labWrap = new DataWrapper<>();
					labWrap.data = labs;
					pw.println(gson.toJson(labWrap));
					break;
				case "lecture":		// Retrieves all the lecture information in the Lectures database
					List<Lecture> lectures = new ArrayList<>();
					ps = conn.prepareStatement("SELECT number, lectureDate, lectureTopics, chapters, lectureSlides, programsLinks FROM Lectures");
					rs = ps.executeQuery();
					while (rs.next()) {
						Lecture lecture = new Lecture();
						lecture.number = rs.getInt("number");
						lecture.lectureDate = rs.getString("lectureDate");
						lecture.lectureTopics = rs.getString("lectureTopics");
						lecture.chapters = rs.getString("chapters");
						lecture.lectureSlides = rs.getBoolean("lectureSlides");
						lecture.programFiles = rs.getInt("programsLinks");
						lectures.add(lecture);
					}
					//Insert into the data wrapper
					DataWrapper<Lecture> lectureWrap = new DataWrapper<>();
					lectureWrap.data = lectures;
					pw.println(gson.toJson(lectureWrap));
					break;
				case "exam":		// Retrieves all the previous exam information in the PreviousExams database
					List<Exam> exams = new ArrayList<>();
					ps = conn.prepareStatement("SELECT examDate, type, pdfLink, solutions FROM PreviousExams");
					rs = ps.executeQuery();
					while (rs.next()) {
						Exam exam = new Exam();
						exam.examDate = rs.getString("examDate");
						exam.type = rs.getString("type");
						exam.pdfLink = rs.getInt("pdfLink");
						exam.solutions = rs.getBoolean("solutions");
						exams.add(exam);
					}
					//Insert into the data wrapper
					DataWrapper<Exam> examWrap = new DataWrapper<>();
					examWrap.data = exams;
					pw.println(gson.toJson(examWrap));
					break;
				default:		// The file type was invalid
					DataWrapper wrap = new DataWrapper();
					wrap.error = "Unknown File Type: '" + FileType + "'. Please specify from assignment, lecture, lab, or exam";
					pw.println(gson.toJson(wrap));
			}
		} catch (SQLException sqle) {
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

class Assignment {
	int number; // assignment number
	String title; // title
	float gradePercent; // final grade percent
	String assignedDate; // assigned date
	String dueDate; // due date 
	int pdfLink; // link to the assignment's pdf
	int solutionLink; // link to the solution 
	boolean additionalFiles; // link to any additional files (can be empty)
}

class Lab {
	int number; // lab number 
	String labDate; // lab date 
	String labTopics; // lab topics 
	int pdfLink; // link to the lab description pdf 
	boolean additionalFiles; // link to any additional files for the lab (can be empty)
}

class Lecture {
	int number; // lecture number 
	String lectureDate; // lecture date 
	String lectureTopics; // lecture topics
	String chapters; // chapter numbers 
	boolean lectureSlides; // link to lecture slides 
	int programFiles; // links to demos/programs/git repo (can be empty)
}

class Exam {
	String examDate; // the period of the exam is given
	String type; // whether or not the exam was written or programming 
	int pdfLink; // the link to pdf of the exam
	boolean solutions; // links to the solutions 
}

class DataWrapper<E> {
	String error = "";
	List<E> data = new LinkedList<>();
}
