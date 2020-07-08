package online.attrest.core.codetype;

public class FieldModel {
    
    /** 参数名称 */
    private String Name;

    public String getName() {
        return Name;
    }

    //只获取类名
    public String getShortName(){
       String[] packages = Name.split(".");
       if(packages.length == 0) return Name;
       return packages[packages.length-1]; //返回最后一个
    }

    public void setName(String name) {
        Name = name;
    }

    /** 属性描述描述 */
    private String Description;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    /** 参数类型 */
    private Class<?> FieldType;

    public void setFieldType(Class<?> fieldType) {
        FieldType = fieldType;
    }

    public Class<?> getFieldType() {
        return FieldType;
    }

    /** 默认值 */
    private Object DefaultValue;

    public void setDefaultValue(Object defaultValue) {
        DefaultValue = defaultValue;
    }

    public Object getDefaultValue() {
        return DefaultValue;
    }
}