# 双山（南昆山-罗浮山）游客小程序后端示例 (Java Spring Boot)

本仓库已实现 Spring Boot 内存后端用于快速演示需求 API。生产环境请替换为持久化存储、完善鉴权、安全与监控。下文包含 JWT 使用、商家电话一键拨打说明与接口列表。

## 架构概览

- 入口：`SceneryApplication.java`
- 模型：`model` 内部实体 (User / POI / Checkin / Review / Gift / GiftRedeem / PointLedger / Merchant ...)
- 仓库：`repository` 基于 `ConcurrentHashMap` 内存存储
- 服务：`service` 业务逻辑、核销、积分、统计聚合
- 鉴权：`security` JWT Filter + `RoleRequired` 注解 + ThreadLocal `SecurityContext`
- 控制器：`controller/visitor` 游客；`controller/merchant` 商家；`controller/admin` 管理；`controller/common` 公共&外部占位
- VO：`vo` 对外返回视图对象（含距离、分类翻译、禁用状态等）
- 响应与错误：`response` 统一结构 & 全局异常
- 持久化：已从内存迁移至 MySQL + Spring Data JPA (见下文)

## 模块与需求映射

| 模块 | 需求点 | 说明 |
|------|--------|------|
| Auth | 登录 (微信 openid) | JWT 登录返回 token + userVO |
| User | 个人中心 | 基本信息与头像/手机号/部门更新 |
| POI | 景点/住宿/饭店 | 列表/详情/距离/热度/分类筛选 |
| Checkin | 地图打卡 / 记录 | 创建打卡、点赞、距离校验（200m）、管理删除异常记录 |
| Ranking | 打卡排名 | 打卡+点赞加权分数 |
| Recommend | 附近推荐 | 距离/热度排序 POI & 商家 |
| Gift / Points | 积分兑换核销 | 积分规则、兑换、核销、流水与汇总统计 |
| Review / Dynamics | 点评动态 | 点评与最近动态（打卡/点评） |
| Admin | 管理端 | 内容/POI/商家/礼品/积分规则与统计、用户中心 |
| Merchant | 商家核销 | 登录、兑换核销、核销统计(day/week/month) |
| Map | 手绘地图 | 资源列表 |
| Stats | 全局统计 | 用户/打卡/礼品/商家/今日打卡 |
| External | 外部占位 | 短信/身份/微信登录模拟接口 |

## 通用响应结构

```json
{
  "code": 0,
  "message": "ok",
  "data": { }
}
```
错误码示例：
- 0: 成功
- 40001: 参数错误
- 40100: 未授权（需扩展）
- 40400: Not Found
- 40900: 冲突（库存不足 / 已核销）
- 50000: 服务端错误

## 接口文档（示例）
Base URL: `http://localhost:8080/api/v1`

### 1. 登录 / 注册 (JWT)

POST `/api/v1/auth/login`

请求示例：
```json
{
  "open_id": "wx_openid_123",
  "nickname": "游客A",
  "avatar": "https://.../avatar.jpg"
}
```

响应示例（含 JWT Token）：
```json
{
  "code":0,
  "message":"ok",
  "data": {
    "token":"<JWT>",
    "user": {"id":"...","nickname":"游客A","role":"USER"}
  }
}
```

后续请求头添加：
```
Authorization: Bearer <JWT>
```
商家登录同理（返回商家信息与 token）。管理员接口需 `role=ADMIN` 的 JWT。

### 商家电话一键拨打

商家接口与推荐返回 `phone` 字段，前端可直接使用：

```js
window.location.href = 'tel:' + merchant.phone
```

