package org.example.domain.exception;

public class FilmNotFoundException extends Throwable {
    public FilmNotFoundException() {
        super("Oops... Films with such title not found");
    }
}
