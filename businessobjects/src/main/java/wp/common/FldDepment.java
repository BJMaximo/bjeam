package wp.common;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldPerson.java
 * §File Path: FldDepment
 * §Descrption: 搜索全局部门
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

public class FldDepment extends MAXTableDomain   {
    public FldDepment(MboValue mbv) throws MXException, RemoteException {
        super(mbv);
        MboRemote mbo = getMboValue().getMbo();
        String personid = mbo.getUserInfo().getPersonId();//获取到当前用户登录的用户信息
        //获取用户当前登录的部门ID
     //   app.getBeanForApp().getMbo().getUserInfo().getPersonId();
        String thisAttr = this.getMboValue().getAttributeName();
        this.setRelationship("bjdepartment", "departmentnum=:" + thisAttr);
        this.setListCriteria("status in (select value from synonymdomain where maxvalue='ACTIVE' and domainid='PERSONSTATUS')");
        this.setErrorMessage("person", "InvalidPerson");
        setLookupKeyMapInOrder(new String[] { personid}, new String[] { "COMPANY" });
    }
}
