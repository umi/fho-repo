package com.example.demo.config; // このファイルが属するパッケージ（フォルダ）

// 必要なクラスやインターフェースをインポートします
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // このクラスは設定クラスであることを示します
@EnableWebSecurity // Webセキュリティを有効にすることを示します
public class SecurityConfig { // セキュリティ設定のクラス

    @Bean // このメソッドの返り値をSpringのBeanとして登録します
    public PasswordEncoder passwordEncoder() { // パスワードエンコーダー（パスワードのハッシュ化）を提供するメソッド
        return new BCryptPasswordEncoder(); // パスワードをBCrypt方式でハッシュ化するエンコーダーを返します
    }

    @Bean // このメソッドの返り値をSpringのBeanとして登録します
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // セキュリティフィルタチェーンを定義するメソッド
        http
            .authorizeHttpRequests(authorizeRequests ->  // 認証リクエストを設定します
                authorizeRequests
                    .requestMatchers("/login", "/register").permitAll() // "/login"と"/register"へのリクエストは認証なしで許可します
                    .anyRequest().authenticated() // それ以外の全てのリクエストは認証が必要です
            )
            .formLogin(formLogin ->  // フォームベースのログインを設定します
                formLogin
                    .loginPage("/login") // ログインページのURLを設定します
                    .permitAll() // ログインページは認証なしで許可します
            )
            .logout(logout ->  // ログアウトを設定します
                logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // ログアウトのリクエストURLを設定します
            );

        return http.build(); // 上記の設定を反映してHttpSecurityオブジェクトをビルドします
    }

    // 他のセキュリティ設定が必要な場合は、ここに追加します
}


