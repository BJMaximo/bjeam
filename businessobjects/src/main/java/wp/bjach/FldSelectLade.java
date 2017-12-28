package wp.bjach;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldSelectLade.java
 * §File Path: wp.bjach.FldSelectLade
 * §Descrption: 绩效管理-选择负责人
 * §Version:  V0.1
 * §Create Date:   2017/12/28
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldSelectLade extends MAXTableDomain {

    public FldSelectLade(MboValue mbv) {
        super(mbv);
        setRelationship("PERSON", "");//数据来源表的名称
        String[] strFrom = {"PERSONID"};//数据来源字段
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
