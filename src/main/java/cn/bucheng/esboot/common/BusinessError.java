package cn.bucheng.esboot.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessError {
    NO_FIND_ROUTING(500, "没有找到访问路径"),
    BIND_PARAM_FAIL(5001, "参数绑定异常"),
    ;
    private int code;
    private String message;
}
