package com.rdi.geegstar.exceptions;

public class GeegStarException extends Throwable {

    public GeegStarException(String message) {
        super(message);
    }

    public GeegStarException(Throwable throwable) {
        super(throwable);
    }
}
