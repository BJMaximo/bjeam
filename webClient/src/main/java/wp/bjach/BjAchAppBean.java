package wp.bjach;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  BjAchAppBean.java
 * §File Path: wp.bjach.BjAchAppBean
 * §Descrption: 绩效管理AppBean
 * §Version:  V0.1
 * §Create Date:   2017/12/28
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class BjAchAppBean extends AppBean {
    @Override
    public int INSERT() throws MXException, RemoteException {
        int insert = super.INSERT();
        MboRemote appMbo = app.getAppBean().getMbo();
        appMbo.setValue("APPNAME", "BJACH");
        app.getAppBean().refreshTable();
        return insert;
    }
}
