package com.wp.bean;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 当年月数（死亡人数、重伤人数、轻伤人数）
 * @author Administrator
 *
 */
public class FldDyrs extends MboValueAdapter {

	public FldDyrs(MboValue mbv) {
		super(mbv); 
		// TODO Auto-generated constructor stub
	}
	//重写action()方法
	@Override
	public void action() throws MXException, RemoteException {
		/**
		 * 获取当前字段的信息
		 * 获取当前字段属性值
		 * 通过该字段获取这条记录
		 * 判断该条记录是否为新建，否则提示需要保存
		 */
		MboValue mboValue = getMboValue();
		String attributeName = mboValue.getAttributeName();
		Mbo mbo = mboValue.getMbo();
		if(mbo.isNew()) {
			throw new MXApplicationException("提示", "请先保存记录！");
		}
		
		double ljsw=0;
		double ljzs =0;
		double ljqs = 0;
		//获取字段跟当前界面存在字段对比(好处就是将多个字段类写到一起)
		if("DEAD_NUM_CURM".equalsIgnoreCase(attributeName)) {
			//当年死亡
			ljsw = mboValue.getDouble() - mboValue.getInitialValue().asDouble();
				mbo.setValue("DEAD_NUM_SUMM", mbo.getInitialValue("DEAD_NUM_SUMM").asDouble()+ljsw);
		} else if("HEAVY_NUM_CURM".equals(attributeName)) {
			//当年重伤
			ljzs = mboValue.getDouble() - mboValue.getInitialValue().asDouble();
				mbo.setValue("HEAVY_NUM_SUMM", mbo.getInitialValue("HEAVY_NUM_SUMM").asDouble()+ljzs);
		} else if("LIGHT_NUM_CURM".equalsIgnoreCase(attributeName)) {
			//当年轻伤
			ljqs = mboValue.getDouble() - mboValue.getInitialValue().asDouble();
				mbo.setValue("LIGHT_NUM_SUMM", mbo.getInitialValue("LIGHT_NUM_SUMM").asDouble()+ljqs);
		} else if("DEAD_NUM_SUB_CURM".equals(attributeName)) {
			//fenbao死亡
			ljzs = mboValue.getDouble() - mboValue.getInitialValue().asDouble();
				mbo.setValue("DEAD_NUM_SUB_SUMM", mbo.getInitialValue("DEAD_NUM_SUB_SUMM").asDouble()+ljzs);
		} else if("HEAVY_NUM_SUB_CURM".equalsIgnoreCase(attributeName)) {
			//fenbao重伤
			ljqs = mboValue.getDouble() - mboValue.getInitialValue().asDouble();
				mbo.setValue("HEAVY_NUM_SUB_SUMM", mbo.getInitialValue("HEAVY_NUM_SUB_SUMM").asDouble()+ljqs);
		} else if("LIGHT_NUM_SUB_CURM".equalsIgnoreCase(attributeName)) {
			//fenbao轻伤
			ljqs = mboValue.getDouble() - mboValue.getInitialValue().asDouble();
				mbo.setValue("LIGHT_NUM_SUB_SUMM", mbo.getInitialValue("LIGHT_NUM_SUB_SUMM").asDouble()+ljqs);
		}   
		//将新建记录累计到对应总数里面(汇总)
		mbo.setValue("TOTAL_NUM_CURM", mbo.getDouble("DEAD_NUM_CURM")+mbo.getDouble("HEAVY_NUM_CURM")+mbo.getDouble("LIGHT_NUM_CURM"));
		mbo.setValue("TOTAL_NUM_SUMM", mbo.getDouble("DEAD_NUM_SUMM")+mbo.getDouble("HEAVY_NUM_SUMM")+mbo.getDouble("LIGHT_NUM_SUMM"));
		super.action();
	}

}
