package wp.common;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldPerson.java
 * §File Path: common.FldPerson
 * §Descrption: 根据当前登录人员的编号获取其所的部门
 * §Version:  V0.1
 * §Create Date:   2017/11/21
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */


import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

public class FldDepmentByLoginAccont extends MAXTableDomain   {
    public FldDepmentByLoginAccont(MboValue mbv) throws MXException, RemoteException {
        super(mbv);
        MboRemote mbo = getMboValue().getMbo();
        String personid = mbo.getUserInfo().getPersonId();//获取到当前用户登录的用户信息
        //获取用户当前登录的部门ID
  //      mbo.getBeanForApp().getMbo().getUserInfo().getPersonId();
        String thisAttr = this.getMboValue().getAttributeName();
        this.setRelationship("bjdepartment", "departmentnum=:" + thisAttr);
        this.setListCriteria("status in (select value from synonymdomain where maxvalue='ACTIVE' and domainid='PERSONSTATUS')");
        this.setErrorMessage("person", "InvalidPerson");
        setLookupKeyMapInOrder(new String[] { personid}, new String[] { "COMPANY" });
    }
}
