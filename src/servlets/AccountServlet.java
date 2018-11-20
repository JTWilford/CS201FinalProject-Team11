package servlets;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;

@WebServlet(
        name = "AccountServlet",
        description = "Handles logging into the site and retrieving user information",
        urlPatterns = {"/Accounts"}
)
public class AccountServlet extends HttpServlet {
    private static final String sqlusername = "root";
    private static final String sqlpassword = "root";

    private static Connection conn = null;
    private static ResultSet rs = null;
    private static PreparedStatement ps = null;
    private Gson gson = new Gson();

    /*
    *   Request Parameters:
    *       email: string
    *       password: string
    *       uscID: long
    *       firstName: string
    *       lastName: string
    *       gitHub: string
    *       accountLevel: string
    * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[AccountServlet] In POST");
        PrintWriter pw = response.getWriter();
        //Add the access control header to the response
        response.setHeader("Access-Control-Allow-Origin", "*");

        connect();

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        long uscID = Long.parseLong(request.getParameter("uscID"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String gitHub = request.getParameter("gitHub");
        String accountLevel = request.getParameter("accountLevel");

        //Error checking
        LinkedList<String> errors = new LinkedList<>();
        if(email == null || email.equals(""))
            errors.add("Email");
        if(password == null || password.equals(""))
            errors.add("Password");
        if(uscID <= 0)
            errors.add("USC ID");
        if(firstName == null || firstName.equals(""))
            errors.add("First Name");
        if(lastName == null || lastName.equals(""))
            errors.add("Last Name");
        //No error checking for github account (they don't have to specify it)
//        if(gitHub == null || gitHub.equals(""))
//            errors.add("GitHub Account");
        int level = 4;
        switch (accountLevel) {
            case "Student":
                level = 3;
                break;
            case "TA/CP":
                level = 2;
                break;
            case "Professor":
                level = 1;
                break;
            default:
                errors.add("Account Level");
                break;
        }

        if(errors.size() != 0) {
            System.out.println("User sent an invalid account creation request");
            System.out.print("\tInvalid Fields: ");
            String formattedErrors = "";
            for(String item: errors) {
                if(!formattedErrors.equals(""))
                    formattedErrors += ", ";
                formattedErrors += item;
            }
            System.out.println(formattedErrors);
            DataWrapper wrap = new DataWrapper();
            wrap.error = "Invalid Fields: " + formattedErrors;
            pw.println(gson.toJson(wrap));
            return;
        }

        //If no errors, then add a new entry into the SQL database
        try {
            ps = conn.prepareStatement("INSERT INTO " +
                    "Account (uscID, firstName, lastName, email, password, githubAccount, accountLevel)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setLong(1, uscID);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.setString(5, password);
            ps.setString(6, gitHub);
            ps.setInt(7, level);

            ps.execute();
            DataWrapper wrap = new DataWrapper();
            pw.println(gson.toJson(wrap));
        } catch (SQLException sqle) {
            System.out.println("sqle: " + sqle.getMessage());
            DataWrapper wrap = new DataWrapper();
            wrap.error = "SQL Exception: " + sqle.getMessage();
            pw.println(gson.toJson(wrap));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private static void connect() {
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
}

class newUser {
    public long uscID;
    public String email;
    public String password;
    public String firstName;
    public String lastName;
    public String github;
}
