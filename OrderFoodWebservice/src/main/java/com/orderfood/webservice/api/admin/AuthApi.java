package com.orderfood.webservice.api.admin;

import com.orderfood.webservice.dto.JsonResult;
import com.orderfood.webservice.entity.UserEntity;
import com.orderfood.webservice.jwt.JwtToken;
import com.orderfood.webservice.jwt.JwtTokenProvider;
import com.orderfood.webservice.security.MyUserDetails;
import com.orderfood.webservice.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class AuthApi {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/login")
    public JsonResult<String> authenticateUser(@RequestBody UserEntity userEntity) {
        // Xác thực thông tin người dùng Request lên
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userEntity.getUsername(),
                        userEntity.getPassword()
                ));
        if (authentication.isAuthenticated()) {
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Trả về jwt cho người dùng.
            String jwt = tokenProvider.generateToken((MyUserDetails) authentication.getPrincipal());
            return new JsonResult<String>().success("Đăng nhập thành công", jwt);
        }
        return new JsonResult<String>().error("Đăng nhập thất bại");
    }

    // Api /api/random yêu cầu phải xác thực mới có thể request
    @GetMapping("/random")
    public JsonResult<String> randomStuff() {
        return new JsonResult<String>().success("JWT Hợp lệ mới có thể thấy được message này", null);
    }

    @GetMapping("/uploads/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
