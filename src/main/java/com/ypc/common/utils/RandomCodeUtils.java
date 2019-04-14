package com.ypc.common.utils;

import org.springframework.util.StringUtils;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Random;


public class RandomCodeUtils {

	
	public static String generateNumber() { 
	String no=""; 
	int num[]=new int[8]; 
	int c=0;
	
	for (int i = 0; i < 8; i++) { 
	num[i] = new Random().nextInt(10); 
	c = num[i]; 
	for (int j = 0; j < i; j++) { 
	if (num[j] == c) { 
	i--; 
	break; 
	} 
	} 
	} 
	
	if (num.length>0) { 
	for (int i = 0; i < num.length; i++) { 
	no+=num[i]; 
	} 
	} 
	
	return "jobcode-"+no; 
	} 
	
	
	public static String randomCodeWithNumber(int number,String code) { 
	String no=""; 
	int num[]=new int[number]; 
	int c=0;
	
	for (int i = 0; i < number; i++) { 
	num[i] = new Random().nextInt(10); 
	c = num[i]; 
	for (int j = 0; j < i; j++) { 
	if (num[j] == c) { 
	i--; 
	break; 
	} 
	} 
	} 
	
	if (num.length>0) { 
	for (int i = 0; i < num.length; i++) { 
	no+=num[i]; 
	} 
	} 
	
	if(StringUtils.hasText(code)){
		
		return code+"-"+no; 
	}else{
		return "JWD-"+no;
	}
	
	} 
	
	
	public static String randomCodeWithNumber(int number) { 
		String no=""; 
		int num[]=new int[number]; 
		int c=0;
		
		for (int i = 0; i < number; i++) { 
			num[i] = new Random().nextInt(10); 
			c = num[i]; 
			for (int j = 0; j < i; j++) { 
				if (num[j] == c) { 
					i--; 
					break; 
				} 
			} 
		} 
		
		if (num.length>0) { 
			for (int i = 0; i < num.length; i++) { 
				no+=num[i]; 
			} 
		} 
			return no; 
	} 
	
	/**
	* 生成随机密码
	* @param pwd_len
	* 生成的密码的总长度
	* @return 密码的字符串
	*/
	public static String genRandomNum(int pwd_len) {
	// 26*2个字母+10个数字
	final int maxNum = 62;
	int i; // 生成的随机数
	int count = 0; // 生成的密码的长度
	char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
	'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
	'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
	'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
	'x', 'y', 'z','0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	StringBuffer pwd = new StringBuffer("");
	Random r = new Random();
	while (count < pwd_len) {
	// 生成随机数，取绝对值，防止生成负数，
	i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为62-1
	if (i >= 0 && i < str.length) {
	pwd.append(str[i]);
	count++;
	}
	}
	return pwd.toString();
	}
	
	
	
	public static void main(String[] args) { 
		int a = 3;
		String code ="";
		for (int i = 0; i < 10; i++) { 
		// System.out.println(generateNumber()); 
			
		System.out.println(randomCodeWithNumber(a,code)); 
		} 
		
		}
	
	
	
	//计算百分比
	public static String calculateProgress(int amount,int realAmount){
		String result = "0";
        if(amount > 0 &&realAmount > 0){
        	if(amount<=realAmount){
        		result="100";
        	}else {
        		
        		NumberFormat nf = NumberFormat.getNumberInstance();
        		// 保留两位小数
        		nf.setMaximumFractionDigits(4); 
        		// 如果不需要四舍五入，可以使用RoundingMode.DOWN
        		nf.setRoundingMode(RoundingMode.UP);
        		
        		double i = (double)realAmount/(double)amount;
        		double j=i*100.0;
        		result=nf.format(j);
        	}
        	
        }else{
        	result="0";
        }
        return result;
    }

}
