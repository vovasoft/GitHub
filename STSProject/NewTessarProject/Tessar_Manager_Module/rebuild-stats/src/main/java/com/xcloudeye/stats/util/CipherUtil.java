package com.xcloudeye.stats.util;

import java.security.MessageDigest;
   
 /**  
  * 对密码进行加密和验证的类 
  */  
 public class CipherUtil{  
	 
	 /** hexDigitsChar 可逆加密算法因子*/
	private final static char[] hexDigitsChar = {'d', 'e', 'f', '0', '1', '2', '3', '4',  
	        '5', '6', '7', '8', '9', 'a', 'b', 'c'};
       
     /** hexDigits MD5算法因子*/
    private final static String[] hexDigits = {"d", "e", "f", "0", "1", "2", "3", "4",  
         "5", "6", "7", "8", "9", "a", "b", "c"};  
       
     /** * 把inputString加密     */  
     public static String generatePassword(String inputString){  
         return encodeByMD5(inputString);  
     }  
       
     /**
    * <p>Title: validatePassword</p>
    * <p>Description: 比较明文字符串和Md5字符串是否相同</p>
    * @param password MD5 加密密码字符串
    * @param inputString 明文字符串
    * @return 返回boolean字符型结果
    */
    public static boolean validatePassword(String password, String inputString){  
         if(password.equals(encodeByMD5(inputString))){  
             return true;  
         } else{  
             return false;  
         }  
     }  
     
     /**
    * <p>Title: returnEncodeByMde</p>
    * <p>Description: 将字符串加密为密文字符串</p>
    * @param originString
    * @return
    */
    public static String returnEncodeByMde(String originString){
    	 return encodeByMD5(originString);
     }
    
     /**
    * <p>Title: encodeByMD5</p>
    * <p>Description: 对字符串进行MD5加密 </p>
    * @param originString 明文密码字符串
    * @return
    */
    private static String encodeByMD5(String originString){  
         if (originString != null){  
             try{  
                 MessageDigest md = MessageDigest.getInstance("MD5");  
                 //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算  
                 byte[] results = md.digest(originString.getBytes());  
                 //将得到的字节数组变成字符串返回  
                 String resultString = byteArrayToHexString(results);  
                 String pass =  resultString.toUpperCase();  
                 return pass;
             } catch(Exception ex){  
                 ex.printStackTrace();  
             }  
         }  
         return null;  
     }  
       
   
     /**
    * <p>Title: byteArrayToHexString</p>
    * <p>Description: 将得到的字节数组加偏量后返回字符串</p>
    * @param b
    * @return
    */
    private static String byteArrayToHexString(byte[] b){  
         StringBuffer resultSb = new StringBuffer();  
         for (int i = 0; i < b.length; i++){  
             resultSb.append(byteToHexString(b[i]));  
         }  
         return resultSb.toString();  
     }  
       
     /**
    * <p>Description: 将一个字节转化成十六进制形式的字符串</p>
    * @param b
    * @return
    */
    private static String byteToHexString(byte b){  
         int n = b;  
         if (n < 0)  
             n = 256 + n;  
         int d1 = n / 16;  
         int d2 = n % 16;  
         return hexDigits[d1] + hexDigits[d2];  
     }  
     
	/**
	* <p>Title: convertMD5</p>
	* <p>Description: 可逆加密解密算法 执行一次加密，两次解密</p>
	* @param inStr 加密或者解密的输入字符串
	* @param cipher 加密解密因子的选取index
	* @return
	*/
	public static String convertMD5(String inStr, int cipher){

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++){
			a[i] = (char) (a[i] ^ hexDigitsChar[cipher]);
		}
		String s = new String(a);
		return s;
	}
 }  