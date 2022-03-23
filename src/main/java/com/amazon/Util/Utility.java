package com.amazon.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.amazon.ExceptionHandle.GlobalException;

public class Utility {

	public static Date StringToDateConvert(String inputDate) {
		Date converteddate = null;
		try {
			converteddate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(inputDate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GlobalException("Date Conversion Error!!");
		}
		return converteddate;
	}

	public static double roundAvoid(double value, int places) {
		double scale = Math.pow(10, places);
		return Math.round(value * scale) / scale;
	}

	public static double amazonSellingPrice(double price) {
		if (price >= 0 && price <= 500) {
			price = price + ((price * 1.5) / 100);
			return price;
		} else if (price >= 501 && price <= 5000) {
			price = price + ((price * 6) / 100);
			return price;
		} else if (price >= 5001 && price <= 10000) {
			price = price + ((price * 8) / 100);
			return price;
		} else if (price >= 10001 && price <= 50000) {
			price = price + ((price * 22) / 100);
			return price;
		} else if (price >= 50001 && price <= 100000) {
			price = price + ((price * 27) / 100);
			return price;
		} else {
			price = price + ((price * 11) / 100);
			return price;
		}
	}
}
