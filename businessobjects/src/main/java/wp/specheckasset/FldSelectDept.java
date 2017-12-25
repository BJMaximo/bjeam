package wp.specheckasset;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldSelectDept.java
 * §File Path: wp.specheckasset.FldSelectDept
 * §Descrption: 检具量具应用程序-选择部门
 * §Version:  V0.1
 * §Create Date:   2017/12/25
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldSelectDept extends MAXTableDomain {

    public FldSelectDept(MboValue mbv) {
        super(mbv);
        setRelationship("BJDEPT", "");//数据来源表的名称
        String[] strFrom = {"BJDEPTNUM"};//数据来源字段
        String[] strTo = {getMboValue().getAttributeName()};//目标表字段
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote thisMbo=getMboValue().getMbo();
        setListCriteria("orgid='"+thisMbo.getString("orgid")+"'");
        return super.getList();
    }

    @Override
    public void validate() throws MXException, RemoteException {
    }

    @Override
    public void action() throws MXException, RemoteException {

    }
}
