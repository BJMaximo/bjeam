package wp.cron;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  BJPMCronTask.java
 * §File Path: wp.cron.BJPMCronTask
 * §Descrption: PM计划应用程序--cron任务
 * §Version:  V0.1
 * §Create Date:   2018/1/8
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class BJPMCronTask extends SimpleCronTask {
    public BJPMCronTask() {
    }

    public void cronAction() {
        try {
            UserInfo user = MXServer.getMXServer().getUserInfo("maxadmin");
            MboRemote personMbo = MXServer.getMXServer().getMboSet("person", user).getMbo(3);
            if(null!=personMbo){
                MboSetRemote bjPmSet=personMbo.getMboSet("$PM","PM","STATUS='活动'");
                MboSetRemote bjWorkOrdeSet=personMbo.getMboSet("$WORKORDER","WORKORDER","1=1");
                for(int i=0;i<bjPmSet.count();i++){
                    MboRemote bjpmMbo=bjPmSet.getMbo(i);
                    Date createdate=bjpmMbo.getDate("CREATEDATE");//创建日期
                    Date lastStartDate=bjpmMbo.getDate("LASTSTARTDATE");//上次开始日期
                    int tqscDay=bjpmMbo.getInt("TQSCDAY");//提前生成（天）
                    int pl=bjpmMbo.getInt("FREQUENCY");//频率
                    String plUnit=bjpmMbo.getString("FREQUNIT");//频率单位
                    if("天".equalsIgnoreCase(plUnit)){
                        boolean flagSave=isCreateWorkOrderSuccess(lastStartDate,createdate,new Date(),
                                Calendar.DAY_OF_MONTH,Calendar.DAY_OF_MONTH,pl,bjWorkOrdeSet,bjpmMbo,tqscDay);
                        if(flagSave){
                            bjWorkOrdeSet.save();
                            bjPmSet.save();
                        }
                    }else if("周".equalsIgnoreCase(plUnit)){
                        int weekDays=7;//定义一周中有7天
                        boolean flagSave=isCreateWorkOrderSuccess(lastStartDate,createdate,new Date(),
                                Calendar.DAY_OF_MONTH,Calendar.DAY_OF_MONTH,(pl*weekDays),bjWorkOrdeSet,bjpmMbo,tqscDay);
                        if(flagSave){
                            bjWorkOrdeSet.save();
                            bjPmSet.save();
                        }
                    }else if("月".equalsIgnoreCase(plUnit)){
                        boolean flagSave=isCreateWorkOrderSuccess(lastStartDate,createdate,new Date(),
                                Calendar.MONTH,Calendar.MONTH,pl,bjWorkOrdeSet,bjpmMbo,tqscDay);
                        if(flagSave){
                            bjWorkOrdeSet.save();
                            bjPmSet.save();
                        }
                    }else if("年".equalsIgnoreCase(plUnit)){
                        boolean flagSave=isCreateWorkOrderSuccess(lastStartDate,createdate,new Date(),
                                Calendar.YEAR,Calendar.YEAR,pl,bjWorkOrdeSet,bjpmMbo,tqscDay);
                        if(flagSave){
                            bjWorkOrdeSet.save();
                            bjPmSet.save();
                        }
                    }
                }
            }
        } catch (Exception var12) {
            var12.getCause();
            var12.getStackTrace();
        }
    }

    /**
     * 返回是否成功创建工单
     * @param lastStartDate 上次开始日期
     * @param createdate 创建日期
     * @param curdate 现在日期
     * @param calendarParmer1 Calendar参数1
     * @param calendarParmer2 Calendar参数2
     * @param pl 频率
     * @param bjWorkOrdeSet 工单MboSet
     * @param bjpmMbo PM计划MboSet
     * @return 是否成功创建工单
     * @throws RemoteException
     * @throws MXException
     */
    private static boolean isCreateWorkOrderSuccess(Date lastStartDate,Date createdate,Date curdate,
                                         int calendarParmer1,int calendarParmer2,int pl,MboSetRemote bjWorkOrdeSet,
                                         MboRemote bjpmMbo,int tqscDay) throws RemoteException, MXException {
        boolean isSuccess = false;
        if(null==lastStartDate){//如果上次开始日期为null，就以本次创建日期为开始日期
            boolean flag=isSameDate(createdate,curdate,calendarParmer1,calendarParmer2,pl,tqscDay);
            if(flag){
                MboRemote bjWorkOrdeMbo = bjWorkOrdeSet.add();
                setAddWorkOrderValue(bjWorkOrdeMbo,bjpmMbo);
                isSuccess=true;
            }
        }else{
            boolean flag=isSameDate(lastStartDate,curdate,calendarParmer1,calendarParmer2,pl,tqscDay);
            if(flag){
                MboRemote bjWorkOrdeMbo = bjWorkOrdeSet.add();
                setAddWorkOrderValue(bjWorkOrdeMbo,bjpmMbo);
                isSuccess=true;
            }
        }
        if(isSuccess==true){
            return true;
        }else{
            return false;
        }
    }



    /**
     * 设置自动生成的工单记录的值
     * @param bjWorkOrdeMbo
     * @param bjpmMbo
     * @throws RemoteException
     * @throws MXException
     */
    private static void setAddWorkOrderValue(MboRemote bjWorkOrdeMbo,MboRemote bjpmMbo) throws RemoteException, MXException {
        bjWorkOrdeMbo.setValue("PMUID",bjpmMbo.getInt("PMUID"),2L);//设置与工单表关联字段

        bjpmMbo.setValue("LASTSTARTDATE",new Date(),2L);//设置上次开始日期
    }

    /**
     * 返回两个日期比较的结果 true||false
     * @param startdate 开始日期
     * @param curdate 现在日期
     * @param parmar1 Calendar参数1
     * @param parmar2 Calendar参数2
     * @return 开始日期和现在日期是否相等
     */
    private static boolean isSameDate(Date startdate, Date curdate,int parmar1,int parmar2,int pl,int tqscDay) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(startdate);
        calendar.set(parmar1,calendar.get(parmar2)+pl);
        System.out.println(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-tqscDay);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String addDate=format.format(calendar.getTime());
        String date=format.format(curdate);
        return addDate.equals(date);
    }
}
