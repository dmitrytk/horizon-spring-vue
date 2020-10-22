package com.takkand.horizon.load.setter;

import com.takkand.horizon.domain.view.MerView;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MerBatchSetter implements BatchPreparedStatementSetter {
    private Long fieldId;
    private List<MerView> mer;

    public MerBatchSetter(Long fieldId, List<MerView> mer) {
        this.fieldId = fieldId;
        this.mer = mer;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        ps.setString(1, mer.get(i).getWellName());
        ps.setLong(2, fieldId);
        ps.setDate(3, mer.get(i).getDate());
        ps.setObject(4, mer.get(i).getStatus());
        ps.setObject(5, mer.get(i).getRate());
        ps.setObject(6, mer.get(i).getProduction());
        ps.setObject(7, mer.get(i).getWorkDays());
    }

    @Override
    public int getBatchSize() {
        return mer.size();
    }
}
