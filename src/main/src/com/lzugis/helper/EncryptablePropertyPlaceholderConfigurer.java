package com.lzugis.helper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static final String Key= "U001002003";
    private static final String Encrypted = "password.encrypted";
    private static final String Password = "jdbc.password";
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        try {
//                DesEncrypt des = new DesEncrypt();

            String encrypted = props.getProperty(Encrypted);
            boolean isEncrypted = false;
            if("true".equals(encrypted)||"1".equals(encrypted)){
                isEncrypted=true;
            }
            String pwd = props.getProperty(Password);
            if (isEncrypted && pwd != null) {
                props.setProperty(Password, DesEncrypt.getDesString(pwd,Key));
            }

            super.processProperties(beanFactory, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeanInitializationException(e.getMessage());
        }
    }
}