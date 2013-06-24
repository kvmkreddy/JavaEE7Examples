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
 * <p>Title: SimplifiedJMSCDISenderWithProperties</p>
 * <p><b>Description:</b> SimplifiedJMSCDISenderWithProperties</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Open Source Development.</p>
 * @author Murali Reddy
 * @version 1.0
 */
@Local(value=IJMSSender.class)
@Stateless(name="simplifiedJMSCDISenderWithProperties")
public class SimplifiedJMSCDISenderWithProperties implements IJMSSender {

    @Resource(lookup = "java:global/jms/demoConnectionFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup = "java:global/jms/demoQueue")
    Queue demoQueue;
    
    public void sendMessage(String body) {
        try (JMSContext context = connectionFactory.createContext();){
            switch(java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK)){
        		case java.util.Calendar.SUNDAY:
        		case java.util.Calendar.TUESDAY:
        		case java.util.Calendar.WEDNESDAY:
        		case java.util.Calendar.FRIDAY:
            		context.createProducer().setPriority(1).setProperty("IS_NON_VEG_DAY", "YES").send(demoQueue, body);
        		case java.util.Calendar.MONDAY:
        		case java.util.Calendar.THURSDAY:
        		case java.util.Calendar.SATURDAY:
        			context.createProducer().setPriority(2).setProperty("IS_NON_VEG_DAY", "NO");			
        	}
        } catch (JMSRuntimeException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }	
}