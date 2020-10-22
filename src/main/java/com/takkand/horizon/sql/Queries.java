package com.takkand.horizon.sql;

public class Queries {
    public static String merLoadQuery = "INSERT INTO mer\n" +
            "(well_id, date, status, rate, production, work_days) VALUES\n" +
            "((SELECT id FROM wells w WHERE w.name=? and w.field_id=?),?,?,?,?,?)\n" +
            "ON CONFLICT (well_id, DATE) \n" +
            "DO UPDATE SET status = EXCLUDED.status,\n" +
            "\trate = EXCLUDED.rate,\n" +
            "\tproduction = EXCLUDED.production,\n" +
            "\twork_days = EXCLUDED.work_days;";
}
