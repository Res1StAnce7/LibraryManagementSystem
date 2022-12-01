package springboot.exception;

import cn.hutool.core.util.StrUtil;
import springboot.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ServiceException.class)
    public Result serviceExceptionError(ServiceException e) {
        log.error("Service error", e);
        String code = e.getCode();
        if (StrUtil.isNotBlank(code)) {
            return Result.error(code, e.getMessage());
        }
        return Result.error(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public Result exceptionError(Exception e) {
        log.error("System error", e);
        return Result.error("System error");
    }

}
