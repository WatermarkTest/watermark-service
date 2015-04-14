package com.bishop.app;

public class UnavailableException extends Exception {

    private static final long serialVersionUID = 6369887249139027632L;

    public UnavailableException(String ticket) {
        super("Document is unavailable for ticket: " + ticket
                + ". Please poll again later.");
    }

}
