package org.attrest.core;

public class BasicSetting {
    
    private String host;

    private boolean isCheckedApi;

    public BasicSetting(String _host,boolean _isCheckedApi){
        this.host = _host;
        this.isCheckedApi = _isCheckedApi;
    }

    /**
     * 获取基础的Host地址
     * @return
     */
    public String getHost() {
        return host;
    }

    /**
     * 获取是否需要检测api
     * @return
     */
    public boolean isCheckedApi() {
        return isCheckedApi;
    }
}