package com.llf.ssm.service;

import com.llf.ssm.bo.Test;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

/**
 * 测试表 服务类
 * @Author 李良发
 * @Date 2019-12-26
 */
public interface TestService {

        /**
    *  根据主键获取
    */
    public Test selectByPrimaryKey(String uid);

    /**
    *  根据主键删除
    */
    public  int deleteByPrimaryKey(String uid);

    /**
    *  批量删除
    */
    public  int deleteBatch(List<String> list);

    /**
    *  新增
    */
    public int insert(Test record);

    /**
    *  修改
    */
    public int updateByPrimaryKey(Test record);

    /**
    *  条件查询
    */
    public List<Test> selectByProperty(Test record);

    /**
    *  批量新增
    */
    public int insertBatch(List<Test> list);

    /**
    *  分页查询
    */
    public IPage<Test> selectByProperty(Page<Test> page, Test record);

}