package com.bishop.external;

import com.bishop.model.Document;

public interface DocumentRepository {

	String saveDocumentAndCreateTicket(Document document);

	void saveDocument(String ticket, Document document);

    Document getDocument(String ticket);

}
