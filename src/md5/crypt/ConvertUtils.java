package md5.crypt;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 转换工具
 */
public abstract class ConvertUtils {

	private static final DecimalFormat simpleFormat = new DecimalFormat("####");

	/**
	 * Object转换boolean
	 * 
	 * @param o 待转换对象
	 * @return
	 */
	public static final boolean objectToBoolean(Object o) {
		return o != null ? Boolean.valueOf(o.toString()).booleanValue() : false;
	}

	/**
	 * Object转换Integer
	 * 
	 * @param o 待转换对象
	 * @return
	 */
	public static final int objectToInt(Object o) {
		if (o instanceof Number) {
	        return ((Number) o).intValue();
        }
		try {
			if (o == null) {
	            return -1;
            } else {
	            return Integer.parseInt(o.toString());
            }
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * Object转换Short
	 * 
	 * @param o 待转换对象
	 * @return
	 */
	public static final short objectToShort(Object o) {
		if (o instanceof Number) {
	        return ((Number) o).shortValue();
        }
		try {
			if (o == null) {
	            return -1;
            } else {
	            return Short.parseShort(o.toString());
            }
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * Object转换Double
	 * 
	 * @param o 待转换对象
	 * @return
	 */
	public static final double objectToDouble(Object o) {
		if (o instanceof Number) {
	        return ((Number) o).doubleValue();
        }
		try {
			if (o == null) {
	            return -1D;
            } else {
	            return Double.parseDouble(o.toString());
            }
		} catch (NumberFormatException e) {
			return -1D;
		}
	}

	/**
	 * Object转换Long
	 * 
	 * @param o 待转换对象
	 * @return
	 */
	public static final long objectToLong(Object o) {
		if (o instanceof Number) {
	        return ((Number) o).longValue();
        }
		try {
			if (o == null) {
	            return -1L;
            } else {
	            return Long.parseLong(o.toString());
            }
		} catch (NumberFormatException e) {
			return -1L;
		}
	}

	/**
	 * 对象转换成字符串
	 * 
	 * @param obj 待转换对象
	 * @param fmt 格式
	 * @return
	 */
	public static final String objectToString(Object obj, DecimalFormat fmt) {
		fmt.setDecimalSeparatorAlwaysShown(false);
		if (obj instanceof Double) {
			return fmt.format(((Double) obj).doubleValue());
		}
		if (obj instanceof Long) {
			return fmt.format(((Long) obj).longValue());
		} else {
			return obj.toString();
		}
	}

	/**
	 * 获取对象值
	 * 
	 * @param value 值
	 * @return
	 */
	public static final Object getObjectValue(String value) {
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			return Double.valueOf(value);
		} catch (NumberFormatException e) {
			return value;
		}
	}

	/**
	 * Long转换成字符串
	 * 
	 * @param value 待转换值
	 * @return
	 */
	public static String longToSimpleString(long value) {
		return simpleFormat.format(value);
	}

	/**
	 * 十六进制编码
	 * 
	 * @param hash 编码值
	 * @return
	 */
	public static String asHex(byte[] hash) {
		return toHex(hash);
	}

	/**
	 * 转变成十六进制编码
	 * 
	 * @param input 输入字符串
	 * @return
	 */
	public static String toHex(byte[] input) {
		if (input == null) {
			return null;
		}
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16) {
				output.append("0");
			}
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	/**
	 * 从十六进制转换
	 * 
	 * @param input 待转换十六进制字符串
	 * @return
	 */
	public static byte[] fromHex(String input) {
		if (input == null) {
			return null;
		}
		byte[] output = new byte[input.length() / 2];
		for (int i = 0; i < output.length; i++) {
			output[i] = (byte) Integer.parseInt(input.substring(i * 2, (i + 1) * 2), 16);
		}

		return output;
	}

	/**
	 * 字符串到十六制转换
	 * 
	 * @param input 待转换值
	 * @param encoding 编码类型
	 * @return
	 * @throws UnsupportedEncodingException 未支持异常
	 */
	public static String stringToHexString(String input, String encoding) throws UnsupportedEncodingException {
		return input != null ? toHex(input.getBytes(encoding)) : null;
	}

	/**
	 * 字符串到十六进制字符串转换
	 * @param input 待转换字符串
	 * @return
	 */
	public static String stringToHexString(String input) {
		try {
			return stringToHexString(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding is not supported by JVM");
		}
	}

	/**
	 * 十六进制到字符串转换
	 * @param input 等转换字符串
	 * @param encoding 编码
	 * @return
	 * @throws UnsupportedEncodingException 不支持编码异常
	 */
	public static String hexStringToString(String input, String encoding)
	        throws UnsupportedEncodingException {
		return input != null ? new String(fromHex(input), encoding) : null;
	}

	/**
	 * 十六到字符串转换
	 * @param input 待转换字符串
	 * @return
	 */
	public static String hexStringToString(String input) {
		try {
			return hexStringToString(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding is not supported by JVM");
		}
	}

	/**
	 * 时间到编码转换
	 * @param tz 到编码转换
	 * @return
	 */
	public static String timeZoneToCode(TimeZone tz) {

		return timeZoneToString(tz);
	}

	/**
	 * 编码到tz转换
	 * @param tzString TZ字符串
	 * @return
	 */
	public static TimeZone codeToTimeZone(String tzString) {

		return stringToTimeZone(tzString);
	}

	/**
	 * TZ转字符串
	 * @param tz 待转换TZ
	 * @return
	 * @author LiuKun
	 * @date 2012-11-12
	 */
	public static String timeZoneToString(TimeZone tz) {

		return tz != null ? tz.getID() : "";
	}

	/**
	 * 字符串到TZ转换
	 * @param tzString TZ字符串
	 * @return
	 */
	public static TimeZone stringToTimeZone(String tzString) {

		return TimeZone.getTimeZone(tzString != null ? tzString : "");
	}

	/**
	 * local转换成code
	 * @param aLocale locale
	 * @return
	 */
	public static String localeToCode(Locale aLocale) {

		return localeToString(aLocale);
	}

	/**
	 * code转换成Locale
	 * @param locString locale
	 * @return
	 */
	public static Locale codeToLocale(String locString) {

		return stringToLocale(locString);
	}

	/**
	 * local转成字符串
	 * @param loc 待转换local
	 * @return
	 * @author LiuKun
	 * @date 2012-11-12
	 */
	public static String localeToString(Locale loc) {

		return loc != null ? loc.toString() : "";
	}

	/**
	 * 字符串转换成
	 * @param locString 待转换字符串
	 * @return
	 */
	public static Locale stringToLocale(String locString) {

		locString = locString != null ? locString.trim() : "";
		if (locString.equals("")) {
	        return new Locale("", "", "");
        }
		int pos = locString.indexOf(95);
		if (pos == -1) {
	        return new Locale(locString, "", "");
        }
		String language = locString.substring(0, pos);
		locString = locString.substring(pos + 1);
		pos = locString.indexOf(95);
		if (pos == -1) {
			return new Locale(language, locString, "");
		} else {
			String country = locString.substring(0, pos);
			locString = locString.substring(pos + 1);
			return new Locale(language, country, locString);
		}
	}

	/**
	 * 日期转成Sql
	 * @param d 待转换日期
	 * @return
	 */
	public static Date dateToSQLDate(java.util.Date d) {

		return d != null ? new Date(d.getTime()) : null;
	}

	/**
	 *  日期转换成SQLTime
	 * @param d Util日期
	 * @return
	 */
	public static Time dateToSQLTime(java.util.Date d) {

		return d != null ? new Time(d.getTime()) : null;
	}

	/**
	 * 日期转换成SQL日期
	 * @param d Util日期
	 * @return
	 */
	public static Timestamp dateToSQLTimestamp(java.util.Date d) {

		return d != null ? new Timestamp(d.getTime()) : null;
	}

	/**
	 * SQL日期转成Util日期
	 * @param t 待转换SQL时间
	 * @return
	 */
	public static java.util.Date sqlTimestampToDate(Timestamp t) {

		return t != null ? new java.util.Date(Math.round((double) t.getTime()
		        + (double) t.getNanos() / 1000000D)) : null;
	}

	/**
	 * 获取当前日期
	 * @return
	 */
	public static Timestamp getCurrentDate() {

		Calendar c = Calendar.getInstance();
		c.set(c.get(1), c.get(2), c.get(5), 0, 0, 0);
		Timestamp t = new Timestamp(c.getTime().getTime());
		t.setNanos(0);
		return t;
	}

	/**
	 * 获取日期
	 * @param y 年
	 * @param m 月
	 * @param d 日
	 * @param inclusive 不包括
	 */
	public static java.util.Date getDate(int y, int m, int d, boolean inclusive) {
		java.util.Date dt = null;
		Calendar c = Calendar.getInstance();
		c.clear();
		if (c.getActualMinimum(1) <= y && y <= c.getActualMaximum(1)) {
			c.set(1, y);
			if (c.getActualMinimum(2) <= m && m <= c.getActualMaximum(2)) {
				c.set(2, m);
				if (c.getActualMinimum(5) <= d && d <= c.getActualMaximum(5)) {
	                c.set(5, d);
                }
			}
			if (inclusive) {
				c.add(5, 1);
				c.add(14, -1);
			}
			dt = c.getTime();
		}
		return dt;
	}

	/**
	 * 获取开始日期
	 * @param d Util日期
	 */
	public static java.util.Date getDateStart(java.util.Date d) {

		Calendar c = new GregorianCalendar();
		c.clear();
		Calendar co = new GregorianCalendar();
		co.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, co.get(Calendar.DAY_OF_MONTH));
		c.set(Calendar.MONTH, co.get(Calendar.MONTH));
		c.set(Calendar.YEAR, co.get(Calendar.YEAR));
		// c.add(Calendar.DAY_OF_MONTH,1);
		// c.add(Calendar.MILLISECOND,-1);
		return c.getTime();
	}

	/**
	 * 获取日期尾
	 * @param d 日期
	 * @return
	 */
	public static java.util.Date getDateEnd(java.util.Date d) {
		Calendar c = Calendar.getInstance();
		c.clear();
		Calendar co = Calendar.getInstance();
		co.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, co.get(Calendar.DAY_OF_MONTH));
		c.set(Calendar.MONTH, co.get(Calendar.MONTH));
		c.set(Calendar.YEAR, co.get(Calendar.YEAR));
		c.add(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MILLISECOND, -1);
		return c.getTime();
	}

	/**
	 * 四舍五入
	 * @param rowNumber 行数
	 * @param roundingPoint 小数点后几位
	 * @return
	 */
	public static double roundNumber(double rowNumber, int roundingPoint) {
		double base = Math.pow(10D, roundingPoint);
		return (double) Math.round(rowNumber * base) / base;
	}

	/**
	 * 类型与字符串获取对象
	 * @param type 类型
	 * @param value 待获取字符串
	 * @return
	 * @throws Exception 各种异常
	 */
	public static Object getObject(String type, String value) throws Exception {

		type = type.toLowerCase();
		if ("boolean".equals(type)) {
	        return Boolean.valueOf(value);
        }
		if ("byte".equals(type)) {
	        return Byte.valueOf(value);
        }
		if ("short".equals(type)) {
	        return Short.valueOf(value);
        }
		if ("char".equals(type)) {
	        if (value.length() != 1) {
	            throw new NumberFormatException(
	                    "Argument is not a character!");
	        } else {
	            return Character.valueOf(value.toCharArray()[0]);
	        }
        }
		if ("int".equals(type)) {
	        return Integer.valueOf(value);
        }
		if ("long".equals(type)) {
	        return Long.valueOf(value);
        }
		if ("float".equals(type)) {
	        return Float.valueOf(value);
        }
		if ("double".equals(type)) {
	        return Double.valueOf(value);
        }
		if ("string".equals(type)) {
	        return value;
        } else {
			Object [] objs = new String[] {value };
			return Class.forName(type).getConstructor(new Class[] {java.lang.String.class })
			        .newInstance(objs);
		}
	}

	private ConvertUtils() {
	}

//    public static void main(String[] args)
//    {
//    	System.out.println(getDateStart(new java.util.Date()));
//    }
}
