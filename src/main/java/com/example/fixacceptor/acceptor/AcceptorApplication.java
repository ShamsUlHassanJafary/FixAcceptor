package com.example.fixacceptor.acceptor;

import org.springframework.stereotype.Component;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;

@Component
public class AcceptorApplication implements Application {

    public static volatile SessionID sessionID;

    @Override
    public void onCreate(SessionID sessionId) {
        System.out.println(String.format("Acceptor Created : {%s}", sessionId));
    }

    @Override
    public void onLogon(SessionID sessionId) {

        AcceptorApplication.sessionID = sessionId;
        System.out.println(String.format("Accepted Fix connection from Fusion : {%s}", sessionId));
    }

    @Override
    public void onLogout(SessionID sessionId) {
        AcceptorApplication.sessionID = null;
        System.out.println(String.format("Acceptor App Logout : {%s}", sessionId));
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        System.out.println("Acceptor App toAdmin : " + message);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        System.out.println(String.format("Acceptor App fromAdmin : {%s}, {%s}", message, sessionId));
    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {

        System.out.println(String.format("Acceptor toApp: {%s}, {%s}", message, sessionID));
    }

    @Override
    public void fromApp(Message message, SessionID sessionId)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {

        System.out.println(String.format("Acceptor toApp: {%s}, {%s}", message, sessionID));
    }
}
