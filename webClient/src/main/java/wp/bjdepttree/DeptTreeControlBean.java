package wp.bjdepttree;

import psdi.util.MXException;
import psdi.webclient.beans.common.TreeControlBean;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  DeptTreeControlBean.java
 * §File Path: wp.bjdepttree.DeptTreeControlBean
 * §Descrption: 部门管理应用程序-部门树控制类
 * §Version:  V0.1
 * §Create Date:   2017/12/25
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class DeptTreeControlBean extends TreeControlBean {
    String uniqueidvalue;

    protected void initialize() throws MXException, RemoteException {
        super.initialize();
    }

    public int selectrecord() throws MXException, RemoteException {
        System.out.println("选择中了当前行。。。。");
        return 1;
    }

    /**
     * 点击节点触发事件
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    public int selectnode() throws MXException, RemoteException {

        DataBean appbean = this.app.getAppBean();
        this.uniqueidvalue = getuniqueidvalue();
        System.out.println(uniqueidvalue+" uniqueidvalue");
        appbean.setAppWhere("BJDEPTID = '" + this.uniqueidvalue + "'");
        appbean.reset();
        appbean.reloadTable();
        appbean.refreshTable();
        this.sessionContext.queueRefreshEvent();
        appbean.setCurrentRow(0);

        return super.selectnode();
    }

    public int execute() throws MXException, RemoteException {
        return 1;
    }
}
