package com.takkand.horizon.load.setter;

import com.takkand.horizon.domain.view.RateView;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RateBatchSetter implements BatchPreparedStatementSetter {

    private Long fieldId;
    private List<RateView> data;

    public RateBatchSetter(Long fieldId, List<RateView> data) {
        this.fieldId = fieldId;
        this.data = data;
    }

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
}
