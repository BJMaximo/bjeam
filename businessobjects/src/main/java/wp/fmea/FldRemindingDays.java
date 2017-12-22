package wp.fmea;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import wp.util.BjDate;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldSelectAssetnum.java
 * §File Path: wp.fmea.FldRemindingDays
 * §Descrption: FMEA应用程序-提前提醒天数字段
 * §Version:  V0.1
 * §Create Date:   2017/12/21
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldRemindingDays extends MAXTableDomain {

    public FldRemindingDays(MboValue mbv) {
        super(mbv);
        setRelationship("BJFMEA", "");//数据来源表的名称
        String[] strFrom = {"TQTXDAYS"};//数据来源字段
        String[] strTo = {getMboValue().getAttributeName()};//目标表字段
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        return super.getList();
    }

    @Override
    public void validate() throws MXException, RemoteException {
    }

    @Override
    public void action() throws MXException, RemoteException {
        MboRemote thisMbo=getMboValue().getMbo();
        if(thisMbo!=null){
            int tqtxDays=thisMbo.getInt("TQTXDAYS");
            Date nextDate=thisMbo.getDate("NEXTREVIEWDATE");
            if(nextDate==null){
                throw new MXApplicationException("tqtxts","tqtxts_error",new String[]{});
            }else {
                String txtxDate = BjDate.calculationDate(nextDate, tqtxDays);
                thisMbo.setValue("TQTXDATE", txtxDate);
            }
        }
    }

}
