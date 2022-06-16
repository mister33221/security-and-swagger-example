package com.bezkoder.springjwt.springDocConfig;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

//swagger的首頁標題
@OpenAPIDefinition(
        info = @Info(
                title = "我的第一個spring security 與 swagger 3 的練習範例",
                version = "0.0",
                description = "版本: \n\n " +
                        "spring boot : 2.1.8\n\n" +
                        "jjwt : 0.9.1\n\n" +
                        "springdoc-openapi-ui : 1.2.32\n\n" +
                        "其他sercurity jpa MySQL 等等 沒有寫版本(???\n\n" +
                        "主要會練習使用各種sercurity 以及 swagger 3 常用的功能",
                license = @License(name = "License名稱", url = "License url"),
                contact = @Contact(url = "http://可以輸入聯絡我的網址", name = "可以輸入我的名字", email = "mister33221@gmail.com")
        )
)
// 如果有使用JWT的話 就可以使用以下這些 來使 swagger頁面產生一個 Authorize的按鈕，在其中輸入token就可以使用那些被鎖住的功能
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SpringDocConfig {
//  上下兩種寫法都可以
//    @Bean
//    public OpenAPI customOpenAPI() {
//        // 兩個\n\n 才有換行喔
//        return new OpenAPI()
//                .components(new Components())
//                .info(new Info().title("我的第一個spring security 與 swagger 3 的練習範例").description(
//                                "版本: \n\n" +
//                                "spring boot : 2.1.8\n\n" +
//                                "jjwt : 0.9.1\n\n" +
//                                "springdoc-openapi-ui : 1.2.32\n\n" +
//                                "其他sercurity jpa MySQL 等等 沒有寫版本(???\n\n" +
//                                "主要會練習使用各種sercurity 以及 swagger 3 常用的功能"));
//    }


}