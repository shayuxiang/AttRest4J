package online.attrest.core.codetype;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import online.attrest.core.BasicSetting;
import online.attrest.core.SpringExt;
import online.attrest.core._enum.AttClientFrame;
import online.attrest.core.interfaces.ClientCodeFactory;
import online.attrest.core.interfaces.IClientCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiEnumerable {

    private final String base64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAADc0lEQVRoQ2NkcJ/7n2EYAMZRjwyyWByNkUEWIQyjMTIaIzQKgdGkRaOAJdvYkRkj8Ro8DBPKbbGG2obdNxkSl90jO0Qp1UhSjKxKVmNwtVHGaufNe68ZLJrPUOoesvWT5JGb3bYMYiI8OC0r6DzMsPDGF7IdQ4lGoj1SYi7EUJ1hjteu3UfuMoTNvUWJe8jWS7RHtuZoM1gZy8Et+vnrD5jNzsYCF3v15guDeulhsh1DiUaiPfJwkiMDHy8H3K4L156D2QZakij2D1TyIsojk/xlGGIDdFEcvHjDZTAfXZyY5FX19xOD0L/fDO+YWBnamPnwRgSxaonyyP5SA5SQByUrifTdDC7ibAyrO5xRHPLp8w8G+bz9OB235cddBo9vu+HyhzntGRw5NbGqJ0UtQY+AHLukyR4lL4CSlWP3BbDlJ2pNGNSVRFEc0jrjJEPPyXdYHff240YG/r+QZAkDLEIZFKsl6JH5UUoMAa7qGMkqb+MTsBg2+WNnHzF4T7mK1XF/3s3AEMflEVLUEvQIeojDkhXMNaQmL1IcR4pavB7B1iRBTlYwz1xssWSQkxZACempS88x1Ox5CRbr+f2WIfPLTgb2/5/wZuyXLEoMyzmNiVYrzecGNw+vR7A1SUClFSxZwUzBpg7Zw+iZFp9vdnC5ohQG+NTW8UfBSz28HkFvkqAnK5gl2GIOWW3qv+8MHZ93YWRydEc+YNVi6OQ2JVqtCq8d4RjB5jh8mRhbOwxb7JGS7klRizNG0JskeBM3Dkls+YkUx5GiFqdH0Jsk5HgElLxi6g4y7Hn5C66dFMeRoharR1pcxBmyo43IcTuGHvQOFymOI0UtVo+gN0ko8RF6h4sUx5GiFqtHXsx0RWmS4Cqt0D2Iq88SWrEXnrxIcRwpajE8gq3JgS3T4oolbKUXcvLC5jhQ3fGFkY1hL6sIw2wmTrz5CZdaDI+Q2ghE9xC2yhG5w3X5yykGzV/nsIbDT0Y+Bm7BKLgcKWpRPEJquwmba3CNtMA6XKDmSsHn1TizHXJtTYpaFI9gC01iOkrorsIWq8jmzP71gsHrxyUG8T+I4SNQbOznNGfw4UAdpSFWLcHWLyUlFj31jnqEnqFNjF2jMUJMKNFTzWiM0DO0ibFrNEaICSV6qhmNEXqGNjF2jcYIMaFETzWjMULP0CbGLgDmcfWYVXNodQAAAABJRU5ErkJggg==";

    private static AttClientFrame attCodeType;

    @Autowired
    private BasicSetting setting;

    /** 从Spring获取 */
    @Autowired
    public void setAttCodeType(AttClientFrame attCodeType) {
        ApiEnumerable.attCodeType = attCodeType;
    }

    /** 所有方法 */
    public ArrayList<ApiModel> ApiModels = new ArrayList<ApiModel>();

    /** 所有需要重新编译的Entity */
    public ArrayList<EntityModel> EntityModels = new ArrayList<EntityModel>();

    /** 所有需要重新编译的枚举 */
    public ArrayList<EntityModel> EnumModels = new ArrayList<EntityModel>();

    /** 根据不同类型的客户端生成本地代码 */
    public String ToCode() {
        ArrayList<IClientCode> codeClient = ClientCodeFactory.getInstance().GetCodeBuilder(attCodeType);
        String retstr = "";
        for (IClientCode client : codeClient) {
            client.SetHost(setting.getHost());
            retstr += client.ToCode();
        }
        return "function AttClient(){" + retstr + "}";
    }

    /// <summary>
    /// 获取API的整体架构描述
    /// </summary>
    /// <param name="RouteName"></param>
    /// <param name="Host"></param>
    /// <returns></returns>
    public String ToSchema(String Host) {
        final String host = "http://" + Host + "";
        // 定义枚举
        StringBuilder func = new StringBuilder();
        String _func = "";
        for (EntityModel model : this.EnumModels) {
            String enumjs = "<span class='EnumName'>" + model.getClassName()
                    + "(枚举)</span><span class='EnumDetail'>Spring描述:[" + model.getDescription() + "]</span>";
            String func_body = "";
            for (FieldModel param : model.getParameters()) {
                func_body += "<ul class='EnumParam'><li>参数名称:" + param.getName() + "</li> <li>" + param.getDescription()
                        + "</li> <li>示例： var x = " + model.getClassName() + "." + param.getName() + ";</li> <li>编译值:"
                        + param.getDefaultValue() + "</li></ul> ";
            }
            enumjs = enumjs + func_body;
            // 所有的初始化
            _func = "<li>" + enumjs + "</li>";
        }
        func.append("<p class='title_p'>AttRest-Document:[" + host + "]<img class='logo' src='"+ base64 +"'></img></p><ul class='enum_ul'>" + _func + "</ul>");

        // 定义对象
        for (EntityModel model : this.EntityModels) {
            // 原型方式创建js对象
            String entityjs = "<span  class='ModelName'>" + model.getClassName()
                    + "(服务器对象)</span><span  class='ModelDetail'>Spring描述:[ " + model.getDescription() + "]</span>";
            String func_body = "";
            for (FieldModel param : model.getParameters()) {
                String defaultValue = "null";
                // 自定义类型
                if (param.getFieldType().equals(String.class)) {
                    defaultValue = "字符串";
                } else if (param.getFieldType().equals(Integer.class) || param.getFieldType().equals(Double.class)
                        || param.getFieldType().equals(Long.class) || param.getFieldType().equals(Float.class)) {
                    defaultValue = "数字型";
                } else if (param.getFieldType().isArray()) {
                    defaultValue = "数组";
                } else if (param.getFieldType().equals(Date.class)) {
                    defaultValue = "时间日期";
                } else if (param.getFieldType().getClassLoader() != null) {
                    // 自定义类型
                    defaultValue = "自定义类型:" + param.getFieldType().getName();
                }
                func_body += "<ul class='ModelParam'><li>参数名称: " + param.getName() + " </li> <li> "
                        + param.getDescription() + " </li> <li>示例： var x = new " + model.getClassName()
                        + "; console.log(x." + param.getName() + ");</li> <li>传入类型:" + defaultValue + "</li></ul> ";
            }
            entityjs = entityjs + func_body;
            func.append("<ul  class='entity_ul'>" + entityjs + "</ul>");
        }
        // 定义接口对象
        List<List<ApiModel>> groupList = new ArrayList<>();
        // 对所有api接口进行分组
        SpringExt.apiEnumerable.ApiModels.stream()
                .collect(Collectors.groupingBy(ApiModel::getControllerName, Collectors.toList()))
                .forEach((name, fooList) -> {
                    groupList.add(fooList);
                });
        // 循環api列表
        groupList.forEach((apiList) -> {
            if (apiList.size() <= 0) {
                return;
            } // 错误判断，一般不会出现
              // 原型方式创建js-api对象
            String apijs = "<span class='apiName'>" + apiList.get(0).getControllerName().replace("Controller", "")
                    + "(API对象)</span>";
            StringBuilder func_body = new StringBuilder();
            apiList.forEach((method) -> {
                String _params = "";
                RouteAnaly routeAnaly = new RouteAnaly(method.getArea(), method.getRouteFilter(),
                        method.getControllerName(), method.getActionName(), method.getActionRouteFilter());
                String url = "{Host}" + routeAnaly.getLinkShow();
                String link = host + routeAnaly.getLinkShow();
                // 参数整理
                for (ParamTypeEntity p : method.getParamTypes()) {
                    String typeStr = "";
                    if (p.getParamType().equals(String.class)) {
                        typeStr = "字符串";
                    } else if (p.getParamType().equals(Integer.class) || p.getParamType().equals(int.class)
                            || p.getParamType().equals(Double.class) || p.getParamType().equals(double.class)
                            || p.getParamType().equals(Long.class) || p.getParamType().equals(long.class)
                            || p.getParamType().equals(Float.class) || p.getParamType().equals(float.class)) {
                        typeStr = "数字类型";
                    } else if (p.getParamType().isArray()) {

                        typeStr = "数组";
                    } else if (p.getParamType().equals(Date.class)) {
                        typeStr = "时间日期";
                    } else if (p.getParamType().getClassLoader() != null) {
                        // 自定义类型
                        typeStr = "自定义类型:" + p.getParamType().getName();
                    } else {
                        typeStr = "未知类型";
                    }
                    _params += "<li class='apiParamUnit'>参数" + p.getName() + "</li><li class='apiParamUnit'>传入类型:"
                            + typeStr + "</li>";
                }
                func_body.append("<ul class='apiParam'><li class='apiUnit'>方法名称:" + method.getActionName()
                        + "</li><li class='apiUnit'><span>请求方式:" + method.getRequestMethod()
                        + "</span></li><li class='apiUnit'>请求地址:<a target='_blank' href='" + link + "'>" + url
                        + "</a><li class='apiDesc'>Spring描述:[" + method.getDescription() + "]<li/></li>" + _params
                        + "</ul>");
            });
            // 添加到代码返回
            apijs = apijs + func_body.toString();
            func.append("<ul class='func_ul'>" + apijs + "</ul>");
        });
        return "<body>" + func.toString() + "</body>";
    }

    /// <summary>
    /// 获取Schema页面基础CSS央视
    /// </summary>
    /// <returns></returns>
    public String GetDefaultCss() {
        String defaultcss = "*{margin:0;padding:0;}strong{font-weight:bold;color:#ff6600;font-size:14px;}body{font-family:\"Arial\",\"Microsoft YaHei\",\"宋体\",\"宋体\",sans-serif;background:#223321;}.logo{float: left;width: 35px;height: 35px;margin: 4 4 0 4;border-radius: 4px;}.title_p{display:block;width:1080px;margin:0 auto;font-size:25px;text-indent:10px;color:#222;background:orange;height:42px;line-height:42px;}ul,li{list-style:none;}.enum_ul{display:block;width:1080px;margin:0 auto;height:auto;}.enum_ul > li{float:left;display:block;width:100%;margin:5px auto;}.enum_ul,.entity_ul,.func_ul{padding-bottom:30px;}.enum_ul:after,.entity_ul:after,.func_ul:after{content:\"\";display:block;width:100%;height:0px;clear:both;}.entity_ul{display:block;width:1080px;margin:0 auto;height:auto;}.entity_ul > li{float:left;display:block;width:100%;margin:5px auto;}.func_ul{display:block;width:1080px;margin:0 auto;height:auto;}.func_ul > li{float:left;display:block;width:100%;margin:0 auto;}.EnumName{width:100%;display:block;font-weight:800;line-height:40px;color:#FEFEFE;}.ModelName{width:100 %;display:block;line-height:40px;font-weight:800;color:#0094ff;}.EnumDetail,.ModelDetail,.apiDesc{width:100%;display:block;font-weight:300;font-size:14px;color:#EEE;margin-bottom:15px;}.EnumParam,.ModelParam{width:100%;display:block;}.ModelParam,.EnumParam{padding:15px 0px;border-bottom:1px solid #ededed;}.ModelParam:after,.EnumParam:after,.apiParam:after{content:\"\";display:block;width:100%;height:0px;clear:both;}.ModelParam > li,.EnumParam > li{float:left;width:25%;font-size:11px;display:block;color:#FFFF16;font-size:12px;line-height:18px;}.ModelParam > li:nth-child(3),.EnumParam > li:nth-child(3){width:40%;}.ModelParam > li:nth-child(4),.EnumParam > li:nth-child(4){width:10%;}.apiName{width:100%;display:block;font-weight:800;line-height:40px;color:#14a100;}.apiParam{width:100%;display:block;border-top:1px solid #999;}.apiParamUnit{float:left;width:50%;font-size:11px;display:block;border-top:1px solid #ededed;border-bottom:1px solid #ededed;line-height:25px;color:#AAA;}.apiUnit{float:left;width:33%;color:#EDEDED;display:block;line-height:15px;font-weight:800;line-height:30px;font-size:12px;padding:5px 0px;white-space:pre-wrap;word-break:break-all;word-wrap:break-word;}.apiUnit > a{margin-left:5px;color:#C07af9}.apiUnit:nth-child(2){width:16%;}.apiUnit:nth-child(3){width:50%;}.bottom{width:100%;height:30px;bottom:0;position:fixed;}.footer{height:100px;bottom:0;display:table-cell;width:100 %;float:left;text-align:center;font-size:10px;color:#666;vertical-align:bottom;}.footer > p{position:relative;bottom:0px;padding:0px;margin-top:80px}";
        return "<head><style type=\"text/css\">" + defaultcss + "</style></head>";
    }
}