package org.nico.yasso.exceptions;

public class MissingException extends YassoException{

    private static final long serialVersionUID = 6072193141111668155L;

    public MissingException() {
        super();
    }

    public MissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingException(String message) {
        super(message);
    }

    public MissingException(Throwable cause) {
        super(cause);
    }

}
