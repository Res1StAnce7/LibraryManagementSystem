package springboot.service;

import springboot.controller.request.BaseRequest;
import springboot.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ImplUserService {
    List<User> list();

    PageInfo<User> page(BaseRequest baseRequest);

    void save(User user);

    User getById(Integer id);

    void update(User user);

    void deleteById(Integer id);

    void handleAccount(User user);
}
