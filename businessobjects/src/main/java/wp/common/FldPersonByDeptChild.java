package wp.common;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldPersonByDeptChild.java
 * §File Path: wp.common.FldPersonByDeptChild
 * §Descrption:查找本部门的所有成员
 * §Version:  V0.1
 * §Create Date:   2017/12/11
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldPersonByDeptChild extends MAXTableDomain {

    public FldPersonByDeptChild(MboValue mbv) throws RemoteException, MXException {
        super(mbv);
        MboRemote mbo = getMboValue().getMbo();
        String personId = mbo.getUserInfo().getPersonId();//获取用户的编号
        String thisAttr = this.getMboValue().getAttributeName();//获取当前操作的表名
        String orgId = this.getMboValue("ORGID").getCurrentValue().asString();
        StringBuffer listWhereSql = new StringBuffer();//条件语句组装
        listWhereSql.append(" status in (select value from synonymdomain where maxvalue='ACTIVE' and domainid='PERSONSTATUS') ");
        listWhereSql.append(" AND  BJDEPTNUM IN( ");
        listWhereSql.append(" SELECT BJDEPTNUM FROM BJDEPT M start WITH m.Bjdeptnum= ");
        listWhereSql.append("(SELECT Bjdeptnum FROM BJDEPT M  WHERE M.PARENT IS NULL AND M.ORGID='%s'");
        listWhereSql.append(" START WITH M.BJDEPTNUM =(SELECT BJDEPTNUM FROM PERSON WHERE PERSONID = '%s')   ");
        listWhereSql.append("  CONNECT BY NOCYCLE PRIOR M.PARENT = M.BJDEPTNUM )  connect by m.parent=prior m.Bjdeptnum ) ");
        String where = String.format(listWhereSql.toString(),orgId, personId);
        this.setRelationship("PERSON", "personid=:" + thisAttr);
        this.setListCriteria(where);
        String[] persionIds = {"personid"};//设置数据库中对象的列
        String[] attr = {thisAttr};////设置要填充的界面对象上的列,该列要与数据库表列一一对应;
        setLookupKeyMapInOrder(attr, persionIds);//设置对应关系;
    }
}
