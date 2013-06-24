package org.os.javaee.demo.jms.sender;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.os.javaee.demo.jms.IJMSSender;

/**
 * <p>Title: ClassicJMSSender</p>
 * <p><b>Description:</b> ClassicJMSSender</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Open Source Development.</p>
 * @author Murali Reddy
 * @version 1.0
 */
@Local(value=IJMSSender.class)
@Stateless(name="classicJMSSender")
public class ClassicJMSSender implements IJMSSender {

    @Resource(lookup = "java:global/jms/demoConnectionFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup = "java:global/jms/demoQueue")
    Queue demoQueue;
    
    public void sendMessage(String body) {
        try {
            Connection connection = connectionFactory.createConnection();
            try {
                Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
                MessageProducer messageProducer = session.createProducer(demoQueue);
                TextMessage textMessage = session.createTextMessage(body);
                messageProducer.send(textMessage);
            } finally {
                connection.close();
            }
        } catch (JMSException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}