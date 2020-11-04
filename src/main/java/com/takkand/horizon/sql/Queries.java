package com.takkand.horizon.sql;

public class Queries {
    public static String FIELD_INCLINOMETRY_VIEW = "SELECT * FROM inclinometry_view v\n" +
            "where v.well in (select w.name from wells w where w.field_id = :fieldId)";

    public static String FIELD_MER_VIEW = "SELECT * FROM mer_view v\n" +
            "where v.well in (select w.name from wells w where w.field_id = :fieldId)";

    public static String FIELD_RATES_VIEW = "SELECT * FROM rates_view v\n" +
            "where v.well in (select w.name from wells w where w.field_id = :fieldId)";

    public static String FIELD_ZONES_VIEW = "SELECT * FROM zones_view v\n" +
            "where v.well in (select w.name from wells w where w.field_id = :fieldId)";

    public static String CREATE_WELL_FROM_NAME = "INSERT INTO wells (name, field_id)\n" +
            "VALUES (?, ?)\n" +
            "ON CONFLICT DO NOTHING;";


    public static String MER_LOAD = "INSERT INTO mer\n" +
            "(well_id, date, status, rate, production, work_days) VALUES\n" +
            "((SELECT id FROM wells w WHERE w.name=? and w.field_id=?),?,?,?,?,?)\n" +
            "ON CONFLICT (well_id, date) \n" +
            "DO UPDATE SET status = EXCLUDED.status,\n" +
            "\trate = EXCLUDED.rate,\n" +
            "\tproduction = EXCLUDED.production,\n" +
            "\twork_days = EXCLUDED.work_days;";

    public static String RATE_LOAD = "INSERT INTO rates\n" +
            "(well_id, date, rate, dynamic, static, pressure) VALUES\n" +
            "((SELECT id FROM wells w WHERE w.name=? and w.field_id=?),?,?,?,?,?);\n";

    public static String INCLINOMETRY_LOAD = "INSERT INTO inclinometry\n" +
            "(well_id, md, inc, azi) VALUES\n" +
            "((SELECT id FROM wells w WHERE w.name=? and w.field_id=?),?,?,?);\n";


    public static String ZONE_LOAD = "INSERT INTO zones\n" +
            "(well_id, name, top_md, bot_md, top_tvd, bot_tvd, h) VALUES\n" +
            "((SELECT id FROM wells w WHERE w.name=? and w.field_id=?),?,?,?,?,?,?)\n" +
            "ON CONFLICT (well_id, name) \n" +
            "DO UPDATE SET top_md = EXCLUDED.top_md,\n" +
            "\tbot_md = EXCLUDED.bot_md,\n" +
            "\ttop_tvd = EXCLUDED.top_tvd,\n" +
            "\tbot_tvd = EXCLUDED.bot_tvd,\n" +
            "\th = EXCLUDED.h;";
}
