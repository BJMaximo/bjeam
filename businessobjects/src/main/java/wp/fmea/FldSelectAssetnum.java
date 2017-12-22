package wp.fmea;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldSelectAssetnum.java
 * §File Path: wp.fmea.FldSelectAssetnum
 * §Descrption: FMEA应用程序-选择设备编码
 * §Version:  V0.1
 * §Create Date:   2017/12/21
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldSelectAssetnum extends MAXTableDomain {

    public FldSelectAssetnum(MboValue mbv) {
        super(mbv);
        setRelationship("ASSET", "");//数据来源表的名称
        String[] strFrom = {"ASSETNUM"};//数据来源字段
        String[] strTo = {getMboValue().getAttributeName()};//目标表字段
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote thisMbo=getMboValue().getMbo();
        System.out.println("xxxxxxxxxxxxxxx");
        setListCriteria("orgid='"+thisMbo.getString("orgid")+"' and siteid='"+thisMbo.getString("siteid")+"'");
        return super.getList();
    }

    @Override
    public void validate() throws MXException, RemoteException {
    }

    @Override
    public void action() throws MXException, RemoteException {
    }
}
