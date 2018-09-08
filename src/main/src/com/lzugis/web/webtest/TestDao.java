package com.lzugis.web.webtest;

import com.lzugis.helper.CommonDao;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TestDao  extends CommonDao {
    public List getCapitals(){
        String sql = "SELECT id, name, lon, lat from capital";
        return this.jdbcTemplate.queryForList(sql);
    }


    public List getChinaUniversity(String name, String level, String province, String isprivate){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT name, province, school_lev as level, memo as isprivate, ");
        sql.append("st_x(geom) as lon, st_y(geom) as lat from layer_university where 1=1");
        List<String> para = new ArrayList();
        if(!StringUtil.isEmpty(name)) {
            sql.append(" and name like ?");
            para.add("%"+name+"%");
        }
        if(!StringUtil.isEmpty(level)) {
            sql.append(" and school_lev = ?");
            para.add(level);
        }
        if(!StringUtil.isEmpty(province)) {
            sql.append(" and province = ?");
            para.add(province);
        }
        if(!StringUtil.isEmpty(isprivate)) {
            sql.append(" and memo = ?");
            para.add(isprivate);
        }
        return this.jdbcTemplate.queryForList(sql.toString(), para.toArray());
    }
}