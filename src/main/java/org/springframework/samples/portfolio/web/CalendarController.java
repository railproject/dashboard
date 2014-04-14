package org.springframework.samples.portfolio.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.samples.portfolio.model.Cell;
import org.springframework.samples.portfolio.service.CalendarService;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

/**
 * Created by star on 4/14/14.
 */
@Controller
public class CalendarController {
    private static final Log logger = LogFactory.getLog(CalendarController.class);

    @Autowired
    private CalendarService calendarService;

    @SubscribeMapping("/calendars")
    public List<Cell> getCalendar(Principal principal) throws Exception {
        logger.debug("Positions for " + principal.getName());
        return this.calendarService.getCalendar();
    }
}
