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
import org.springframework.samples.portfolio.model.Grid;
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
        List<Cell> list = new ArrayList<Cell>();;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            LocalDate now = LocalDate.now();
            Map<String, String> params = new HashMap<String, String>();
            params.put("sourceTime", now.toString("yyyy-MM-dd") + " 00:00:00");
            params.put("targetTime", now.plusDays(42).toString("yyyy-MM-dd") + " 00:00:00");
            params.put("code", "01"); // 看板01  分析10
            params.put("timeFormat", "yyyy-MM-dd hh:mm:ss");
            String resp =  getData(params);
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
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
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

    @Override
    public List<Grid> getGrid(String date) {
        List<Grid> list = new ArrayList<Grid>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> params = new HashMap<String, String>();
            LocalDate dd = LocalDate.parse(date);
            params.put("sourceTime", date + " 00:00:00");
            params.put("targetTime", dd.plusDays(1).toString("yyyy-MM-dd") + " 00:00:00");
            params.put("code", "10"); // 看板01  分析10
            params.put("timeFormat", "yyyy-MM-dd hh:mm:ss");
            String resp =  getData(params);

            Map<String, Object> result = objectMapper.readValue(resp, Map.class);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            List<Map<String, Object>> counts = (List<Map<String, Object>>) data.get(0).get("planBureauStatisticsDtos");
            for(Map<String, Object> bb: counts) {
                Grid grid = new Grid();
                grid.setId((String) bb.get("bureauId"));
                grid.setName((String) bb.get("bureauName"));
                grid.setTd_sf_zd((Integer) bb.get("templateSourceTargetTrainlineCounts"));
                grid.setTd_sf_jc((Integer) bb.get("templateSourceSurrenderTrainlineCounts"));
                grid.setTd_jr_zd((Integer) bb.get("templateAccessTargetTrainlineCounts"));
                grid.setTd_jr_jc((Integer) bb.get("templateAccessSurrenderTrainlineCounts"));
                grid.setLk_sf_zd((Integer) bb.get("otherSourceTargetTrainlineCounts"));
                grid.setLk_sf_jc((Integer) bb.get("otherSourceSurrenderTrainlineCounts"));
                grid.setLk_jr_zd((Integer) bb.get("otherAccessTargetTrainlineCounts"));
                grid.setLk_jr_jc((Integer) bb.get("otherAccessSurrenderTrainlineCounts"));
                list.add(grid);
            }
            Collections.sort(list, new Comparator<Grid>() {
                @Override
                public int compare(Grid o1, Grid o2) {
                    if(o1 == null) return -1;
                    if(o2 == null) return 1;
                    if(o1.getCode() <= o2.getCode()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    private int getNum() {
        int t = new java.util.Random().nextInt(99999);
        if(t < 10000) t+=10000;
        return t;
    }

    private String getData(Map<String, String> params) {
        ObjectMapper objectMapper = new ObjectMapper();
        String values = "";
        String resp = "";
        try {
            values = objectMapper.writeValueAsString(params);
            logger.debug("REST REQ POST:" + values);
            ClientResponse response = webResource.path("/rail/plan").type("application/json").accept("application/json").post(ClientResponse.class, values);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            resp = response.getEntity(String.class);
            logger.debug("RESP RESP:" + resp);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
//            e.printStackTrace();
            logger.error("调用REST接口失败");
            logger.error(e.getMessage());
        }
        return resp;
    }

    public static void main(String[] args) {
        /*LocalDate now = LocalDate.now();
        Map<String, String> params = new HashMap<String, String>();
        params.put("sourceTime", now.toString("yyyy-MM-dd") + " 00:00:00");
        params.put("targetTime", now.toString("yyyy-MM-dd") + " 00:00:00");
        params.put("code", "10"); // 看板01  分析10
        params.put("timeFormat", "yyyy-MM-dd hh:mm:ss");
        CalendarServiceImpl service = new CalendarServiceImpl();
        service.getData(params);*/
       /* String resp = "{\"code\":\"200\",\"name\":null,\"dataSize\":1,\"data\":[{\"sourceTime\":\"2014-04-26 00:00:00\",\"targetTime\":\"2014-04-26 00:00:00\",\"timeFormat\":\"yyyy-MM-dd hh:mm:ss\",\"trainlineCounts\":0,\"passengerTrainlineCounts\":0,\"freightTrainlineCounts\":0,\"constructionCounts\":0,\"planBureauStatisticsDtos\":[{\"bureauId\":\"b1262b3f-b0c6-4a23-af39-8c5e6f82ff7a\",\"bureauName\":\"哈尔滨铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"4c64d097-f655-4bed-9a5d-8a0b907c0236\",\"bureauName\":\"乌鲁木齐铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"9cd643b7-a1d0-478e-aac6-4b95cc023703\",\"bureauName\":\"昆明铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"e440a4ed-4946-4c56-8908-44dbddab1c82\",\"bureauName\":\"成都铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"2f3264c9-ff63-4fb8-b420-974f331136b0\",\"bureauName\":\"郑州铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"968b2154-94b3-48f5-85d5-cbc08534d409\",\"bureauName\":\"青藏铁路公司\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"cdc85da6-cd23-4037-83b4-2192309f6870\",\"bureauName\":\"南昌铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"7b67eef8-de46-40df-bd8b-a3c1db603b97\",\"bureauName\":\"沈阳铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"1ceca80f-2860-47fe-a3f3-415b5fee9e20\",\"bureauName\":\"北京铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"c2cdc0d0-a673-41c7-9e70-0dfb3dcf3ec2\",\"bureauName\":\"太原铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"0781b3c8-6072-4c05-b83d-95612f50b394\",\"bureauName\":\"兰州铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"b7586bd7-0757-4a9a-a343-3f443a62423f\",\"bureauName\":\"上海铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"75239882-8fc6-474d-affe-aada4f5210a7\",\"bureauName\":\"武汉铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"b33ac577-ca70-43a4-9008-7ed9ffcd4843\",\"bureauName\":\"广州铁路（集团）公司\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"0468d138-3ef0-4f2a-bae8-63349382571a\",\"bureauName\":\"南宁铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"d03a250a-b06a-425f-83f2-28f4314f5623\",\"bureauName\":\"济南铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"990571dc-c10d-4066-a04c-36431922af0c\",\"bureauName\":\"西安铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0},{\"bureauId\":\"f7f67875-7e7b-4058-9455-30c32d7f0153\",\"bureauName\":\"呼和浩特铁路局\",\"templateSourceTargetTrainlineCounts\":0,\"templateSourceSurrenderTrainlineCounts\":0,\"templateAccessTargetTrainlineCounts\":0,\"templateAccessSurrenderTrainlineCounts\":0,\"otherSourceTargetTrainlineCounts\":0,\"otherSourceSurrenderTrainlineCounts\":0,\"otherAccessTargetTrainlineCounts\":0,\"otherAccessSurrenderTrainlineCounts\":0}]}],\"exceptionSize\":0,\"exceptions\":[]}";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> result = objectMapper.readValue(resp, Map.class);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(0).get("planBureauStatisticsDtos");
            for(Map<String, Object> bb: list) {
                Grid grid = new Grid();
                grid.setId((String) bb.get("bureauId"));
                grid.setName((String) bb.get("bureauName"));
                grid.setTd_sf_zd((Integer) bb.get("templateSourceTargetTrainlineCounts"));
                grid.setTd_sf_jc((Integer) bb.get("templateSourceSurrenderTrainlineCounts"));
                grid.setTd_jr_zd((Integer) bb.get("templateAccessTargetTrainlineCounts"));
                grid.setTd_jr_jc((Integer) bb.get("templateAccessSurrenderTrainlineCounts"));
                grid.setLk_sf_zd((Integer) bb.get("otherSourceTargetTrainlineCounts"));
                grid.setLk_sf_jc((Integer) bb.get("otherSourceSurrenderTrainlineCounts"));
                grid.setLk_jr_zd((Integer) bb.get("otherAccessTargetTrainlineCounts"));
                grid.setLk_jr_jc((Integer) bb.get("otherAccessSurrenderTrainlineCounts"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        LocalDate date = LocalDate.parse("2014-04-26");
        System.out.println(date.plusDays(1).toString("yyyy-MM-dd"));
    }

}
