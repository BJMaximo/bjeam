package wp.bjskillzc;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  BjAttAppBean.java
 * §File Path: wp.bjskillzc.BjSkillZcAppBean
 * §Descrption:技改管理(整车）应用程序AppBean
 * §Version:  V0.1
 * §Create Date:   2018/1/3
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class BjSkillZcAppBean extends AppBean {

    @Override
    public int INSERT() throws MXException, RemoteException {
        int insert = super.INSERT();
        MboRemote appMbo = app.getAppBean().getMbo();
        appMbo.setValue("APPNAME", "BJSKILLZC");
        app.getAppBean().refreshTable();
        return insert;
    }
}
