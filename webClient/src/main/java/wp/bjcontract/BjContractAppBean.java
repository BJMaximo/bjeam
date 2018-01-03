package wp.bjcontract;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  BjAttAppBean.java
 * §File Path: wp.bjcontract.BjContractAppBean
 * §Descrption:合同管理应用程序AppBean
 * §Version:  V0.1
 * §Create Date:   2018/1/3
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class BjContractAppBean extends AppBean {

    @Override
    public int INSERT() throws MXException, RemoteException {
        int insert = super.INSERT();
        MboRemote appMbo = app.getAppBean().getMbo();
        appMbo.setValue("APPNAME", "BJCONTRACT");
        app.getAppBean().refreshTable();
        return insert;
    }
}
