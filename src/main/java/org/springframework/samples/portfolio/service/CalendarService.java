package org.springframework.samples.portfolio.service;


import org.springframework.samples.portfolio.model.Cell;
import org.springframework.samples.portfolio.model.Grid;
import org.springframework.samples.portfolio.model.Grid2;

import java.util.List;

/**
 * Created by star on 4/14/14.
 */
public interface CalendarService {
    List<Cell> getCalendar();
    List<String> get40Days();
    List<Grid> getGrid(String date);
    List<Grid2> getGrid2(String date, String type);
}
