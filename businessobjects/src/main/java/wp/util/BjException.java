package wp.util;

import psdi.util.MXApplicationException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  BjException.java
 * §File Path: wp.util.BjException
 * §Descrption:
 * §Version:  V0.1
 * §Create Date:   2017/12/14
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class BjException {
    public static void sendMsg(String key ,String code,String[] msg) throws MXApplicationException {
        if(msg.length<=0){
            msg=new String[]{};
        }
        throw new MXApplicationException(key, code,msg);
    }
}
