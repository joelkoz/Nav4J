package org.nav4j;

import java.util.Objects;

/**
 * An ApplicationException is any type of exception that is generated
 * inside the application that is unexpected and does not have an
 * obvious way to resolve itself. Using ApplicationExceptions allows
 * for cleaner code littered with try/catch or throws statements.
 * 
 * @author Joel Kozikowski
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String msg) {
        super(msg);
    }
    
    public ApplicationException(Throwable e) {
        super(e);
    }

    public ApplicationException(String msg, Throwable e) {
        super(msg, e);
    }
    
    @Override
    public String getMessage() {
        Throwable root = this.getRootCause();
        if (!this.equals(root)) {
            return super.getMessage() + ": " + root.getMessage();
        }
        else {
            return super.getMessage();
        }
    }
    
    
    public Throwable getRootCause() {
        return findRootCause(this);
    }
    
    
    public static Throwable findRootCause(Throwable throwable) {
        Objects.requireNonNull(throwable);
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }    

}
