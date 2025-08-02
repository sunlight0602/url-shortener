// package com.example.demo.config;// package com.example.demo.config;
//
// import com.example.demo.entity.ShortUrlEntity;
// import com.example.demo.repository.ShortUrlRepository;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;
//
// @Slf4j
// @Configuration
// @RequiredArgsConstructor
// public class DataInitializer1 {
//     @Autowired
//     ShortUrlRepository shortUrlRepository;
//
//     @Bean
//     public CommandLineRunner initData() {
//         List<ShortUrlEntity> shortUrlEntityList = new ArrayList<>();
//         return args -> {
//             for (int i = 0; i < 10; i++) {
//                 ShortUrlEntity shortUrlEntity = new ShortUrlEntity();
//                 shortUrlEntity.setLongUrl("https://long/" + UUID.randomUUID().toString());
//                 shortUrlEntity.setShortCode("https-short-" + i);
//                 shortUrlEntityList.add(shortUrlEntity);
//                 log.info(String.format("Created ShortUrlEntity => [%s]", i));
//             }
//             shortUrlRepository.saveAll(shortUrlEntityList);
//         };
//     }
// }
