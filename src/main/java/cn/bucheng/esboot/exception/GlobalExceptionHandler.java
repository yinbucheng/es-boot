package cn.bucheng.esboot.exception;

import cn.bucheng.esboot.common.BusinessError;
import cn.bucheng.esboot.common.ServerModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

/**
 * @author ：yinchong
 * @create ：2019/7/25 17:32
 * @description：
 * @modified By：
 * @version:
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class, Throwable.class})
    public Object handleError(HttpServletRequest req, HttpServletResponse resp, Object error) {
        log.error(error.toString());
        if (error instanceof BusinessException) {
            BusinessException bus = (BusinessException) error;
            return ServerModel.fail(bus.getCode(), bus.getMessage());
        } else if (error instanceof ServletRequestBindingException) {
            return ServerModel.fail(BusinessError.BIND_PARAM_FAIL.getCode(), BusinessError.BIND_PARAM_FAIL.getMessage());
        } else if (error instanceof NoHandlerFoundException) {
            return ServerModel.fail(BusinessError.NO_FIND_ROUTING.getCode(), BusinessError.NO_FIND_ROUTING.getMessage());
        } else {
            return ServerModel.error("服务器异常", error.toString());
        }
    }
}
