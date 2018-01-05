package wp.bjtrain;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldSelectCurrNum.java
 * §File Path: wp.bjtrain.FldSelectCurrNum
 * §Descrption:
 * §Version:  V0.1
 * §Create Date:   2018/1/4
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldSelectCurrNum extends MAXTableDomain {
    private static String[] copyCloum = new String[]{"LEAD", "BJDEPTNUM", "TRAFORM", "TRANUMBER", "COUHOURS", "EXPBUDGET", "COUCATEGORY"};

    public FldSelectCurrNum(MboValue mbv) {
        super(mbv);
        setRelationship("BJTRAIN", "");//数据来源表的名称
        String[] strFrom = {"CURRNUM"};//数据来源字段
        String[] strTo = {getMboValue().getAttributeName()};//目标表字段
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public void action() throws MXException, RemoteException {
        MboValue mboValue = this.getMboValue();
        String thisAttr = mboValue.getAttributeName();
        MboRemote mbo = mboValue.getMbo();
        if (null != mbo) {
            String appName = mbo.getThisMboSet().getApp();
            if ("BJRTAIN".equals(appName.toUpperCase())) {
                String currNums = mbo.getString(thisAttr);
                MboSetRemote mboSetRemote = mbo.getMboSet("_CurrNum", "BJTRAIN", " CURRNUM='" + currNums + "'" +
                        " and APPNAME='CURRICUL' and orgid='" + mbo.getString("orgid") + "'");
                for (String s : copyCloum) {
                    mbo.setValue(s, mboSetRemote.getMbo(0).getString(s));
                }
              /*  String lead = mboSetRemote.getMbo(0).getString("LEAD");
                String lead = mboSetRemote.getMbo(0).getString("BJDEPTNUM");
                String lead = mboSetRemote.getMbo(0).getString("TRAFORM");
                String lead = mboSetRemote.getMbo(0).getString("TRANUMBER");
                String lead = mboSetRemote.getMbo(0).getString("COUHOURS");
                String lead = mboSetRemote.getMbo(0).getString("EXPBUDGET");//ACTCOST
                String lead = mboSetRemote.getMbo(0).getString("COUCATEGORY");//TRAPROJECT*/
            }
        }
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        MboRemote mbo = getMboValue().getMbo();
        String thisAttr = this.getMboValue().getAttributeName();
        this.setRelationship("BJTRAIN", "CURRNUM=:" + thisAttr);
        setListCriteria("APPNAME='CURRICUL' and orgid='" + mbo.getString("orgid") + "'");
        return super.getList();
    }
}
