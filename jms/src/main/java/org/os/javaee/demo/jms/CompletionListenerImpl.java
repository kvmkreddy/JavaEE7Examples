package org.os.javaee.demo.jms;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.CompletionListener;
import javax.jms.Message;

/**
 * <p>Title: CompletionListenerImpl</p>
 * <p><b>Description:</b> CompletionListenerImpl</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: Open Source Development.</p>
 * @author Murali Reddy
 * @version 1.0
 */
public class CompletionListenerImpl implements CompletionListener {

	public void onCompletion(Message arg0) {
		 Logger.getLogger(getClass().getName()).log(Level.INFO, "Message sent successfully.", arg0);

	}

	public void onException(Message arg0, Exception arg1) {
		 Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Exception thrown while message sent..", arg1);

	}

}
