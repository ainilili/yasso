package org.nico.yasso.exceptions;

public class YassoException extends Exception{

    private static final long serialVersionUID = 3679286795326985650L;

    public YassoException() {
        super();
    }

    public YassoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public YassoException(String message, Throwable cause) {
        super(message, cause);
    }

    public YassoException(String message) {
        super(message);
    }

    public YassoException(Throwable cause) {
        super(cause);
    }

    
}
