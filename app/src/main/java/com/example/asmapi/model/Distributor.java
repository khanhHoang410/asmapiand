package com.example.asmapi.model;

import com.google.gson.annotations.SerializedName;

public class Distributor {
    @SerializedName("_id")
    private String id;
    private String name, createdXe, updatedXe;

    public Distributor(String id, String name, String createdXe, String updatedXe) {
        this.id = id;
        this.name = name;
        this.createdXe = createdXe;
        this.updatedXe = updatedXe;
    }

    public Distributor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedXe() {
        return createdXe;
    }

    public void setCreatedXe(String createdXe) {
        this.createdXe = createdXe;
    }

    public String getUpdatedXe() {
        return updatedXe;
    }

    public void setUpdatedXe(String updatedXe) {
        this.updatedXe = updatedXe;
    }
}
