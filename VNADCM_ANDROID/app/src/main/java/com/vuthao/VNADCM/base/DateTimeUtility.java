package com.vuthao.VNADCM.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeUtility {

    public static String formatDateStart(long milliseconds) {
        return new SimpleDateFormat("dd/MM").format(milliseconds);
    }

    public static String formatDateToDayMonth(long milliseconds) {
        return new SimpleDateFormat("dd MMMM").format(milliseconds);
    }

    public static String formatDateToDayMonthYear(long milliseconds) {
        return new SimpleDateFormat("dd/MM/yyyy").format(milliseconds);
    }

    public static String formatFullTime(long milliseconds) {
        return new SimpleDateFormat("HH:mm").format(milliseconds);
    }

    public static String formatFullTime(long milliseconds, String format) {
        return new SimpleDateFormat(format).format(milliseconds);
    }

    public static String formatEvent(long startMilliseconds, long endMilliseconds) {
        String timeStart = new SimpleDateFormat("hh:mma").format(startMilliseconds);
        String timeEnd = new SimpleDateFormat("hh:mma").format(endMilliseconds);
        String time = timeStart + " - " + timeEnd;
        return time;
    }

    public static String formatFullEventTime(long startMilliseconds, long endMilliseconds) {
        String dateStart = new SimpleDateFormat("EEEE, dd MMMM, yyyy").format(startMilliseconds);
        String dateEnd = new SimpleDateFormat("EEEE, dd MMMM, yyyy").format(endMilliseconds);
        String timeStart = new SimpleDateFormat("hh:mma").format(startMilliseconds);
        String timeEnd = new SimpleDateFormat("hh:mma").format(endMilliseconds);
        if (dateStart.equalsIgnoreCase(dateEnd)) {
            return dateStart + " " + timeStart + " - " + timeEnd;
        } else {
            return dateStart + " - " + dateEnd + " " + timeStart + " - " + timeEnd;
        }
    }

    public static String format(long milliseconds) {
        return new SimpleDateFormat("dd/MM/yyyy hh:mma").format(milliseconds);
    }

    public static String format(long milliseconds, String format) {
        return new SimpleDateFormat(format).format(milliseconds);
    }

    public static String formatDayofWeek(long milliseconds) {
        return new SimpleDateFormat("EEEE").format(milliseconds);
    }

    public static String formatTime(long milliseconds) {
        return new SimpleDateFormat("hh:mma").format(milliseconds);
    }

    public static String getHour(long milliseconds) {
        return new SimpleDateFormat("HH").format(milliseconds);
    }

    public static String getMinute(long milliseconds) {
        return new SimpleDateFormat("mm").format(milliseconds);
    }

    public static String getDay(long milliseconds) {
        return new SimpleDateFormat("dd").format(milliseconds);
    }

    public static String getMonth(long milliseconds) {
        return new SimpleDateFormat("M").format(milliseconds);
    }

    public static String getYear(long milliseconds) {
        return new SimpleDateFormat("yyyy").format(milliseconds);
    }

    public static String formatDateToMonth(long milliseconds) {
        return new SimpleDateFormat("MMMM").format(milliseconds);
    }

    public static String formatDate(long milliseconds) {
        return new SimpleDateFormat("dd/MM/yyyy").format(milliseconds);
    }

    public static String formatDateEvent(long timeStart, long timeEnd, long date) {
        String start = new SimpleDateFormat(" hh:mma ").format(timeStart);
        String end = new SimpleDateFormat(" hh:mma ").format(timeEnd);
        String dateStart = new SimpleDateFormat("dd/MM").format(timeStart);
        String dateEnd = new SimpleDateFormat("dd/MM").format(timeEnd);
        if (dateStart.equalsIgnoreCase(dateEnd)) {
            return dateStart + " " + start + "-" + end;
        } else {
            dateStart = new SimpleDateFormat("dd/MM").format(timeStart);
            dateEnd = new SimpleDateFormat("dd/MM").format(timeEnd);
            return dateStart + "-" + dateEnd + " " + start + "-" + end;
        }
    }

    /**
     * Checking two timestamp to know they are in one day or not
     *
     * @param time1 timestamp 1
     * @param time2 timestamp 2
     * @return True if they are in one day
     */
    public static boolean isOneDay(long time1, long time2) {
        return formatDateToDayMonthYear(time1).equalsIgnoreCase(formatDateToDayMonthYear(time2));
    }

    public static long getFirstDayOfWeek() {
        // get today and clear time of day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal.getTimeInMillis();
    }

    public static long getTimeStartOfDay(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis); // compute start of the day for the timestamp
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getTimeEndOfDay(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis); // compute start of the day for the timestamp
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    public static long getLastDayOfWeek() {
        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();
        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        // Print dates of the current week starting on Monday
        c.add(Calendar.DATE, 6);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTimeInMillis();
    }

    public static long getFirstDayOfMonth(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);
        return c.getTimeInMillis();
    }

    public static long getLastDayOfMonth(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DATE, -1);
        return c.getTimeInMillis();
    }

    public static int getWeekOfYear(long millis) {
        Calendar c = Calendar.getInstance(Locale.ROOT);
        c.setTimeInMillis(millis);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getDayOfWeek(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static long addDays(long millis, int days) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        c.add(Calendar.DAY_OF_YEAR, days);
        return c.getTimeInMillis();
    }

    public static long addMonth(long millis, int month) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        c.add(Calendar.MONTH, month);
        return c.getTimeInMillis();
    }

    public static final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1));

    public static String formatTimeNotification(Long dateFrom) {
        return DateTimeUtility.toDuration(System.currentTimeMillis(), dateFrom, Locale.getDefault().getDisplayLanguage());
    }

    private static String toDuration(long now, long time, String language) {
        StringBuilder res = new StringBuilder();
        boolean isVN = language.equals("Tiếng Việt");

        long dateDiff = now - time;
        int daySpan = Math.round(dateDiff / (24 * 60 * 60 * 1000.0f));

        // Display the day if that day longer than 7 days
        if (daySpan > 30) {
            return formatDate(time);
        }

        if (daySpan > 0) {
            res.append(isVN ? daySpan + " ngày trước" : daySpan + " days ago");
        } else {
            int hourSpan = Math.round(dateDiff / (60 * 60 * 1000.0f));
            if (hourSpan > 0) {
                res.append(formatFullTime(time));
            } else {
                int minuteSpan = Math.round(dateDiff / (60 * 1000.0f));
                if (minuteSpan < 0) {
                    res.append(formatFullTime(time));
                } else if (minuteSpan == 0) {
                    res.append(isVN ? "Vài giây trước" : "A few seconds ago");
                } else {
                    res.append(isVN ? minuteSpan + " phút trước" : minuteSpan + " minutes ago");
                }
            }
        }

        return res.toString();
    }

    public static boolean isDateInCurrentWeek(long date) {
        Date date1 = new Date(date);
        return isDateInCurrentWeek(date1);
    }

    public static boolean isTheSameDay(long day1, long day2) {
        return getTimeStartOfDay(day1) == getTimeStartOfDay(day2);
    }

    public static boolean isAfterDay(long day1, long day2) {
        return getTimeStartOfDay(day1) < getTimeStartOfDay(day2);
    }

    public static boolean isBeforeDay(long day1, long day2) {
        return getTimeStartOfDay(day1) > getTimeStartOfDay(day2);
    }

    private static boolean isDateInCurrentWeek(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }
}
