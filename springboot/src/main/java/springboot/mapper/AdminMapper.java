package springboot.mapper;

import springboot.controller.request.BaseRequest;
import springboot.controller.request.PasswordRequest;
import springboot.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<Admin> list();

    List<Admin> listByCondition(BaseRequest baseRequest);

    void save(Admin obj);

    Admin getById(Integer id);

    void updateById(Admin user);

    void deleteById(Integer id);

    int updatePassword(PasswordRequest request);

    Admin getByUsername(String username);
}
