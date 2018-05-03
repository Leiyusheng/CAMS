package com.lei.main.comm.util;

import com.lei.main.comm.bean.DictionaryEntity;

import java.util.HashMap;
import java.util.Map;


public class Constant {
    public static final String courseSet = "courses";
    public static final String groupPre = "g";

    public static final String schemaPattern = "KK";
    public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String sqlDateFormat = "yyyy-mm-dd HH24:mi:ss";

    public static Map<String,DictionaryEntity> DUser = new HashMap<String,DictionaryEntity>();//用户名
    public static Map<String,DictionaryEntity> DGoodsType = new HashMap<String,DictionaryEntity>();//商品种类
    public static Map<String,DictionaryEntity> DGoodsOperate = new HashMap<String,DictionaryEntity>();//商品操作
}