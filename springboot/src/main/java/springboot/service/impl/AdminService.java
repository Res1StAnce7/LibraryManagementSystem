package springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import springboot.controller.credential.LoginCred;
import springboot.controller.request.BaseRequest;
import springboot.controller.request.LoginRequest;
import springboot.controller.request.PasswordRequest;
import springboot.entity.Admin;
import springboot.exception.ServiceException;
import springboot.mapper.AdminMapper;
import springboot.service.ImplAdminService;
import springboot.util.Token;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements ImplAdminService {
    @Autowired
    AdminMapper adminMapper;

    private static final String DEFAULT_PASS = "123456";
    private static final String PASS_SALT = "ring";

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> users = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(users);
    }

    @Override
    public void save(Admin obj) {
        if (StrUtil.isBlank(obj.getPassword())) {
            obj.setPassword(DEFAULT_PASS);
        }
        obj.setPassword(securePass(obj.getPassword()));
        try {
            adminMapper.save(obj);
        }
        catch (DuplicateKeyException e) {
            log.error("Fail to insert， username:{}", obj.getUsername(), e);
            throw new ServiceException("Username already exists");
        }
    }


    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    @Override
    public void update(Admin user) {
        user.setUpdatetime(new Date());
        adminMapper.updateById(user);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public LoginCred login(LoginRequest request) {
        Admin admin = null;
        try {
            admin = adminMapper.getByUsername(request.getUsername());
        } catch (Exception e) {
            log.error("Username {} does not exist", request.getUsername());
            throw new ServiceException("Wrong username");
        }
        if (admin == null) {
            throw new ServiceException("Wrong username or password");
        }

        String securePass = securePass(request.getPassword());
        if (!securePass.equals(admin.getPassword())) {
            System.out.println(securePass);
            System.out.println("securePass: " + securePass);
            System.out.println("admin.getPassword(): " + admin.getPassword());
            throw new ServiceException("Wrong username or password");
        }
        if (!admin.isStatus()) {
            throw new ServiceException("The account is disabled, please contact the administrator");
        }
        LoginCred loginCred = new LoginCred();
        BeanUtils.copyProperties(admin, loginCred);

        String token = Token.genToken(String.valueOf(admin.getId()), admin.getPassword());
        loginCred.setToken(token);
        return loginCred;
    }

    @Override
    public void changePass(PasswordRequest request) {
        request.setNewPass(securePass(request.getNewPass()));
        int count = adminMapper.updatePassword(request);
        if (count <= 0) {
            throw new ServiceException("Fail to change password");
        }
    }

    private String securePass(String password) {
        return SecureUtil.md5(password + PASS_SALT);
    }
}
