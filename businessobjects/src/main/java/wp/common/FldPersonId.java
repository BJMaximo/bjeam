package wp.common;

import psdi.app.system.MaxDomain;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldPersonId.java
 * §File Path: FldPersonId
 * §Descrption:
 * §Version:  V0.1
 * §Create Date:   2017/11/27
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldPersonId extends MAXTableDomain {

    public FldPersonId(MboValue mbv) throws RemoteException {
        super(mbv);
        MboRemote mbo = getMboValue().getMbo();
        String personid = mbo.getUserInfo().getPersonId();//获取到当前用户登录的用户信息
        //获取用户当前登录的部门ID
        //   app.getBeanForApp().getMbo().getUserInfo().getPersonId();
        String thisAttr = this.getMboValue().getAttributeName();
        this.setRelationship("PERSON", "personid=:" + thisAttr);
        this.setListCriteria("status in (select value from synonymdomain where maxvalue='ACTIVE' and domainid='PERSONSTATUS')");
        this.setErrorMessage("person", "InvalidPerson");
    }
}
