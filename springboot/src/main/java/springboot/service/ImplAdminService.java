package springboot.service;

import springboot.controller.credential.LoginCred;
import springboot.controller.request.BaseRequest;
import springboot.controller.request.LoginRequest;
import springboot.controller.request.PasswordRequest;
import springboot.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ImplAdminService {
    List<Admin> list();

    PageInfo<Admin> page(BaseRequest baseRequest);

    void save(Admin obj);

    Admin getById(Integer id);

    void update(Admin obj);

    void deleteById(Integer id);

    LoginCred login(LoginRequest request);

    void changePass(PasswordRequest request);
}