或在小程序中调用拨号能力。展示前可进行确认弹窗。
```

### 2. 创建打卡
POST `/checkins`
```jsonc
Request {
  "user_id":"user-1",
  "poi_id":"poi-1",
  "latitude":23.63,
  "longitude":114.15,
  "photo":"https://.../photo.jpg"
}
```
失败示例（超距离）：
```json
{"code":40001,"message":"距离过远，无法打卡"}
```

### 3. 点赞打卡
POST `/checkins/{id}/like`
Response 成功：`{"code":0,"message":"ok"}`

### 4. 排行榜
GET `/rankings`
```json
{"code":0,"data":[{"user_id":"...","nickname":"游客A","score":120,"checkins":12,"likes":0}]}
```
评分公式（示例）：`score = 打卡数*10 + 点赞数`

### 5. 礼品列表
GET `/gifts`
Response: 礼品数组

### 6. 兑换礼品
POST `/gifts/{id}/redeem?user_id={uid}`
成功返回兑换记录（含二维码）：
```json
{"code":0,"data":{"id":"...","gift_id":"...","qrcode":"<uuid>","used":false}}
```
错误：`库存不足 / 积分不足`

### 7. 我的兑换记录
GET `/gifts/my?user_id={uid}`

### 8. 礼品核销（商家）
POST `/gifts/redeems/{id}/verify`
成功：`{"code":0}` 已核销冲突：`{"code":40900,"message":"已核销"}`

### 新增已实现接口（除上文外）

| 分类 | Endpoint | 方法 | 描述 |
|------|----------|------|------|
| 用户更新 | `/users/{id}` | PATCH | 更新昵称/头像/手机号 |
| 用户打卡记录 | `/users/{id}/checkins` | GET | 已打卡列表 |
| 个人排行 | `/rankings/me?user_id=` | GET | 返回当前用户排行位置与数据 |
| 积分规则 | `/points/rules` | GET | 静态积分规则列表 |
| 积分流水 | `/points/ledger?user_id=` | GET | Earn/Spend 流水列表 |
| 积分统计 | `/points/stats?user_id=` | GET | earned/spent/balance 汇总 |
| 点评提交 | `/reviews` | POST | 星级(1-5)+内容+关联POI/打卡 |
| 点评列表 | `/reviews?poi_id=` | GET | 指定 POI 的点评列表 |
| 动态 | `/dynamics?limit=` | GET | 最近点评(可扩展包含打卡) |
| 推荐POI | `/recommend/pois?lat=&lon=&sort=` | GET | 距离或热度排序 |
| POI列表 | `/pois?type=&lat=&lon=&sort=` | GET | 分类/距离/热度 |
| POI详情 | `/pois/{id}?lat=&lon=` | GET | 包含距离与中文分类 |
| 管理端打卡列表 | `/admin/checkins` | GET | 过滤 poiId/userId/time 范围 |
| 管理端删除打卡 | `/admin/checkins/{id}` | DELETE | 删除异常打卡 |
| 管理端打卡统计 | `/admin/checkins/stats/poi` | GET | 按 POI 聚合数量 |
| 管理端积分流水 | `/admin/points-ledgers` | GET | 全量流水过滤 type/user |
| 管理端积分统计 | `/admin/points-ledgers/stats` | GET | Earn/Spend 汇总 |
| 管理端礼品兑换列表 | `/admin/gift-redeems` | GET | 用/未用过滤 |
| 管理端礼品兑换统计 | `/admin/gift-redeems/stats` | GET | used/unused 汇总 |
| 管理端用户列表 | `/admin/users` | GET | role/disabled 过滤 |
| 管理端更新角色 | `/admin/users/{id}/role` | PUT | 变更用户角色 |
| 管理端禁用用户 | `/admin/users/{id}/disable` | PUT | 设置禁用 |
| 管理端启用用户 | `/admin/users/{id}/enable` | PUT | 取消禁用 |
| 管理端部门更新 | `/admin/users/{id}/department` | PUT | 设置部门字段 |
| 全局统计 | `/admin/stats/global` | GET | 用户/打卡/礼品/商家/今日打卡 |
| 外部短信占位 | `/external/sms/send` | POST | 返回固定验证码 |
| 外部身份占位 | `/external/id/verify` | POST | mock 验证通过 |
| 外部微信登录占位 | `/external/wechat/login` | POST | mock openId |
| 推荐商家 | `/recommend/merchants?lat=&lon=&sort=` | GET | sort=distance 或 heat |
| 手绘地图 | `/map/handdrawn` | GET | 返回地图资源列表 |
| 管理端创建POI | `/admin/pois` | POST | 新建景点/酒店/餐厅 |
| 管理端更新POI | `/admin/pois/{id}` | PUT | 更新 POI |
| 管理端删除POI | `/admin/pois/{id}` | DELETE | 删除 POI |
| 管理端礼品创建 | `/admin/gifts` | POST | 新建礼品 |
| 管理端礼品更新 | `/admin/gifts/{id}` | PUT | 更新礼品 |
| 管理端礼品删除 | `/admin/gifts/{id}` | DELETE | 删除礼品 |
| 管理端商家创建 | `/admin/merchants` | POST | 新建商家 |
| 管理端商家更新 | `/admin/merchants/{id}` | PUT | 更新商家信息 |
| 管理端商家删除 | `/admin/merchants/{id}` | DELETE | 删除商家 |

## 数据模型摘要

| 模型 | 关键字段 | 说明 |
|------|----------|------|
| User | ID, OpenID, Points, Phone | 用户与积分 |
| POI | Type, Lat/Lon, Heat | 景点/住宿/餐饮点位 |
| Checkin | UserID, POIID, Likes | 打卡与点赞 |
| Review | Stars, Content, POIID | 点评与星级 |
| Gift | PointsCost, Stock | 礼品库存与兑换消耗 |
| GiftRedeem | QRCode, Used, UsedAt | 礼品兑换与核销状态 |
| PointLedger | Type(Earn/Spend), Amount | 积分流水记录 |
| Merchant | Name, Lat/Lon, Heat | 附近商家推荐与核销场景 |
| PointsRule | Code, Points | 静态积分规则展示 |
| MapAsset | URL | 手绘地图资源 |

## 距离校验逻辑
使用简化 Haversine：若用户当前坐标与目标 POI 距离 > 200m 返回错误。

## 排行计算示例

```text
score = checkin_count * 10 + like_count
```
可后续扩展：时间衰减、权重配置、积分加成等。

## Java 运行

```cmd
mvn clean package
mvn spring-boot:run
```
访问: `http://localhost:8080/actuator/health` 或接口前缀 `http://localhost:8080/api/v1`。

