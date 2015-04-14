package com.bishop.external;

/**
 * This interface encapsulates the spring managed asynchronous messaging
 * consumer.
 *
 */

public interface MessagingConsumer {
    void receiveTicket(String ticket);
}
