package online.attrest.core.codetype;

import java.util.ArrayList;

public class ApiModel {
    /** 接口描述 */
    private String description;

    public String getDescription() {
        if (description.trim() == "") {
            return "接口" + this.getActionName() + ":无描述";
        }
        return "接口" + this.getActionName() + ":" +description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** 请求方式 get/post/put/delete */
    private String RequestMethod;

    public String getRequestMethod() {
        return RequestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        RequestMethod = requestMethod;
    }

    /** Route过滤 */
    private String RouteFilter;

    public String getRouteFilter() {
        return RouteFilter;
    }

    public void setRouteFilter(String routeFilter) {
        RouteFilter = routeFilter;
    }

    /** 控制器名称 */
    private String ControllerName;

    public String getControllerName() {
        return ControllerName;
    }

    public void setControllerName(String controllerName) {
        ControllerName = controllerName;
    }

    /** 请求路径名称 */
    private String ActionName;

    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String actionName) {
        ActionName = actionName;
    }

    /** 路径Route拦截 */
    private String ActionRouteFilter;

    public String getActionRouteFilter() {
        return ActionRouteFilter;
    }

    public void setActionRouteFilter(String actionRouteFilter) {
        ActionRouteFilter = actionRouteFilter;
    }

    /** 是否为异步 */
    private boolean Async;

    public boolean isAsync() {
        return Async;
    }

    public void setAsync(boolean async) {
        Async = async;
    }

    /** 参数是否有自定义类型 */
    private boolean AllParamPrimitive;

    public boolean isAllParamPrimitive() {
        return AllParamPrimitive;
    }

    public void setAllParamPrimitive(boolean allParamPrimitive) {
        AllParamPrimitive = allParamPrimitive;
    }

    /** 返回是否为自定义类型 */
    private boolean ReturnPrimitive;

    public boolean isReturnPrimitive() {
        return ReturnPrimitive;
    }

    public void setReturnPrimitive(boolean returnPrimitive) {
        ReturnPrimitive = returnPrimitive;
    }

    /** 参数类型 */
    private ArrayList<ParamTypeEntity> ParamTypes = new ArrayList<ParamTypeEntity>();

    public ArrayList<ParamTypeEntity> getParamTypes() {
        return ParamTypes;
    }

    public void setParamTypes(ArrayList<ParamTypeEntity> paramTypes) {
        ParamTypes = paramTypes;
    }

    /** 方法返回的类型 */
    private Class<?> ReturnType;

    public Class<?> getReturnType() {
        return ReturnType;
    }

    public void setReturnType(Class<?> returnType) {
        ReturnType = returnType;
    }

    /** 获取区域 */
    private String Area = "";

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }
}