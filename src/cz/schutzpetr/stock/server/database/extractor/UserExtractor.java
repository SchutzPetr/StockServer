package cz.schutzpetr.stock.server.database.extractor;

import cz.schutzpetr.stock.core.user.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Petr Schutz on 19.05.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class UserExtractor implements ResultSetExtractor<User> {

    /**
     * Implementations must implement this method to process the entire ResultSet.
     *
     * @param resultSet ResultSet to extract data from. Implementations should not close this: it will be closed by the calling JdbcTemplate.
     * @return an arbitrary result object, or null if none (the extractor will typically be stateful in the latter case).
     * @throws SQLException        if a SQLException is encountered getting column values or navigating (that is, there's no need to catch SQLException)
     * @throws DataAccessException in case of custom exceptions
     */
    @Override
    public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        return new User(resultSet.getString("user_Name"), resultSet.getString("user_FullName"),
                resultSet.getString("user_server"), resultSet.getString("user_permissions"));
    }
}
