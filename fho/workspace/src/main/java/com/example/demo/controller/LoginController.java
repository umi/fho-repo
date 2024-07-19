package com.example.demo.controller; // このファイルが属するパッケージ（フォルダ）

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// 必要なクラスをインポートします
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Fho;
import com.example.demo.entity.LoginUser;
import com.example.demo.service.FhoService;
import com.example.demo.service.UserPrincipal;

@Controller // このクラスがWebコントローラーであることを示します
public class LoginController {
    @Autowired
    private FhoService fhoService;

    @GetMapping("/login") // "/login"というURLに対するGETリクエストを処理します
    public String login() {
        return "login";  // login.htmlを表示します
    }
    
    @GetMapping("/") // ルートURL ("/") に対するGETリクエストを処理します
    public String redirectToIndex() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 現在のユーザーの認証情報を取得します
        if (authentication != null && authentication.isAuthenticated()) { // ユーザーがログインしている場合
            return "redirect:/fho";  // "/index"にリダイレクトします
        }
        return "redirect:/login"; // ユーザーがログインしていない場合、"/login"にリダイレクトします
    }
    
    @GetMapping("/fho") // "/index"というURLに対するGETリクエストを処理します
    public String index(Model model) {
    	 List<Fho> fho = fhoService.getFho();
         model.addAttribute("fho", fho);
         
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
     	UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
     	LoginUser user = userPrincipal.getUser();
     	String authorityCode = user.getAuthorityCode();
     	
     	if(authorityCode.equals("500")) {
     		return "fho/index500";
     	}else {
     		return "fho/index400"; // index.htmlを表示します
     	}
        
    }
}

