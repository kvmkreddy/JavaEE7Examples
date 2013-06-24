<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JMS 2.0 in Java EE 7 demonstration</title>
    </head> 
    <body>
        <h1>JMS 2.0 means less code to send or receive a message</h1>
        This demonstration allows you to examine the new JMS 2.0 API for sending and receiving messages
        and see how it compares with the existing JMS 1.1 API. <br>
        Follow the links labelled <img src="arrow.gif">&nbsp; to go through the demonstration, or simply explore the links.
        <h2>Sending a message (Java EE)</h1>
        <p>Here are three very simple examples of Java EE applications which send a message.<br> 
            Follow the links to view and run code examples.
        <p><img src="arrow.gif">&nbsp;<a href="SimplifiedJMSSender.html">Using the JMS 2.0 simplified API to send a message (SimplifiedJMSSender)</a>
        <p><a href="SimplifiedJMSCDISender.html">Using the JMS 2.0 simplified API and injection to send a message (SimplifiedJMSCDISender)</a>
        <h2>Receiving a message synchronously (Java EE)</h1>
        <p>Here are three very simple examples of Java EE applications which synchronously receive a message. <br>
            Follow the links to view and run code examples.<br>
            Before running them, use the previous examples to put a few messages on the queue first.
        <p><a href="SimplifiedJMSReceiver.html">Using the JMS 2.0 simplified API to receive a message (SimplifiedJMSReceiver)</a>
        <p><a href="SimplifiedJMSCDIReceiver.html">Using the JMS 2.0 simplified API and injection to receive a message  (SimplifiedJMSCDIReceiver)</a>
        <br>
        <h2>Sending a message, setting delivery options and message properties(Java EE)</h1>
        <p>Here are two slightly more complex examples of Java EE applications which
            set message delivery options and message properties before sending the message.<br> 
            Follow the links to view and run code examples.
        <p><a href="SimplifiedJMSCDISenderWithProperties.html">Using the JMS 2.0 simplified API with injection to send a message, setting delivery options and message properties (JavaEESenderNewCDIWithProperties)</a>
        <h2>Receiving a message synchronously, displaying delivery options and message properties (Java EE)</h1>
        <p>Here is an example which demonstrates how to extract message delivery options and message proprtties from the received message.<br>
            Follow the links to view and run code it.<br>
            Before running it, use the previous examples to put a few messages on the queue first.            
        <p><a href="SimplifiedJMSCDIReceiverWithProperties.html">Using the JMS 2.0 simplified API and injection to receive a message, displaying delivery options and message properties (JavaEESyncReceiverNewCDIWithProperties)</a>
        <br>
        </body>
</html>
