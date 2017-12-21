package wp.bjequipos;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  ParameterDataBean.java
 * §File Path: wp.bjequipos.ParameterDataBean
 * §Descrption:
 * §Version:  V0.1
 * §Create Date:   2017/12/21
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class ParameterDataBean extends DataBean {
    @Override
    public int addrow() throws MXException {
        int i = super.addrow();
        try {
            MboRemote mbo=this.getMbo();
            MboRemote appMbo=app.getAppBean().getMbo();
            int countNum=mbo.getThisMboSet().count();
            mbo.setValue("ORDERNUM",countNum+"");//设置序号
            //设置关联字段值
            mbo.setValue("ASSETNUM",appMbo.getString("ASSETNUM"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        app.getAppBean().refreshTable();
        return i;
    }
}
