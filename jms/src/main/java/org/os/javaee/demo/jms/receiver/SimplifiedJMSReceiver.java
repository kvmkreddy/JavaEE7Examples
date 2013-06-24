package org.os.javaee.demo.jms.receiver;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;

import org.os.javaee.demo.jms.IJMSReceiver;

/**
 * <p>Title: SimplifiedJMSReceiver</p>
 * <p><b>Description:</b> SimplifiedJMSReceiver</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Open Source Development.</p>
 * @author Murali Reddy
 * @version 1.0
 */
@Local(value=IJMSReceiver.class)
@Stateless(name="simplifiedJMSReceiver")
public class SimplifiedJMSReceiver implements IJMSReceiver{

    @Resource(lookup = "java:global/jms/demoConnectionFactory")
    ConnectionFactory connectionFactory;
    @Resource(lookup = "java:global/jms/demoQueue")
    Queue demoQueue;

	public String receiveMessage() {
        try (JMSContext context = connectionFactory.createContext();){
            JMSConsumer consumer = context.createConsumer(demoQueue);
            return "Received " + consumer.receiveBody(String.class, 1000);
        } catch (JMSRuntimeException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;	
    }
}