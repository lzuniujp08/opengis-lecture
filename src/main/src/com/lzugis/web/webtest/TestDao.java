package com.lzugis.web.webtest;

import com.lzugis.helper.CommonDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestDao  extends CommonDao {
    public List getCapitals(){
        String sql = "SELECT id, name, lon, lat from capital";
        return this.jdbcTemplate.queryForList(sql);
    }
}