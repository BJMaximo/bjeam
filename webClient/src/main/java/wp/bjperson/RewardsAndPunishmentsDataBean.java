package wp.bjperson;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  EducationBackgroundDataBean.java
 * §File Path: wp.bjperson.RewardsAndPunishmentsDataBean
 * §Descrption: 奖惩情况DataBean
 * §Version:  V0.1
 * §Create Date:   2017/12/28
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class RewardsAndPunishmentsDataBean extends DataBean{

    @Override
    public int addrow() throws MXException {
        int i = super.addrow();
        try {
            MboRemote appMbo=app.getAppBean().getMbo();
            MboRemote mbo=this.getMbo();
            mbo.setValue("JCQKPERSONID", appMbo.getString("PERSONID"));
            mbo.setValue("ORGID",appMbo.getString("ORGID"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return i;
    }
}
