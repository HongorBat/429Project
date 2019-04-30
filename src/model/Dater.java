package model;

public class Dater {
	
	private int d; // days
	private int m; // months
	private int y; // years
	private int df0; // days from zero, the day 0/0/0
	
	public Dater (String delimiter, String contents) {
		String[] args = contents.split(delimiter);
		int month = Integer.valueOf(args[0]).intValue();
		int day = Integer.valueOf(args[1]).intValue();
		int year = Integer.valueOf(args[2]).intValue();
		initialize(day, month, year);
	}
	public Dater (int day, int month, int year) {
		initialize(day, month, year);
	}
	
	private void initialize(int day, int month, int year) {
		d = (day > 31 ? -1 : day);
		m = (month > 12 ? -1 : month);
		if (year < 100 && year > -1) {
			y = 2000 + year; 
		} else {
			y = (year > 9999 || year < 0 ? -1 : year);
		}
		// this is approximate, doesnt take into account leap years fully
		// it takes into account the average amount of months per year, counting 
		// for leap years, which is ~30.4375f. 
		df0 = (y * 365) + Math.round(m * 30.4375f) + d;
	}
	public int getDay() { return d; }
	public int getMonth() { return m; }
	public int getYear() { return y; }
	private int getDaysFromZero() { return df0; }
	
	public int yearsBetween(Dater date) { return Math.abs(date.getYear() - y); }
	// This is approximate, does not take into account leap years.
	public int daysBetween(Dater date) { return Math.abs(date.getDaysFromZero() - df0); }
}
