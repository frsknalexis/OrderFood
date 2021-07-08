package com.orderfood.webservice.api.admin;

import com.orderfood.webservice.dto.JsonResult;
import com.orderfood.webservice.entity.OrderEntity;
import com.orderfood.webservice.entity.RestaurantEntity;
import com.orderfood.webservice.notify.PushNotificationRequest;
import com.orderfood.webservice.notify.PushNotificationService;
import com.orderfood.webservice.repository.OrderRepository;
import com.orderfood.webservice.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class OrderApi {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PushNotificationService pushNotificationService;

    /**
     * Lấy danh sách đơn hàng theo trạng thái
     */
    @GetMapping("/order")
    public List<OrderEntity> getListByStatus(@RequestParam("status") Integer status) {
        RestaurantEntity restaurantEntity = SecurityUtil.getPrincipal().getUserEntity().getRestaurant();
        return orderRepository.findByFood_RestaurantAndStatus(restaurantEntity, status);
    }

    /**
     * Xác nhận đơn hàng
     */
    @PostMapping("/order/confirm")
    public JsonResult<String> confirm(Long id, Integer status) {
        try {
            OrderEntity entity = orderRepository.findOneById(id);
            entity.setStatus(status);
            orderRepository.saveAndFlush(entity);

            String msg = "Đã xác nhận";
            if (status == 3)
                msg = "Đã hủy đơn";

            PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
            pushNotificationRequest.setTitle(msg);
            pushNotificationRequest.setMessage("Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi");
            pushNotificationRequest.setTopic("");
            pushNotificationRequest.setToken(entity.getUser().getTokenFCM());
            pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);

            return new JsonResult<String>().success(msg, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<String>().error("Lỗi hệ thống");
        }
    }
}
