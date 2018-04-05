package com.lzugis.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class CommonDao {
    // 创建JDBC模板
    @Autowired
    protected JdbcTemplate jdbcTemplate;
}
