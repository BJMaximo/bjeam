package wp.bjemeplan;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  BjEmeplanAppBean.java
 * §File Path: wp.bjemeplan.BjEmeplanAppBean
 * §Descrption: 应急预案应用程序AppBean
 * §Version:  V0.1
 * §Create Date:   2017/12/22
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class BjEmeplanAppBean extends AppBean {
    @Override
    public int INSERT() throws MXException, RemoteException {
        int insert = super.INSERT();
        MboRemote appMbo = app.getAppBean().getMbo();
        appMbo.setValue("APPNAME", "BJEMEPLAN");
        app.getAppBean().refreshTable();
        return insert;
    }
}
