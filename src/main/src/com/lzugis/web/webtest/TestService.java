package com.lzugis.web.webtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @Autowired
    private TestDao testDao;

    public List getCapitals(){
        return testDao.getCapitals();
    }

    public List getChinaUniversity(String name, String level, String province, String isprivate){
        return testDao.getChinaUniversity(name, level, province, isprivate);
    }
}
