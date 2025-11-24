package com.doubleshan.scenery.controller.admin;

import com.doubleshan.scenery.model.Gift;
import com.doubleshan.scenery.model.Merchant;
import com.doubleshan.scenery.model.POI;
import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.RoleRequired;
import com.doubleshan.scenery.service.*;
import com.doubleshan.scenery.vo.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RoleRequired({ "ADMIN" })
public class AdminController {
    private final ContentService contentService;
    private final POIService poiService;
    private final MerchantService merchantService;
    private final GiftService giftService;
    private final SupplierService supplierService;
    private final PointsRuleAdminService pointsRuleAdminService;

    public AdminController(ContentService contentService, POIService poiService, MerchantService merchantService,
            GiftService giftService, SupplierService supplierService, PointsRuleAdminService pointsRuleAdminService) {
        this.contentService = contentService;
        this.poiService = poiService;
        this.merchantService = merchantService;
        this.giftService = giftService;
        this.supplierService = supplierService;
        this.pointsRuleAdminService = pointsRuleAdminService;
    }

    static class IntroUpdate {
        public String title;
        public String body_md;
        public String media_urls;
    }

    @PutMapping("/intro")
    public ApiResponse<IntroVO> updateIntro(@RequestBody IntroUpdate req) {
        return ApiResponse.ok(IntroVO.from(contentService.updateIntro(req.title, req.body_md, req.media_urls)));
    }

    static class ActivityUpdate {
        public String body_md;
    }

    @PutMapping("/activity")
    public ApiResponse<ActivityVO> updateActivity(@RequestBody ActivityUpdate req) {
        return ApiResponse.ok(ActivityVO.from(contentService.updateActivity(req.body_md)));
    }

    static class HotlineUpdate {
        public String number;
    }

    @PutMapping("/hotline")
    public ApiResponse<HotlineVO> updateHotline(@RequestBody HotlineUpdate req) {
        return ApiResponse.ok(HotlineVO.from(contentService.updateHotline(req.number)));
    }

    static class POICreate {
        public String name;
        public String type;
        public double latitude;
        public double longitude;
        public String description;
        public String media;
    }

    @PostMapping("/pois")
    public ApiResponse<POIVO> createPoi(@RequestBody POICreate req) {
        POI p = new POI(req.name, req.type, req.latitude, req.longitude);
        p.setDescription(req.description);
        p.setMedia(req.media);
        return ApiResponse.ok(POIVO.from(poiService.create(p)));
    }

    static class POIUpdate {
        public String name;
        public String description;
        public String media;
    }

    @PutMapping("/pois/{id}")
    public ApiResponse<POIVO> updatePoi(@PathVariable String id, @RequestBody POIUpdate req) {
        return ApiResponse.ok(POIVO.from(poiService.update(id, req.name, req.description, req.media)));
    }

    @DeleteMapping("/pois/{id}")
    public ApiResponse<Void> deletePoi(@PathVariable String id) {
        poiService.delete(id);
        return ApiResponse.ok(null);
    }

    static class MerchantCreate {
        public String name;
        public double latitude;
        public double longitude;
        public String address;
        public String phone;
    }

    @PostMapping("/merchants")
    public ApiResponse<MerchantVO> createMerchant(@RequestBody MerchantCreate req) {
        Merchant m = new Merchant(req.name, req.latitude, req.longitude);
        m.setAddress(req.address);
        m.setPhone(req.phone);
        return ApiResponse.ok(MerchantVO.from(merchantService.create(m)));
    }

    static class MerchantUpdate {
        public String address;
        public String phone;
    }

    @PutMapping("/merchants/{id}")
    public ApiResponse<MerchantVO> updateMerchant(@PathVariable String id, @RequestBody MerchantUpdate req) {
        return ApiResponse.ok(MerchantVO.from(merchantService.update(id, req.address, req.phone)));
    }

    @DeleteMapping("/merchants/{id}")
    public ApiResponse<Void> deleteMerchant(@PathVariable String id) {
        merchantService.delete(id);
        return ApiResponse.ok(null);
    }

    static class GiftCreate {
        public String name;
        public int points_cost;
        public int stock;
    }

    @PostMapping("/gifts")
    public ApiResponse<GiftVO> createGift(@RequestBody GiftCreate req) {
        Gift g = new Gift(req.name, req.points_cost, req.stock);
        return ApiResponse.ok(GiftVO.from(giftService.create(g)));
    }

    static class GiftUpdate {
        public String name;
        public Integer points_cost;
        public Integer stock;
        public String supplier_id;
    }

    @PutMapping("/gifts/{id}")
    public ApiResponse<GiftVO> updateGift(@PathVariable String id, @RequestBody GiftUpdate req) {
        Gift g = giftService.update(id, req.name, req.points_cost, req.stock);
        if (req.supplier_id != null)
            supplierService.assignSupplierToGift(id, req.supplier_id);
        return ApiResponse.ok(GiftVO.from(g));
    }

    @DeleteMapping("/gifts/{id}")
    public ApiResponse<Void> deleteGift(@PathVariable String id) {
        giftService.delete(id);
        return ApiResponse.ok(null);
    }

    static class SupplierCreate {
        public String name;
    }

    @PostMapping("/suppliers")
    public ApiResponse<SupplierVO> createSupplier(@RequestBody SupplierCreate req) {
        return ApiResponse.ok(SupplierVO.from(supplierService.create(req.name)));
    }

    @GetMapping("/suppliers")
    public ApiResponse<List<SupplierVO>> suppliers() {
        List<SupplierVO> list = supplierService.all().stream().map(SupplierVO::from).toList();
        return ApiResponse.ok(list);
    }

    @PutMapping("/gifts/{id}/supplier")
    public ApiResponse<Void> assignSupplier(@PathVariable String id, @RequestParam("supplier_id") String supplierId) {
        supplierService.assignSupplierToGift(id, supplierId);
        return ApiResponse.ok(null);
    }

    static class PointsRuleCreate {
        public String code;
        public int points;
        public String description;
    }

    @PostMapping("/points-rules")
    public ApiResponse<PointsRuleVO> createRule(@RequestBody PointsRuleCreate req) {
        return ApiResponse.ok(PointsRuleVO.from(pointsRuleAdminService.create(req.code, req.points, req.description)));
    }

    static class PointsRuleUpdate {
        public Integer points;
        public String description;
    }

    @PutMapping("/points-rules/{id}")
    public ApiResponse<PointsRuleVO> updateRule(@PathVariable String id, @RequestBody PointsRuleUpdate req) {
        return ApiResponse.ok(PointsRuleVO.from(pointsRuleAdminService.update(id, req.points, req.description)));
    }

    @GetMapping("/points-rules")
    public ApiResponse<List<PointsRuleVO>> listRules() {
        List<PointsRuleVO> list = pointsRuleAdminService.all().stream().map(PointsRuleVO::from).toList();
        return ApiResponse.ok(list);
    }

    @DeleteMapping("/points-rules/{id}")
    public ApiResponse<Void> deleteRule(@PathVariable String id) {
        pointsRuleAdminService.delete(id);
        return ApiResponse.ok(null);
    }
}
