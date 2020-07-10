package online.attrest.core.util;

import online.attrest.core._enum.DriverMode;

public class BasicSetting {
    
    private String host;

    private boolean isCheckedApi;

    private DriverMode driverMode;

    public BasicSetting(String _host,boolean _isCheckedApi,String mode){
        this.host = _host;
        this.isCheckedApi = _isCheckedApi;
        if(mode.toLowerCase().equals("duplex")){
            driverMode = DriverMode.Duplex;
        }else if(mode.toLowerCase().equals("")){
            driverMode = DriverMode.FrontFirst;
        }else {
            driverMode = DriverMode.BackendFirst;
        }
    }

    /**
     * 获取用户设置的启动模式
     * @return
     */
    public DriverMode getDriverMode() {
        return driverMode;
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