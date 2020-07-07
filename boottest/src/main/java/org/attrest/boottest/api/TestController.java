package org.attrest.boottest.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.attrest.core.AttRestCore;
import org.attrest.core.codetype.AttDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/Test")
@AttDescription("测试接口")
public class TestController {
    @Autowired
    public HttpServletResponse response;

    @GetMapping(path = "/Get1")
    @RequestMapping(method = RequestMethod.GET, path = "/get")
    @AttDescription("这是一个测试的标注")
    public void Get() {
        AttRestCore.ResponseScript(response, "gbk");
    }

    @GetMapping(value = "/Get2")
    public String Get2(@RequestBody TestEnum enum1,TestEnum enum2,@RequestBody TestEnum enum3) {
        return "Hello ";
    }

    @PostMapping(value = "/Post")
    @ResponseBody
    public String Post(@RequestParam String x,@RequestParam String y){
        return "post hello" + x + ","+ y;
    }

    
    @GetMapping(value = "/Get3")
    public String Get3(@RequestBody TestEntity entity) {
        return "Hello " + entity.getName();
    }


    
    @GetMapping(value = "/Get3/{name}/hello")
    public String Get4(@PathVariable int name) {
        return "Hello :" + name;
    }

    @GetMapping("GetSchema")
    public void GetSchema() {
        response.setContentType("text/html;charset=utf-8");
        try 
        {
            PrintWriter pw =  response.getWriter();
            pw.write(AttRestCore.DefaultCss());
            pw.write(AttRestCore.Schema("localhost:8081"));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}