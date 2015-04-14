package com.bishop.app;

import com.bishop.external.DocumentRepository;
import com.bishop.external.MessagingConsumer;
import com.bishop.external.MessagingProducer;
import com.bishop.model.Document;
import com.google.gson.Gson;

/**
 * Asynchroneous service creates watermarks for different types of documents.
 *
 */
public class WatermarkService implements MessagingConsumer {

    private DocumentRepository repository;

    private MessagingProducer producer;

    /**
     * Saves the document in the repository and sends the ticket to the messaging
     * queue and returns the ticket.
     * 
     * @param document
     * @return
     */
    public String pushDocument(Document document) {
        String ticket = repository.saveDocumentAndCreateTicket(document);
        producer.send(ticket);
        return ticket;
    }

    /**
     * As a messaging consumer the WatermarkService receives the ticket
     * asynchronously from the spring managed messaging system. Generates a
     * watermark for the document and stores the document in the @see
     * DocumentRepository for later retrieval.
     * 
     * @param ticket
     */
    public void receiveTicket(String ticket) {
        Document document = repository.getDocument(ticket);
        String json = generateWatermark(document);
        document.setWatermark(json);
        repository.saveDocument(ticket, document);

    }

    /**
     * Generate the watermark from the properties of the document
     * @param document
     * @return
     */
    protected String generateWatermark(Document document) {
        Gson gson = new Gson();
        String json = gson.toJson(document);
        return json;
    }
    
    

    /**
     * Rerieve already processed document from the repository or throw
     * UnavailableException, if not yet finished.
     * 
     * @param ticket
     * @return
     * @throws UnavailableException
     */
    public Document getDocument(String ticket) throws UnavailableException {
        Document document = repository.getDocument(ticket);
        if (null == document || null == document.getWatermark()) {
            throw new UnavailableException(ticket);
        }
        return document;
    }

    public DocumentRepository getDocumentRepository() {
        return repository;
    }

    public void setDocumentRepository(DocumentRepository repository) {
        this.repository = repository;
    }

    public MessagingProducer getProducer() {
        return producer;
    }

    public void setProducer(MessagingProducer producer) {
        this.producer = producer;
    }

}
