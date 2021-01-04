package br.com.danilopaixao.ws.profile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

import java.util.function.Supplier;

@Getter
public abstract class ProfileException extends RuntimeException {
	
	private static final long serialVersionUID = -6634056391059593250L;

	private static final String MSG = "Profile Error status=%d body=%s info=%s";

	public static final Supplier<? extends RuntimeException> invalidArgumentSupplier = () ->
		new RuntimeException("Invalid argument for profile");

	private final HttpStatus statusCode;
	private final Object error;

	public ProfileException(final String info, final ResponseEntity<?> response) {
		super(String.format(MSG, response.getStatusCodeValue(), response.getBody(), info));
		this.statusCode = response.getStatusCode();
		this.error = response.getBody();
	}
	
	public ProfileException(String message, HttpStatus statusCode, Object error) {
		this.statusCode = statusCode;
		this.error = error;
	}
}
