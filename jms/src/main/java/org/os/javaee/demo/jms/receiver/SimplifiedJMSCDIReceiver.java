package org.os.javaee.demo.jms.receiver;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;

import org.os.javaee.demo.jms.IJMSReceiver;

/**
 * <p>Title: SimplifiedJMSCDIReceiver</p>
 * <p><b>Description:</b> SimplifiedJMSCDIReceiver</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Open Source Development.</p>
 * @author Murali Reddy
 * @version 1.0
 */
@Local(value=IJMSReceiver.class)
@Stateless(name="simplifiedJMSCDIReceiver")
public class SimplifiedJMSCDIReceiver implements IJMSReceiver {

    @Inject
    @JMSConnectionFactory("java:global/jms/demoConnectionFactory") // <== could omit this and use the default
    private JMSContext context;	
    @Resource(lookup = "java:global/jms/demoQueue")
    Queue inboundQueue;
    
	public String receiveMessage() {
        try {
            JMSConsumer consumer = context.createConsumer(inboundQueue);
            return "Received " + consumer.receiveBody(String.class, 1000);
        } catch (JMSRuntimeException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
	}
}