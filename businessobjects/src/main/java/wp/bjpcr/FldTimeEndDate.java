package wp.bjpcr;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;
import wp.util.BjDate;
import wp.util.BjException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldTimeEndDate.java
 * §File Path: wp.bjpcr.FldTimeEndDate
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
public class FldTimeEndDate extends MAXTableDomain {
    private static String startContainsTime = "START", endContainsTime = "END";
    private static String[] nums = {"1", "2", "3", "4", "5"};

    public FldTimeEndDate(MboValue mboValue) throws MXException {
        super(mboValue);
    }

    public void action() throws MXException, RemoteException {
        MboRemote thisMbo = getMboValue().getMbo();
        if (thisMbo != null) {
            String attrName = getMboValue().getAttributeName();
            String firstColoum = attrName.substring(0, 2);
            if (firstColoum.equals("BD") || firstColoum.equals("LD")) {
                boolean containStart = attrName.contains(startContainsTime),
                        containEnd = attrName.contains(endContainsTime);
                if (containStart || containEnd) {
                    String startTime = null, endTime = null;
                    if (containStart) {
                        startTime = attrName;
                        endTime = attrName.replace(startContainsTime, endContainsTime);
                    } else if (containEnd) {
                        startTime = attrName.replace(endContainsTime, startContainsTime);
                        endTime = attrName;
                    }
                    String replaceIndex = null;
                    if (null == replaceIndex) {
                        for (String s : nums) {
                            if (attrName.contains(s)) {
                                replaceIndex = s;
                                break;
                            }
                        }
                    }
                    if (null != replaceIndex && !"".equals(replaceIndex)) {
                        long longTimes = 0;
                        Date beginTimes = null, endTimes = null;
                        for (String s : nums) {
                            String s_temps = startTime.replace(replaceIndex, s);
                            String e_temps = endTime.replace(replaceIndex, s);
                            MboValue StartDate = getMboValue(s_temps);
                            MboValue EndDate = getMboValue(e_temps);
                            if (!EndDate.isNull() && !StartDate.isNull()) {
                                Date st = StartDate.getDate();
                                Date et = EndDate.getDate();
                                longTimes = longTimes + BjDate.getLongTims(st, et);
                            }
                            if (null == beginTimes) {
                                beginTimes = StartDate.getDate();
                            }
                            if (!EndDate.isNull()) {
                                endTimes = EndDate.getDate();
                            }
                        }
                        String column_NameLong = "TIMETOTAL";//统计 分钟
                        String column_NameString = "TIMEDUR";//统计时分秒
                        String begin_Column = "DOWNSTARTTIME";//开始时间
                        String end_Column = "DOWNENDTIME";//结束时间
                        if (firstColoum.contains("BD")) {
                            column_NameLong = "BD" + column_NameLong;
                            column_NameString = "BD" + column_NameString;
                            begin_Column="BREAK"+begin_Column;
                            end_Column="BREAK"+end_Column;
                        } else {
                            column_NameLong = "LD" + column_NameLong;
                            column_NameString = "LD" + column_NameString;
                            begin_Column="LINE"+begin_Column;
                            end_Column="LINE"+end_Column;
                        }
                        String timeHours = BjDate.getDistanceTimes(longTimes);
                        long timeMins = BjDate.getMins(longTimes);
                        this.getMboValue().getMbo().setValue(column_NameLong, timeMins);
                        this.getMboValue().getMbo().setValue(column_NameString, timeHours);
                        //开始时间，结束时间
                        this.getMboValue().getMbo().setValue(begin_Column,beginTimes );
                        this.getMboValue().getMbo().setValue(end_Column, endTimes);
                    }
                }
            }
        }
    }

    //在validate()方法中实现数据的判断功能
    @Override
    public void validate() throws MXException, RemoteException {
        //获取到当前操作的字段
        String attrName = getMboValue().getAttributeName();
        String startTime = null, endTime = null;
        boolean containStart = attrName.contains(startContainsTime), containEnd = attrName.contains(endContainsTime);
        if (containStart || containEnd) {
            if (containStart) {
                startTime = attrName;
                endTime = attrName.replace(startContainsTime, endContainsTime);
            } else if (containEnd) {
                startTime = attrName.replace(endContainsTime, startContainsTime);
                endTime = attrName;
            }
            if (null != startTime && null != endTime) {
                boolean comp = compareDate(startTime, endTime);
                if (comp) {
                    BjException.sendMsg("error", "error_1", null);
                }
            }

        }


    }

    public boolean compareDate(String beginTime, String endTime) throws RemoteException, MXException {
        MboValue StartDate = getMboValue(beginTime);
        MboValue EndDate = getMboValue(endTime);
        if (EndDate.isNull() || StartDate.isNull())
            return false;
        if (StartDate.getDate().after(EndDate.getDate()))
            return true;
        else
            return false;
    }


}
