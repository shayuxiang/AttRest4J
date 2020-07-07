package org.attrest.core.codetype;

import java.util.ArrayList;

public class EntityModel {
    /// <summary>
    /// 对象描述
    /// </summary>
    private String Description;
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    /// <summary>
    /// 命名空间
    /// </summary>
    private String Namespace;

    public String getNamespace() {
        return Namespace;
    }

    public void setNamespace(String namespace) {
        Namespace = namespace;
    }

    /// <summary>
    /// 类名
    /// </summary>
    private String ClassName;

    public String getClassName() {
        return ClassName;
    }


    //只获取类名 去除包名
    public String getShortName(){
        if(ClassName.lastIndexOf(".")>-1&&ClassName.lastIndexOf(".")<ClassName.length()-1){
            return ClassName.substring(ClassName.lastIndexOf(".")+1,ClassName.length()-1);
        }
        return ClassName;
     }

    public void setClassName(String className) {
        ClassName = className;
    }

    /// <summary>
    /// 库名
    /// </summary>
    private String DllName;

    public String getDllName() {
        return DllName;
    }

    public void setDllName(String dllName) {
        DllName = dllName;
    }

    /// <summary>
    /// 属性集合
    /// </summary>
    private ArrayList<FieldModel> Parameters = new ArrayList<FieldModel>();

    public ArrayList<FieldModel> getParameters() {
        return Parameters;
    }

}