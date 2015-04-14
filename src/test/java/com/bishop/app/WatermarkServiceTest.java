package com.bishop.app;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bishop.external.DocumentRepository;
import com.bishop.external.MessagingProducer;
import com.bishop.model.Book;
import com.bishop.model.Document;
import com.bishop.model.Topic;
import com.google.gson.Gson;

@RunWith(MockitoJUnitRunner.class)
public class WatermarkServiceTest {

    private static final String TICKET = "ticket-123";

    private static final String EXPECTED_WATERMARK = "{\"topic\":\"Science\","
            + "\"content\":\"book\",\"title\":\"The Dark Code\",\"author\":\"Bruce Wayne\"}";

    @Mock
    private DocumentRepository repository;

    @Mock
    private MessagingProducer producer;

    WatermarkService service = new WatermarkService();

    @Test
    public void testPushDocument() {
        Document document = new Book("The Dark Code", "Bruce Wayne",
                Topic.Science);
        when(repository.saveDocumentAndCreateTicket(document)).thenReturn(
                TICKET);
        service.setDocumentRepository(repository);
        service.setProducer(producer);

        String returnedTicket = service.pushDocument(document);
        assertNotNull("Ticket is null.", returnedTicket);
        verify(producer).send(TICKET);
    }

    @Test
    public void testReceiveTicket() {
        Document document = new Book("The Dark Code", "Bruce Wayne",
                Topic.Science);
        when(repository.getDocument(TICKET)).thenReturn(document);
        service.setDocumentRepository(repository);
        service.receiveTicket(TICKET);
        verify(repository).saveDocument(TICKET, document);
    }
    
    @Test
    public void testBook() {
        Document document = new Book("The Dark Code", "Bruce Wayne",
                Topic.Science);
        String json = service.generateWatermark(document);
        assertNotNull("JSon is null", json);
        assertEquals(EXPECTED_WATERMARK, json);
    }


    @Test
    public void testPollForDocument() throws UnavailableException {
        Document document = new Book("The Dark Code", "Bruce Wayne",
                Topic.Science);
        document.setWatermark(EXPECTED_WATERMARK);
        when(repository.getDocument(TICKET)).thenReturn(document);
        service.setDocumentRepository(repository);
        Document returnedDocument = service.getDocument(TICKET);
        assertNotNull("Document is null.", returnedDocument);
    }

    @Test(expected = UnavailableException.class)
    public void testPollForUnavailableDocument() throws UnavailableException {
        when(repository.getDocument(TICKET)).thenReturn(null);
        service.setDocumentRepository(repository);
        service.getDocument(TICKET);
    }
}
