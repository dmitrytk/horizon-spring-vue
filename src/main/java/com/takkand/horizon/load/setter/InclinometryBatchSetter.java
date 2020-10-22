package com.takkand.horizon.load.setter;

import com.takkand.horizon.domain.view.InclinometryView;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class InclinometryBatchSetter implements BatchPreparedStatementSetter {

    private Long fieldId;
    private List<InclinometryView> data;

    public InclinometryBatchSetter(Long fieldId, List<InclinometryView> data) {
        this.fieldId = fieldId;
        this.data = data;
    }

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
}
