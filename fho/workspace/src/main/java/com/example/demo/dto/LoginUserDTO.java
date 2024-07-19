package com.example.demo.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDTO {  // ユーザーのデータを扱うためのクラス

    @NotEmpty  // ユーザー名は空であってはならないというルール
    private String username;  // ユーザー名を保存するための場所

    @NotEmpty  // パスワードは空であってはならないというルール
    private String password;  // パスワードを保存するための場所
    
    @NotEmpty  // 権限コードは空であってはならないというルール
    private int authorityCode;  // 権限コードを保存するための場所

}


