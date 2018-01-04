package wp.common;

import psdi.mbo.*;
import psdi.server.MXServer;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Vector;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldPersonByOrg.java
 * §File Path: wp.common.FldPersonByOrg
 * §Descrption: 选择本组织下的所有人员
 * §Version:  V0.1
 * §Create Date:   2017/12/11
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldPersonByOrg extends MAXTableDomain {
    HashMap<String, String[][]> cachedKeyMapHash = new HashMap(2);
    String listWhere = null;
    String multiKeyWhereForLookup = null;
    String objectName = null;

    public FldPersonByOrg(MboValue mbv) throws MXException, RemoteException {
        super(mbv);
        MboRemote mbo = getMboValue().getMbo();
        String thisAttr = this.getMboValue().getAttributeName();//获取当前操作的表字段
        StringBuffer listWhereSql = new StringBuffer();//条件语句组装
        String where = "";
        listWhereSql.append(" status in (select value from synonymdomain where maxvalue='ACTIVE' and domainid='PERSONSTATUS') ");
        where = listWhereSql.toString();
        MboValue mboValue = this.getMboValue();
        if (!mboValue.isNull()) {
            String orgId = mboValue.getString();
            String siteorg = this.getMboSet().getMboSetInfo().getSiteOrgTypeAsString();
            listWhereSql.append(" AND BJDEPTNUM  IN (SELECT BJDEPTNUM FROM bjdept WHERE orgid = '%s' ) ");
            where += String.format(listWhereSql.toString(), orgId);
        }

        this.setRelationship("PERSON", "personid=:" + thisAttr);
        this.setListCriteria(where);
        String[] persionIds = {"personid"};//设置数据库中对象的列
        String[] attr = {thisAttr};////设置要填充的界面对象上的列,该列要与数据库表列一一对应;
        setLookupKeyMapInOrder(attr, persionIds);//设置对应关系;
         System.out.println("============");
    }

}
