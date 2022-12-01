package springboot.service;

import springboot.controller.request.BaseRequest;
import springboot.entity.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ImplBookService {
    List<Book> list();

    PageInfo<Book> page(BaseRequest baseRequest);

    void save(Book obj);

    Book getById(Integer id);

    void update(Book obj);

    void deleteById(Integer id);
}
