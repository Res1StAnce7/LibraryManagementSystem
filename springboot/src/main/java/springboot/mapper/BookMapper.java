package springboot.mapper;

import springboot.controller.request.BaseRequest;
import springboot.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    List<Book> list();

    List<Book> listByCondition(BaseRequest baseRequest);

    void save(Book obj);

    Book getById(Integer id);

    void updateById(Book user);

    void deleteById(Integer id);

    Book getByNo(String bookNo);

    void updateNumByNo(String bookNo);
}