package wp.common;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldPersonByDept.java
 * §File Path: wp.common.FldPersonByDept
 * §Descrption: 只能选择本部门人员 直属本部门的人员
 * §Version:  V0.1
 * §Create Date:   2017/12/11
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldPersonByDept extends MAXTableDomain {
    public FldPersonByDept(MboValue mbv)  throws MXException, RemoteException {
        super(mbv);
        MboRemote mbo = getMboValue().getMbo();
        String personId = mbo.getUserInfo().getPersonId();//获取用户的编号
        String thisAttr = this.getMboValue().getAttributeName();//获取当前操作的表名
         StringBuffer listWhereSql = new StringBuffer();//条件语句组装
        listWhereSql.append(" status in (select value from synonymdomain where maxvalue='ACTIVE' and domainid='PERSONSTATUS') ");
        listWhereSql.append(" AND BJDEPTNUM  IN (SELECT BJDEPTNUM FROM PERSON WHERE PERSONID = '%s') ");
        String where = String.format(listWhereSql.toString(),personId);
        this.setRelationship("PERSON", "personid=:" + thisAttr);
        this.setListCriteria(where);
        String[] persionIds = {"personid"};//设置数据库中对象的列
        String[] attr = {thisAttr};////设置要填充的界面对象上的列,该列要与数据库表列一一对应;
        setLookupKeyMapInOrder(attr, persionIds);//设置对应关系;
    }
}
