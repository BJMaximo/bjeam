package wp.bjpmstd;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldSelectLocation.java
 * §File Path: wp.bjpmstd.FldSelectLocation
 * §Descrption: PM标准应用程序--选择维护区域
 * §Version:  V0.1
 * §Create Date:   2018/1/4
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldSelectLocation extends MAXTableDomain {
    public FldSelectLocation(MboValue mbv) {
        super(mbv);
        setRelationship("LOCATIONS", "");//数据来源表的名称
        String[] strFrom = {"LOCATION"};//数据来源字段
        String[] strTo = {getMboValue().getAttributeName()};//目标表字段
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote thisMbo=getMboValue().getMbo();
//        setListCriteria("orgid='"+thisMbo.getString("orgid")+"'");
        return super.getList();
    }

    @Override
    public void validate() throws MXException, RemoteException {
    }

    @Override
    public void action() throws MXException, RemoteException {
    }
}
