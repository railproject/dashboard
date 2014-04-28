package org.springframework.samples.portfolio.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * Created by star on 4/25/14.
 */
public class JdbcServiceImpl implements JdbcService {

    private String sql = "select sum(tk) as tk, sum(lk) as lk, sum(hc) as hc from (\n" +
            "select count(*) as tk, 0 as lk, 0 as hc from t_line t where t.cur_lj is not null and ((t.sub_type > -1 and t.sub_type<33) or (t.sub_type > 38 and t.sub_type<44)) and to_char(time_begin,'yyyymmdd')=to_char\n" +
            "(sysdate,'yyyymmdd') and t.time_begin<sysdate\n" +
            "union\n" +
            "select 0 as tk, count(*) as kc, 0 as hc from t_line t where t.cur_lj is not null and (t.sub_type >=33 and t.sub_type<=38) and to_char(time_begin,'yyyymmdd')=to_char\n" +
            "(sysdate,'yyyymmdd') and t.time_begin<sysdate\n" +
            "--货车\n" +
            "union\n" +
            "select 0 as tk, 0 as lk, count(*) as hc from t_line t where t.cur_lj is not null and (t.sub_type > 43 and t.sub_type<96) and to_char(time_begin,'yyyymmdd')=to_char\n" +
            "(sysdate,'yyyymmdd') and t.time_begin<sysdate\n" +
            ")";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> getCounts() {
        Map<String, Object> result = this.jdbcTemplate.queryForMap(this.sql/*"select id as kc, num as hc from t_test"*/);
        return result;
    }
}
