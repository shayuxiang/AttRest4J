package org.attrest.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import org.attrest.core.codetype.ApiCheck;
import org.attrest.core.codetype.ApiEnumerable;
import org.attrest.core.codetype.ApiModel;
import org.attrest.core.codetype.AttDescription;
import org.attrest.core.codetype.EntityModel;
import org.attrest.core.codetype.FieldModel;
import org.attrest.core.codetype.ParamTypeEntity;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Component
public class SpringExt implements ApplicationContextAware {

    /** 当前的接口列表 */
    public static ApiEnumerable apiEnumerable;

    /** 注入bean */
    @Autowired
    public void setApiEnumerable(ApiEnumerable apiEnumerable) {
        SpringExt.apiEnumerable = apiEnumerable;
    }

    private static ApplicationContext applicationContext;

    @Autowired
    private ApiCheck apiCheck;

    /**
     * 通过Spring的注入拿到文档结构
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringExt.applicationContext = applicationContext;

        String[] beans = SpringExt.applicationContext.getBeanDefinitionNames();
        for (String beanName : beans) {
            Class<?> beanType = SpringExt.applicationContext.getType(beanName);
            RestController rest = beanType.getAnnotation(RestController.class);
            // 是控制器类型,扫描到控制器
            if (beanName.endsWith("Controller") && rest != null) {

                for (Method method : beanType.getMethods()) {
                    // 创建ApiModel对象
                    ApiModel apiModel = new ApiModel();
                    // 获取描述
                    AttDescription attDesc = method.getAnnotation(AttDescription.class);
                    String attMethodDesc = attDesc == null ? "" : attDesc.value();
                    // 设置描述
                    apiModel.setDescription(attMethodDesc);
                    apiModel.setAsync(false);// Java没有直接的Async方法
                    apiModel.setControllerName(beanName.replace("Controller", "")); // 设置对象名
                    apiModel.setActionName(method.getName());
                    // 自动获取路由前缀
                    RequestMapping route = beanType.getAnnotation(RequestMapping.class);
                    apiModel.setRouteFilter(GetPathOrValue(route));
                    // 获取method的标注，只需要管第一个RequestMapping的标注
                    Annotation[] mapping = method.getAnnotations();
                    // 获取mapping映射路径
                    boolean isAddMethod = false;
                    for (Annotation an : mapping) {
                        if (an.annotationType().equals(RequestMapping.class)) {
                            RequestMapping rq = method.getAnnotation(RequestMapping.class);
                            // 这里不认可多路径，客户端只认第一个路径
                            apiModel.setActionRouteFilter(GetPathOrValue(rq));
                            apiModel.setRequestMethod(rq.method()[0].name());
                            isAddMethod = true;
                            // 添加到内存
                            apiEnumerable.ApiModels.add(apiModel);
                        }
                    }
                    if (!isAddMethod) {
                        // 未找到RequestMapping,才找其它标注
                        for (Annotation an : mapping) {
                            if (an.annotationType().equals(GetMapping.class)) {
                                GetMapping rq = method.getAnnotation(GetMapping.class);
                                // 这里不认可多路径，客户端只认第一个路径
                                apiModel.setActionRouteFilter(GetPathOrValue(rq));
                                apiModel.setRequestMethod("GET");
                                isAddMethod = true;
                            } else if (an.annotationType().equals(PostMapping.class)) {
                                PostMapping rq = method.getAnnotation(PostMapping.class);
                                // 这里不认可多路径，客户端只认第一个路径
                                apiModel.setActionRouteFilter(GetPathOrValue(rq));
                                apiModel.setRequestMethod("POST");
                                isAddMethod = true;
                            } else if (an.annotationType().equals(PutMapping.class)) {
                                PutMapping rq = method.getAnnotation(PutMapping.class);
                                // 这里不认可多路径，客户端只认第一个路径
                                apiModel.setActionRouteFilter(GetPathOrValue(rq));
                                apiModel.setRequestMethod("PUT");
                                isAddMethod = true;
                            } else if (an.annotationType().equals(DeleteMapping.class)) {
                                DeleteMapping rq = method.getAnnotation(DeleteMapping.class);
                                // 这里不认可多路径，客户端只认第一个路径
                                apiModel.setActionRouteFilter(GetPathOrValue(rq));
                                apiModel.setRequestMethod("DELETE");
                                isAddMethod = true;
                            }
                        }
                        if (isAddMethod) {
                            // 添加到内存
                            apiEnumerable.ApiModels.add(apiModel);
                        }
                    }

                    // 获取所有参数上的注解
                    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                    Parameter[] params = method.getParameters();

                    // 扫描参数和返回值
                    for (int x = 0; x < parameterAnnotations.length; x++) {
                        Parameter p = params[x];
                        Annotation[] paramAnn = parameterAnnotations[x];
                        // 需要是自定义的类才创建
                        // System.out.println(p.getName()+ ","+p.getType()+","+p.getType().isEnum());
                        if (p != null && p.getType().getClassLoader() != null) {
                            // System.out.println(p.getName()+":"+p.getName()+","+p.getType().isEnum());
                            Class<?> paramClass = p.getType(); // 获取参数类型
                            // 说明
                            AttDescription pdesc = paramClass.getAnnotation(AttDescription.class);
                            String Description = pdesc == null ? "枚举" + paramClass.getName() + ",无说明" : pdesc.value(); // 说明
                            EntityModel enumModel = new EntityModel(); // 建立enumModel
                            enumModel.setClassName(paramClass.getName());
                            enumModel.setDllName(paramClass.getPackage().getName()); // Java里DLL与Namespace一致
                            enumModel.setNamespace(paramClass.getPackage().getName());

                            enumModel.setDescription(Description);
                            if (paramClass.isEnum()) {
                                // 枚举类型
                                Object[] enumField = paramClass.getEnumConstants();
                                String[] enumKey = new String[enumField.length];
                                for (int i = 0; i < enumField.length; i++) {
                                    enumKey[i] = enumField[i].toString();
                                }
                                for (Field f : paramClass.getDeclaredFields()) {
                                    AttDescription fdesc = f.getAnnotation(AttDescription.class);
                                    String fDescription = fdesc == null ? "枚举字段" + f.getName() + ",无说明" : fdesc.value(); // 说明
                                    boolean isIn = Arrays.asList(enumKey).contains(f.getName());
                                    if (isIn) {
                                        // 设置字段
                                        FieldModel fm = new FieldModel();
                                        fm.setName(f.getName());
                                        fm.setDescription(fDescription);
                                        fm.setFieldType(f.getType());
                                        Object val = null;
                                        try {
                                            Field fslot = f.getClass().getDeclaredField("slot");
                                            fslot.setAccessible(true);
                                            val = fslot.get(f);
                                        } catch (Exception e) {
                                            System.err.println(e.getMessage());
                                        } finally {
                                        }
                                        fm.setDefaultValue(val);
                                        enumModel.getParameters().add(fm);
                                    }
                                }
                                apiEnumerable.EnumModels.add(enumModel);

                            } else {
                                // Pojo
                                for (Field f : paramClass.getDeclaredFields()) {
                                    f.setAccessible(true); // 直接拿到私有属性
                                    AttDescription fdesc = f.getAnnotation(AttDescription.class);
                                    String fDescription = fdesc == null ? "实体字段" + f.getName() + ",无说明" : fdesc.value(); // 说明
                                    // 设置字段
                                    FieldModel fm = new FieldModel();
                                    fm.setName(f.getName());
                                    fm.setDescription(fDescription);
                                    fm.setFieldType(f.getType());
                                    Object val = null;
                                    try {
                                        Field fslot = f.getClass().getDeclaredField("slot");
                                        fslot.setAccessible(true);
                                        val = fslot.get(f);
                                    } catch (Exception e) {
                                        System.err.println(e.getMessage());
                                    } finally {
                                    }
                                    fm.setDefaultValue(val);
                                    enumModel.getParameters().add(fm);
                                }
                                apiEnumerable.EntityModels.add(enumModel);
                            }
                        } else {
                            // 构建泛型

                        }
                        ParamTypeEntity pe = new ParamTypeEntity();
                        // 拿到参数标注
                        for (Annotation annotation : paramAnn) {
                            if (annotation.annotationType().equals(RequestBody.class)
                                    || annotation.annotationType().equals(RequestParam.class)
                                    || annotation.annotationType().equals(PathVariable.class)) {
                                // 只检索一个即可
                                pe.setAnnotationType(annotation.annotationType());
                                break;
                            }
                        }
                        pe.setName(p.getName());
                        pe.setParamType(p.getType());
                        apiModel.getParamTypes().add(pe);
                    }

                    // 返回值类型
                    apiModel.setReturnType(method.getReturnType());

                    // Check API
                    apiCheck.doControllerMethodCheck(apiModel);
                }
            }
        }

    }

    /** 获取相对路径的Path或Value */
    private String GetPathOrValue(Object _mapping) {
        if (_mapping == null)
            return "";
        if (_mapping instanceof RequestMapping) {
            RequestMapping mapping = (RequestMapping) _mapping;
            if (mapping.value() != null && mapping.value().length > 0)
                return mapping.value()[0];
            else if (mapping.path() != null && mapping.path().length > 0)
                return mapping.path()[0];
            else
                return "";
        }

        if (_mapping instanceof GetMapping) {
            GetMapping mapping = (GetMapping) _mapping;
            if (mapping.value() != null && mapping.value().length > 0)
                return mapping.value()[0];
            else if (mapping.path() != null && mapping.path().length > 0)
                return mapping.path()[0];
            else
                return "";
        }

        if (_mapping instanceof PostMapping) {
            PostMapping mapping = (PostMapping) _mapping;
            if (mapping.value() != null && mapping.value().length > 0)
                return mapping.value()[0];
            else if (mapping.path() != null && mapping.path().length > 0)
                return mapping.path()[0];
            else
                return "";
        }

        if (_mapping instanceof PutMapping) {
            PutMapping mapping = (PutMapping) _mapping;
            if (mapping.value() != null && mapping.value().length > 0)
                return mapping.value()[0];
            else if (mapping.path() != null && mapping.path().length > 0)
                return mapping.path()[0];
            else
                return "";
        }
        if (_mapping instanceof DeleteMapping) {
            DeleteMapping mapping = (DeleteMapping) _mapping;
            if (mapping.value() != null && mapping.value().length > 0)
                return mapping.value()[0];
            else if (mapping.path() != null && mapping.path().length > 0)
                return mapping.path()[0];
            else
                return "";
        }
        return "";
    }
}