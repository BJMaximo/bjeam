package wp.bjequipos;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;
import java.util.Vector;

/**
 * ╔════════════════════════════════╗
 * §File Name:  SelectItemTableBean.java
 * §File Path: wp.bjequipos.SelectItemTableBean
 * §Descrption: 设备应用程序-备件选项卡-选择备件
 * §Version:  V0.1
 * §Create Date:   2017/12/20
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class SelectItemTableBean extends DataBean {

    @Override
    public synchronized int execute() throws MXException, RemoteException {
        int ex = super.execute();
        MboRemote appMbo = app.getAppBean().getMbo();
        DataBean targetDataBean = app.getDataBean("sparepartsform_parts_table"); // 获取备件DataBean
        Vector<MboRemote> vec = getSelection();
        if (!vec.isEmpty()) {
            for(int i=0;i<vec.size();i++) {
                MboRemote vMbo = vec.get(i);
                System.out.println(vMbo.getName()+"   cccc");
//                targetDataBean.addrow();
//                MboRemote targetMbo = targetDataBean.getMbo(targetDataBean.getCurrentRow());
//                targetMbo.setValue("ST_NORMALPLANNUM", targetMbo.getOwner().getString("ST_NORMALPLANNUM"));// 设置关联值
//                targetMbo.setValue("ASSETNUM", vMbo.getString("ASSETNUM"));
//                MboRemote assetMbo = targetMbo.getMboSet("$ASSET", "ASSET", "ASSETNUM='"+vMbo.getString("ASSETNUM")+"'").getMbo(0);
//                targetMbo.setValue("ASSETNAME", assetMbo.getString("DESCRIPTION"));
//                targetMbo.setValue("LOCATION", assetMbo.getString("LOCATIONS"));
//                targetMbo.setValue("LOCATIONNAME", assetMbo.getString("LOCATIONSDESC"));
//                targetMbo.setValue("PROBLEMS", vMbo.getString("PROBLEM"));
//                targetMbo.setValue("PROBLEMPOSITION", vMbo.getString("PROBLEMPOSITION"));
//                targetMbo.setValue("REASON", vMbo.getString("REASON"));
            }
        }
        this.app.getAppBean().refreshTable();
        return ex;
    }
}
