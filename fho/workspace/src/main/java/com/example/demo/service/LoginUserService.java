package com.example.demo.service; // このファイルが属するパッケージ（フォルダ）

// 必要なクラスをインポートします
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginUserDTO;
import com.example.demo.entity.LoginUser;
import com.example.demo.repository.LoginUserRepository;

@Service // このクラスがサービス層のクラスであることを示します
public class LoginUserService implements UserDetailsService { // UserDetailsServiceインターフェースを実装しています

    @Autowired // Springが自動的にUserRepositoryの実装を注入します
    private LoginUserRepository loginUserRepository;

    @Autowired // Springが自動的にPasswordEncoderの実装を注入します
    private PasswordEncoder passwordEncoder;

    @Override // UserDetailsServiceインターフェースのメソッドを上書きします
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	LoginUser user = loginUserRepository.findByUsername(username); // ユーザー名でユーザーを検索します
        if (user == null) {
            throw new UsernameNotFoundException("User not found"); // ユーザーが見つからない場合、例外をスローします
        }
        return new UserPrincipal(user); // ユーザーが見つかった場合、UserPrincipalを作成し返します
    }

    //新たにメソッドを追加します
    public LoginUser findByUsername(String username) {
        return loginUserRepository.findByUsername(username); // ユーザー名でユーザーを検索し返します
    }

    @Transactional // トランザクションを開始します。メソッドが終了したらトランザクションがコミットされます。
    public void save(LoginUserDTO userDTO) {
        // UserDTOからUserへの変換
    	LoginUser user = new LoginUser();
        user.setUsername(userDTO.getUsername());
        // パスワードをハッシュ化してから保存
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        
        if(userDTO.getUsername().equals("resemara")) {
        	user.setAuthorityCode("500");
        }else {
        	user.setAuthorityCode("400");
        }
       
        // データベースへの保存
        loginUserRepository.save(user); // UserRepositoryを使ってユーザーをデータベースに保存します
    }
}


