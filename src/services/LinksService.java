package services;

import javax.naming.LinkLoopException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinksService {
    private static final String sqlusername = "root";
    private static final String sqlpassword = "root";

    private static Connection conn = null;
    private static ResultSet rs = null;
    private static PreparedStatement ps = null;

    public static Link getLink(int id) {
        connect();

        try {
            ps = conn.prepareStatement("SELECT * FROM Links WHERE linkID=?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                Link link = new Link();
                link.display = rs.getString("display");
                link.href = rs.getString("href");
                return (link);
            }
            else {
                System.out.println("Link with id " + id + " not found");
                return (null);
            }
        } catch (SQLException sqle) {
            System.out.println("sqle: " + sqle.getMessage());
        }
        return(null);
    }
    public static LinkedList<Link> getLinks(List<Integer> ids) {
        LinkedList<Link> results = new LinkedList<>();
        for(Integer item : ids) {
            Link temp = getLink(item);
            if(temp != null)
                results.add(temp);
            else
                System.out.println("Found a null link for id: " + item);
        }
        return results;
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
