 package wp.app.dfrc;
 
import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.tamit.app.asset.TAMITFldAssetClassStructureid;
import psdi.util.MXException;
 
 public class HXFldAssetNum extends TAMITFldAssetClassStructureid
 {
   public HXFldAssetNum(MboValue mbv)
     throws MXException, RemoteException
   {
     super(mbv);
   }
 
   public void action()
     throws MXException, RemoteException
   {
     if (getMboValue().isNull()) {
       super.action();
       return;
     }
 
     String assetNum = null;
     if (getMboValue().getMbo().getMboSet("CLASSSTRUCTURE") != null)
     {
       String classif = getMboValue().getMbo().getString("CLASSSTRUCTURE.CLASSIFICATIONID");
       if ((classif == null) || ("".equals(classif.trim()))) {
         super.action();
         return;
       }
 
       assetNum = classif + getRunningNumber();
       getMboValue().getMbo().setValue("ASSETNUM", assetNum, 11L);
     }
 
     super.action();
   }
 
   public void validate() throws MXException, RemoteException
   {
     super.validate();
   }
 
   public void init() throws MXException, RemoteException {
     super.init();
   }
 
   public void initValue() throws MXException, RemoteException
   {
     super.initValue();
   }
 
   private String getRunningNumber()
     throws RemoteException, MXException
   {
     String assetuid = String.valueOf(getMboValue("ASSETUID").getInt());
     int count = 6 - assetuid.length();
     String zero = "";
 
     for (int i = 0; i < count; i++) {
       zero = zero + "0";
     }
     return zero + assetuid;
   }
 }