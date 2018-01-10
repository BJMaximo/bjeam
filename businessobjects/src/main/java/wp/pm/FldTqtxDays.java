package wp.pm;

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
 * §File Name:  FldTqtxDays.java
 * §File Path: wp.pm.FldTqtxDays
 * §Descrption: PM计划--提前生成（天）
 * §Version:  V0.1
 * §Create Date:   2018/1/9
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldTqtxDays extends MAXTableDomain {
    public FldTqtxDays(MboValue mbv) {
        super(mbv);
        setRelationship("PM", "");//数据来源表的名称
        String[] strFrom = {"TQSCDAY"};//数据来源字段
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
        Date nextDate;
        if(thisMbo!=null){
            int tqtxDays=thisMbo.getInt("TQSCDAY");
            Date lastStartDate=thisMbo.getDate("LASTSTARTDATE");
            if(null==lastStartDate){
                nextDate=thisMbo.getDate("CREATEDATE");
            }else{
                nextDate=lastStartDate;
            }
            String tqtxDate=BjDate.calculationDate(nextDate, tqtxDays);
            thisMbo.setValue("TQSCDATE", tqtxDate);
        }
    }


}
