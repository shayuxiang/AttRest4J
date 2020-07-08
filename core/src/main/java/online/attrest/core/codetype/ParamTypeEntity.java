package online.attrest.core.codetype;

public class ParamTypeEntity {
    
    private String Name;

    private Class<?> ParamType;

    

    /**参数的入参注解 */
    private String AnnotationTypeName = "";

    public String getAnnotationTypeName() {
        return AnnotationTypeName;
    }

    /**
     * 设置参数的标注类型
     */
    public void setAnnotationType(Class<?> annotationType) {
            
        if(annotationType.getTypeName().equals("org.springframework.web.bind.annotation.RequestBody")){
            AnnotationTypeName = "PathVariable";
        }
        if(annotationType.getTypeName().equals("org.springframework.web.bind.annotation.RequestBody")){
            AnnotationTypeName = "RequestBody";
        }
        if(annotationType.getTypeName().equals("org.springframework.web.bind.annotation.RequestParam")){
            AnnotationTypeName = "RequestParam";
        }
        //this.annotationType = annotationType;
    }

    public String getName(){
        return Name;
    }

    public void setName(String name){
        Name = name;
    }

    
    public Class<?> getParamType(){
        return ParamType;
    }

    public void setParamType(Class<?> paramType){
        ParamType = paramType;
    }
}