package com.lei.main.comm.dao;

import com.lei.main.comm.dao.page.DataStore;
import com.lei.main.comm.dao.page.PagingParameter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface BaseDao {

    void update(String sql, Object[] par) throws Exception;

    int delete(String whereSql) throws Exception;

    DataStore findPageForMap(String sql, int curPage, int pageSize);

    int getRecordCounts(String sql, JdbcTemplate jdt, int limit);

    List getRecordData(String sql, PagingParameter pagingParameter, JdbcTemplate jdt);
}
