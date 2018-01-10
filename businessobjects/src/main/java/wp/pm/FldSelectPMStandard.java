package wp.pm;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldSelectPMStandard.java
 * §File Path: wp.pm.FldSelectPMStandard
 * §Descrption: PM计划--选择PM标准
 * §Version:  V0.1
 * §Create Date:   2018/1/9
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldSelectPMStandard extends MAXTableDomain {
    public FldSelectPMStandard(MboValue mbv) {
        super(mbv);
        setRelationship("BJPMSTD", "");//数据来源表的名称
        String[] strFrom = {"BJPMSTDNUM"};//数据来源字段
        String[] strTo = {getMboValue().getAttributeName()};//目标表字段
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote thisMbo=getMboValue().getMbo();
        setListCriteria("orgid='"+thisMbo.getString("orgid")+"' and siteid='"+thisMbo.getString("siteid")+"'");
        return super.getList();
    }

    @Override
    public void validate() throws MXException, RemoteException {
    }

    @Override
    public void action() throws MXException, RemoteException {
    }
}
