package wp.bjskillzc;

import psdi.mbo.MboRemote;
import psdi.mbo.*;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.WebClientEvent;

import java.rmi.RemoteException;

/**
 * ╔════════════════════════════════╗
 * §File Name:  BjAttAppBean.java
 * §File Path: wp.bjskillzc.BjSkillZcAppBean
 * §Descrption:技改管理(整车）应用程序AppBean
 * §Version:  V0.1
 * §Create Date:   2018/1/3
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class BjSkillZcAppBean extends AppBean {

    @Override
    public int INSERT() throws MXException, RemoteException {
        int insert = super.INSERT();
        MboRemote appMbo = app.getAppBean().getMbo();
        appMbo.setValue("APPNAME", "BJSKILLZC");
        app.getAppBean().refreshTable();
        return insert;
    }

    public int SAVE() throws MXException, RemoteException {
        MboRemote appMbo=app.getAppBean().getMbo();
        String translate=appMbo.getString("BJTRANSLATE");
        if("现场施工".equalsIgnoreCase(translate) || "项目验收".equalsIgnoreCase(translate)){
            appMbo.setFieldFlag("BJVENDORNUM",128L,true);
        }else if("取消暂停".equalsIgnoreCase(translate)){
            setFieldOnlyReadOrOther(appMbo,7L,true);//设置界面所有字段为只读
        }else{
            setFieldOnlyReadOrOther(appMbo,7L,false);//设置界面所有字段不为只读
            appMbo.setFieldFlag("BJVENDORNUM",128L,false);
        }
        return 1;
    }

    /**
     * 为当前界面所有字段设置状态
     * @param mbo mbo
     * @param statusCode 状态代码 只读为7L，必填为128L
     * @param flag true或false
     * @throws RemoteException
     */
    public void setFieldOnlyReadOrOther(MboRemote mbo,long statusCode,boolean flag) throws RemoteException {
        mbo.setFieldFlag("PROJECTTYPE",statusCode,flag);//项目类别
        mbo.setFieldFlag("AMOUNT",statusCode,flag);//项目总投资
        mbo.setFieldFlag("SERVICENAME",statusCode,flag);//项目名称
        mbo.setFieldFlag("SERVICECONTENT",statusCode,flag);//具体内容
        mbo.setFieldFlag("DESRANGE",statusCode,flag);//立项原因
        mbo.setFieldFlag("LEAD",statusCode,flag);//负责人
        mbo.setFieldFlag("BJDEPTNUM",statusCode,flag);//部门
        mbo.setFieldFlag("ATTRIBUTE",statusCode,flag);//属性
        mbo.setFieldFlag("PROGRESS",statusCode,flag);//进度%
        mbo.setFieldFlag("REGION",statusCode,flag);//区域
        mbo.setFieldFlag("LOCATION",statusCode,flag);//实施区域
        mbo.setFieldFlag("BJVENDORNUM",statusCode,flag);//定点供应商
        mbo.setFieldFlag("STARTTIME",statusCode,flag);//开始时间
        mbo.setFieldFlag("ENDTIME",statusCode,flag);//结束时间
        mbo.setFieldFlag("REMARKS",statusCode,flag);//备注

    }

}
