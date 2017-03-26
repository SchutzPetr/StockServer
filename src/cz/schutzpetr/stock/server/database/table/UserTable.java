package cz.schutzpetr.stock.server.database.table;


import cz.schutzpetr.stock.core.auth.AuthData;
import cz.schutzpetr.stock.core.auth.AuthResult;
import cz.schutzpetr.stock.core.user.User;
import cz.schutzpetr.stock.server.database.mapper.UserMapper;
import cz.schutzpetr.stock.server.utils.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import javax.sql.DataSource;
import java.net.PasswordAuthentication;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by Petr Schutz on 17.12.2016
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class UserTable {

    /**
     * Instance of {@code DataSource}
     */
    private final DataSource dataSource;
    /**
     * Instance of {@code JdbcTemplate}
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * @param jdbcTemplate instance of {@code JdbcTemplate}
     */
    public UserTable(JdbcTemplate jdbcTemplate, DataSource dataSource) {

        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    /**
     * This method check auth data and return result in {@code AuthResult} object
     *
     * @param authData instance of {@code AuthData}
     * @return instance of {@code AuthResult}
     */
    public AuthResult check(AuthData authData) {
        return check(new PasswordAuthentication(authData.getUserName(), authData.getPassword()));
    }

    /**
     * This method check auth data and return result in {@code AuthResult} object
     *
     * @param passwordAuthentication instance of {@code PasswordAuthentication}
     * @return instance of {@code AuthResult}
     */
    public AuthResult check(PasswordAuthentication passwordAuthentication) {
        /*String SQL = String.format("SELECT * FROM `users` WHERE `users`.user_Name = \"%s\" AND `users`.user_Password = \"%s\"",
                passwordAuthentication.getUserName(), new String(passwordAuthentication.getPassword()));*/
        try {
            PreparedStatementCreator psc = connection -> {
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM `users` WHERE `users`.user_Name = ? AND `users`.user_Password = ?");
                preparedStatement1.setString(1, passwordAuthentication.getUserName());
                preparedStatement1.setString(2, new String(passwordAuthentication.getPassword()));
                return preparedStatement1;
            };

            List<User> users = jdbcTemplate.query(psc, new UserMapper());
            if (users.size() == 0) {
                return new AuthResult(null, false, "Neexistující uživatel nebo chybné heslo!");
            } else {
                return new AuthResult(users.get(0), true, "Přihlášení proběhlo úspěšně!");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            Logger.log("Chyba spojení s databází");
            return new AuthResult(null, false, "Chyba spojení s databází");
        }
    }
}
