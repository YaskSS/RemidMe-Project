package com.example.yass.remindme.dto;

import java.util.List;

/**
 * Created by yass on 1/31/17.
 */

public class RemindDto {

    private String status;
    private String meta;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public RemindDto(){}

    public RemindDto(String title) {
        this.status = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
