/*     */ package wp.app.dfrc;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.rmi.RemoteException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import psdi.mbo.Mbo;
/*     */ import psdi.mbo.MboRemote;
/*     */ import psdi.mbo.MboSet;
/*     */ import psdi.mbo.MboSetRemote;
/*     */ import psdi.mbo.MboValue;
/*     */ import psdi.server.MXServer;
/*     */ import psdi.util.MXException;
/*     */ 
/*     */ public class YhglMbo extends Mbo
/*     */   implements MboRemote
/*     */ {
/*     */   public YhglMbo(MboSet ms)
/*     */     throws RemoteException
/*     */   {
/*  16 */     super(ms);
/*     */   }
/*     */ 
/*     */   public void modify() throws MXException, RemoteException {
/*  20 */     super.modify();
/*     */ 
/*  22 */     double unitprice = getMboValue("UNITPRICE").getDouble();
/*  23 */     System.out.println("商品单价为：" + unitprice);
/*     */ 
/*  26 */     int zjkc = getMboValue("ZJKC").getInt();
/*  27 */     setValue("TOTALPRICE", unitprice * zjkc);
/*  28 */     System.out.println("商品总价为：" + unitprice * zjkc);
/*     */   }
/*     */ 
/*     */   protected void save() throws MXException, RemoteException {
/*  32 */     if ((getMboValue("ZJKC").isModified()) || 
/*  33 */       (getMboValue("ZJKC").isModified())) {
/*  34 */       double modfCoun = getDouble("ZJKC");
				System.out.println("modfCoun"+modfCoun);
				
/*  35 */       double totalCount = getDouble("CCL");
				System.out.println("totalCount"+totalCount);
/*  36 */       setValue("CCL", totalCount + modfCoun);
/*  37 */       super.save();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void init() throws MXException {
/*  42 */     super.init();
/*     */     try {
/*  44 */       String[] readonlyForAppr = { "UNITPRICE", "ZJKC", "YHTYPE" };
/*     */ 
/*  46 */       String unitprice = getString("UNITPRICE");
/*  47 */       System.out.println("商品单价：" + unitprice);
/*     */ 
/*  49 */       String zjkc = getString("ZJKC");
/*  50 */       System.out.println("购买数量：" + zjkc);
/*     */ 
/*  53 */       if ((unitprice != "") && (zjkc != "")) {
/*  54 */         setFieldFlag(readonlyForAppr, 7L, true);
/*  55 */         System.out.println("判断完成；此时 商品价格和购买数量 为只读！");
/*     */       } else {
/*  57 */         setFieldFlag(readonlyForAppr, 7L, false);
/*     */       }
/*     */     } catch (RemoteException e) {
/*  60 */       e.getMessage();
/*     */     }
/*     */   }
/*     */ 
/*     */   public MboRemote duplicate() throws MXException, RemoteException {
/*  65 */     System.out.println("开始执行 duplicate 操作。 ");
/*  66 */     MboRemote newmbo = copy();
/*  67 */     newmbo.setValue("YHNUM", numberGeneration());
/*  68 */     return newmbo;
/*     */   }
/*     */ 
/*     */   public void add() throws MXException, RemoteException {
/*  72 */     super.add();
/*  73 */     String yhnum = numberGeneration();
/*  74 */     getMboValue("YHNUM").setValue(yhnum);
/*     */   }
/*     */ 
/*     */   private String numberGeneration() throws MXException, RemoteException {
/*  78 */     System.out.println("开始进入mbo中的add 方法中！");
/*  79 */     System.out.println("1、开始生成 工单编号的前缀。");
/*  80 */     String prefix = "YH";
/*  81 */     System.out.println("1.1、前缀生成完成：" + prefix);
/*  82 */     System.out.println("2、开始生成中间的日期编号。");
/*  83 */     Date date = new Date();
/*  84 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
/*  85 */     String sysdate = sdf.format(date);
/*  86 */     System.out.println("2.1、当前操作日期：" + sysdate);
/*  87 */     System.out.println("2.2、中间的日期编号生成完成：" + sysdate);
/*  88 */     System.out.println("3、开始生成两位的流水号。");
/*  89 */     System.out.println("3.1 首先判断以下当前的编号已经生成到多大了？");
/*  90 */     System.out.println("3.2 获取到要操作的表的名称");
/*  91 */     MboSetRemote yhglSet = MXServer.getMXServer().getMboSet("YHGL", 
/*  92 */       getUserInfo());
/*  93 */     System.out.println("3.3、要操作的对象：" + yhglSet);
/*  94 */     String runningNumber = null;
/*  95 */     System.out.println("3.4、初始化 runningNumber 为 null ");
/*  96 */     System.out.println("3.5、开始执行Where子句，查看当前的表中在当天的数据总共多少条。");
/*  97 */     yhglSet.setWhere("CREATEDATE IS NOT NULL AND TO_CHAR(CREATEDATE ,'yyyy-MM-dd') = TO_CHAR(SYSDATE,'yyyy-MM-dd')");
/*  98 */     yhglSet.setOrderBy(" YHGLID DESC ");
/*  99 */     System.out.println("3.6、在一天内的数据总条数：" + yhglSet.count());
/* 100 */     System.out.println("3.7、开始进入if 判断。 ");
/* 101 */     if ((yhglSet != null) && (!yhglSet.isEmpty())) {
/* 102 */       String yhnum = yhglSet.getMbo(0).getString("YHNUM");
/* 103 */       System.out.println("获取到检索到出来的工单编号：" + yhnum);
/* 104 */       runningNumber = String.valueOf(Integer.parseInt(yhnum.substring(10)) + 1);
/* 105 */       System.out.println("截取之后的两位的流水号：" + runningNumber);
/*     */     } else {
/* 107 */       System.out.println("如果不能检索出工单编号，则将 runningNumber 置为 01。");
/* 108 */       runningNumber = "01";
/*     */     }
/* 110 */     System.out.println("如果 runningNumber.length() < 2 的话！ ");
/* 111 */     if (runningNumber.length() < 2) {
/* 112 */       runningNumber = "0" + runningNumber;
/* 113 */       System.out.println("此时的2位流水号：" + runningNumber);
/*     */     }
/* 115 */     System.out.println("开始生成工单编号。");
/* 116 */     String yhnum = prefix + sysdate + runningNumber;
/* 117 */     System.out.println("生成的易耗单编号 :" + yhnum);
/* 118 */     String createyhnum = getString("YHNUM");
/* 119 */     System.out.println("复123制前的工单编号：" + createyhnum);
/* 120 */     return yhnum;
/*     */   }
/*     */ }

/* Location:           E:\MAXIMO.ear\businessobjects.jar\
 * Qualified Name:     zhice.app.yhgl.YhglMbo
 * JD-Core Version:    0.6.2
 */