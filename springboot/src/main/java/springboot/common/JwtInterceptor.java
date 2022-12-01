package springboot.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import springboot.entity.Admin;
import springboot.exception.ServiceException;
import springboot.service.ImplAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    private static final String ERROR_CODE_401 = "401";

    @Autowired
    private ImplAdminService adminService;

    /**
     * Pre handler
     * @param request the request
     * @param response the response
     * @param handler handler
     * @return result
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(ERROR_CODE_401, "token is empty");
        }

        String adminId = null;
        Admin admin = null;
        try {
            adminId = JWT.decode(token).getAudience().get(0);
            admin = adminService.getById(Integer.parseInt(adminId));
        }
        catch (Exception e) {
            String errMsg = "token is invalid";
            log.error(errMsg + ", token=" + token, e);
            throw new ServiceException(ERROR_CODE_401, errMsg);
        }
        if (admin == null) {
            throw new ServiceException(ERROR_CODE_401, "user not exist");
        }

        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
            jwtVerifier.verify(token);
        }
        catch (JWTVerificationException e) {
            throw new ServiceException(ERROR_CODE_401, "token is invalid");
        }
        return true;
    }
}

