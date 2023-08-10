package com.vuthao.VNADCM.base.model;

public class Status {
    private String status;
    private Mess mess;
    private String dateNow;

    public Status() { }

    public String getStatus() {
        return status;
    }

    public Mess getMess() {
        if (mess == null) {
            mess = new Mess();
        }
        return mess;
    }

    public String getDateNow() {
        return dateNow;
    }

    public void setDateNow(String dateNow) {
        this.dateNow = dateNow;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
