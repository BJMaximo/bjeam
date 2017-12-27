package wp.bjdepttree;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  DeptManageAppBean.java
 * §File Path: wp.bjdepttree.DeptManageAppBean
 * §Descrption: 部门管理应用程序AppBean
 * §Version:  V0.1
 * §Create Date:   2017/12/25
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class DeptManageAppBean extends AppBean {
    public void initializeApp() throws MXException, RemoteException {
        super.initializeApp();
        this.app.getAppBean().setAppWhere(" parent is null ");
        this.app.getAppBean().setOrderBy(" orderby ");
        this.app.getAppBean().reset();
        this.app.getAppBean().setCurrentRow(0);
        this.app.getAppBean().reloadTable();
        this.app.getAppBean().refreshTable();
    }


    public void deletebutton() throws MXException, RemoteException {
        MboRemote appMbo = this.app.getAppBean().getMbo();
        WebClientEvent wce = clientSession.getCurrentEvent();
        int msgReturn = wce.getMessageReturn();
        if (msgReturn < 0) {
            throw new MXApplicationYesNoCancelException("是否删除©","deletebutton", "deletebutton_alert");
        }
        //经过测试确定==2 关闭==4
        if(msgReturn==2){
            confirmDelte(appMbo);
        }
        DataBean db = this.app.getDataBean("1294636799046");
        db.reloadTable();
        db.refreshTable();
        this.sessionContext.queueRefreshEvent();
    }

    public void confirmDelte(MboRemote appMbo) throws RemoteException, MXException {
        if(appMbo!=null){
            int deptId=appMbo.getInt("BJDEPTID");
            MboSetRemote deptChidrenSet=appMbo.getMboSet("$BJDEPT2","BJDEPT","parent="+deptId+"");
            for(int i=0;i<deptChidrenSet.count();i++){
                MboRemote iMbo=deptChidrenSet.getMbo(i);
                iMbo.delete();
            }
            appMbo.delete();
            this.SAVE();
        }
    }

    public void cancelsave() throws RemoteException, MXException {
        super.CLEAR();
    }

    public void addroottree() throws RemoteException, MXException {
        MboRemote appMbo = app.getAppBean().getMbo();
        if(appMbo==null){
            super.INSERT();
            MboRemote appInsertMbo = app.getAppBean().getMbo();
            appInsertMbo.setValue("BJDEPTLEVEL", 0);
            appInsertMbo.setValue("ORDERBY", 1);
        }else{
            int parentid = appMbo.getInt("BJDEPTID");//父级id
            int level = appMbo.getInt("BJDEPTLEVEL");//层级等级
            super.INSERT();
            MboRemote appNewMbo = app.getAppBean().getMbo();
            appNewMbo.setValue("parent", parentid);
            appNewMbo.setValue("BJDEPTLEVEL", level + 1);
            appNewMbo.setValue("ORDERBY", getDisplayNum(parentid));
            app.getAppBean().reloadTable();
            app.getAppBean().refreshTable();
        }
    }


    public void addtree() throws RemoteException, MXException {
        MboRemote appMbo = app.getAppBean().getMbo();
        if(appMbo==null){
            super.INSERT();
            MboRemote appInsertMbo = app.getAppBean().getMbo();
            appInsertMbo.setValue("BJDEPTLEVEL", 0);
            appInsertMbo.setValue("ORDERBY", 1);
        }else{
            int parentid = appMbo.getInt("BJDEPTID");//父级id
            int level = appMbo.getInt("BJDEPTLEVEL");//层级等级
            super.INSERT();
            MboRemote appNewMbo = app.getAppBean().getMbo();
            appNewMbo.setValue("parent", parentid);
            appNewMbo.setValue("BJDEPTLEVEL", level + 1);
            appNewMbo.setValue("ORDERBY", getDisplayNum(parentid));
            app.getAppBean().reloadTable();
            app.getAppBean().refreshTable();
        }
    }



    public int getDisplayNum(int parentid) throws RemoteException, MXException {
        int num = 0;
        MboRemote appMbo = app.getAppBean().getMbo();
        MboSetRemote deptSet=appMbo.getMboSet("$BJDEPT","BJDEPT","parent="+parentid+"");
        if(deptSet.count()<=0){
            num=1;
        }else{
            System.out.println(deptSet.max("orderby"));
            num = (int) (deptSet.max("orderby")+1);
        }
        return num;
    }


    /**
     * 保存按钮
     * @throws RemoteException
     * @throws MXException
     */
    public void savebutton() throws RemoteException, MXException {
        super.SAVE();
        DataBean db = this.app.getDataBean("1294636799046");
        db.reloadTable();
        db.refreshTable();
        this.sessionContext.queueRefreshEvent();
    }
}
