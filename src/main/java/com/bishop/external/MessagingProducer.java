package com.bishop.external;

/**
 * This interface encapsulates the spring managed asynchronous messaging
 * producer to push documents into the queue.
 *
 */

public interface MessagingProducer {

	void send(String ticket);

}
