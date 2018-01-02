package wp.bjach;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  PersonListDataBean.java
 * §File Path: wp.bjach.PersonListDataBean
 * §Descrption:绩效管理应用程序--人员子表DataBean
 * §Version:  V0.1
 * §Create Date:   2017/12/28
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class PersonListDataBean extends DataBean {
    @Override
    public int addrow() throws MXException {
        int i = super.addrow();
        try {
            MboRemote mbo=this.getMbo();
            MboRemote ownerMbo=mbo.getOwner();
            mbo.setValue("BJACHID", ownerMbo.getString("BJACHID"));
            mbo.setValue("ORGID",ownerMbo.getString("ORGID"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return i;
    }
}
