package com.orderfood.webservice.api.admin;

import com.orderfood.webservice.dto.JsonResult;
import com.orderfood.webservice.entity.UserEntity;
import com.orderfood.webservice.service.FileStorageService;
import com.orderfood.webservice.service.UserService;
import com.orderfood.webservice.util.SecurityUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserApi {
    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Đăng kí tài khoản
     *
     * @param userEntity UserEntity
     */
    @PostMapping("/register")
    public JsonResult<String> register(@RequestParam(value = "file", required = false) MultipartFile file,
                                       UserEntity userEntity) {
        try {
            String uri = "";
            UserEntity entity = userService.findOneByUsername(userEntity.getUsername());
            if (file != null) {
                String fileName = fileStorageService.storeFile(file);
                uri = "/uploads/" + fileName;
            }
            if (userEntity.getId() == null) {
                if (entity != null)
                    return new JsonResult<String>().error("SĐT đã được đăng kí");
                else {
                    userEntity.setAvatar(uri);
                    userService.register(userEntity);
                    return new JsonResult<String>().success("Đăng kí thành công", null);
                }
            } else {
                if (userEntity.getPassword() != null)
                    entity.setPassword(userEntity.getPassword());
                if (!uri.equals(""))
                    entity.setAvatar(uri);
                entity.setName(userEntity.getName());
                entity.setSex(userEntity.getSex());
                userService.register(entity);
                return new JsonResult<String>().success("Cập nhật thành công", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<String>().error("Lỗi hệ thống");
        }
    }

    @GetMapping("/api/user/info")
    public JsonResult<UserEntity> getInfo() {
        try {
            UserEntity userEntity = SecurityUtil.getPrincipal().getUserEntity();
            return new JsonResult<UserEntity>().success("Thông tin tài khoản", userEntity);
        } catch (Exception e) {
            return new JsonResult<UserEntity>().error("Lỗi hệ thống");
        }
    }

    /**
     * Lưu token firebase cloud message (mã thông báo) sau khi người dùng đăng nhập thành công
     */
    @PutMapping("/api/register/token/fcm")
    public JsonResult<String> storeTokenFCM(String token) {
        try {
            UserEntity userEntity = userService.findOneByUsername(SecurityUtil.getPrincipal().getUsername());
            userEntity.setTokenFCM(token);
            userService.save(userEntity);
            return new JsonResult<String>().success("Lưu token firebase cloud message thành công", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<String>().error("Lỗi hệ thống");
        }
    }
}
