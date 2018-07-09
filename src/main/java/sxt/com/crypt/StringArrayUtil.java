package sxt.com.crypt;
import java.text.DecimalFormat;

/**
 * 字符串数组转换工具
 */
public final class StringArrayUtil {
	private StringArrayUtil() {
	}

	/**
	 * 判断某字符串是否在某一字符串数组中，并返回其所在位置，否则返回-1
	 * 
	 * @param s String
	 * @param ss String[]
	 * @return int
	 */
	public static int contain(String s, String[] ss) {
		for (int i = 0; i < ss.length; i++) {
			if (s.equals(ss[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 把字符串数组按照分割符转换为字符串
	 * 
	 * @param ss 字符串数组
	 * @param delimeter 分隔符
	 * @return
	 */
	public static String arrayToString(String[] ss, String delimeter) {
		StringBuffer sb = new StringBuffer();

		if (ss != null && ss.length > 0) {
			for (int i = 0; i < ss.length; i++) {
				sb.append(ss[i]);
				if ((i + 1) < ss.length) {
	                sb.append(delimeter);
                }
			}
		}

		return sb.toString();
	}

	/**
	 * 把字符串数组按照分割符转换为字符串
	 * 
	 * @param ss 待分割字符串数组
	 * @param delimeter 分隔符
	 * @return
	 */
	public static String arrayToString(String[] ss, char delimeter) {
		return arrayToString(ss, Character.toString(delimeter));
	}

	/**
	 * 检查字符串是否有效的数字串
	 * 
	 * @param str 待检查字符串
	 * @return
	 */
	public static boolean isValidDecString(String str) {
		char ch;
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch < '0' || ch > '9') {
	            return false;
            }
		}
		return true;
	}

	/**
	 * 整型转字符型
	 * @param val 待转换整数
	 * @return
	 * @author LiuKun
	 * @date 2012-11-13
	 */
	public static String int2char2(int val) {
		DecimalFormat fmt = new DecimalFormat("00");

		if ((val < 0) || (val > 99)) {
	        return null;
        }
		return fmt.format(val);
	}

	/**
	 * 整型转3位字符
	 * @param val 待转换整数
	 * @return
	 * @author LiuKun
	 * @date 2012-11-13
	 */
	public static String int2char3(int val) {
		DecimalFormat fmt = new DecimalFormat("000");

		if ((val < 0) || (val > 999)) {
	        return null;
        }
		return fmt.format(val);
	}

	/**
	 * 字符串左补'0'
	 * 
	 * @param str 待补字符串
	 * @param len 补齐长度
	 * @return
	 */
	public static String stringFillLeftZero(String str, int len) {
		if (str.length() < len) {
			StringBuffer sb = new StringBuffer(len);
			for (int i = 0; i < len - str.length(); i++) {
	            sb.append('0');
            }
			sb.append(str);
			return new String(sb);
		} else {
	        return str;
        }
	}

	/**
	 * 字符串右补空格
	 * 
	 * @param str 待补字符串
	 * @param len 长度
	 * @return
	 */
	public static String stringFillRightBlank(String str, int len) {
		if (str.length() < len) {
			StringBuffer sb = new StringBuffer(len);
			sb.append(str);
			for (int i = 0; i < len - str.length(); i++) {
	            sb.append(' ');
            }
			return new String(sb);
		} else {
	        return str;
        }
	}

	/**
	 *  整型转字符串并左补0
	 * @param val 待转换长整型
	 * @param len 长度
	 * @return
	 * @author LiuKun
	 * @date 2012-11-13
	 */
	public static String long2StringLeftZero(long val, int len) {
		String pattern = "";
		for (int i = 0; i < len; i++) {
	        pattern += '0';
        }

		DecimalFormat fmt = new DecimalFormat(pattern);
		return fmt.format(val);
	}

	/**
	 * 填充字符
	 * @param val 待填充字符
	 * @param len 长度
	 * @return
	 * @author LiuKun
	 * @date 2012-11-13
	 */
	public static String fillString(char val, int len) {
		String str = "";
		for (int i = 0; i < len; i++) {
	        str = str + val;
        }
		return str;
	}

	/**
	 *  整型转字符串并右补0
	 * @param val 待转换长整型
	 * @param len 长度
	 */
	public static String long2StringRightBlank(long val, int len) {
		String pattern = "#";
		DecimalFormat fmt = new DecimalFormat(pattern);
		String str = fmt.format(val);
		if (str.length() < len) {
			return str + fillString(' ', len - str.length());
		} else {
	        return str.substring(str.length() - len);
        }
	}

	/**
	 * 把byte串转换成十六进制字符串
	 * 
	 * @param b 待转换字符数组
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		return byte2hex(b, (char) 0);
	}

	/**
	 * 
	 */
	private static final char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
	        'B', 'C', 'D', 'E', 'F' };

	private static void byte2hex(byte b, StringBuffer buf) {
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		buf.append(hexChars[high]);
		buf.append(hexChars[low]);
	}

	/**
	 * 字符到十六进制转换
	 * @param b 待转换字符
	 * @return
	 */
	public static String byte2hex(byte b) {
		int high = b;
		high = (high & 0xf0) >> 4; // System.out.print(Integer.toHexString(high));
		int low = (b & 0x0f); // System.out.print(Integer.toHexString(low));
		return "" + hexChars[high] + hexChars[low];
	}

	/**
	 * 把byte串转换成十六进制字符串，同时每2个字符间增加分割符
	 * 
	 * @param b 待转换字符数组
	 * @param delimeter -- 字符间分割符
	 * @return
	 */
	public static String byte2hex(byte[] b, char delimeter) {
		StringBuffer sb = new StringBuffer();

		for (int n = 0; n < b.length; n++) {
			byte2hex(b[n], sb);

			// 如果不是最后一个byte，补个空格
			if (n < (b.length - 1) && delimeter != 0) {
	            sb.append(delimeter);
            }

		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 把16进制串转换为字节流
	 * 
	 * @param hexStr ： 16进制串
	 * @param len ： 16进制串的长度，必须为2的倍数
	 * @return
	 */
	public static byte[] hex2byte(String hexStr, int len) {
		if ((len % 2) != 0) {
	        throw new AssertionError("Hex2Byte() fail: len should be divided by 2");
        }

		byte[] buf = new byte[len / 2];
		for (int i = 0, j = 0; i < len; i += 2, j++) {
			byte hi = (byte) Character.toUpperCase(hexStr.charAt(i));
			byte lo = (byte) Character.toUpperCase(hexStr.charAt(i + 1));
			if (!Character.isDigit((char) hi) && !(hi >= 'A' && hi <= 'F')) {
	            throw new AssertionError(
	                    "Hex2Byte() fail: hex string is invalid in " + i + " with char '"
	                            + hexStr.charAt(i) + "'");
            }
			if (!Character.isDigit((char) lo) && !(lo >= 'A' && lo <= 'F')) {
	            throw new AssertionError(
	                    "Hex2Byte() fail: hex string is invalid in " + (i + 1) + " with char '"
	                            + hexStr.charAt(i + 1) + "'");
            }

			int ch = 0;
			ch |= (Character.isDigit((char) hi) ? (hi - '0') : (0x0a + hi - 'A')) << 4;
			ch |= (Character.isDigit((char) lo) ? (lo - '0') : (0x0a + lo - 'A'));
			buf[j] = (byte) ch;
		}

		return buf;
	}

	/**
	 * 字符串左补零
	 * 
	 * @param val String
	 * @param c char
	 * @param maxlen int
	 * @return String
	 */
	public static String leftFillChar(String val, char c, int maxlen) {
		StringBuffer sb = new StringBuffer();
		if (val.length() < maxlen) {
			for (int i = 0; i < maxlen - val.length(); i++) {
				sb.append('0');
			}
		}
		sb.append(val);
		return sb.toString();
	}

}
