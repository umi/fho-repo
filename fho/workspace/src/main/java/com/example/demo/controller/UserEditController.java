package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/userEdit")
public class UserEditController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public String index(@PathVariable("id") Integer id, Model model) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
        } else {
            // ユーザーが見つからない場合の処理を追加
            // 例えばエラーメッセージをモデルに追加するなど
        }
        return "userEdit/edit";
    }

}
