package online.attrest.core.clients;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import online.attrest.core.SpringExt;
import online.attrest.core._enum.DriverMode;
import online.attrest.core.codetype.ApiModel;
import online.attrest.core.codetype.EntityModel;
import online.attrest.core.codetype.FieldModel;
import online.attrest.core.codetype.ParamTypeEntity;
import online.attrest.core.codetype.RouteAnaly;
import online.attrest.core.interfaces.IClientCode;
import online.attrest.core.util.AttRestCore;
import online.attrest.core.util.LocalApi;

public class VueClient implements IClientCode {

    private String Host = "";
    private String area = "";

    /**
     * 生成vue代码
     */
    @Override
    public String ToCode(DriverMode mode) {
        StringBuilder sb = new StringBuilder();
        String api = ToAPI(mode);
        String _enum = ToEnum();
        String entity = ToEntity();
        String returnOjb = " return function(){this.Vue = that.Vue;}";
        String ret = sb.append("this.Vue = function(vue,axios){ vue.prototype.$api = {};")
                .append("vue.prototype.$enum = {};vue.prototype.$entity= {};var that = this;that.ErrorCatch = null; ")
                .append(api).append(_enum).append(entity).append(returnOjb).append("};").toString();
        return ret;
    }

    /**
     * 生成自定义对象
     * 
     * @return
     */
    private String ToEntity() {
        // 定义对象
        String func = "";
        for (EntityModel model : SpringExt.apiEnumerable.EntityModels) {
            // 原型方式创建js对象
            String entityjs = "/** @description 实体对象[" + model.getDescription() + "]  */ vue.prototype.$entity."
                    + model.getShortName() + "= {};";
            String func_body = "";
            for (FieldModel param : model.getParameters()) {
                String defaultValue = "null";
                // 自定义类型
                if (param.getFieldType().equals(String.class)) {
                    defaultValue = "''";
                } else if (param.getFieldType().equals(Integer.class) || param.getFieldType().equals(int.class)
                        || param.getFieldType().equals(Double.class) || param.getFieldType().equals(double.class)
                        || param.getFieldType().equals(Long.class) || param.getFieldType().equals(long.class)
                        || param.getFieldType().equals(Float.class) || param.getFieldType().equals(float.class)) {
                    defaultValue = "0";
                } else if (param.getFieldType().isArray()) {
                    defaultValue = "[]";
                } else if (param.getFieldType().equals(Date.class)) {
                    defaultValue = "new Date()";
                } else if (param.getFieldType().getClassLoader() != null) {
                    // 自定义类型
                    defaultValue = "new " + param.getFieldType().getName() + "()";
                }
                func_body += "/** @description " + param.getDescription() + "    @example in vue : this.$entity."
                        + model.getShortName() + "." + param.getName() + "; */ vue.prototype.$entity."
                        + model.getShortName() + "." + param.getName() + " = " + defaultValue + ";";
            }
            func += entityjs + func_body;
        }
        return func;
    }

    /**
     * 生成枚举代码
     * 
     * @return
     */
    private String ToEnum() {
        // 定义枚举
        String func = "";

        for (EntityModel model : SpringExt.apiEnumerable.EnumModels) {
            String enumjs = "/** @description 枚举[" + model.getDescription() + "]  */ vue.prototype.$enum."
                    + model.getShortName() + " = new Object();";
            String func_body = "";
            for (FieldModel param : model.getParameters()) {
                func_body += "/** @description " + param.getDescription() + "   @example vue: this.$enum."
                        + model.getShortName() + "." + param.getName() + ";   */ vue.prototype.$enum."
                        + model.getShortName() + "." + param.getName() + " = " + param.getDefaultValue() + "; ";
            }
            enumjs = enumjs + func_body;
            // 所有的初始化
            func += enumjs;
        }
        return func;
    }

