package wp.common;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.server.MXServer;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FldPersonId.java
 * §File Path: FldPersonId
 * §Descrption:
 * §Version:  V0.1
 * §Create Date:   2017/11/27
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FldPersonId extends MAXTableDomain {
    public FldPersonId(MboValue mbv) throws RemoteException {
        super(mbv);
        MboRemote mbo = getMboValue().getMbo();
        String personid = mbo.getUserInfo().getPersonId();//获取到当前用户登录的用户信息
        //获取用户当前登录的部门ID
        //   app.getBeanForApp().getMbo().getUserInfo().getPersonId();
        String thisAttr = this.getMboValue().getAttributeName();
        this.setRelationship("PERSON", "personid=:" + thisAttr);
        this.setListCriteria("status in (select value from synonymdomain where maxvalue='ACTIVE' and domainid='PERSONSTATUS')");
        this.setErrorMessage("person", "InvalidPerson");
    }

    @Override
    public void action() throws MXException, RemoteException {
        Connection con =
                MXServer.getMXServer().getDBManager().getSequenceConnection();
        Statement stat = null;
        ResultSet rs = null;
        String thisAttr = this.getMboValue().getAttributeName();
        MboRemote mbo = getMboValue().getMbo();
        if (null != mbo) {
            if ("LEAD".equals(thisAttr)) {
                String lead = mbo.getString(thisAttr);
                if (null != lead && !"".equals(lead) && !"null".equals(lead)) {
                    System.out.println("lead:" + lead);
                    String sql = "SELECT g.bjdeptnum,dept.description FROM person g  LEFT JOIN bjdept dept ON dept.bjdeptnum=g.bjdeptnum  WHERE g.PERSONID='" + lead + "'";
                    // Vector v= mbo.getMboDataSet("BJDEPTNUM");
                    try {
                        stat = con.createStatement();
                        rs = stat.executeQuery(sql);
                        // int numCount = rs.getMetaData().getColumnCount();
                        if (rs.next()) {
                            String bjDeptNum = rs.getString(1);
                            String description = rs.getString(2);
                            System.out.println("bjDeptNum:" + bjDeptNum);
                            mbo.setValue("BJDEPTNUM", bjDeptNum);
                            mbo.setValue("PLADEPARTMENT", bjDeptNum);
                            // mbo.setValue("BJDEPT.DESCRIPTION", description);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
