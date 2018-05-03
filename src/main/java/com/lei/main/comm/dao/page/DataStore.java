package com.lei.main.comm.dao.page;

import com.lei.main.comm.bean.Entity;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;


/**
 * 封装响应的分页数据(数据模型)
 * 线程不安全
 */
@ApiModel("分页数据")
public class DataStore extends Entity {
    
    private static final long serialVersionUID = -4959755652836494752L;

    @ApiModelProperty("总记录数")
    private Integer total;
    @ApiModelProperty("当前分页数据")
    private Object rows;
    /**
     * @param records 总记录
     * @param datas     数据
     */
    public DataStore(int records, Object datas) {
        this.total = records;
        this.rows = datas;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }
}
