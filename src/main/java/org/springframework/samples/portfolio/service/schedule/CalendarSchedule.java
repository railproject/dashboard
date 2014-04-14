package org.springframework.samples.portfolio.service.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.samples.portfolio.service.CalendarService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by star on 4/14/14.
 */
@Service
public class CalendarSchedule {

    private static final Log logger = LogFactory.getLog(CalendarSchedule.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private CalendarService calendarService;

    @Scheduled(fixedDelay=3000)
    public void updateCalendar() {
        this.messagingTemplate.convertAndSend("/topic/calendar.update", calendarService.getCalendar());
    }
}
