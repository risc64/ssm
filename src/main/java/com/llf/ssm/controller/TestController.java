package com.llf.ssm.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.llf.ssm.service.TestService;
import com.llf.ssm.bo.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import java.util.UUID;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.llf.ssm.util.JsonUtil;
import com.llf.ssm.util.StatusEnum;
import com.llf.ssm.util.ResultJSON;

/**
 * 测试表 接口
 * @Author 李良发
 * @Date 2019-12-26
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Resource
    public TestService testService;

    /**
     * 分页查询
     * @param
     * @param page
     * @return
     */
    @RequestMapping(value="/select/page", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getSelectPage(@RequestBody Test record, @ModelAttribute Page page) {
        ResultJSON result = new ResultJSON();
        try {
            if(page.getSize() == 0) {
                page.setSize(10);
                page.setCurrent(1);
            }
            IPage<Test> t = testService.selectByProperty(page, record);
            if(t != null) {
                result.setStatus(StatusEnum.SUCCESS.getKey());
                result.setMsg(StatusEnum.SUCCESS.getMsg());
                result.setData(t);
            } else {
                result.setStatus(StatusEnum.NOTDATA.getKey());
                result.setMsg(StatusEnum.NOTDATA.getMsg());
            }
        } catch (Exception e) {
            logger.error("TestController: " + e.toString());
            result.setStatus(StatusEnum.EXCEPTION.getKey());
            result.setMsg(StatusEnum.EXCEPTION.getMsg());
        }
        return JsonUtil.objToJsonString(result);
    }

    /**
     * 条件查询不分页
     * @param
     * @return
     */
    @RequestMapping(value="/select/property", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getSelectByProperty(@RequestBody Test record) {
        ResultJSON result = new ResultJSON();
        try {
            List<Test> list = testService.selectByProperty(record);
            if(list != null) {
                result.setStatus(StatusEnum.SUCCESS.getKey());
                result.setMsg(StatusEnum.SUCCESS.getMsg());
                result.setData(list);
            } else {
                result.setStatus(StatusEnum.NOTDATA.getKey());
                result.setMsg(StatusEnum.NOTDATA.getMsg());
            }
        } catch (Exception e) {
            logger.error("TestController: " + e.toString());
            result.setStatus(StatusEnum.EXCEPTION.getKey());
            result.setMsg(StatusEnum.EXCEPTION.getMsg());
        }
        return JsonUtil.objToJsonString(result);
    }

    /**
     * 根据id查询记录
     * @param
     * @return
     */
    @RequestMapping(value="/select/{id}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getSelectPage(@PathVariable String id) {
        ResultJSON result = new ResultJSON();
        try {
            Test record = testService.selectByPrimaryKey(id);
            if(record != null) {
                result.setStatus(StatusEnum.SUCCESS.getKey());
                result.setMsg(StatusEnum.SUCCESS.getMsg());
                result.setData(record);
            } else {
                result.setStatus(StatusEnum.NOTDATA.getKey());
                result.setMsg(StatusEnum.NOTDATA.getMsg());
            }
        } catch (Exception e) {
            logger.error("TestController: " + e.toString());
            result.setStatus(StatusEnum.EXCEPTION.getKey());
            result.setMsg(StatusEnum.EXCEPTION.getMsg());
        }
        return JsonUtil.objToJsonString(result);
    }

    /**
     * 根据id集合批量删除
     * @param
     * @return
     */
    @RequestMapping(value ="/delete/batch", method = RequestMethod.DELETE, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteBatch(@RequestBody List<String> list) {
        ResultJSON result = new ResultJSON();
        try {
            int changeNum =  testService.deleteBatch(list);
            if(changeNum != 0) {
                result.setStatus(StatusEnum.SUCCESS.getKey());
                result.setMsg(StatusEnum.SUCCESS.getMsg());
            } else {
                result.setStatus(StatusEnum.ERROR.getKey());
                result.setMsg(StatusEnum.ERROR.getMsg());
            }
        } catch(Exception e) {
            logger.error("TestController: " + e.toString());
            result.setStatus(StatusEnum.EXCEPTION.getKey());
            result.setMsg(StatusEnum.EXCEPTION.getMsg());
        }
        return JsonUtil.objToJsonString(result);
    }

    /**
     * 批量新增
     * @param
     * @return
     */
    @RequestMapping(value = "/insert/batch", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String insertBatch(@RequestBody List<Test> list) {
        ResultJSON result = new ResultJSON();
            for(int i = 0; i < list.size(); i++) {
                list.get(i).setUid(UUID.randomUUID().toString().replace("-", ""));
            }
        try {
            int changeNum = testService.insertBatch(list);
            if(changeNum != 0) {
                result.setStatus(StatusEnum.SUCCESS.getKey());
                result.setMsg(StatusEnum.SUCCESS.getMsg());
                result.setData(list);
            } else {
                result.setStatus(StatusEnum.ERROR.getKey());
                result.setMsg(StatusEnum.ERROR.getMsg());
            }
        } catch(Exception e) {
            logger.error("TestController: " + e.toString());
            result.setStatus(StatusEnum.ERROR.getKey());
            result.setMsg(StatusEnum.ERROR.getMsg());
        }
        return JsonUtil.objToJsonString(result);
    }

    /**
     * 编辑
     * @param
     * @return
     */
    @RequestMapping(value ="/update", method = RequestMethod.PUT, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateByPrimaryKey(@RequestBody Test record) {
        ResultJSON result = new ResultJSON();
            try {
            int changeNum = testService.updateByPrimaryKey(record);
            if(changeNum != 0) {
                result.setStatus(StatusEnum.SUCCESS.getKey());
                result.setMsg(StatusEnum.SUCCESS.getMsg());
                result.setData(record);
            } else {
                result.setStatus(StatusEnum.ERROR.getKey());
                result.setMsg(StatusEnum.ERROR.getMsg());
            }
        } catch(Exception e) {
            logger.error("TestController: " + e.toString());
            result.setStatus(StatusEnum.EXCEPTION.getKey());
            result.setMsg(StatusEnum.EXCEPTION.getMsg());
        }
        return JsonUtil.objToJsonString(result);
    }


}