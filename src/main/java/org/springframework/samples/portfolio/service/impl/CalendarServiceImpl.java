package org.springframework.samples.portfolio.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.portfolio.dao.JdbcService;
import org.springframework.samples.portfolio.model.Cell;
import org.springframework.samples.portfolio.model.Grid;
import org.springframework.samples.portfolio.model.Grid2;
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

    @Override
    public List<Grid2> getGrid2(String date, String type) {
        List<Grid2> list = new ArrayList<Grid2>();
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
                Grid2 grid = new Grid2();
                grid.setId((String) bb.get("bureauId"));
                grid.setName((String) bb.get("bureauName"));
                grid.setShortname((String) bb.get("bureauShortName"));
                grid.setCode((Integer) bb.get("bureauCode"));
                List<Map<String, Object>> cc = (List<Map<String, Object>>) bb.get("planBureauTsDtos");
                if("sf".equals(type)) {
                    for(Map<String, Object> ee: cc) {
                        if("客运图定".equals((String)ee.get("id"))) {
                            grid.setTd_jc((Integer) ee.get("sourceSurrenderTrainlineCounts"));
                            grid.setTd_zd((Integer) ee.get("sourceTargetTrainlineCounts"));
                        } else if("客运临客".equals((String)ee.get("id"))) {
                            grid.setLk_jc((Integer) ee.get("sourceSurrenderTrainlineCounts"));
                            grid.setLk_zd((Integer) ee.get("sourceTargetTrainlineCounts"));
                        } else if("货运".equals((String)ee.get("id"))) {
                            grid.setHc_jc((Integer) ee.get("sourceSurrenderTrainlineCounts"));
                            grid.setHc_zd((Integer) ee.get("sourceTargetTrainlineCounts"));
                        } else if("行包专列".equals((String)ee.get("id"))) {
                            grid.setXywd_jc((Integer) ee.get("sourceSurrenderTrainlineCounts"));
                            grid.setXywd_zd((Integer) ee.get("sourceTargetTrainlineCounts"));
                        } else if("路用".equals((String)ee.get("id"))) {
                            grid.setLy_jc((Integer) ee.get("sourceSurrenderTrainlineCounts"));
                            grid.setLy_zd((Integer) ee.get("sourceTargetTrainlineCounts"));
                        }
                    }
                } else if("jr".equals(type)) {
                    for(Map<String, Object> ee: cc) {
                        if("客运图定".equals((String)ee.get("id"))) {
                            grid.setTd_jc((Integer) ee.get("accessSurrenderTrainlineCounts"));
                            grid.setTd_zd((Integer) ee.get("accessTargetTrainlineCounts"));
                        } else if("客运临客".equals((String)ee.get("id"))) {
                            grid.setLk_jc((Integer) ee.get("accessSurrenderTrainlineCounts"));
                            grid.setLk_zd((Integer) ee.get("accessTargetTrainlineCounts"));
                        } else if("货运".equals((String)ee.get("id"))) {
                            grid.setHc_jc((Integer) ee.get("accessSurrenderTrainlineCounts"));
                            grid.setHc_zd((Integer) ee.get("accessTargetTrainlineCounts"));
                        } else if("行包专列".equals((String)ee.get("id"))) {
                            grid.setXywd_jc((Integer) ee.get("accessSurrenderTrainlineCounts"));
                            grid.setXywd_zd((Integer) ee.get("accessTargetTrainlineCounts"));
                        } else if("路用".equals((String)ee.get("id"))) {
                            grid.setLy_jc((Integer) ee.get("accessSurrenderTrainlineCounts"));
                            grid.setLy_zd((Integer) ee.get("accessTargetTrainlineCounts"));
                        }
                    }
                }
                list.add(grid);
            }
            Collections.sort(list, new Comparator<Grid2>() {
                @Override
                public int compare(Grid2 o1, Grid2 o2) {
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
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDate now = LocalDate.now();
        Map<String, String> params = new HashMap<String, String>();
        params.put("sourceTime", now.toString("yyyy-MM-dd") + " 00:00:00");
        params.put("targetTime", now.toString("yyyy-MM-dd") + " 00:00:00");
        params.put("code", "10"); // 看板01  分析10
        params.put("timeFormat", "yyyy-MM-dd hh:mm:ss");
        try {
            String values = objectMapper.writeValueAsString(params);
            Client client = Client.create();
            ClientResponse response = client.resource("http://10.1.191.135:7003").path("/rail/plan").type("application/json").accept("application/json").post(ClientResponse.class, values);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            String resp = response.getEntity(String.class);
            System.out.println(resp);

            Map<String, Object> result = objectMapper.readValue(resp, Map.class);
            List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
            List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(0).get("planBureauStatisticsDtos");
            for(Map<String, Object> bb: list) {
                System.out.println(bb.get("bureauId"));
                System.out.println(bb.get("bureauName"));
                System.out.println(bb.get("bureauShortName"));
                System.out.println(bb.get("bureauCode"));

                List<Map<String, Object>> cc = (List<Map<String, Object>>) bb.get("planBureauTsDtos");
                for(Map<String, Object> dd: cc) {
                    System.out.println(dd.get("id"));
                    System.out.println(dd.get("sourceTargetTrainlineCounts"));
                    System.out.println(dd.get("sourceSurrenderTrainlineCounts"));
                    System.out.println(dd.get("accessTargetTrainlineCounts"));
                    System.out.println(dd.get("accessSurrenderTrainlineCounts"));
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
