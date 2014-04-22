package org.springframework.samples.portfolio.service;


import org.springframework.samples.portfolio.model.Cell;
import java.util.List;

/**
 * Created by star on 4/14/14.
 */
public interface CalendarService {
    List<Cell> getCalendar();
    List<String> get40Days();
}
