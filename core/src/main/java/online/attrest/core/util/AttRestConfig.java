package online.attrest.core.util;

import online.attrest.core._enum.AttClientFrame;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application.properties")
public class AttRestConfig 
{
    @Value("${attrest.client}")
    private String ClientType;
    
    
    @Value("${attrest.checkapi}")
    private boolean CheckAPI;

    
    @Value("${attrest.host}")
    private String Host;

    @Value("${attrest.mode}")
    private String mode;

    @Bean
    public  BasicSetting getBasicSetting(){
        BasicSetting setting = new BasicSetting(Host, CheckAPI,mode);
        return setting;
    }

    @Bean
    public AttClientFrame getClientType() {
        if( ClientType.toLowerCase().equals("vue")){
            return AttClientFrame.Vue;
        }
        if( ClientType.toLowerCase().equals("angular") ||  ClientType.toLowerCase().equals("angular2")){
            return AttClientFrame.Angular2;
        }
        if( ClientType.toLowerCase().equals("react")){
            return AttClientFrame.React;
        }
        if( ClientType.toLowerCase().equals("jquery")){
            return AttClientFrame.JQuery;
        }
        if( ClientType.toLowerCase().equals("avalon")){
            return AttClientFrame.Avalon;
        }
        return null;
    }

}