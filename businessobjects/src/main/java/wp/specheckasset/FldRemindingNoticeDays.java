package wp.specheckasset;

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
 * §File Name:  FldRemindingNoticeDays.java
 * §File Path: wp.specheckasset.FldRemindingNoticeDays
 * §Descrption: 特种设备应用程序&检具量具-提前通知
 * §Version:  V0.1
 * §Create Date:   2017/12/22
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldRemindingNoticeDays extends MAXTableDomain {
    public FldRemindingNoticeDays(MboValue mbv) {
        super(mbv);
        setRelationship("BJSPECHECKASSET", "");//数据来源表的名称
        String[] strFrom = {"TQNOTICE"};//数据来源字段
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
            int tqtxDays=thisMbo.getInt("TQNOTICE");
            Date nextDate=thisMbo.getDate("NEXTTESTDATE");
            String appName=thisMbo.getThisMboSet().getApp();
            if("BJSPEASSET".equalsIgnoreCase(appName)){//特种设备应用程序
                String tqtxDate=tqtxDateBJSPEASSET(nextDate,tqtxDays);
                thisMbo.setValue("ADVNOTDATE",tqtxDate);
            }
            if("BJTOOLS".equalsIgnoreCase(appName)){//检具量具应用程序
                String tqtxDate=tqtxDateBJTOOLS(nextDate,tqtxDays);
                thisMbo.setValue("ADVNOTDATE",tqtxDate);
            }
        }
    }

    /**
     * 特种设备应用程序提前提醒
     * @param nextDate
     * @param tqtxDays
     * @return
     * @throws MXApplicationException
     */
    public String tqtxDateBJSPEASSET(Date nextDate,int tqtxDays) throws MXApplicationException {
        if(nextDate==null){
            throw new MXApplicationException("tqtxrq_tzsb","tqtxts_error",new String[]{});
        }else {
            return BjDate.calculationDate(nextDate, tqtxDays);
        }
    }

    /**
     * 检具量具应用程序提前提醒
     * @param nextDate
     * @param tqtxDays
     * @return
     * @throws MXApplicationException
     */
    public String tqtxDateBJTOOLS(Date nextDate,int tqtxDays) throws MXApplicationException {
        if(nextDate==null){
            throw new MXApplicationException("tqtxrq_jjlj","tqtxts_error",new String[]{});
        }else {
            return BjDate.calculationDate(nextDate, tqtxDays);
        }
    }
}
