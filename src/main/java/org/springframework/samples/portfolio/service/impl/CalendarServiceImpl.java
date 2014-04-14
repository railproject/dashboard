package org.springframework.samples.portfolio.service.impl;

import org.springframework.samples.portfolio.model.Cell;
import org.springframework.samples.portfolio.service.CalendarService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by star on 4/14/14.
 */
@Service
public class CalendarServiceImpl implements CalendarService{

    @Override
    public List<Cell> getCalendar() {
        List<Cell> list = new ArrayList<Cell>();
        Cell cell = new Cell();
        cell.setZysx(12345);
        cell.setTd(12345);
        cell.setLk(12345);
        cell.setQt(12345);
        cell.setHc(12345);
        cell.setSg(12345);
        list.add(cell);
        return list;
    }
}
