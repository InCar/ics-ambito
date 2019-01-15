package com.incarcloud.ics.ambito.entity;

import com.incarcloud.ics.ambito.jdbc.Id;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/15
 */
public class SuperBaseBean implements Serializable {

    private static final long serialVersionUID = -4375476220053935738L;

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuperBaseBean)) return false;
        SuperBaseBean that = (SuperBaseBean) o;
        return Objects.equals(getId(), that.getId());
    }
}
