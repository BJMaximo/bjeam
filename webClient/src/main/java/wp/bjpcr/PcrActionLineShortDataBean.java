package wp.bjpcr;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  PcrActionLineShortDataBean.java
 * §File Path: wp.bjpcr.PcrActionLineShortDataBean
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
public class PcrActionLineShortDataBean extends DataBean {
    @Override
    public int addrow() throws MXException {
        int i = super.addrow();
        try {
            MboRemote mbo = this.getMbo();
            MboRemote ownerMbo= mbo.getOwner();
            mbo.setValue("TYPE", "short");
            mbo.setValue("PCRID",ownerMbo.getInt("bjpcrid"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        app.getAppBean().refreshTable();
        return i;
    }


}