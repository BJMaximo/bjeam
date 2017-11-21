package app;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;

/**
 * ╔════════════════════════════════╗
 * §File Name:  FLDSelect.java
 * §File Path: com.wp.bean.FLDSelect
 * §Descrption:
 * §Version:  V0.1
 * §Create Date:   2017/11/17
 * §IDE:    IntelliJ IDEA.2017
 * §Font Code:  UTF-8
 * §JDK :1.8
 * §Author: Ocean_Hy
 * §History Version Note:
 * ╚════════════════════════════════╝
 */
public class FLDSelect<T ,K > extends MAXTableDomain {
    public FLDSelect(MboValue mbv) {
        super(mbv);
        Relation();
    }
    public   void  Relation(){

        String objectName= "";
        String whereClause="";
        setRelationship(objectName,whereClause);
        setLookupKeyMapInOrder(new String[]{ "CREATEUSERID"}, new String[] { "PERSONID" });
    }
}
