package services;

import com.google.gson.Gson;
import servlets.DataWrapper;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

@ServerEndpoint("/session")
public class AuthorizationService {
    //A vector to store all active sessions
    Vector<SessionThread> sessions = new Vector<>();
    Gson gson = new Gson();
    private static final String sqlusername = "root";
    private static final String sqlpassword = "root";

    private static Connection conn = null;
    private static ResultSet rs = null;
    private static PreparedStatement ps = null;

    @OnOpen
    public void open(Session session) {
        System.out.println("Connection!");
        SessionThread sess = new SessionThread(session, new SessionInfo());
        sess.start();
        sessions.add(sess);
//        try {
//            DataWrapper<String> wrap = new DataWrapper<>();
//            wrap.error = "";
//            wrap.data.add("Welcome to the party!");
//            session.getBasicRemote().sendText(gson.toJson(wrap));
//        } catch(IOException ioe) {
//            System.out.println("ioe: " + ioe.getMessage());
//        }
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
                //Find the current session thread
                SessionThread curr = null;
                for(SessionThread item : sessions) {
                    if(item.session == session) {
                        curr = item;
                    }
                }
                if(curr == null) {
                    System.out.println("Session not found!!!");
                    DataWrapper<SessionInfo> wrap = new DataWrapper<>();
                    wrap.error = "General Exception: Theres something seriously wrong with the socket";
                    return wrap;
                }

                curr.info.uscID = (rs.getLong("uscID"));
                curr.info.firstName = rs.getString("firstName");
                curr.info.lastName = rs.getString("lastName");
                curr.info.email = rs.getString("email");
                curr.info.level = rs.getInt("accountLevel");
                curr.info.authorized = true;
                DataWrapper<SessionInfo> wrap = new DataWrapper<>();
                wrap.data.add(curr.info);
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
class SessionThread extends Thread {
    private static final long TIMEOUT_MILLIS = 6000000;     //1 hour in milliseconds
//    private static final long TIMEOUT_MILLIS = 600000;       //10 minutes in milliseconds
//    private static final long TIMEOUT_MILLIS = 120000;      //2 minutes in milliseconds
//    private static final long TIMEOUT_MILLIS = 6000;       //6 seconds in milliseconds

    public Session session;
    public SessionInfo info;
    private long lastActivity = 0;
    private Gson gson = new Gson();
    private boolean isRunning = true;

    public SessionThread(Session session, SessionInfo info) {
        this.session = session;
        this.info = info;
        this.lastActivity = System.currentTimeMillis();
    }

    public void updateActivity() {
        this.lastActivity = System.currentTimeMillis();
    }

    public void run() {
        while(isRunning) {
            //Timeout has been reached
            long timeout = TIMEOUT_MILLIS;
            if(this.info.email.contains("timeout")) {
//                System.out.println("Timeout account found");
                timeout = 6000;
            }
            if (System.currentTimeMillis() - lastActivity >= timeout) {
                System.out.println("[AuthenticationService] User \'" + this.info.email + "\' has timed out");
                DataWrapper wrap = new DataWrapper();
                wrap.error = "You have been logged out due to inactivity";
                try {
                    this.session.getBasicRemote().sendText(gson.toJson(wrap));
                } catch (IOException ioe) {
                    System.out.println("ioe: " + ioe.getMessage());
                } finally {
                    this.isRunning = false;
                }
            }
//            System.out.println("[THREAD] yielding");
            Thread.yield();
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

    public SessionInfo() {
        this.authorized = false;
        this.uscID = 0;
        this.email = "";
        this.firstName = "";
        this.lastName = "";
        this.level = -1;
    }
}

class LoginInfo {
    public String email;
    public String password;
}