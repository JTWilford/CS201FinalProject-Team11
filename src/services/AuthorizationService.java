package services;

import com.google.gson.Gson;
import servlets.DataWrapper;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.Vector;

@ServerEndpoint("/session")
public class AuthorizationService {
    //A vector to store all active sessions
    Vector<SessionInfo> sessions = new Vector<>();
    Gson gson = new Gson();
    private static final String sqlusername = "root";
    private static final String sqlpassword = "root";

    private static Connection conn = null;
    private static ResultSet rs = null;
    private static PreparedStatement ps = null;

    @OnOpen
    public void open(Session session) {
        System.out.println("Connection!");
        sessions.add(new SessionInfo(session));
        try {
            DataWrapper<String> wrap = new DataWrapper<>();
            wrap.error = "";
            wrap.data.add("Welcome to the party!");
            session.getBasicRemote().sendText(gson.toJson(wrap));
        } catch(IOException ioe) {
            System.out.println("ioe: " + ioe.getMessage());
        }
    }

    @OnMessage
    public void message(String message, Session session) {
        System.out.println(message);
        LoginInfo user = gson.fromJson(message, LoginInfo.class);
        try {
            DataWrapper<SessionInfo> wrap = login(user.email, user.password, session);
            session.getBasicRemote().sendText(gson.toJson(wrap));
        } catch (IOException ioe) {
            System.out.println("ioe: " + ioe.getMessage());
        }
    }

    @OnClose
    public void close(Session session) {
        System.out.println("Disconnected!");
        for(int i = 0; i < sessions.size(); i++) {
            if(sessions.get(i).session == session) {
                sessions.remove(i);
                break;  //We only want to remove one session, so break afterwards
            }
        }
    }

    @OnError
    public void error(Throwable error) {
        System.out.println("There was an error found in one of the sockets: ");
        System.out.println(error.getMessage());
    }

    private DataWrapper<SessionInfo> login(String email, String password, Session session) {
        try {
            connect();
            ps = conn.prepareStatement("SELECT * FROM Account WHERE email=? AND password=?");
            email = email.toLowerCase();        //Make the email lower case
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()) {     //User found!
                int i = 0;
                for (; i < sessions.size(); i++) {
                    if (sessions.get(i).session == session) {
                        break;
                    }
                }
                SessionInfo curr = sessions.get(i);

                curr.uscID = (rs.getLong("uscID"));
                curr.firstName = rs.getString("firstName");
                curr.lastName = rs.getString("lastName");
                curr.email = rs.getString("email");
                curr.level = rs.getInt("accountLevel");
                curr.authorized = true;
                DataWrapper<SessionInfo> wrap = new DataWrapper<>();
                wrap.data.add(curr);
                return wrap;
            }
            else {      //User not found
                DataWrapper<SessionInfo> wrap = new DataWrapper<>();
                wrap.error = "Invalid email or password";
                return wrap;
            }

        } catch(SQLException sqle) {
            System.out.println("sqle: " + sqle.getMessage());
            DataWrapper<SessionInfo> wrap = new DataWrapper<>();
            wrap.error = "SQL Exception: " + sqle.getMessage();
            return wrap;
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
    }
}

//A class for holding session info
class SessionInfo {
    //Whether or not the user has logged in
    public boolean authorized = false;
    //Unique ID
    public long uscID;
    //User's email
    public String email;
    //User's full name
    public String firstName;
    public String lastName;
    //User's permission level
    public int level;
    //The user's Websocket Session
    public transient Session session;

    public SessionInfo(Session session) {
        this.authorized = false;
        this.uscID = 0;
        this.email = "";
        this.firstName = "";
        this.lastName = "";
        this.level = -1;
        //Set the session
        this.session = session;
    }
}

class LoginInfo {
    public String email;
    public String password;
}