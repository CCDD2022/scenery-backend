# 双山引领区小程序及管理后台 API 接口文档

本文档描述了游客小程序、管理后台及商家端的所有 API 接口。

## 基础信息

- **Base URL**: `/api/v1`
- **鉴权方式**:
  - 游客端：部分接口需要 `user_id` 参数或 Token（示例中使用 `user_id` 查询参数或 Header）。
  - 管理端：Header `X-Role: admin`
  - 商家端：Header `X-Role: merchant`, `X-Merchant-ID: <id>`

---

## 1. 游客小程序接口

### 1.1 认证与用户

- **登录/注册**
  - `POST /auth/login`
  - Body: `{ "open_id": "...", "nickname": "...", "avatar": "..." }`
  - Response: User Object

- **更新用户信息**
  - `PATCH /users/:id`
  - Body: `{ "nickname": "...", "avatar": "...", "phone": "..." }`

- **获取用户打卡记录**
  - `GET /users/:id/checkins`

- **获取未打卡点提示**
  - `GET /checkins/unvisited?user_id=...`

### 1.2 简介与活动

- **获取引领区简介**
  - `GET /intro`
  - Response: `{ "title": "...", "body_md": "...", "media_urls": [] }`

- **获取活动说明**
  - `GET /activity`

- **获取客服热线**
  - `GET /hotline`

### 1.3 景点与打卡

- **获取手绘地图配置**
  - `GET /map/handdrawn`

- **推荐景点 (附近/热度)**
  - `GET /recommend/pois?lat=...&lon=...&sort=distance|heat`

- **打卡**
  - `POST /checkins`
  - Body: `{ "user_id": "...", "poi_id": "...", "latitude": 23.0, "longitude": 114.0, "photo": "url" }`

- **点赞打卡记录**
  - `POST /checkins/:id/like?user_id=...`

- **获取打卡动态 (点评)**
  - `GET /dynamics`

- **发布点评**
  - `POST /reviews`
  - Body: `{ "user_id": "...", "poi_id": "...", "checkin_id": "...", "stars": 5, "content": "..." }`

### 1.4 收藏

- **获取收藏列表**
  - `GET /favorites?user_id=...`
- **添加收藏**
  - `POST /favorites`
  - Body: `{ "user_id": "...", "poi_id": "..." }`
- **取消收藏**
  - `DELETE /favorites/:id`

### 1.5 排行榜

- **获取总排行榜**
  - `GET /rankings`
- **获取我的排名**
  - `GET /rankings/me?user_id=...`

### 1.6 积分与礼品

- **获取积分明细**
  - `GET /points/ledger?user_id=...`
- **获取积分统计**
  - `GET /points/stats?user_id=...`
- **获取积分规则**
  - `GET /points/rules`
- **礼品列表**
  - `GET /gifts`
- **兑换礼品**
  - `POST /gifts/:id/redeem?user_id=...`
- **我的兑换记录 (核销码)**
  - `GET /gifts/my?user_id=...`

### 1.7 商家推荐

- **推荐商家**
  - `GET /recommend/merchants?lat=...&lon=...&sort=distance|heat`

---

## 2. 商家端接口

*需 Header `X-Role: merchant`*

- **商家登录**
  - `POST /merchant/login`
  - Body: `{ "username": "...", "password": "..." }`

- **核销礼品**
  - `POST /gifts/redeems/:id/verify`
  - Header: `X-Merchant-ID: ...`, `X-User-ID: ...` (操作员)

- **核销记录查询**
  - `GET /verify/logs`
  - Header: `X-Merchant-ID: ...`

- **核销统计**
  - `GET /verify/stats?period=day|week|month`
  - Header: `X-Merchant-ID: ...`

---

## 3. 管理端接口

*需 Header `X-Role: admin`*

### 3.1 内容管理

- **更新简介**
  - `PUT /intro`
- **更新活动说明**
  - `PUT /activity`
- **更新热线**
  - `PUT /hotline`

### 3.2 资源管理 (POI/商家/礼品)

- **POI 管理**
  - `POST /admin/pois`, `PUT /admin/pois/:id`, `DELETE /admin/pois/:id`
- **商家管理**
  - `POST /admin/merchants`, `PUT /admin/merchants/:id`, `DELETE /admin/merchants/:id`
- **礼品管理**
  - `POST /admin/gifts`, `PUT /admin/gifts/:id`, `DELETE /admin/gifts/:id`
- **供应商管理**
  - `POST /admin/suppliers`, `GET /admin/suppliers`
- **关联礼品供应商**
  - `PUT /admin/gifts/:id/supplier`

### 3.3 积分规则管理

- **积分规则 CRUD**
  - `GET /admin/points-rules`
  - `POST /admin/points-rules`
  - `PUT /admin/points-rules/:id`
  - `DELETE /admin/points-rules/:id`

### 3.4 用户权限 (基础)

- **角色列表**
  - `GET /users/roles`
- **权限列表**
  - `GET /users/permissions`

---

## 缺失功能/待完善项说明

1. **高级统计分析**:
   - 目前管理端缺乏全局的打卡统计（如热力图数据）、积分发放/消耗总览图表接口。
   - 建议增加 `/admin/stats/checkins` 和 `/admin/stats/points` 接口。

2. **RBAC 完整管理**:
   - 目前仅有角色/权限列表查询，缺乏对后台用户（AdminUser）的增删改查及角色分配接口。
   - 建议增加 `/admin/users` 相关 CRUD 接口。

3. **短信与认证集成**:
   - 需求中提到的“短信验证接口”、“身份认证接口”目前未集成第三方服务（如阿里云 SMS）。
   - 需在 `AuthService` 中实现对接。

4. **微信支付/登录深度集成**:
   - 目前登录仅为模拟 OpenID 交换，未实现微信 `jscode2session` 及支付回调逻辑。
