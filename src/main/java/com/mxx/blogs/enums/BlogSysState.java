package com.mxx.blogs.enums;

public enum BlogSysState {
    ERROR("系统内部异常", 500),
    SUCCESS("操作成功", 200),
    FIELD("字段解析错误", 100),
    USER_NO_LOGIN("用户没有登录", 501),
    USER_EDIT_PASS("用户修改了密码", 502),
    USER_EDIT_COOKIE("用户修改了COOKIE信息", 503),
    USER_NOT_LOGIN("用户没有登录", 401);


    private String KEY;
    private Integer VALUE;

    BlogSysState(String KEY, int VALUE) {
        this.KEY = KEY;
        this.VALUE = VALUE;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public Integer getVALUE() {
        return VALUE;
    }

    public void setVALUE(Integer VALUE) {
        this.VALUE = VALUE;
    }
}
