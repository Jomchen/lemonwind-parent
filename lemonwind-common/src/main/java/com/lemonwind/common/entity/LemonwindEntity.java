package com.lemonwind.common.entity;

import java.io.Serializable;
import java.util.Date;

public class LemonwindEntity <T> implements Serializable, Comparable<T>, Cloneable {

    protected Long id;

    protected Date createTime;

    protected Date updateTime;

    public LemonwindEntity() {
        super();
    }

    public LemonwindEntity(Long id) {
        super();
        this.id = id;
    }

    public LemonwindEntity(Long id, Date createTime, Date updateTime) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int compareTo(T o) { return equals(o) ? 0 : 1; }

}
