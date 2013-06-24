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
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.Queue;

import org.os.javaee.demo.jms.IJMSReceiver;


/**
 * <p>Title: SimplifiedJMSCDIReceiverWithProperties</p>
 * <p><b>Description:</b> SimplifiedJMSCDIReceiverWithProperties</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Open Source Development.</p>
 * @author Murali Reddy
 * @version 1.0
 */
@Local(value=IJMSReceiver.class)
@Stateless(name="simplifiedJMSCDIReceiverWithProperties")
public class SimplifiedJMSCDIReceiverWithProperties implements IJMSReceiver {

    @Inject
    @JMSConnectionFactory("java:global/jms/demoConnectionFactory") // <== could omit this and use the default
    private JMSContext context;	
    @Resource(lookup = "java:global/jms/demoQueue")
    Queue inboundQueue;
    
	public String receiveMessage() {
        try {
            JMSConsumer consumer = context.createConsumer(inboundQueue);
            
            Message message = consumer.receive(1000);
            if(message == null){
            	return "Received NULL Message";
            }
            return "Received Message with Body -->:"+(message.getBody(String.class))+", with JMS Priority" + (message.getJMSPriority())+" and property IS_NON_VEG_DAY -->:"+(message.getStringProperty("IS_NON_VEG_DAY"));
        } catch (JMSRuntimeException | JMSException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
	}
}