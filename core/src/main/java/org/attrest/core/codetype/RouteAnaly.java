package org.attrest.core.codetype;


public class RouteAnaly {

    private String routeFilter;
    private String controllerName;
    private String actionName;
    private String actionRouteFilter;
    private String areaFilter = "";

    /**
     * 配合C#统一的字段，预留，无实际运算
     * @return
     */
    public String getControllerName() {
        return controllerName;
    }

    /**
     * 配合C#统一的字段，预留，无实际运算
     * @return
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * 配合C#统一的字段，预留，无实际运算
     * @return
     */
    public String getAreaFilter() {
        return areaFilter;
    }

    /// <summary>
    /// 是否为伪静态链接
    /// </summary>
    private boolean HasRewrite = false;

    public boolean getHasRewrite() {
        return HasRewrite;
    }

    /// <summary>
    /// 链接的地址
    /// </summary>
    private String Link;

    public String getLink() {
        return Link;
    }

    /// <summary>
    /// 链接的地址显示
    /// </summary>
    private String LinkShow;

    public String getLinkShow() {
        return LinkShow;
    }

    /// <summary>
    /// 构造
    /// </summary>
    /// <param name="RouteFilter"></param>
    public RouteAnaly(String Area, String RouteFilter, String ControllerName, String ActionName,
            String ActionRouteFilter) {
        areaFilter = Area == null || Area.trim() == "" ? "" : Area;
        routeFilter = RouteFilter == null || RouteFilter.trim() == "" ? "" : RouteFilter;
        actionName = ActionName;
        controllerName = ControllerName;
        actionRouteFilter = ActionRouteFilter;
        Init();
    }

    /// <summary>
    /// 转为真实的URL
    /// </summary>
    /// <returns></returns>
    private void Init() {
        if (!routeFilter.startsWith("/") || routeFilter.startsWith("\\")) {
            routeFilter = "/" + routeFilter;
        }
        if (!actionRouteFilter.startsWith("/") || actionRouteFilter.startsWith("\\")) {
            actionRouteFilter = "/" + actionRouteFilter;
        }
        String filter = routeFilter + actionRouteFilter;
        Link = filter.replace("{", "' + ").replace("}", " + '");
        LinkShow = filter; //后续版本处理{}不能直接调试的问题
    }
}