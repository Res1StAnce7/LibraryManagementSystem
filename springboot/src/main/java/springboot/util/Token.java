package springboot.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import springboot.entity.Admin;
import springboot.service.ImplAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
public class Token {
    private static ImplAdminService staticAdminService;

    @Resource
    private ImplAdminService adminService;

    @PostConstruct
    public void setUserService() {
        staticAdminService = adminService;
    }

    /**
     *
     * @param adminId id of admin
     * @param sign key
     */
    public static String genToken(String adminId, String sign) {
        // store user id to token
        return JWT.create().withAudience(adminId)
                .withExpiresAt(DateUtil.offsetHour(new Date(), 24))
                .sign(Algorithm.HMAC256(sign));
    }

    public static String genToken(String adminId, String sign, int days) {
        return JWT.create().withAudience(adminId)
                .withExpiresAt(DateUtil.offsetDay(new Date(), days))
                .sign(Algorithm.HMAC256(sign));
    }

    /**
     * get admin id from token
     */
    public static Admin getCurrentAdmin() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if (StrUtil.isBlank(token)) {
                token = request.getParameter("token");
            }
            if (StrUtil.isBlank(token)) {
                log.error("Fail to get token， token: {}", token);
                return null;
            }
            String adminId = JWT.decode(token).getAudience().get(0);
            return staticAdminService.getById(Integer.valueOf(adminId));
        } catch (Exception e) {
            log.error("Failed to get admin information, token={}", token,  e);
            return null;
        }
    }
}

