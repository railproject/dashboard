package org.springframework.samples.portfolio.dao;

import java.util.Map;

/**
 * Created by star on 4/25/14.
 */
public interface JdbcService {
    Map<String, Object> getCounts();
}
