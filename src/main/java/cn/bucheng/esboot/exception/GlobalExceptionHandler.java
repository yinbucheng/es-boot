package cn.bucheng.esboot.exception;

import cn.bucheng.esboot.common.BusinessError;
import cn.bucheng.esboot.model.vo.ServerModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

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
    @ResponseBody
    public Object handleError(HttpServletRequest req, HttpServletResponse resp, Object error) {
        log.error(error.toString());
        if (error instanceof BusinessException) {
            BusinessException bus = (BusinessException) error;
            return ServerModel.fail(bus.getCode(), bus.getMessage());
        } else if (error instanceof ServletRequestBindingException) {
            return ServerModel.fail(BusinessError.BIND_PARAM_FAIL.getCode(), BusinessError.BIND_PARAM_FAIL.getMessage());
        } else if (error instanceof NoHandlerFoundException) {
            return ServerModel.fail(BusinessError.NO_FIND_ROUTING.getCode(), BusinessError.NO_FIND_ROUTING.getMessage());
        } else if (error instanceof BindException) {//post的非json校验失败会进入到这里
            BindException err = (BindException) error;
            return ServerModel.fail(BusinessError.PARAM_VERIFY_FAIL.getCode(), BusinessError.PARAM_VERIFY_FAIL.getMessage() + ":" + errorMessage(err.getBindingResult()));
        } else if(error instanceof MethodArgumentNotValidException){//post的json校验失败会进入到这里
            MethodArgumentNotValidException err = (MethodArgumentNotValidException)error;
            return ServerModel.fail(BusinessError.PARAM_VERIFY_FAIL.getCode(), BusinessError.PARAM_VERIFY_FAIL.getMessage() + ":" + errorMessage(err.getBindingResult()));
        }else {
            return ServerModel.error("服务器异常", error.toString());
        }
    }


    private List<String> errorMessage(BindingResult result) {
        List<String> errors = new LinkedList<>();
        List<ObjectError> allErrors = result.getAllErrors();
        if (allErrors != null) {
            for (ObjectError error : allErrors) {
                errors.add(error.getDefaultMessage());
            }
        }

        return errors;
    }
}
