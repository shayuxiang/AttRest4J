package org.attrest.core.interfaces;

import java.util.ArrayList;

import org.attrest.core._enum.AttClientFrame;
import org.attrest.core.clients.*;

public class ClientCodeFactory<T> {
    private static ClientCodeFactory<?> _instance = null;
    public static ClientCodeFactory<?> getInstance(){
        if(_instance == null){
            return _instance = new ClientCodeFactory<>();
        }
        return _instance;
    } 
    private ClientCodeFactory() { }


    /// <summary>
    /// 获取代码生成类
    /// </summary>
    /// <param name="attCodeType"></param>
    /// <returns></returns>
    public ArrayList<IClientCode> GetCodeBuilder(AttClientFrame attCodeType) {
        ArrayList<IClientCode> codes = new ArrayList<IClientCode>();
        if ((AttClientFrame.Vue.getValue() & attCodeType.getValue()) == AttClientFrame.Vue.getValue()) codes.add(new VueClient());
        if ((AttClientFrame.React.getValue() & attCodeType.getValue()) == AttClientFrame.React.getValue()) codes.add(new ReactClient());
        if ((AttClientFrame.Angular2.getValue() & attCodeType.getValue()) == AttClientFrame.Angular2.getValue()) codes.add(new AngularClient());
        if ((AttClientFrame.JQuery.getValue() & attCodeType.getValue()) == AttClientFrame.JQuery.getValue()) codes.add(new JqueryClient());
        return codes;
    }
}