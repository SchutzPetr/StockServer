package cz.schutzpetr.stock.core.auth;

import java.io.Serializable;

/**
 * Created by Petr Schutz on 04.01.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class AuthData implements Serializable {
    private final String userName;
    private final char[] pass;

    public AuthData(String userName, char[] pass) {
        this.userName = userName;
        this.pass = pass;
    }

    public char[] getPassword() {
        return pass;
    }

    public String getUserName() {
        return userName;
    }
}
