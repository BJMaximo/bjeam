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
        MboRemote appMbo=app.getAppBean().getMbo();
        String orgid="";
        String personid="";
        if(appMbo!=null){
            orgid=appMbo.getString("orgid");
            personid=appMbo.getUserInfo().getPersonId();
        }
        this.app.getAppBean().setAppWhere(" PARENTID is null and orgid='"+orgid+"' or 1=(case when 'maxadmin'='"+personid+"' THEN 1 ELSE 0 END) ");
        this.app.getAppBean().setOrderBy(" orderby ");
        this.app.getAppBean().reset();
        this.app.getAppBean().setCurrentRow(0);
        this.app.getAppBean().reloadTable();
        this.app.getAppBean().refreshTable();
    }

    /**
     * 删除目录按钮
     * @throws MXException
     * @throws RemoteException
     */
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

    /**
     * 确认删除逻辑
     * @param appMbo
     * @throws RemoteException
     * @throws MXException
     */
    public void confirmDelte(MboRemote appMbo) throws RemoteException, MXException {
        if(appMbo!=null){
            int deptId=appMbo.getInt("BJDEPTID");
            MboSetRemote deptChidrenSet=appMbo.getMboSet("$BJDEPT2","BJDEPT","parentid="+deptId+"");
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

    /**
     * 新建根目录按钮
     * @throws RemoteException
     * @throws MXException
     */
    public void addroottree() throws RemoteException, MXException {
        MboRemote appMbo = app.getAppBean().getMbo();
        if(appMbo==null){
            super.INSERT();
            MboRemote appInsertMbo = app.getAppBean().getMbo();
            appInsertMbo.setValue("BJDEPTLEVEL", 1);
            appInsertMbo.setValue("ORDERBY", 1);
        }else{
            super.INSERT();
            MboRemote appNewMbo = app.getAppBean().getMbo();
            appNewMbo.setValue("BJDEPTLEVEL",  1);
            appNewMbo.setValue("ORDERBY", getRootDisplayNum(appNewMbo.getString("ORGID")));
            app.getAppBean().reloadTable();
            app.getAppBean().refreshTable();
        }
    }

    /**
     * 获取根目录中的排序号（orderby)
     * @param orgid
     * @return
     * @throws RemoteException
     * @throws MXException
     */
    public int getRootDisplayNum(String orgid) throws RemoteException, MXException {
        int num = 0;
        MboRemote appMbo = app.getAppBean().getMbo();
        MboSetRemote deptSet=appMbo.getMboSet("$BJDEPT","BJDEPT","PARENTID is null and orgid='"+orgid+"'");
        if(deptSet.count()<=0){
            num=1;
        }else{
            num = (int) (deptSet.max("orderby")+1);
        }
        return num;
    }

    /**
     * 新建子目录按钮
     * @throws RemoteException
     * @throws MXException
     */
    public void addtree() throws RemoteException, MXException {
        MboRemote appMbo = app.getAppBean().getMbo();
        if(appMbo==null){
            super.INSERT();
            MboRemote appInsertMbo = app.getAppBean().getMbo();
            appInsertMbo.setValue("BJDEPTLEVEL", 1);
            appInsertMbo.setValue("ORDERBY", 1);
        }else{
            int parentid=appMbo.getInt("BJDEPTID");
            String deptnum=appMbo.getString("BJDEPTNUM");
            String orgid=appMbo.getString("ORGID");
            int level = appMbo.getInt("BJDEPTLEVEL");//层级等级
            super.INSERT();
            MboRemote appNewMbo = app.getAppBean().getMbo();
            appNewMbo.setValue("PARENTID", parentid);
            appNewMbo.setValue("PARENTNUM",deptnum);
            appNewMbo.setValue("BJDEPTLEVEL", level + 1);
            appNewMbo.setValue("ORDERBY", getDisplayNum(parentid,deptnum,orgid));
            app.getAppBean().reloadTable();
            app.getAppBean().refreshTable();
        }
    }


    /**
     * 获取子目录中的排序号（orderby)
     * @param deptnum
     * @param orgid
     * @return
     * @throws RemoteException
     * @throws MXException
     */
    public int getDisplayNum(int parentid,String deptnum,String orgid) throws RemoteException, MXException {
        int num = 0;
        MboRemote appMbo = app.getAppBean().getMbo();
        MboSetRemote deptSet=appMbo.getMboSet("$BJDEPT","BJDEPT","PARENTID="+parentid+" and  PARENTNUM='"+deptnum+"' and orgid='"+orgid+"'");
        if(deptSet.count()<=0){
            num=1;
        }else{
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
