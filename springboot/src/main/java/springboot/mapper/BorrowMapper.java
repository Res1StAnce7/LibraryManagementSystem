package springboot.mapper;

import springboot.controller.request.BaseRequest;
import springboot.entity.Borrow;
import springboot.entity.Return;
import springboot.mapper.sub.BorrowReturnCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BorrowMapper {

    List<Borrow> list();

    List<Borrow> listByCondition(BaseRequest baseRequest);

    List<Return> listReturByCondition(BaseRequest baseRequest);

    void save(Borrow obj);

    void saveRetur(Return obj);

    Borrow getById(Integer id);

    void updateById(Borrow user);

    void deleteById(Integer id);

    void deleteReturById(Integer id);

    void updateStatus(String status, Integer id);

    List<BorrowReturnCount> getCountByTimeRange(@Param("timeRange") String timeRange, @Param("type") int type);
}
