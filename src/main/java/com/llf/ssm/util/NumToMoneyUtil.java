package com.llf.ssm.util;

/**  
 * @author: 爱不留     
 * @date:   2018年7月12日 上午11:17:49   
 * @Description:  
 */
public class NumToMoneyUtil {

	private static final char[] data = new char[] { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
	private static final char[] units = new char[] { '元', '十', '百', '千', '万', '十', '百', '千', '亿' };
	
	private static final char[] dataDaxie = new char[] { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
	private static final char[] unitsDaxie = new char[] { '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿' };

	public static String convert(int money) {
		StringBuffer sbf = new StringBuffer();
		int unit = 0;
		while (money != 0) {
			sbf.insert(0, units[unit++]);
			int number = money % 10;
			sbf.insert(0, data[number]);
			money /= 10;
		}
		return sbf.toString();
	}
	
	public static String convertDaxie(int money) {
		StringBuffer sbf = new StringBuffer();
		int unit = 0;
		while (money != 0) {
			sbf.insert(0, unitsDaxie[unit++]);
			int number = money % 10;
			sbf.insert(0, dataDaxie[number]);
			money /= 10;
		}
		return sbf.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(convert(135689123));
		System.out.println(convertDaxie(135689123));
	}
}