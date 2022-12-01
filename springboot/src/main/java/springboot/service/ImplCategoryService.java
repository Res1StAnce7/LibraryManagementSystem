package springboot.service;

import springboot.controller.request.BaseRequest;
import springboot.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ImplCategoryService {
    List<Category> list();

    PageInfo<Category> page(BaseRequest baseRequest);

    void save(Category obj);

    Category getById(Integer id);

    void update(Category obj);

    void deleteById(Integer id);

}
