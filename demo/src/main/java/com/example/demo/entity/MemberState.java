package com.example.demo.entity;

public enum MemberState {
    NORMAL("正常"),
    INVALID("已过期"),
    ;

    private String descp;

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    MemberState(String descp) {
        this.descp = descp;
    }
}
