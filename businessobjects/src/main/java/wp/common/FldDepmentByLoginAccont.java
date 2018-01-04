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
import psdi.util.MaxType;

import java.rmi.RemoteException;

public class FldDepmentByLoginAccont extends MAXTableDomain {
    public FldDepmentByLoginAccont(MboValue mbv) throws MXException, RemoteException {
        super(mbv);
        MboRemote mbo = getMboValue().getMbo();
        String orgId = this.getMboValue("ORGID").getCurrentValue().asString();
        String personId = mbo.getUserInfo().getPersonId();//获取用户的编号
        String thisAttr = this.getMboValue().getAttributeName();//获取当前操作的表名
        this.setRelationship("BJDEPT", " BJDEPTNUM=:" + thisAttr);
        StringBuffer listWhereSql = new StringBuffer();//条件语句组装
        listWhereSql.append(" bjdeptid IN( ");
        listWhereSql.append(" SELECT bjdeptid FROM BJDEPT M start WITH m.Bjdeptnum= ");
        listWhereSql.append("(SELECT Bjdeptnum FROM BJDEPT M  WHERE M.Parentid IS NULL AND M.ORGID='%s'");
        listWhereSql.append(" START WITH M.BJDEPTNUM =(SELECT BJDEPTNUM FROM PERSON WHERE PERSONID = '%s')   ");
        listWhereSql.append("  CONNECT BY NOCYCLE PRIOR M.Parentid = M.Bjdeptid )  connect by m.parentid=prior m.Bjdeptid ) ");
         String where = String.format(listWhereSql.toString(), orgId, personId);
        this.setRelationship("BJDEPT", where);
        this.setListCriteria(where);//设置过虑条件
        String[] deptNum = {"BJDEPTNUM"};////设置数据库中对象的列
        String[] attr = {thisAttr};////设置要填充的界面对象上的列,该列要与数据库表列一一对应;
        setLookupKeyMapInOrder(attr, deptNum);//设置对应关系;

    }
}
