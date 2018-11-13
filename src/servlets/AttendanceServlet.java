package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "AttendanceServlet",
        description = "Handles requests for attendance",
        urlPatterns = {"/Attendance"}
)
public class AttendanceServlet extends HttpServlet {
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
    *       time: the time of the check-in (see RFC3339, its the format the Java DateTime object should give you)
    *
    * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
    *           time: the time of the check-in (mm:mm:ssT[Time-Zone])(see RFC3339, its the format the Java DateTime object should give you)
    *           classID: the unique class identifier
    *
    * */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
