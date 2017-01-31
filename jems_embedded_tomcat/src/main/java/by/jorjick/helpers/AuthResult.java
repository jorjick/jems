package by.jorjick.helpers;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Created by gora on 1/9/17.
 */
@JsonPropertyOrder({"success","redirectUrl", "message"})
public class AuthResult {
    private boolean success;
    private String redirectUrl;
    private String message;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
