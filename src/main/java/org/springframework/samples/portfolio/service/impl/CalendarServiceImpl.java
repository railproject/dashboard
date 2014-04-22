package org.springframework.samples.portfolio.service.impl;

import org.joda.time.LocalDate;
import org.springframework.samples.portfolio.model.Cell;
import org.springframework.samples.portfolio.service.CalendarService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by star on 4/14/14.
 */
@Service
public class CalendarServiceImpl implements CalendarService{

    @Override
    public List<Cell> getCalendar() {
        LocalDate date = LocalDate.now();
        List<Cell> list = new ArrayList<Cell>();
        for(int i = 0; i < 40; i++) {
            Cell cell = new Cell();
            cell.setDate(date.plusDays(i).toString("yyyy-MM-dd"));
            cell.setDayOfWeek(date.plusDays(i).dayOfWeek().getAsShortText(Locale.CHINA));
            cell.setZysx(getNum());
            cell.setTd(getNum());
            cell.setLk(getNum());
            cell.setQt(getNum());
            cell.setHc(getNum());
            cell.setSg(getNum());
            list.add(cell);
        }
        return list;
    }

    @Override
    public List<String> get40Days() {
        List<String> list = new ArrayList<String>();
        LocalDate today = LocalDate.now();
        for(int i = 0; i< 40; i++) {
            list.add(today.plusDays(i).toString("yyyy-MM-dd"));
        }
        return list;
    }

    private int getNum() {
        int t = new java.util.Random().nextInt(99999);
        if(t < 10000) t+=10000;
        return t;
    }
}
