package wp.emeplan;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldSelectClass.java
 * §File Path: wp.emeplan.FldEmeplanSelectClass
 * §Descrption: 应急预案应用程序-选择分类
 * §Version:  V0.1
 * §Create Date:   2017/12/22
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldEmeplanSelectClass extends MAXTableDomain {

    public FldEmeplanSelectClass(MboValue mbv) {
        super(mbv);
        setRelationship("CLASSSTRUCTURE", "");//数据来源表的名称
        String[] strFrom = {"CLASSIFICATIONID"};//数据来源字段
        String[] strTo = {getMboValue().getAttributeName()};//目标表字段
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote thisMbo=getMboValue().getMbo();
        setListCriteria("siteid='"+thisMbo.getString("siteid")+"'");
        return super.getList();
    }

    @Override
    public void validate() throws MXException, RemoteException {
    }

    @Override
    public void action() throws MXException, RemoteException {
    }
}
