package org.springframework.samples.portfolio.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.portfolio.model.Cell;
import org.springframework.samples.portfolio.service.CalendarService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/list", method= RequestMethod.GET)
    public List<Cell> getCalendar() throws Exception {
        logger.debug("get all calendars");
        return this.calendarService.getCalendar();
    }
}