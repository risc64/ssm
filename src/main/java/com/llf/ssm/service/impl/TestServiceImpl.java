package com.llf.ssm.service.impl;

import com.llf.ssm.bo.Test;
import com.llf.ssm.dao.TestDao;
import com.llf.ssm.service.TestService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;
import java.util.List;

/**
 * 测试表 服务实现类
 * @Author 李良发
 * @Date 2019-12-26
 */
@Service
@Transactional
public class TestServiceImpl  implements TestService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Resource
    TestDao testDao;

        /**
    *  根据主键获取
    */
    @Override
    public Test selectByPrimaryKey(String uid) {
        return testDao.selectByPrimaryKey(uid);
    }

    /**
    *  根据主键删除
    */
    @Override
    public  int deleteByPrimaryKey(String uid) {
        return testDao.deleteByPrimaryKey(uid);
    }

    /**
    *  批量删除
    */
    @Override
    public  int deleteBatch(List<String> list) {
        return testDao.deleteBatch(list);
    }

    /**
    *  新增
    */
    @Override
    public int insert(Test record) {
        return testDao.insert(record);
    }

    /**
    *  修改
    */
    @Override
    public int updateByPrimaryKey(Test record) {
        return testDao.updateByPrimaryKey(record);
    }

    /**
    *  条件查询
    */
    @Override
    public List<Test> selectByProperty(Test record) {
        return testDao.selectByProperty(record);
    }

    /**
    *  批量新增
    */
    @Override
    public int insertBatch(List<Test> list) {
        return testDao.insertBatch(list);
    }

    /**
    *  分页查询
    */
    @Override
    public IPage<Test> selectByProperty(Page<Test> page, Test record) {
        return testDao.selectByProperty(page, record);
    }
}