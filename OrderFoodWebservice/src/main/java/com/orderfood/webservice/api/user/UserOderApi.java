package com.orderfood.webservice.api.user;

import com.orderfood.webservice.entity.CartEntity;
import com.orderfood.webservice.entity.OrderEntity;
import com.orderfood.webservice.entity.RestaurantEntity;
import com.orderfood.webservice.notify.PushNotificationRequest;
import com.orderfood.webservice.notify.PushNotificationService;
import com.orderfood.webservice.service.CartService;
import com.orderfood.webservice.service.FoodService;
import com.orderfood.webservice.service.OrderService;
import com.orderfood.webservice.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("api/user/order")
public class UserOderApi {
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
    @Autowired
    FoodService foodService;
    @Autowired
    PushNotificationService pushNotificationService;

    @RequestMapping("getOrderOfUser")
    public List<OrderEntity> getOrderOfUser(@RequestParam("status") int status){
        List<OrderEntity> l=orderService.getOrderOfUser(SecurityUtil.getPrincipal().getUserEntity().getUsername(),status);
        return l;
    }

    @PostMapping("addOrder")
    public void addOrder(@RequestParam("address") String address) {
        System.out.println("-----------------------------");
        Set<RestaurantEntity> restaurants = new HashSet<>();
        for (CartEntity c : cartService.getAllByUser(Objects.requireNonNull(SecurityUtil.getPrincipal()).getUsername())) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setAddress(address);
            orderEntity.setAmount(c.getAmount());
            orderEntity.setFood(c.getFood());
            orderEntity.setOrderDate(new Date(System.currentTimeMillis()));
            orderEntity.setPrice(c.getFood().getPrice());
            orderEntity.setPhone(SecurityUtil.getPrincipal().getUserEntity().getUsername());
            orderEntity.setStatus(0);
            orderEntity.setUser(SecurityUtil.getPrincipal().getUserEntity());

            restaurants.add(c.getFood().getRestaurant());
            orderService.save(orderEntity);
            cartService.deleteById(c.getId());
        }
        Iterator<RestaurantEntity> iterator = restaurants.iterator();
      while (iterator.hasNext()){
          PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
          pushNotificationRequest.setTitle("Bạn có đơn hàng mới");
          pushNotificationRequest.setMessage(SecurityUtil.getPrincipal().getUsername());
          pushNotificationRequest.setTopic("");
          pushNotificationRequest.setToken(iterator.next().getUser().getTokenFCM());
          pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
      }
    }
}
