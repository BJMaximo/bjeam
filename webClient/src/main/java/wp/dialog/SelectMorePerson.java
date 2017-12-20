package wp.dialog;

import psdi.app.system.MaxDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.util.MXSystemException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.LookupBean;

import java.rmi.RemoteException;
import java.util.Vector;

/**
 * ╔════════════════════════════════╗
 * §File Name:  SelectMorePerson.java
 * §File Path: wp.dialog.SelectMorePerson
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
public class SelectMorePerson extends LookupBean {

    public int execute() throws MXException, RemoteException {
        int i = super.execute();
        MboRemote appMbo = app.getAppBean().getMbo();
        if (appMbo != null) {
            Vector v = appMbo.getMboSet("DUTYMANAGER").getSelection();
        }

        return i;
    }
    public MboSetRemote getMboSetRemote() throws MXException, RemoteException {
        MboSetRemote mboSetRemote = null;
        if (this.creatingEvent != null && this.creatingEvent.getValue() instanceof MboSetRemote) {
            mboSetRemote = (MboSetRemote)this.creatingEvent.getValue();
            this.setTableFlag(32L, true);
            this.setTableFlag(256L, true);
            this.fetchData = true;
        } else {
            if (this.mboName != null || this.parentRelationship != null) {
                return super.getMboSetRemote();
            }

            if (this.parent != null) {
                try {
                    mboSetRemote = this.parent.getRemoteForLookup();
                } catch (RemoteException var3) {
                    throw new MXSystemException("system", "remoteexception", var3);
                }
            }
        }

        return mboSetRemote;
    }
}
