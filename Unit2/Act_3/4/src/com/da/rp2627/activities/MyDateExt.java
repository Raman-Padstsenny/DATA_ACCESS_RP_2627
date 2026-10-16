package com.da.rp2627.activities;

public class MyDateExt extends MyDate {
    @Override
    public void setDay(int day) {
        checkDate(day, getMonth(), getYear());
        super.setDay(day);
    }

    @Override
    public void setMonth(int month) {
        checkDate(getDay(), month, getYear());
        super.setMonth(month);
    }

    @Override
    public void setYear(int year) {
        checkDate(getDay(), getMonth(), year);
        super.setYear(year);
    }

    public boolean isLeapYear() {
        return isLeapYear(getYear());
    }

    private boolean isLeapYear(int year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

    private void checkDate(int day, int month, int year) {
        int daysInMonth = 31;
        if (month == 2) {
            daysInMonth = isLeapYear(year) ? 29 : 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        }

        if (day < 1 || day > daysInMonth) {
            throw new IllegalArgumentException(
                    "Day must be between 1 and " + daysInMonth + " for this month and year.");
        }
    }
}
