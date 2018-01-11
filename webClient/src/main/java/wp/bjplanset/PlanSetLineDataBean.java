package wp.bjplanset;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  PlanSetLineDataBean.java
 * §File Path: wp.bjplanset.PlanSetLineDataBean
 * §Descrption:
 * §Version:  V0.1
 * §Create Date:   2018/1/11
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class PlanSetLineDataBean extends DataBean{
    @Override
    public int addrow() throws MXException {
        int i = super.addrow();
        try {
            MboRemote appMbo=app.getAppBean().getMbo();
            MboRemote mbo=this.getMbo();
            //BJTPMID=:BJTPMID AND  SHIFT=:SHIFT  AND  ORGID=:ORGID  AND SITEID=:SITEID
            mbo.setValue("BJTPMID",appMbo.getInt("BJTPMID"));
            mbo.setValue("SHIFT",appMbo.getString("SHIFT"));
            mbo.setValue("ORGID",appMbo.getString("ORGID"));
            mbo.setValue("SITEID",appMbo.getString("SITEID"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return i;
    }
}
