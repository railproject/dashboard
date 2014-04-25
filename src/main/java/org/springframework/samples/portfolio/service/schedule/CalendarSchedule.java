package org.springframework.samples.portfolio.service.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.samples.portfolio.model.Cell;
import org.springframework.samples.portfolio.service.CalendarService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by star on 4/14/14.
 */
@Service
public class CalendarSchedule {

    private static final Log logger = LogFactory.getLog(CalendarSchedule.class);

    private static List<Cell> preList = new ArrayList<Cell>();

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private CalendarService calendarService;

    @Scheduled(fixedDelay=3000)
    public void updateCalendar() {
        logger.debug("Scheduled: updateCalendar");
        List<Cell> list = calendarService.getCalendar();
        if(preList.containsAll(list)) {
            logger.info("没有数据更新!");
        } else {
            this.messagingTemplate.convertAndSend("/topic/calendar.update", list);
        }
        preList = list;
    }

    public static void main(String[] args) {
        List<Cell> list = new ArrayList<Cell>();
        Cell cell = new Cell();
        cell.setDate(LocalDate.now().toString("yyyy-MM-dd"));
        cell.setDayOfWeek(LocalDate.now().dayOfWeek().getAsShortText(Locale.CHINA));
        cell.setTd(1234);
        cell.setSg(2345);
        list.add(cell);

        List<Cell> list1 = new ArrayList<Cell>();
        Cell cell1 = new Cell();
        cell1.setDate(LocalDate.now().toString("yyyy-MM-dd"));
        cell1.setDayOfWeek(LocalDate.now().dayOfWeek().getAsShortText(Locale.CHINA));
        cell1.setTd(1234);
        cell1.setSg(2345);
        list1.add(cell1);

        System.out.println(cell.getDate().hashCode());
        System.out.println(cell1.getDate().hashCode());
        System.out.println(cell.equals(cell1));
        System.out.println(list.contains(cell1));
        System.out.println(list.containsAll(list1));
    }
}
