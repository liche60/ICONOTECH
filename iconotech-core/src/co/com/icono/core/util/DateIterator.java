package co.com.icono.core.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class DateIterator implements Iterator<Date>, Iterable<Date> {

private Calendar start = Calendar.getInstance();
private Calendar end = Calendar.getInstance();
private Calendar current = Calendar.getInstance();

public DateIterator(Date start, Date end) {
this.start.setTime(start);
this.end.setTime(end);
this.current.setTime(start);
}

public boolean hasNext() {
return !current.after(end);
}

public Date next() {
current.add(Calendar.DATE, 1);
return current.getTime();
}

public void remove() {
throw new UnsupportedOperationException("Cannot remove");
}

public Iterator<Date> iterator() {
return this;
}

	public static void main(String[] args) {
		Date d1 = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -20);
		Date d2 = cal.getTime();
		Iterator<Date> i = new DateIterator(d2,d1);
		while (i.hasNext()) {
			Date date = i.next();
			System.out.println(date);
		}
	}
}