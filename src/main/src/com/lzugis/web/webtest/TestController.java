package com.lzugis.web.webtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping(value="test/capitals")
    @ResponseBody
    public List databaseTest(){
        try {
            List dbData = testService.getCapitals();
            return dbData;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList();
        }
    }

    @RequestMapping(value="test/university")
    @ResponseBody
    public List getChinaUniversity(String name, String level, String province, String isprivate){
        try {
            List dbData = testService.getChinaUniversity(name, level, province, isprivate);
            return dbData;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList();
        }
    }
}
