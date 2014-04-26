package org.springframework.samples.portfolio.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.portfolio.dao.JdbcService;
import org.springframework.samples.portfolio.model.Cell;
import org.springframework.samples.portfolio.service.CalendarService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by star on 4/14/14.
 */
@Service
public class CalendarServiceImpl implements CalendarService{

    private static Log logger = LogFactory.getLog(CalendarServiceImpl.class);

    @Autowired
    private WebResource webResource;

    @Autowired
    private JdbcService jdbcService;


    @Override
    public List<Cell> getCalendar() {
        /*LocalDate date = LocalDate.now();
        List<Cell> list = new ArrayList<Cell>();
        for(int i = 0; i < 42; i++) {
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
        Map<String, Object> result = jdbcService.getCounts();
        Cell cell = list.get(0);
        cell.setTd_sj((Integer)result.get("kc"));
        cell.setHc_sj((Integer)result.get("hc"));*/
        return getCount();
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

    private List<Cell> getCount() {
        List<Cell> list = new ArrayList<Cell>();
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDate now = LocalDate.now();
        Map<String, String> params = new HashMap<String, String>();
        params.put("sourceTime", now.toString("yyyy-MM-dd") + " 00:00:00");
        params.put("targetTime", now.plusDays(42).toString("yyyy-MM-dd") + " 00:00:00");
        params.put("code", "01");
        params.put("timeFormat", "yyyy-MM-dd hh:mm:ss");
        //groupByDay:false


//        Client client = Client.create();
//
//        WebResource webResource = client.resource("http://10.1.191.135:7003/rail/plan");


        String values = null;
        try {
            values = objectMapper.writeValueAsString(params);
            logger.debug("REST REQ POST:" + values);


            ClientResponse response = webResource.path("/rail/plan").type("application/json").accept("application/json").post(ClientResponse.class, values);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            String resp = response.getEntity(String.class);
            logger.debug("RESP RESP:" + resp);

            Map<String, Object> result = objectMapper.readValue(resp, Map.class);

            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            for(Map<String, Object> dd: data) {
                Cell cell = new Cell();
                cell.setDate((String) dd.get("sourceTime"));
                cell.setTd((Integer) dd.get("trainlineCounts"));
                list.add(cell);
            }
            Collections.sort(list, new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    LocalDate d1 = LocalDate.parse(o1.getDate());
                    LocalDate d2 = LocalDate.parse(o2.getDate());
                    return d1.compareTo(d2);
                }
            });
            Map<String, Object> counts = jdbcService.getCounts();
            Cell cell = list.get(0);
            cell.setTd_sj(Integer.parseInt(counts.get("kc").toString()));
            cell.setHc_sj(Integer.parseInt(counts.get("hc").toString()));
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
            logger.error(e);
        } catch (IOException e) {
//            e.printStackTrace();
            logger.error(e);
        }
        return list;
    }

    public static void main(String[] args) {
        CalendarServiceImpl service = new CalendarServiceImpl();
        service.getCount();
    }
}
