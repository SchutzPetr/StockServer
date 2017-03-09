package cz.schutzpetr.stock.core.auth;

import cz.schutzpetr.stock.core.user.User;

import java.io.Serializable;

/**
 * Created by Petr Schutz on 04.01.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class AuthResult implements Serializable{

    private final User user;
    private final boolean result;
    private final String message;

    public AuthResult(User user, boolean result, String message) {
        this.user = user;
        this.result = result;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public boolean isResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
