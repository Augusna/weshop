package tech.wetech.weshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.wetech.weshop.enums.OrderStatusEnum;
import tech.wetech.weshop.enums.PayStatusEnum;
import tech.wetech.weshop.po.Order;
import tech.wetech.weshop.query.PageQuery;
import tech.wetech.weshop.service.OrderService;
import tech.wetech.weshop.utils.Result;
import tech.wetech.weshop.vo.OrderDetailVO;
import tech.wetech.weshop.web.base.BaseCrudController;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
@RestController
@RequestMapping("/admin/order")
@Validated
public class AdminOrderController extends BaseCrudController<Order> {

    @Autowired
    private OrderService orderService;

    @Override
    public Result<List<Order>> queryList(Order entity, PageQuery pageQuery) {
        return super.queryList(entity, pageQuery)
                .addExtra("orderStatus", Arrays.stream(OrderStatusEnum.values()).collect(Collectors.toMap(o -> o, OrderStatusEnum::getName)))
                .addExtra("payStatus", Arrays.stream(PayStatusEnum.values()).collect(Collectors.toMap(p -> p, PayStatusEnum::getName)));
    }

    @GetMapping("/detail")
    public Result<OrderDetailVO> queryOrderDetail(@RequestParam("orderId") @NotNull Integer orderId) {
        return Result.success(orderService.queryOrderDetail(orderId));
    }

}
