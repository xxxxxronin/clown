package com.diandi.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by lenli on 2016/5/12.
 */
public class Consumer {

    private static String brokerURL = "tcp://localhost:61616";
    private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;

    public Consumer() throws JMSException {
        factory = new ActiveMQConnectionFactory(brokerURL);
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws JMSException {
        Consumer consumer = new Consumer();
        args = new String[]{"start","b","c"};
        for (String stock : args) {
            Destination destination = consumer.getSession().createTopic("STOCKS." + stock);
            MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);
            messageConsumer.setMessageListener(new Listener());
        }
    }

    public Session getSession() {
        return session;
    }

}
