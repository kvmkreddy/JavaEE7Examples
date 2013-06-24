package org.os.javaee.demo.jms.sender;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;

import org.os.javaee.demo.jms.IJMSSender;

/**
 * <p>Title: SimplifiedJMSCDISender</p>
 * <p><b>Description:</b> SimplifiedJMSCDISender</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Open Source Development.</p>
 * @author Murali Reddy
 * @version 1.0
 */
@Local(value=IJMSSender.class)
@Stateless(name="simplifiedJMSCDISender")
public class SimplifiedJMSCDISender implements IJMSSender {

    @Inject private JMSContext context;
    
    @Resource(lookup = "java:global/jms/demoQueue")
    Queue inboundQueue;

	public void sendMessage(String body) {
        try {
            context.createProducer().send(inboundQueue, body);
        } catch (JMSRuntimeException ex){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

}
