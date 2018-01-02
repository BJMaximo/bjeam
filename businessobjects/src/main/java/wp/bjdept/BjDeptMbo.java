package wp.bjdept;

import psdi.mbo.*;
import psdi.server.MXServer;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  BjDeptMbo.java
 * §File Path: wp.bjdept.BjDeptMbo
 * §Descrption:部门管理Mbo
 * §Version:  V0.1
 * §Create Date:   2017/12/25
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class BjDeptMbo extends Mbo implements HierarchicalMboRemote,MboRemote {

    public BjDeptMbo(MboSet ms) throws RemoteException {
        super(ms);
    }

    public void init()throws MXException {
    }


    public boolean isTop() throws MXException, RemoteException {
        return (!(isTop()));
    }

    public boolean hasChildren() throws MXException, RemoteException {
        String where = " PARENTID=" + getInt("BJDEPTID") + "";
        MboSetRemote msr = getMboSet("$BJDEPT", "BJDEPT", where);
        boolean rtn = !(msr.isEmpty());
        msr.close();
        return rtn;
    }

    public MboRemote getParent() throws MXException, RemoteException {
        MboRemote mbortn = null;
        if (isNull("PARENTID")){
            return null;
        }
        MboSetRemote msr = getMboSet("PARENTID");
        if (!(msr.isEmpty())){
            mbortn = msr.getMbo(0);
        }
        return mbortn;
    }

    public void add()throws MXException, RemoteException{
    }

    public boolean hasParents() throws MXException, RemoteException {
        return isNull("PARENTID");
    }
}
