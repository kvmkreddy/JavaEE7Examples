package org.os.javaee.demo.jms.sender;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;

import org.os.javaee.demo.jms.IJMSSender;

/**
 * <p>Title: SimplifiedJMSSender</p>
 * <p><b>Description:</b> SimplifiedJMSSender</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Open Source Development.</p>
 * @author Murali Reddy
 * @version 1.0
 */
@Local(value=IJMSSender.class)
@Stateless(name="simplifiedJMSSender")
public class SimplifiedJMSSender implements IJMSSender{

    @Resource(lookup = "java:global/jms/demoConnectionFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup = "java:global/jms/demoQueue")
    Queue demoQueue;
    
    public void sendMessage(String body) {
        try (JMSContext context = connectionFactory.createContext();){
            context.createProducer().send(demoQueue, body);
        } catch (JMSRuntimeException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }	
}