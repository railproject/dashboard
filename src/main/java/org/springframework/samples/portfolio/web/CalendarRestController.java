package org.springframework.samples.portfolio.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.portfolio.model.Cell;
import org.springframework.samples.portfolio.model.Grid;
import org.springframework.samples.portfolio.service.CalendarService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by star on 4/16/14.
 */
@RestController
@RequestMapping(value="/calendar")
public class CalendarRestController {

    private static final Log logger = LogFactory.getLog(CalendarController.class);

    @Autowired
    private CalendarService calendarService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Cell> getCalendar() throws Exception {
        logger.debug("get all calendars");
        return this.calendarService.getCalendar();
    }

    @RequestMapping(value = "/days", method = RequestMethod.GET)
    public List<String> get40Days() throws Exception {
        logger.debug("get 40 days");
        return this.calendarService.get40Days();
    }

    @RequestMapping(value = "/grid", method = RequestMethod.GET)
    public List<Grid> getGrid(@RequestParam(value="date") String date) throws Exception {
        logger.debug("get grid: " + date);
        return this.calendarService.getGrid(date);
    }
}
