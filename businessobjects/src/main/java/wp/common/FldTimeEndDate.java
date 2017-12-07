package wp.common;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldTimeEndDate.java
 * §File Path: FldTimeEndDate
 * §Descrption: 時間比較
 * §Version:  V0.1
 * §Create Date:   2017/11/23
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldTimeEndDate extends MboValueAdapter {
    public FldTimeEndDate(MboValue arg0) throws MXException {
        super(arg0);
    }

    //在validate()方法中实现数据的判断功能
    @Override
    public void validate() throws MXException, RemoteException {
        //获取到当前操作的字段
        String attrName = getMboValue().getAttributeName();
        String MboName = getMboValue().getName();
        MboValue EndDate = getMboValue("NEXTTESTDATE");
        MboValue StartDate = getMboValue("TESTDATE");
        if (EndDate.isNull() || StartDate.isNull())
            return;
        Date ENDDATE = EndDate.getDate();
        Date STARTDATE = StartDate.getDate();
        if (STARTDATE.after(ENDDATE))
            throw new MXApplicationException("contract", "endDateBeforeToday");
        else
            return;
    }
}
