package com.orderfood.webservice.api.admin;

import com.orderfood.webservice.dto.JsonResult;
import com.orderfood.webservice.entity.RestaurantEntity;
import com.orderfood.webservice.entity.UserEntity;
import com.orderfood.webservice.service.FileStorageService;
import com.orderfood.webservice.service.RestaurantService;
import com.orderfood.webservice.service.UserService;
import com.orderfood.webservice.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
public class RestaurantApi {

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/restaurant/register")
    public JsonResult<RestaurantEntity> uploadFile(@RequestParam(name = "file", required = false) MultipartFile file,
                                                   RestaurantEntity restaurantEntity) {
        try {
            String msg;
            if (restaurantEntity.getId() == null) {
                restaurantEntity.setStatus(0);
                msg = "Đăng kí thành công";
            } else
                msg = "Cập nhật thành công";
            if (file != null) {
                String fileName = fileStorageService.storeFile(file);
                String uri = "/uploads/" + fileName;
                restaurantEntity.setAvatar(uri);
            }
            UserEntity userEntity = userService.findOneByUsername(SecurityUtil.getPrincipal().getUsername());
            restaurantEntity.setUser(userEntity);
            restaurantService.register(restaurantEntity);
            return new JsonResult<RestaurantEntity>().success(msg, restaurantEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<RestaurantEntity>().error("Lỗi hệ thống");
        }
    }

    @PostMapping("/restaurant/close")
    public JsonResult<RestaurantEntity> close(Integer status) {
        try {
            String msg;
            if (status == 0)
                msg = "Đã mở cửa";
            else
                msg = "Đã đóng cửa";
            UserEntity userEntity = userService.findOneByUsername(SecurityUtil.getPrincipal().getUsername());
            RestaurantEntity restaurantEntity = userEntity.getRestaurant();
            restaurantEntity.setStatus(status);
            restaurantService.save(restaurantEntity);
            return new JsonResult<RestaurantEntity>().success(msg, restaurantEntity);
        } catch (Exception e) {
            return new JsonResult<RestaurantEntity>().error("Lỗi hệ thống");
        }
    }

    @GetMapping("/restaurant/exists")
    public JsonResult<RestaurantEntity> exists() {
        try {
            UserEntity userEntity = userService.findOneByUsername(SecurityUtil.getPrincipal().getUsername());
            RestaurantEntity restaurantEntity = restaurantService.findOneByUser(userEntity);
            if (restaurantEntity != null)
                return new JsonResult<RestaurantEntity>().success("Đã đăng kí quán ăn", restaurantEntity);
            else
                return new JsonResult<RestaurantEntity>().error("Chưa đăng kí quán ăn");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<RestaurantEntity>().error("Lỗi hệ thống");
        }
    }
}
