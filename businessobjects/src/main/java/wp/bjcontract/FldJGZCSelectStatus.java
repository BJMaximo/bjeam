package wp.bjcontract;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldJGZCSelectStatus.java
 * §File Path: wp.bjcontract.FldJGZCSelectStatus
 * §Descrption:技改管理(整车）应用程序--选择状态
 * §Version:  V0.1
 * §Create Date:   2018/1/3
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldJGZCSelectStatus extends MAXTableDomain {
    public FldJGZCSelectStatus(MboValue mbv) {
        super(mbv);
        setRelationship("BJCONTRACT", "");//数据来源表的名称
        String[] strFrom = {"JGSTATUS"};//数据来源字段
        String[] strTo = {getMboValue().getAttributeName()};//目标表字段
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote thisMbo=getMboValue().getMbo();
//        setListCriteria("orgid='"+thisMbo.getString("ORGID")+"'");
        return super.getList();
    }

    @Override
    public void validate() throws MXException, RemoteException {
    }

    @Override
    public void action() throws MXException, RemoteException {
    }
}
