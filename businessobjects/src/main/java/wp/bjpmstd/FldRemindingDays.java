package wp.bjpmstd;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import wp.util.BjDate;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldRemindingDays.java
 * §File Path: wp.bjpmstd.FldRemindingDays
 * §Descrption: PM标准--提前提醒天
 * §Version:  V0.1
 * §Create Date:   2018/1/5
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
        setRelationship("BJPMSTD", "");//数据来源表的名称
        String[] strFrom = {"EARLYWARNDAY"};//数据来源字段
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
            int tqtxDays=thisMbo.getInt("EARLYWARNDAY");
            Date nextDate=thisMbo.getDate("NEXTREVIEWTIME");
            if(nextDate==null){
                throw new MXApplicationException("bjpmstd","bjpmstd_tqtx",new String[]{});
            }else {
                String tqtxDate=BjDate.calculationDate(nextDate, tqtxDays);
                thisMbo.setValue("EARLYWARNTIME", tqtxDate);
            }
        }
    }
}
