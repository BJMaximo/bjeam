package wp.bjdept;

import psdi.mbo.*;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  BjDeptMboSet.java
 * §File Path: wp.bjdept.BjDeptMboSet
 * §Descrption: 部门管理MboSet
 * §Version:  V0.1
 * §Create Date:   2017/12/25
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class BjDeptMboSet extends HierarchicalMboSet implements HierarchicalMboSetRemote,MboSetRemote {

    public BjDeptMboSet(MboServerInterface ms) throws RemoteException {
        super(ms);
    }

    protected Mbo getMboInstance(MboSet arg0)throws MXException, RemoteException{
        return new BjDeptMbo(arg0);
    }

    public MboValueData[] getParent(String s, String s2, String[] as) throws MXException, RemoteException {
        return null;
    }

    public MboValueData[][] getChildren(String s, String s1, String[] as, int i) throws MXException, RemoteException {
        reset();
        resetQbe();
        setWhere(" PARENT ='" + s1 + "'");
        setOrderBy(" orderby ");
        reset();
        if (!(isEmpty()))
            return getMboValueData(0, i + 2, as);

        return null;

    }



    public MboValueData[][] getSiblings(String s, String s1, String[] strings, int i) throws MXException, RemoteException {
        return null;
    }

    public MboValueData[][] getTop(String[] as, int i) throws MXException, RemoteException {
        reset();
        resetQbe();
        setWhere(" parent is null");
        setOrderBy(" orderby");
        reset();
        if (isEmpty()){
            return null;
        }else{
            return getMboValueData(0,  i+1, as);
        }

    }

    public MboValueData[][] getPathToTop(String s, String s1, String[] strings, int i) throws MXException, RemoteException {
        return new MboValueData[0][];
    }


}
