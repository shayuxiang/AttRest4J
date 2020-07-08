package online.attrest.core.codetype;

import online.attrest.core.BasicSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 检测API是否存在问题的处理
 * 预留类,需要扩充
 */
@Component
public class ApiCheck {

    @Autowired
    private BasicSetting setting;

    public void doControllerMethodCheck(ApiModel api) {
        if (!setting.isCheckedApi())
            return;
        String method = api.getRequestMethod();
        if (method == null)
            return; // 非检测方法
        // GET方法检测
/*         if (method.toLowerCase().equals("get")) {
            // get方法
            for (ParamTypeEntity p : api.getParamTypes()) {
                String annoName = p.getAnnotationTypeName();
                // System.out.println("检测-路由:" + api.getControllerName() + "-" +
                // api.getActionName() + " -参数:" + p.getName());
                if (annoName.equals("RequestBody"))
                    System.err.println("AttRest->" + api.getArea() + ":" + api.getControllerName() + "-"
                            + api.getActionName() + "[!Warring]:GET方法无法取到RequestBody!");
                continue;
            }
        } 
        //POST方法检测
        else if (method.toLowerCase().equals("post")) {
            for (ParamTypeEntity p : api.getParamTypes()) {
                String annoName = p.getAnnotationTypeName();
                // System.out.println("检测-路由:" + api.getControllerName() + "-" +
                // api.getActionName() + " -参数:" + p.getName());
                if (annoName.equals("PathVariable"))
                    System.err.println("AttRest->" + api.getArea() + ":" + api.getControllerName() + "-"
                            + api.getActionName() + "[!Warring]:POST方法无法取到PathVariable!");
                continue;
            }
        }
        // System.out.println(api.getActionName()); */
    }
}