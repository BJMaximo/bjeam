package com.wp.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.ControlHandler;
import psdi.webclient.system.controller.ControlInstance;
import psdi.webclient.system.controller.LabelCacheMgr;
import psdi.webclient.system.controller.SessionContext;
import psdi.webclient.system.controller.UploadFile;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class YanpfwExcelBean extends DataBean {
	private DataBean originalBean;
	private File inputWorkbook;
	private String errMessage;
	private DataBean parentBean;

	// MXServer server;

	public YanpfwExcelBean() {

	}

	protected void initialize() throws MXException, RemoteException {
		super.initialize();
		ControlInstance control = creatingEvent.getSourceControlInstance();
		if (control != null) {
			parentBean = control.getDataBean();
		}
	}
	
	public synchronized int execute() throws MXException, RemoteException {
		UploadFile uf = null;
		String filename = null;
		uf = (UploadFile) app.get("importfile");
		try {
			filename = uf.getFileName();
			boolean haveFilename = (filename != null) && (filename.length() > 0);
			if (haveFilename) {
				LabelCacheMgr.clearSystemCache(clientSession);
				try {
					uf.writeToDisk();// 读取EXCEL
					readExcel(uf.getAbsoluteFileName(), sessionContext);
					uf.delete();
					uf.save();
					sessionContext.queueRefreshEvent();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		app.remove("importfile");
		parent.fireStructureChangedEvent();
		sessionContext.queueRefreshEvent();
		// clientSession.showMessageBox(clientSession.getCurrentEvent(),
		// "提示", "导入完成", 0);
		clientSession.showMessageBox(clientSession.getCurrentEvent(), "提示", "导入完成请查看数据是否正确！", 1);
		return 1;
	}

	@SuppressWarnings("deprecation")
	public void readExcel(String fileName, SessionContext sessionContext) throws Exception {
		WebClientEvent event = sessionContext.getCurrentEvent();
		ControlHandler controlhandler = creatingEvent.getSourceControl();
		originalBean = Utility.getDataSource(sessionContext, controlhandler);
		try {
			inputWorkbook = new File(fileName);
			Workbook workbook = null;
			try {
				InputStream is = new FileInputStream(inputWorkbook);
				String hz = fileName.substring(fileName.lastIndexOf("."), fileName.length());
				if (hz.equalsIgnoreCase(".xls"))// 针对2003版本
				{
					workbook = new HSSFWorkbook(is);// 创建excel2003的文件文本抽取对象
				} else { // 针对2007版本
					workbook = new XSSFWorkbook(fileName);// 创建excel2007的文件文本抽取对象
				}
			} catch (Exception ee) {
				// Utility.showMessageBox(event, "prjmnt", ee.getMessage(),
				// parms);
				ee.printStackTrace();
			}
			getExcelData(workbook, originalBean);
		} catch (MXException mxexception) {
			errMessage = errMessage + "\r\n" + mxexception.getMessage();
			Utility.showMessageBox(event, mxexception);
		}
	}

	public void getExcelData(Workbook workbook, DataBean databean) throws Exception {
		impExcelnr(workbook);
		app.getResultsBean().refreshTable();
	}

	private void impExcelnr(Workbook workbook) throws Exception {
		try {
			MboRemote yanpfwlineAdd = null;
			MboRemote ms = app.getAppBean().getMbo();
			int udyanpfwid = ms.getInt("udyanpfwid");
			MboSetRemote yanpfwlineSet = ms.getMboSet("UDYANPFWLINE");// 验评范围子表
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int zs = 0; zs < numberOfSheets; zs++) {
				Sheet sheet = workbook.getSheetAt(zs);// 得到需要的工作簿sheet
				int lastRowNum = sheet.getLastRowNum();
				int rsRows = sheet.getLastRowNum() + 1;// 获取总行数+1 因从0开始
				// System.out.println("总行数为-->" + rsRows);
				// 循环所有行，第一行作为标题，所以从第二行开始
				for (int i = 3; i < rsRows && lastRowNum > 0; i++) {
					yanpfwlineAdd = yanpfwlineSet.addAtEnd();
					Row row = sheet.getRow(i);// 获取第i行
					Cell cell1 = row.getCell(0);
					Cell cell2 = row.getCell(1);
					Cell cell3 = row.getCell(2);
					Cell cell4 = row.getCell(3);
					Cell cell5 = row.getCell(4);
					Cell cell6 = row.getCell(5);
					Cell cell7 = row.getCell(6);
					Cell cell8 = row.getCell(7);
					Cell cell9 = row.getCell(8);
					Cell cell10 = row.getCell(9);
					Cell cell11 = row.getCell(10);
					Cell cell12 = row.getCell(11);
					Cell cell13 = row.getCell(12);
					Cell cell14 = row.getCell(13);
					Cell cell15 = row.getCell(14);
					yanpfwlineAdd.setValue("udyanpfwid", udyanpfwid, 11L);
					yanpfwlineAdd.setValue("dwgc", cell1.getStringCellValue().trim(), 2L);
					yanpfwlineAdd.setValue("dwgcz", cell2.getStringCellValue().trim(), 2L);
					yanpfwlineAdd.setValue("fbgc", cell3.getStringCellValue().trim(), 2L);
					yanpfwlineAdd.setValue("fbgcz", cell4.getStringCellValue().trim(), 2L);
					yanpfwlineAdd.setValue("fxgc", cell5.getStringCellValue().trim(), 2L);
					yanpfwlineAdd.setValue("jyp", cell6.getStringCellValue().trim(), 2L);
					yanpfwlineAdd.setValue("description", cell7.getStringCellValue().trim(), 11L);
					yanpfwlineAdd.setValue("ISJZD", cell8.getStringCellValue().trim(), 11L);
					yanpfwlineAdd.setValue("ISTGDJD", cell9.getStringCellValue().trim(), 11L);
					yanpfwlineAdd.setValue("ISPZD", cell10.getStringCellValue().trim(), 11L);
					yanpfwlineAdd.setValue("ISSGDW", cell11.getStringCellValue().trim(), 11L);
					yanpfwlineAdd.setValue("ISJLDW", cell12.getStringCellValue().trim(), 11L);
					yanpfwlineAdd.setValue("ISJSDW", cell13.getStringCellValue().trim(), 11L);
					yanpfwlineAdd.setValue("ISSJDW", cell14.getStringCellValue().trim(), 11L);
					yanpfwlineAdd.setValue("ZLYSNUM", cell15.getStringCellValue().trim(), 11L);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
	}

	// 返回true为数字
	public static boolean isNum(String str) {
		boolean matches = false;
		if (!"".equals(str) && str != null) {
			matches = str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		}
		return matches;
	}

}