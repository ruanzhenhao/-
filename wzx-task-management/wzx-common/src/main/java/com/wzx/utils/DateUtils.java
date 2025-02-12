package com.wzx.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {
    //    生成时间列表
    public static List<LocalDate> dateService(LocalDate begin, LocalDate end)
    {
        //用于存放从开始时间到结束时间的日期信息
        List<LocalDate> localDates = new ArrayList<>();

        localDates.add(begin);
        while (!begin.equals(end))
        {
            begin=begin.plusDays(1);
            localDates.add(begin);
        }

        return localDates;
    }
    public static List<String> dateChangeString(List<LocalDate> localDates)
    {
        List<String> detaList = new ArrayList<>();
        for (LocalDate localDate : localDates)
        {
            detaList.add(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        return detaList;
    }
}
