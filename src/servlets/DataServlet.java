package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "DataServlet",
        description = "Handles requests for data on the site to populate tables",
        urlPatterns = {"/Data"}
)
public class DataServlet extends HttpServlet {
    private void Debug(String msg) {
        System.out.println("[DataServlet] " + msg);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Debug("This is a test. We're in POST");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Debug("This is a test. We're in GET");
    }
}
