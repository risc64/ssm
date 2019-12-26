package com.llf.ssm.bo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 测试表
 * @Author 李良发
 * @Date 2019-12-26
 */
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;
            /**
     * UID
     */
    private String uid;

    /**
     * 名字
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    
    public String getUid () {
        return uid;
    }
    public void setUid (String uid) {
        this.uid = uid;
    }
    public String getName () {
        return name;
    }
    public void setName (String name) {
        this.name = name;
    }
    public String getSex () {
        return sex;
    }
    public void setSex (String sex) {
        this.sex = sex;
    }
    public Date getCreateTime () {
        return createTime;
    }
    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }


}
