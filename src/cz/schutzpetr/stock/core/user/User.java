package cz.schutzpetr.stock.core.user;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Petr Schutz on 17.12.2016
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class User implements Serializable {
    private final List<String> permissions;
    private final String userName;
    private final String serverIP;
    private final String fullName;

    public User(String userName, String fullName, String serverIP, String permissions){
        this.permissions = null; //todo: string to list
        this.userName = userName;
        this.serverIP = serverIP;
        this.fullName = fullName;
    }

    public String getServerAdress(){
        return this.serverIP.split(":")[0];
    }

    public int getServerPort(){
        return Integer.parseInt(this.serverIP.split(":")[1]);
    }


    public String getUserName(){
        return this.userName;
    }

    public String getFullName() {
        return fullName;
    }
}
