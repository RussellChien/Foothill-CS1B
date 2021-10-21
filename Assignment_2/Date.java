public class Date implements Comparable<Date>, Cloneable {
	private int month, day, year; 

	public Date() {
		month = 1;
		day = 1; 
		year = 1970;
	}

	public Date(int month, int day, int year) {
		if (month >= 1 && month <= 12) this.month = month;
		if (day >= 1 && day <= 31) this.day = day;
		if (year >= 1970) this.year = year;
	}

	public Date(Date date) {
		this.month = date.month;
		this.day = date.day;
		this.year = date.year;
	}

	public int getMonth() { return month; }

	public void setMonth(int month) { this.month = month; }

	public int getDay() { return day; }

	public void setDay(int day) { this.day = day; }

	public int getYear() { return year; }

	public void setYear(int year) { this.year = year; }

	public String toString() {
		return String.format("%02d/%02d/%04d", month, day, year);
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof Date)) { return false; }
		Date date = (Date) other;
		return this.month == date.month && this.day == date.day && this.year == date.year;
	}

	public Date clone() throws CloneNotSupportedException {
		Date cloned = (Date) super.clone();
		return cloned;
	}

	public int compareTo(Date other) {
		if (this == null && other == null) {
			return 0;
		} else if (this == null) {
        	return 1;
      	} else if (other == null) {
        	return -1;
      	}
		if (other.year > year) {
			return 1;
		} else if (other.year < year) {
			return -1;
		} else {
			if (other.month > month) {
				return 1;
			} else if (other.month < month) {
				return -1;
			} else {
				if(other.day > day) {
					return 1;
				} else if (other.day < day) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
}