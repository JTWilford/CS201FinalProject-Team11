package services;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Vector;

@ServerEndpoint("/session")
public class AuthorizationService {
    //A vector to store all active sessions
    Vector<SessionInfo> sessions = new Vector<>();

    @OnOpen
    public void open(Session session) {
        System.out.println("Connection!");
        sessions.add(new SessionInfo(1, "temp@temp.com", "Temp", "orary", 1, session));
    }

    @OnMessage
    public void message(String message, Session session) {
        System.out.println(message);
        for(SessionInfo s : sessions) {
            try {
                s.session.getBasicRemote().sendText(message);
            } catch (IOException ioe) {
                System.out.println("ioe: " + ioe.getMessage());
            }
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
}

//A class for holding session info
class SessionInfo {
    //Unique ID
    public int uscID;
    //User's email
    public String email;
    //USer's full name
    public String firstName;
    public String lastName;
    //User's permission level
    public int level;
    //The user's Websocket Session
    public Session session;

    public SessionInfo(int uscID, String email, String firstName, String lastName, int level, Session session) {
        this.uscID = uscID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.session = session;
    }
}