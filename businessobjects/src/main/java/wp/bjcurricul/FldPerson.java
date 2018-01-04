package wp.bjcurricul;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldPerson.java
 * §File Path: wp.bjcurricul.FldPerson
 * §Descrption: 课程中心负责人字段事件
 * §Version:  V0.1
 * §Create Date:   2018/1/4
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldPerson extends MAXTableDomain {
    public FldPerson(MboValue mbv) {
        super(mbv);
        setRelationship("PERSON", "");//数据来源表的名称
        String[] strFrom = {"PERSONID"};//数据来源字段
        String[] strTo = {getMboValue().getAttributeName()};//目标表字段
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote mbo = getMboValue().getMbo();
        String personid = mbo.getUserInfo().getPersonId();//获取到当前用户登录的用户信息
        //获取用户当前登录的部门ID
        //   app.getBeanForApp().getMbo().getUserInfo().getPersonId();
        String thisAttr = this.getMboValue().getAttributeName();
        this.setRelationship("PERSON", "personid=:" + thisAttr);
        this.setListCriteria("status in (select value from synonymdomain where maxvalue='ACTIVE' and domainid='PERSONSTATUS')");
        return super.getList();
    }

    @Override
    public void action() throws MXException, RemoteException {
        String thisAttr = this.getMboValue().getAttributeName();
        MboRemote mbo = getMboValue().getMbo();
        if (null != mbo) {
            if ("LEAD".equals(thisAttr)) {
                String lead = mbo.getString(thisAttr);
                MboSetRemote mboSetRemote = mbo.getMboSet("_Dept", "PERSON", " PERSONID='" + lead + "'");
                String bjDeptNum = mboSetRemote.getMbo(0).getString("BJDEPTNUM");
                mbo.setValue("BJDEPTNUM", bjDeptNum);
                mbo.setValue("PLADEPARTMENT", bjDeptNum);
            }
        }
    }
}
