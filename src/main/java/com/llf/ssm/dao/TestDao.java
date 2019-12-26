package com.llf.ssm.dao;

import com.llf.ssm.bo.Test;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 测试表 Dao
 * @Author 李良发
 * @Date 2019-12-26
 */
public interface TestDao {

        /**
    *  根据主键获取
    */
    Test selectByPrimaryKey(String uid);

    /**
    *  根据主键删除
    */
    int deleteByPrimaryKey(String uid);

    /**
    *  批量删除
    */
    int deleteBatch(List<String> list);

    /**
    *  新增
    */
    int insert(Test record);

    /**
    *  修改
    */
    int updateByPrimaryKey(Test record);

    /**
    *  条件查询
    */
    List<Test> selectByProperty(@Param("record") Test record);

    /**
    *  批量新增
    */
    int insertBatch(List<Test> list);

    /**
    *  分页查询
    */
    IPage<Test> selectByProperty(Page page, @Param("record") Test record);
}