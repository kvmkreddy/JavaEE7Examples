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

import org.os.javaee.demo.jms.CompletionListenerImpl;
import org.os.javaee.demo.jms.IJMSSender;


/**
 * <p>Title: SimplifiedJMSAsyncSender</p>
 * <p><b>Description:</b> SimplifiedJMSAsyncSender</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Open Source Development.</p>
 * @author Murali Reddy
 * @version 1.0
 */
@Local(value=IJMSSender.class)
@Stateless(name="simplifiedJMSAsyncSender")
public class SimplifiedJMSAsyncSender implements IJMSSender{

    @Resource(lookup = "java:global/jms/demoConnectionFactory")
    ConnectionFactory connectionFactory;
    
    @Resource(lookup = "java:global/jms/demoQueue")
    Queue demoQueue;
    
    public void sendMessage(String body) {
        try (JMSContext context = connectionFactory.createContext();){
            context.createProducer().setAsync(new CompletionListenerImpl()).send(demoQueue, body);
        } catch (JMSRuntimeException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
/*    public static void main(String[] args){
    	
    	SimplifiedJMSAsyncSender asyncSender = new SimplifiedJMSAsyncSender();
    	
    	try {
			Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.enterprise.naming.SerialInitContextFactory"); 
			env.put("org.omg.CORBA.ORBInitialPort", "3700"); 
			env.put("org.omg.CORBA.ORBInitialHost", "10.198.50.113"); 
			env.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming"); 
			env.put("java.naming.factory.state",  "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl"); 


			Context context = new InitialContext(env);
			ConnectionFactory connectionFactory = (ConnectionFactory)context.lookup("java:global/jms/demoConnectionFactory");
		} catch (NamingException e) {
			e.printStackTrace();
		}
    	
    } */
}