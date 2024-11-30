package com.se.scrumflow.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

    public static List<Date> getDateList(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<>();

        // 设置 startDate 时间为当天的 00:00:00
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // 设置 endDate 时间为当天的 23:59:59
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 999); // 确保最后的毫秒数是 999

        // 获取修改后的 endDate
        Date adjustedEndDate = endCalendar.getTime();

        // 遍历日期，直到调整后的 endDate
        calendar.setTime(startDate);
        while (!calendar.getTime().after(adjustedEndDate)) {
            dateList.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dateList;
    }

}
