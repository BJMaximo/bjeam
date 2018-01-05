package wp.bjpmstd;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  PmTaskDataBean.java
 * §File Path: wp.bjpmstd.PmTaskDataBean
 * §Descrption: PM标准应用程序--标准任务子表DataBean
 * §Version:  V0.1
 * §Create Date:   2018/1/5
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class PmTaskDataBean extends DataBean {

    @Override
    public int addrow() throws MXException {
        int i = super.addrow();
        try {
            MboRemote appMbo=app.getAppBean().getMbo();
            MboRemote mbo=this.getMbo();
            //BJPMSTDID=:BJPMSTDID and orgid=:orgid and siteid=siteid
            mbo.setValue("BJPMSTDID",appMbo.getInt("BJPMSTDID"));
            mbo.setValue("orgid",appMbo.getString("orgid"));
            mbo.setValue("siteid",appMbo.getString("siteid"));
            mbo.setValue("BJPMSTDLINENUM",mbo.getThisMboSet().count());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return i;
    }
}
