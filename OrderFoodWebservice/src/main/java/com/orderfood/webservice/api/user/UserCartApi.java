package com.orderfood.webservice.api.user;

import com.orderfood.webservice.entity.CartEntity;
import com.orderfood.webservice.repository.CartRepository;
import com.orderfood.webservice.repository.FoodRepository;
import com.orderfood.webservice.service.CartService;
import com.orderfood.webservice.service.FoodService;
import com.orderfood.webservice.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/user/cart")
public class UserCartApi {
    @Autowired
    CartService cartService;
    @Autowired
    FoodService foodService;

    @PutMapping("add")
    public Double addCart(@RequestParam("idFood") long idFood, @RequestParam("amount") int amount, CartEntity cartEntity) {
        cartEntity.setFood(foodService.findById(idFood));
        cartEntity.setAmount(amount);
        cartEntity.setUser(Objects.requireNonNull(SecurityUtil.getPrincipal()).getUserEntity());
        if (cartService.findByUserNameAndIdFood(SecurityUtil.getPrincipal().getUsername(), idFood) != null)
            cartEntity.setId(cartService.findByUserNameAndIdFood(SecurityUtil.getPrincipal().getUsername(), idFood).getId());
        cartService.save(cartEntity);

        double total = 0;
        List<CartEntity> list = cartService.getAllByUser(SecurityUtil.getPrincipal().getUsername());
        for (CartEntity cart : list) {
            total += cart.getAmount() * cart.getFood().getPrice();
        }
        return total;

    }

    @GetMapping("getCartOfUser")
    public List<CartEntity> getCartOfUser() {
        return cartService.getAllByUser(SecurityUtil.getPrincipal().getUsername());
    }

    @DeleteMapping("delete")
    public Double delete(@RequestParam("idCart") long idCart) {
        cartService.deleteById(idCart);
        double total = 0;
        List<CartEntity> list = cartService.getAllByUser(SecurityUtil.getPrincipal().getUsername());
        for (CartEntity cartEntity : list) {
            total += cartEntity.getAmount() * cartEntity.getFood().getPrice();
        }
        return total;
    }
}
