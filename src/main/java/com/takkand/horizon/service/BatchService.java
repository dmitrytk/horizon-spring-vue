package com.takkand.horizon.service;

import com.takkand.horizon.domain.view.InclinometryView;
import com.takkand.horizon.domain.view.MerView;
import com.takkand.horizon.domain.view.RateView;
import com.takkand.horizon.domain.view.ZoneView;
import com.takkand.horizon.sql.Queries;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class BatchService {
    private final JdbcTemplate template;

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

    public int[] merImport(final List<MerView> data, Long fieldId) {
        int[] updateCounts = template.batchUpdate(Queries.MER_LOAD_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, data.get(i).getWellName());
                ps.setLong(2, fieldId);
                ps.setDate(3, data.get(i).getDate());
                ps.setObject(4, data.get(i).getStatus());
                ps.setObject(5, data.get(i).getRate());
                ps.setObject(6, data.get(i).getProduction());
                ps.setObject(7, data.get(i).getWorkDays());
            }

            @Override
            public int getBatchSize() {
                return data.size();
            }
        });
        return updateCounts;
    }

    public int[] rateImport(final List<RateView> data, Long fieldId) {
        int[] updateCounts = template.batchUpdate(Queries.RATE_LOAD_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, data.get(i).getWellName());
                ps.setLong(2, fieldId);
                ps.setDate(3, data.get(i).getDate());
                ps.setObject(4, data.get(i).getRate());
                ps.setObject(5, data.get(i).getDynamicLevel());
                ps.setObject(6, data.get(i).getStaticLevel());
                ps.setObject(7, data.get(i).getPressure());
            }

            @Override
            public int getBatchSize() {
                return data.size();
            }
        });
        return updateCounts;
    }

    public int[] zoneImport(final List<ZoneView> data, Long fieldId) {
        int[] updateCounts = template.batchUpdate(Queries.ZONE_LOAD_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, data.get(i).getWellName());
                ps.setLong(2, fieldId);
                ps.setString(3, data.get(i).getName());
                ps.setObject(4, data.get(i).getTopMd());
                ps.setObject(5, data.get(i).getBotMd());
                ps.setObject(6, data.get(i).getTopTvd());
                ps.setObject(7, data.get(i).getBotTvd());
                ps.setObject(8, data.get(i).getH());
            }

            @Override
            public int getBatchSize() {
                return data.size();
            }
        });
        return updateCounts;
    }

}
