package online.attrest.core.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import online.attrest.core.SpringExt;
import online.attrest.core._enum.DriverMode;
import online.attrest.core.codetype.ApiModel;
import online.attrest.core.codetype.AttDescription;

@RestController
@RequestMapping("attrest")
public class AttrestController {
    
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private BasicSetting setting;

    private ArrayList<LocalApi> result;
    
    @GetMapping(path = "/getschema")
    @AttDescription("获取Attrest服务端API基础架构,适用于后台模式和双工模式")
    public void getSchema() {
        if(setting.getDriverMode() == DriverMode.BackendFirst || setting.getDriverMode() == DriverMode.Duplex){
            response.setContentType("text/html;charset=utf-8");
            try 
            {
                PrintWriter pw =  response.getWriter();
                pw.write(AttRestCore.DefaultCss());
                pw.write(AttRestCore.Schema(setting.getHost()));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

    @GetMapping(path="getmode")
    @AttDescription("获取Attrest工作模式,frontfirst:前端优先;backendfirst:后台优先;duplex:双工模式")
    public String getMode(){
        if(setting.getDriverMode()== DriverMode.FrontFirst)  return "frontfirst";
        if(setting.getDriverMode()== DriverMode.Duplex)  return "duplex";
        return "backendfirst";
    }

    @PostMapping(path = "diffcontract")
    @AttDescription("比较前后台接口的差异,适用于前台模式和双工模式")
    @ResponseBody
    public ArrayList<LocalApi> diffContract(@RequestBody LocalApiList localapi)
    {
        result = new ArrayList<LocalApi>();
        if(setting.getDriverMode() == DriverMode.FrontFirst || setting.getDriverMode() == DriverMode.Duplex)
        {
            for(LocalApi local : localapi.getLocalApis()){
                //使用排除法确定localapi是否存在
                local.setExist(false);
                boolean ischecked = false;//是否检测到名称
                for(ApiModel serv: SpringExt.apiEnumerable.ApiModels){
                    //比较api
                    if(serv.getControllerName().toLowerCase().equals(local.getRoute().toLowerCase()) && serv.getActionName().toLowerCase().equals(local.getAction().toLowerCase()) ){
                        boolean param_check_result = true;
                        ischecked = true;
                        String paramString = local.getParams();
                        if(paramString.trim().equals("")){
                            param_check_result = serv.getParamTypes().size()==0?true:false;
                        }else{
                            for(String pname : paramString.split(","))
                            {
                                if(!serv.hasParamName(pname)) {
                                    //只比较名称,只要有一个为false,则直接给出参数不匹配
                                    param_check_result = false;
                                    break;//直接跳出
                                }
                            }
                        }
                        if(!param_check_result){
                            local.setCompareresult(local.getRoute() + "-" +local.getAction() + "参数不匹配");
                        }else{
                            local.setCompareresult(local.getRoute() + "-" +local.getAction() + "已验证");
                            local.setExist(true);
                        }
                        result.add(local);
                    }
                }
                if(!ischecked){
                    local.setCompareresult(local.getRoute() + "-" +local.getAction() + "未检测到该接口");
                    result.add(local);
                }
            }        
            //将缺失的接口在服务端打印
            result.forEach(a->{
                if(!a.isExist()){
                    System.err.println(a.toWarring());
                }
            });
        }
        //返回给客户端验证结果,以配合代码生成
        return result;
    }

    @GetMapping(path = "/get")
    @AttDescription("获取自动生成的前端API调用脚本")
    public void Get() {
        AttRestCore.ResponseScript(response, "gbk",setting.getDriverMode());
    }
}