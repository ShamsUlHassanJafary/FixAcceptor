package com.example.fixacceptor.acceptor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionSettings;
import quickfix.ThreadedSocketAcceptor;
import quickfix.field.ApplVerID;

@Slf4j
@Configuration
public class AcceptorConfig {

    private static final String configFile = "config/fix/fusion.cfg";

    @Autowired
    AcceptorApplication application;

    @Bean
    public ThreadedSocketAcceptor threadedSocketAcceptor() {
        // System.out.println("going through FConfig");
        ThreadedSocketAcceptor threadedSocketAcceptor = null;

        try {
            SessionSettings settings = new SessionSettings(new FileInputStream(configFile));
            // System.out.println("SETTINGS : " + settings);
            MessageStoreFactory storeFactory = new FileStoreFactory(settings);
            LogFactory logFactory = new FileLogFactory(settings);
            MessageFactory messageFactory = new DefaultMessageFactory(ApplVerID.FIX50SP2);
            threadedSocketAcceptor = new ThreadedSocketAcceptor(
                    application, storeFactory, settings, logFactory,
                    messageFactory);
            threadedSocketAcceptor.start();
        } catch (ConfigError configError) {
            configError.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error("FConfig startup", e.getMessage());
        }

        return threadedSocketAcceptor;
    }

}
