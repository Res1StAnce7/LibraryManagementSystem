package springboot.service;

import springboot.controller.request.BaseRequest;
import springboot.entity.Borrow;
import springboot.entity.Return;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ImplBorrowService {
    List<Borrow> list();

    PageInfo<Borrow> page(BaseRequest baseRequest);

    void save(Borrow obj);

    PageInfo<Return> pageReturn(BaseRequest baseRequest);

    void saveReturn(Return obj);

    Borrow getById(Integer id);

    void update(Borrow obj);

    void deleteById(Integer id);

    void deleteReturnById(Integer id);

    Map<String, Object> getCountByTimeRange(String timeRange);
}
