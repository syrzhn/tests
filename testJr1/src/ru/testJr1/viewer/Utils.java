package ru.testJr1.viewer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public static String stringToSqliteDate(String string) {
		String sqliteDateStr = "1904-12-01";
		DateFormat formatIn = new SimpleDateFormat("dd.mm.yyyy"),
				formatOut = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		try {
			date = formatIn.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sqliteDateStr = formatOut.format(date);
		return sqliteDateStr;
	}
}
