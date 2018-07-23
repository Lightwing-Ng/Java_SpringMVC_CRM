package com.lightwing.crm.service;

import com.lightwing.crm.pojo.BaseDict;

import java.util.List;

/**
 * 字典数据表业务逻辑接口
 *
 * @author Lightwing Ng
 */
public interface BaseDictService {
    /**
     * 跟据字典编码查询字典列表
     *
     * @param code
     * @return
     */
    List<BaseDict> getBaseDictByCode(String code);
}
