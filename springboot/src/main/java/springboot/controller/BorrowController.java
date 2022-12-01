package springboot.controller;

import springboot.common.Result;
import springboot.controller.request.BorrowPageRequest;
import springboot.entity.Borrow;
import springboot.entity.Return;
import springboot.service.ImplBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    ImplBorrowService borrowService;

    @PostMapping("/save")
    public Result save(@RequestBody Borrow obj) {
        borrowService.save(obj);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Borrow obj) {
        borrowService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        borrowService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Borrow obj = borrowService.getById(id);
        return Result.success(obj);
    }

    @GetMapping("/list")
    public Result list() {
        List<Borrow> list = borrowService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result page(BorrowPageRequest pageRequest) {
        return Result.success(borrowService.page(pageRequest));
    }

    @GetMapping("/pageReturn")
    public Result pageReturn(BorrowPageRequest pageRequest) {
        return Result.success(borrowService.pageReturn(pageRequest));
    }

    @PostMapping("/saveReturn")
    public Result saveReturn(@RequestBody Return obj) {
        borrowService.saveReturn(obj);
        return Result.success();
    }

    @DeleteMapping("/deleteReturn/{id}")
    public Result deleteReturn(@PathVariable Integer id) {
        borrowService.deleteReturnById(id);
        return Result.success();
    }

    @GetMapping("/lineCharts/{timeRange}")
    public Result lineCharts(@PathVariable String timeRange) {
        return Result.success(borrowService.getCountByTimeRange(timeRange));
    }

}
