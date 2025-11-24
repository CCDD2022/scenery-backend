package com.doubleshan.scenery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import com.doubleshan.scenery.model.*;
import com.doubleshan.scenery.repository.*;
import com.doubleshan.scenery.service.PointsRuleAdminService;

@SpringBootApplication
public class SceneryApplication {
    public static void main(String[] args) {
        SpringApplication.run(SceneryApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(POIRepository poiRepo,
            MerchantRepository merchantRepo,
            GiftRepository giftRepo,
            MapAssetRepository mapRepo,
            PointsRuleAdminService pointsRuleAdminService,
            SupplierRepository supplierRepository) {
        return args -> {
            // 示例 POI
            poiRepo.save(new POI("双山景点A", "scenic", 23.630, 114.150));
            poiRepo.save(new POI("山间民宿", "hotel", 23.632, 114.151));
            poiRepo.save(new POI("特色餐厅", "restaurant", 23.631, 114.152));
            // 商家
            Merchant m1 = new Merchant("特产店", 23.629, 114.149);
            m1.setAddress("双山路1号");
            m1.setPhone("13800000001");
            merchantRepo.save(m1);
            Merchant m2 = new Merchant("咖啡馆", 23.628, 114.148);
            m2.setAddress("双山路2号");
            m2.setPhone("13800000002");
            merchantRepo.save(m2);
            // 礼品
            giftRepo.save(new Gift("纪念徽章", 50, 100));
            giftRepo.save(new Gift("定制水杯", 80, 50));
            // 地图资源
            mapRepo.save(new MapAsset("https://example.com/map1.png", "image"));
            mapRepo.save(new MapAsset("https://example.com/map2.png", "image"));
            // 积分规则
            pointsRuleAdminService.create("CHECKIN", 10, "打卡获取积分");
            pointsRuleAdminService.create("REVIEW", 5, "点评获取积分");
            pointsRuleAdminService.create("LIKE", 1, "点赞获取积分");
            // 供应商
            supplierRepository.save(new Supplier("官方供应商"));
        };
    }
}