    /**
     * 生成接口代码
     * 
     * @return
     */
    private String ToAPI(DriverMode mode) {

        if (mode == DriverMode.BackendFirst || mode == DriverMode.Duplex) { 
            //后台模式,会将后台所有可用接口直接返给前端
            return buildApiCode(SpringExt.apiEnumerable.ApiModels);
        }
       
        else if(mode == DriverMode.FrontFirst){ 
            //前端模式，会读取已验证通过核对的接口进行返回，未通过的不返回
            if(AttRestCore.localApiList == null) return "";
            ArrayList<ApiModel> returnAPI = new ArrayList<ApiModel>();
            for(LocalApi localApi : AttRestCore.localApiList.getLocalApis()){
                if(localApi.isExist()){
                    SpringExt.apiEnumerable.ApiModels.forEach(a->{
                        if(a.getControllerName().toLowerCase().equals(localApi.getRoute().toLowerCase()) 
                        && a.getActionName().toLowerCase().equals(localApi.getAction().toLowerCase())){
                            returnAPI.add(a);
                        }
                    });
                }
            }

            return buildApiCode(returnAPI);
        }
        else{ //双工模式,会将后台所有接口返回前端，同时取得前端有但后台没有接口返回

        }
        return "";
    }

    /**
     * 构建API客户端代码
     * @param apis
     * @return
     */
    private String buildApiCode(ArrayList<ApiModel> apis){
           if(apis.size() == 0) return "";
            List<List<ApiModel>> groupList = new ArrayList<>();
            // 对所有api接口进行分组
            apis.stream()
                    .collect(Collectors.groupingBy(ApiModel::getControllerName, Collectors.toList()))
                    .forEach((name, fooList) -> {
                        groupList.add(fooList);
                    });
            StringBuilder vue_api = new StringBuilder();
            StringBuilder vue_api_list = new StringBuilder();

            groupList.forEach((apiList) -> {
                if (apiList.size() <= 0) {
                    return;
                } // 错误判断，一般不会出现
                vue_api_list.append("'" + apiList.get(0).getControllerName().replace("Controller", "") + "',");
                // 固定区域选项,即用于微服务的前缀
                this.area = apiList.get(0).getArea() == null || apiList.get(0).getArea().trim() == "" ? ""
                        : apiList.get(0).getArea() + "_";
                StringBuilder methods = new StringBuilder();

                apiList.forEach((method) -> {
                    RouteAnaly routeAnaly = new RouteAnaly(method.getArea(), method.getRouteFilter(),
                            method.getControllerName(), method.getActionName(), method.getActionRouteFilter());
                    String link = "'" + Host + "' + '" + routeAnaly.getLink() + "'";
                    // 参数整理
                    String _params = "";
                    String data = "";
                    for (ParamTypeEntity p : method.getParamTypes()) {
                        _params += p.getName() + ",";
                        data += p.getName() + ":" + p.getName() + ",";
                    }
                    if (data.length() > 0) {
                        data = "{" + data.substring(0, data.length() - 1) + "}";
                    } else {
                        data = "{}";
                    }
                    String methodRequestMethod = method.getRequestMethod().toLowerCase();
                    String DataType = methodRequestMethod.equals("get") ? "param" : "data";
                    // API方法构建
                    StringBuilder vue_method = new StringBuilder();
                    vue_method.append(method.getActionName() + ":function(" + _params + "callback){")
                            .append("axios." + methodRequestMethod + "(" + link + ",{")
                            .append(" " + DataType + ":" + data).append(" }).catch(function(error){ ")
                            .append("  if(that.ErrorCatch!=null && that.ErrorCatch!=undefined){ ")
                            .append("that.ErrorCatch(error,\""
                                    + apiList.get(0).getControllerName().replace("Controller", "") + "-"
                                    + method.getActionName() + "\");")
                            .append("}}).then(function(res){  callback(res); })},");
                    methods.append(vue_method);
                });
                vue_api.append(
                        "  vue.prototype.$api." + area + apiList.get(0).getControllerName().replace("Controller", "")
                                + " = {" + methods.toString().substring(0, methods.length() - 1) + "},");
            });

            return vue_api.toString().substring(0, vue_api.length() - 1) + ";";

    }

    @Override
    public void SetHost(String Host) {
        this.Host = Host;
    }
}