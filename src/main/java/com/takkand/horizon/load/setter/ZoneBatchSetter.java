package com.takkand.horizon.load.setter;

import com.takkand.horizon.domain.view.ZoneView;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ZoneBatchSetter implements BatchPreparedStatementSetter {
    private Long fieldId;
    private List<ZoneView> data;

    public ZoneBatchSetter(Long fieldId, List<ZoneView> data) {
        this.fieldId = fieldId;
        this.data = data;
    }

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

}