package com.rabo.pims.exception;

import java.util.List;


public class ErrorResponse {

    private String responseMessage;
 
    private List<String> details;
    
    public ErrorResponse(String responseMessage, List<String> details) {
        super();
        this.setresponseMessage(responseMessage);
        this.details = details;
    }
 
    public String getresponseMessage() {
		return responseMessage;
	}

	public void setresponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
