package cz.schutzpetr.stock.server.database.table;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Petr Schutz on 19.05.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public abstract class DBTable {
    /**
     * Instance of {@code JdbcTemplate}
     */
    protected final JdbcTemplate jdbcTemplate;

    /**
     * @param jdbcTemplate jdbcTemplate
     */
    protected DBTable(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return Instance of {@code JdbcTemplate}
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
