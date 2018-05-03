package com.lei.main.comm.dao.jdbc;

import com.lei.main.comm.dao.BaseDao;
import com.lei.main.comm.dao.page.DataStore;
import com.lei.main.comm.dao.page.PagingParameter;
import com.lei.main.comm.dao.page.PagingSqlBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public abstract class BaseDaoImpl implements BaseDao {

    private String tableName;

    @Autowired
    @Qualifier("jdbcTemplate")
    public JdbcTemplate jdbcTemplate;

    protected Logger log = Logger.getLogger(BaseDaoImpl.class);

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void update(String sql, Object[] par) throws Exception{
        jdbcTemplate.update(sql, par);
    }

    public int delete(String whereSql) throws Exception {
        return this.jdbcTemplate.update("delete from "+getTableName()+" where "+ whereSql);
    }

    private DataStore getDatas(String sql, int curPage, int pageSize, int queryCount, JdbcTemplate jd){
        int totalRows = getRecordCounts(sql, jd, queryCount);
        PagingParameter pagingParameter = new PagingParameter(curPage,pageSize,totalRows);
        List list = getRecordData(sql, pagingParameter, jd);
        return new DataStore(totalRows,list);
    }
    /**
     * 分页,查询总记录限制为10000
     * 原型返回
     * @param sql            查询SQL
     * @param curPage        当前页
     * @param pageSize       页大小
     */
    @Override
    public DataStore findPageForMap(String sql, int curPage, int pageSize) {
        return getDatas(sql, curPage, pageSize, 10000, jdbcTemplate);
    }
    /**
     * 查询总记录数
     * @param jdt    外部数据源
     * @param sql    查询SQL
     * @param limit  0=不限制查询数量
     * @return
     */
    @Override
    public int getRecordCounts(String sql,JdbcTemplate jdt, int limit){
        String countSql = PagingSqlBuilder.getCountSql(sql, limit);
        long _1 = System.currentTimeMillis();
        int records = jdt.queryForInt(countSql);
        long _2 = System.currentTimeMillis();

        log.info("COUNT("+(_2-_1)+"ms)-->"+ countSql);
        return records;
    }
    /**
     * 获取分页数据
     * @param sql              查询SQL
     * @param pagingParameter  pagingParameter=null则不分页
     * @return
     */
    @Override
    public List getRecordData(String sql, PagingParameter pagingParameter,JdbcTemplate jdt){
        String paginationSQL = PagingSqlBuilder.getPagingSql(sql, pagingParameter);
        long _1 = System.currentTimeMillis();
        List list = jdt.queryForList(paginationSQL);
        long _2 = System.currentTimeMillis();
        log.info("PAGE("+(_2-_1)+"ms)-->"+ paginationSQL);
        return list;
    }
}
