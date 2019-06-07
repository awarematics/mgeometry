package com.awarematics.postmedia.transform;

//import org.postgresql.pljava.annotation.Function;
import java.text.SimpleDateFormat;

public class ColumnsProjection {

//	@Function
	public static String MPointPoint(String toWhom) {
		toWhom = toWhom.replace("MPOINT (", "");
		String result = "";
		
		String[] split1 = toWhom.split(",");
		for (int i = 0; i < split1.length; i++) {
			String temp = split1[i].split("\\)")[0].replaceAll(" \\(", "");
			temp = temp.replace(" ", ", ");
			if (i == 0)
				result = result + "(" + temp + ")";
			else
				result = result + ";" + "(" + temp + ")";
		}
		result = result.replace("((", "(");
		return result;
	}

//	@Function
	public static String MPointTime(String toWhom) {
		toWhom = toWhom.replace("MPOINT (", "");
		String result = "";
		String[] split1 = toWhom.split(",");
		for (int i = 0; i < split1.length; i++) {
			if (i == 0)
				result = result + "'" + LongToString(Long.parseLong(split1[i].split("\\) ")[1].replaceAll("\\)", "")))
						+ "'";
			else
				result = result + ", '" + LongToString(Long.parseLong(split1[i].split("\\) ")[1].replaceAll("\\)", "")))
						+ "'";
		}
		return result;
	}

//	@Function
	public static String MDoubleDouble(String toWhom) {

		toWhom = toWhom.replace("MDOUBLE (", "");
		String result = "";
		String[] split1 = toWhom.split(", ");
		for (int i = 0; i < split1.length; i++) {
			String temp = split1[i].split(" ")[0];
			if (i == 0)
				result = result + temp;
			else
				result = result + "; " + temp;
		}
		return result;
	}

//	@Function
	public static String MDoubleTime(String toWhom) {
		toWhom = toWhom.replace("MDOUBLE (", "");
		String result = "";
		String[] split1 = toWhom.split(", ");
		for (int i = 0; i < split1.length; i++) {
			if (i == 0)
				result = result + "'{" + LongToString(Long.parseLong(split1[i].split(" ")[1].replaceAll("\\)", "")))
						+ "}'";
			else
				result = result + ", '{" + LongToString(Long.parseLong(split1[i].split(" ")[1].replaceAll("\\)", "")))
						+ "}'";
		}
		return result;
	}
	
//	@Function
	public static String MBoolBool(String toWhom) {
		toWhom = toWhom.replace("MBOOL ", "");
		toWhom = toWhom.replaceAll("\\(|\\)", "");
		String result = "";		
		String[] split1 = toWhom.split(", ");
		for (int i = 0; i < split1.length; i++) {
			String temp = split1[i].split(" ")[0];
			if (i == 0)
				result = result  + temp ;
			else
				result = result + ";"  + temp ;
		}
		return result;
	}

//	@Function
	public static String MBoolTime(String toWhom) {
		toWhom = toWhom.replace("MBOOL (", "");
		String result = "";
		String[] split1 = toWhom.split(", ");
		for (int i = 0; i < split1.length; i++) {
			if (i == 0)
				result = result + "'{" + LongToString(Long.parseLong(split1[i].split(" ")[1].replaceAll("\\)", "")))
						+ "}'";
			else
				result = result + ", '{" + LongToString(Long.parseLong(split1[i].split(" ")[1].replaceAll("\\)", "")))
						+ "}'";
		}
		return result;
	}

//	@Function
	public static String MVideoPoint(String toWhom) {
		toWhom = toWhom.replace("MVIDEO ", "");
		toWhom = toWhom.replaceAll("\\(|\\)", "");
		String result = "";		
		String[] split1 = toWhom.split(", ");
		for (int i = 0; i < split1.length; i++) {
			String temp = split1[i].split(" ")[11]+","+split1[i].split(" ")[12];
			if (i == 0)
				result = result + "(" + temp + ")";
			else
				result = result + ";" + "(" + temp + ")";
		}
		return result;
	}

//	@Function
	public static String MVideoTime(String toWhom) {
		toWhom = toWhom.replace("MVIDEO ", "");
		toWhom = toWhom.replaceAll("\\(|\\)", "");
		String result = "";		
		String[] split1 = toWhom.split(", ");
		for (int i = 0; i < split1.length; i++) {
			String temp = split1[i].split(" ")[11];
			if (i == 0)
				result = result + "'{" + LongToString(Long.parseLong(temp)) + "}'";
			else
				result = result + "," + "'{" + LongToString(Long.parseLong(temp)) + "}'";
		}
		return result;
	}

//	@Function
	public static String MVideoUri(String toWhom) {
		toWhom = toWhom.replace("MVIDEO ", "");
		toWhom = toWhom.replaceAll("\\(|\\)", "");
		String result = "";		
		String[] split1 = toWhom.split(", ");
		for (int i = 0; i < split1.length; i++) {
			String temp = split1[i].split(" ")[0];
			if (i == 0)
				result = result + temp;
			else
				result = result + ";" + temp;
		}
		return result;
	}

//	@Function
	public static String MVideoDistance(String toWhom) {
		toWhom = toWhom.replace("MVIDEO ", "");
		toWhom = toWhom.replaceAll("\\(|\\)", "");
		String result = "";		
		String[] split1 = toWhom.split(", ");
		for (int i = 0; i < split1.length; i++) {
			String temp = split1[i].split(" ")[3];
			if (i == 0)
				result = result + temp;
			else
				result = result + ";" + temp;
		}
		return result;
	}

//	@Function
	public static String MVideoDirection(String toWhom) {
		toWhom = toWhom.replace("MVIDEO ", "");
		toWhom = toWhom.replaceAll("\\(|\\)", "");
		String result = "";		
		String[] split1 = toWhom.split(", ");
		for (int i = 0; i < split1.length; i++) {
			String temp = split1[i].split(" ")[4];
			if (i == 0)
				result = result + temp;
			else
				result = result + ";" + temp;
		}
		return result;
	}

//	@Function
	public static String MVideoAngle(String toWhom) {
		toWhom = toWhom.replace("MVIDEO ", "");
		toWhom = toWhom.replaceAll("\\(|\\)", "");
		String result = "";		
		String[] split1 = toWhom.split(", ");
		for (int i = 0; i < split1.length; i++) {
			String temp = split1[i].split(" ")[1];
			if (i == 0)
				result = result + temp;
			else
				result = result + ";" + temp;
		}
		return result;
	}

	public static String LongToString(long duration) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String timeText = format.format(duration); // long to string
		return timeText;
	}
}