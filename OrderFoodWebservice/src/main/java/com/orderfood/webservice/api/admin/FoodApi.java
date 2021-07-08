package com.orderfood.webservice.api.admin;

import com.orderfood.webservice.dto.JsonResult;
import com.orderfood.webservice.entity.FoodEntity;
import com.orderfood.webservice.entity.RestaurantEntity;
import com.orderfood.webservice.entity.UserEntity;
import com.orderfood.webservice.service.FileStorageService;
import com.orderfood.webservice.service.FoodService;
import com.orderfood.webservice.service.RestaurantService;
import com.orderfood.webservice.service.UserService;
import com.orderfood.webservice.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
public class FoodApi {
    @Autowired
    private FoodService foodService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Hiển thị danh sách món ăn của chủ quán
     */
    @GetMapping("/food")
    public JsonResult<List<FoodEntity>> getList() {
        try {
            UserEntity userEntity = userService.findOneByUsername(Objects.requireNonNull(SecurityUtil.getPrincipal()).getUsername());
            RestaurantEntity restaurantEntity = restaurantService.findOneByUser(userEntity);
            List<FoodEntity> foodEntityList = foodService.findByRestaurant(restaurantEntity);
            return new JsonResult<List<FoodEntity>>().success(foodEntityList);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<List<FoodEntity>>().error("Lỗi getList() - '/api/food'");
        }
    }

    /**
     * Thêm món ăn cho chủ quán
     */
    @PostMapping("/food")
    public JsonResult<String> add(@RequestParam(value = "file", required = false) MultipartFile file,
                                  FoodEntity foodEntity) {
        String msg;
        if (foodEntity.getId() != null) msg = "Cập nhật món ăn thành công";
        else msg = "Thêm món ăn thành công";
        try {
            if (file != null) {
                String fileName = fileStorageService.storeFile(file);
                String uri = "/uploads/" + fileName;
                foodEntity.setAvatar(uri);
            }
            UserEntity userEntity = userService.findOneByUsername(SecurityUtil.getPrincipal().getUsername());
            RestaurantEntity restaurantEntity = restaurantService.findOneByUser(userEntity);
            foodEntity.setRestaurant(restaurantEntity);
            foodService.save(foodEntity);

//            PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
//            pushNotificationRequest.setTitle(msg);
//            pushNotificationRequest.setMessage("Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi");
//            pushNotificationRequest.setTopic("");
//            pushNotificationRequest.setToken(userEntity.getTokenFCM());
//            pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
            return new JsonResult<String>().success(msg, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<String>().error("Lỗi hệ thống (thêm món ăn)");
        }
    }

    /**
     * Xóa món ăn dành cho chủ quán
     */
    @DeleteMapping("/food")
    public JsonResult<String> delete(Long id) {
        try {
            UserEntity userEntity = userService.findOneByUsername(SecurityUtil.getPrincipal().getUsername());
            RestaurantEntity restaurantEntity = restaurantService.findOneByUser(userEntity);
            foodService.deleteByIdAndRestaurant(id, restaurantEntity);
            return new JsonResult<String>().success("Xóa món ăn thành công", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<String>().error("Lỗi hệ thống (xóa món ăn)");
        }
    }
}
