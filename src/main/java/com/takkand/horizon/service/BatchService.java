package com.takkand.horizon.service;

import com.takkand.horizon.domain.view.InclinometryView;
import com.takkand.horizon.sql.Queries;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class BatchService {
    private JdbcTemplate template;

    public BatchService(JdbcTemplate template) {
        this.template = template;
    }

    public int[] inclinometryImport(final List<InclinometryView> data, Long fieldId) {
        int[] updateCounts = template.batchUpdate(Queries.INCLINOMETRY_LOAD_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, data.get(i).getWellName());
                ps.setLong(2, fieldId);
                ps.setObject(3, data.get(i).getMd());
                ps.setObject(4, data.get(i).getInc());
                ps.setObject(5, data.get(i).getAzi());
            }

            @Override
            public int getBatchSize() {
                return data.size();
            }
        });
        return updateCounts;
    }

}