### MySQL 持久化配置

application.yml 示例：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/scenery?useSSL=false&serverTimezone=UTC&characterEncoding=utf8
    username: root
    password: <你的密码>
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

迁移要点：
- 原内存 `ConcurrentHashMap` 仓库已改为 `JpaRepository` 接口（例如 `POIRepository extends JpaRepository<POI,String>`）。
- 模型添加 `@Entity @Table @Id` 等注解；部分字段增加长度与唯一约束。
- 点赞集合使用 `@ElementCollection` 存储（`checkin_like` 表）。
- 所有写操作仍通过 Service 层，逻辑未变化；删除改为 `deleteById`。
- 初始化示例数据通过 `CommandLineRunner`，仅在表为空时插入。

生产建议：
- 将 `ddl-auto` 改为 `validate` 并使用 Flyway/Liquibase 管理迁移。
- 引入统一审计字段（create_time/update_time），可用 `@MappedSuperclass` + `@EntityListeners`。
- 添加数据库索引：常用筛选列如 `user_id`, `poi_id`, `created_at`。
- 分页查询替换当前部分一次性全量查询。


## 已实现主要接口 (Java)

游客端：登录、用户信息更新、打卡创建/点赞、未打卡提示、动态、点评提交/查询、礼品列表与兑换、我的兑换、积分规则/流水/统计、排行榜与个人排名、收藏增删查、附近 POI/商家推荐、手绘地图资源、简介/活动/热线。

商家端：商家登录、礼品核销、核销记录列表、核销统计(day/week/month)。

管理端：简介/活动/热线更新，POI/商家/礼品 CRUD，供应商创建与礼品供应商关联，积分规则 CRUD。

示例数据：启动后自动初始化若干 POI、商家、礼品、地图资源、积分规则与供应商。

## TODO / 可扩展
- 微信真实登录 + 会话校验
- 短信/身份接口真实对接
- 数据持久化(MySQL) + Redis 排行缓存
- 打卡热力与轨迹分析
- RBAC 权限粒度与操作审计
- 安全防护：速率限制 / XSS / 输入校验 / 日志追踪

## 后续扩展建议
- 鉴权：JWT + 微信登录态校验
- 存储：MySQL (GORM) + Redis 排行缓存
- 地图 & 手绘：文件上传 + CDN + 元数据表
- 排行刷新：定时任务 + 缓存层
- 积分策略：策略模式 / 可配置表
- 统计分析：预计算 + 数据仓库（ClickHouse）
- 安全：输入校验、速率限制、操作审计

## 错误处理与日志
当前示例使用简单统一响应，可扩展：
- Trace ID 贯穿 (X-Request-ID)
- 分级日志 (info/warn/error)
- 监控：Prometheus 指标（QPS, latency, error rate）

## License
示例代码仅用于演示，可自由修改用于生产。
