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

public class FldDepment extends MAXTableDomain {
    public FldDepment(MboValue mbv) throws MXException, RemoteException {
        super(mbv);
        MboRemote mbo = getMboValue().getMbo();
        String thisAttr = this.getMboValue().getAttributeName();//获取当前操作的表名
        this.setRelationship("BJDEPT", " BJDEPTNUM=:" + thisAttr);
         String[] deptNum = {"BJDEPTNUM"};////设置数据库中对象的列
        String[] attr = {thisAttr};////设置要填充的界面对象上的列,该列要与数据库表列一一对应;
        setLookupKeyMapInOrder(attr, deptNum);//设置对应关系;
    }
}
