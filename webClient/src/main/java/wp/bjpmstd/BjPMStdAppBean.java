package wp.bjpmstd;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * ╔════════════════════════════════╗
 * §File Name:  BjPMStdAppBean.java
 * §File Path: wp.bjpmstd.BjPMStdAppBean
 * §Descrption: PM标准AppBean
 * §Version:  V0.1
 * §Create Date:   2018/1/5
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.6
 * §Author: lupe
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class BjPMStdAppBean extends AppBean {

    @Override
    public int INSERT() throws MXException, RemoteException {
        return super.INSERT();
    }

    public int SAVE() throws MXException, RemoteException {
        MboRemote appMbo=app.getAppBean().getMbo();
        if(null!=appMbo){
            String lastmodifier="(";
            if(!appMbo.isNew()){
                String userid = app.getBeanForApp().getMbo().getUserInfo().getPersonId();
                MboRemote personMbo = appMbo.getMboSet("$PERSON", "PERSON", "PERSONID='"+userid+"'").getMbo(0);
                if(null != personMbo) {
                    lastmodifier+=userid+")"+personMbo.getString("DISPLAYNAME");
                }
                appMbo.setValue("LASTMODIFIER",lastmodifier);
                appMbo.setValue("LASTMODIFTIME",new Date());
            }
        }
        app.getAppBean().refreshTable();
        return super.SAVE();
    }
}
