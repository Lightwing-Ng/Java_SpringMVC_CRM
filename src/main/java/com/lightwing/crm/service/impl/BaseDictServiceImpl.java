package com.lightwing.crm.service.impl;

import com.lightwing.crm.mapper.BaseDictMapper;
import com.lightwing.crm.pojo.BaseDict;
import com.lightwing.crm.service.BaseDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseDictServiceImpl implements BaseDictService {
    @Autowired
    private BaseDictMapper baseDictMapper;

    @Override
    public List<BaseDict> getBaseDictByCode(String code) {
        return baseDictMapper.getBaseDictByCode(code);
    }
}
