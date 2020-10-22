package com.takkand.horizon.sql;

public class Queries {
    public static String MER_LOAD_QUERY = "INSERT INTO mer\n" +
            "(well_id, date, status, rate, production, work_days) VALUES\n" +
            "((SELECT id FROM wells w WHERE w.name=? and w.field_id=?),?,?,?,?,?)\n" +
            "ON CONFLICT (well_id, date) \n" +
            "DO UPDATE SET status = EXCLUDED.status,\n" +
            "\trate = EXCLUDED.rate,\n" +
            "\tproduction = EXCLUDED.production,\n" +
            "\twork_days = EXCLUDED.work_days;";

    public static String RATE_LOAD_QUERY = "INSERT INTO rates\n" +
            "(well_id, date, rate, dynamic, static, pressure) VALUES\n" +
            "((SELECT id FROM wells w WHERE w.name=? and w.field_id=?),?,?,?,?,?);\n";

    public static String INCLINOMETRY_LOAD_QUERY = "INSERT INTO inclinometry\n" +
            "(well_id, md, inc, azi) VALUES\n" +
            "((SELECT id FROM wells w WHERE w.name=? and w.field_id=?),?,?,?);\n";

    public static String ZONE_LOAD_QUERY = "INSERT INTO zones\n" +
            "(well_id, name, top_md, bot_md, top_tvd, bot_tvd, h) VALUES\n" +
            "((SELECT id FROM wells w WHERE w.name=? and w.field_id=?),?,?,?,?,?,?)\n" +
            "ON CONFLICT (well_id, name) \n" +
            "DO UPDATE SET top_md = EXCLUDED.top_md,\n" +
            "\tbot_md = EXCLUDED.bot_md,\n" +
            "\ttop_tvd = EXCLUDED.top_tvd,\n" +
            "\tbot_tvd = EXCLUDED.bot_tvd,\n" +
            "\th = EXCLUDED.h;";
}
