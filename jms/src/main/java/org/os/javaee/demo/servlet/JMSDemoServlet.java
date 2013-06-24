package org.os.javaee.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactoryDefinition;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.os.javaee.demo.jms.IJMSReceiver;
import org.os.javaee.demo.jms.IJMSSender;

/**
 * <p>Title: Servlet implementation class JMSDemoServlet</p>
 * <p><b>Description:</b> JMSDemoServlet</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Open Source Development.</p>
 * @author Murali Reddy
 * @version 1.0
 */

@JMSDestinationDefinition(
	name = "java:global/jms/demoQueue",
	description = "Queue to use in demonstration", 
	interfaceName = "javax.jms.Queue"
)
@JMSConnectionFactoryDefinition(
	name="java:global/jms/demoConnectionFactory",
	description="ConnectionFactory to use in demonstration"
)       

@WebServlet(name = "JMSDemoServlet", urlPatterns = {"/JMSDemoServlet"})
public class JMSDemoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	@EJB(beanName="simplifiedJMSSender") private IJMSSender simplifiedJMSSender;
	@EJB(beanName="simplifiedJMSCDISender") private IJMSSender simplifiedJMSCDISender;
	@EJB(beanName="classicJMSSender") private IJMSSender classicJMSSender;
	@EJB(beanName="simplifiedJMSAsyncSender") private IJMSSender simplifiedJMSAsyncSender;
	@EJB(beanName="simplifiedJMSCDISenderWithProperties") private IJMSSender simplifiedJMSCDISenderWithProperties;
	
	@EJB(beanName="simplifiedJMSReceiver") private IJMSReceiver simplifiedJMSReceiver;
	@EJB(beanName="simplifiedJMSCDIReceiver") private IJMSReceiver simplifiedJMSCDIReceiver;
	@EJB(beanName="simplifiedJMSCDIReceiverWithProperties") private IJMSReceiver simplifiedJMSCDIReceiverWithProperties;
	
	// Inject a JMSContext to use - this will use the platform default connection factory
    @Inject JMSContext context;
    
    // Inject a Queue object to use
    @Resource(lookup = "java:global/jms/demoQueue")
    Queue demoQueue;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	
    /** 
     * Processes requests for both HTTP {@code GET} and {@code POST} methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String option = "";
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Java EE 7 - JMSDemoServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            
            option = request.getParameter("option");
            out.println("<p>Servlet JMSDemoServlet at " + request.getContextPath () + " with option="+option+"</h1>");
            handle(option,out);
        } catch (Exception e){
            e.printStackTrace(out);
        } finally {               
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }
	
    @Override
    public String getServletInfo() {
        return "JMS 2.0 Demo Servlet.";
    }

    private void handle(String option,PrintWriter out) throws Exception {
        String result = "";
        switch(option) {
            case "ClassicJMSSender":
                out.println("<h1>Using the JMS 1.1-style API<br> to send a message (ClassicJMSSender)</h1>");
                out.println("<p>Number of messages on queue before: "+ getQueueDepth()+"<br/>");
                classicJMSSender.sendMessage("I sent from Classic JMS Sender");
                out.println("Message successfully sent using ClassicJMSSender<br/>");
                out.println("Number of messages on queue after: "+ getQueueDepth()+"<br/>");
                break;
            case "SimplifiedJMSSender":
                out.println("<h1>Using the JMS 2.0 simplified API<br> to send a message (SimplifiedJMSSender)</h1>");
                out.println("Number of messages on queue before: "+ getQueueDepth()+"<br/>");
                simplifiedJMSSender.sendMessage("I sent from Simplified JMS Sender.");
                out.println("Message successfully sent<br/>");
                out.println("Number of messages on queue after: "+ getQueueDepth()+"<br/>");
                break;
            case "SimplifiedJMSCDISender":
                out.println("<h1>Using the JMS 2.0 simplified API and injection<br> to send a message (SimplifiedJMSCDISender)</h1>");
                out.println("<p>Number of messages on queue before: "+ getQueueDepth()+"<br/>");
                simplifiedJMSCDISender.sendMessage("I sent from Simplified JMS CDI Sender.");
                out.println("Message successfully sent<br/>");    
                out.println("Number of messages on queue after: "+ getQueueDepth()+"<br/>");
                break;   
            case "SimplifiedJMSCDISenderWithProperties":
                out.println("<h1>Using the JMS 2.0 simplified API<br> to send a message,<br>setting delivery options and message properties (SimplifiedJMSCDISenderWithProperties)</h1>");
                out.println("<p>Number of messages on queue before: "+ getQueueDepth()+"<br/>");
                simplifiedJMSCDISenderWithProperties.sendMessage("I sent from Simplified JMS CDI Sender With Properties");
                out.println("Message successfully sent<br/>");
                out.println("Number of messages on queue after: "+ getQueueDepth()+"<br/>");
                break;
            case "SimplifiedJMSAsyncSender":
                out.println("<h1>Using the JMS 2.0 simplified API with injection<br> to send a message,<br>setting delivery options and message properties (SimplifiedJMSAsyncSender)</h1>");
                out.println("<p>Number of messages on queue before: "+ getQueueDepth()+"<br/>");
                simplifiedJMSAsyncSender.sendMessage("I sent from Simplified JMS Async Sender");
                out.println("Message sent<br/>");     
                out.println("Number of messages on queue after: "+ getQueueDepth()+"<br/>");
                break;                 
            case "SimplifiedJMSReceiver":
                out.println("<h1>Using the JMS 2.0 simplified API<br> to receive a message (SimplifiedJMSReceiver)</h1>");
                out.println("<p>Number of messages on queue before: "+ getQueueDepth()+"<br/>");
                result = simplifiedJMSReceiver.receiveMessage();
                out.println("Message received: "+result+"<br/>"); 
                out.println("Number of messages on queue after: "+ getQueueDepth()+"<br/>");
                break;
            case "SimplifiedJMSCDIReceiver":
                out.println("<h1>Using the JMS 2.0 simplified API and injection<br> to receive a message (SimplifiedJMSCDIReceiver)</h1>");
                out.println("<p>Number of messages on queue before: "+ getQueueDepth()+"<br/>");
                result = simplifiedJMSCDIReceiver.receiveMessage();
                out.println("Message received: "+result+"<br/>"); 
                out.println("Number of messages on queue after: "+ getQueueDepth()+"<br/>");
                break;
           case "SimplifiedJMSCDIReceiverWithProperties":
                out.println("<h1>Using the JMS 2.0 simplified API and injection<br> to receive a message,<br>displaying message properties (SimplifiedJMSCDIReceiverWithProperties)</h1>");
                out.println("<p>Number of messages on queue before: "+ getQueueDepth()+"<br/>");
                result = simplifiedJMSCDIReceiverWithProperties.receiveMessage();
                out.println("Message received: "+result+"<br/>"); 
                out.println("Number of messages on queue after: "+ getQueueDepth()+"<br/>");
                break;
            default:
                throw new Exception("Unexpected option "+option);
        }
        out.println("<br/><br/><img src='arrow.gif'>&nbsp;<a href='/JavaEE7JMSDemo/"+option+".html'>Now go back to the example to continue</a>");    
        out.println("<br/><br/><a href='/JavaEE7JMSDemo/'>Java EE 7 - JMS 2.0 examples home</a>");    
    }
    
    String getQueueDepth() throws JMSException{
        int numMessages=0;
        for (Enumeration queueEnumeration = context.createBrowser(demoQueue).getEnumeration(); queueEnumeration.hasMoreElements();) {
            queueEnumeration.nextElement();
            numMessages++;
        } 
        return "<b>"+numMessages+"</b>";
    }
}