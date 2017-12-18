package wp.bjpcr;

import psdi.mbo.MboRemote;
import psdi.mbo.NonPersistentMboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.LookupBean;
import psdi.webclient.system.runtime.WebClientRuntime;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Vector;

/**
 * ╔════════════════════════════════╗
 * §File Name:  PcrAppBean.java
 * §File Path: wp.bjpcr.PcrAppBean
 * §Descrption:
 * §Version:  V0.1
 * §Create Date:   2017/12/15
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class PcrAppBean extends AppBean  {
    public int INSERT() throws MXException, RemoteException {
        int insert = super.INSERT();
        MboRemote appMbo = app.getAppBean().getMbo();
        appMbo.setValue("BJPCRNUM", "-   PCR-BJPR" + new Date().getTime());
        appMbo.setValue("OCCURRENCEDATE", new Date());
        app.getAppBean().refreshTable();
        return insert;
    }

    public int execute() throws MXException, RemoteException {
        int i = super.execute();
        MboRemote appMbo = app.getAppBean().getMbo();
        if (appMbo != null) {
            Vector v = appMbo.getMboSet("DUTYMANAGER").getSelection();
        }
        return i;
    }
}
