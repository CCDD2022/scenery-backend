# 景区小程序与管理端 API 文档（纯 REST 路径版）

基础地址：`http://localhost:8080`

统一响应结构：

```json
{
  "success": true,
  "message": "OK",
  "data": {}
}
```

认证方式：需要登录的接口，使用请求头：`Authorization: Bearer <token>`。

---

## 1. 认证模块

### 1.1 用户注册

`POST /api/auth/register`

请求体：
```json
{
  "username": "alice",
  "password": "md5-hash-here",
  "nickName": "Alice"
}
```
响应：
```json
{
  "success": true,
  "message": "OK",
  "data": {
    "userId": 1,
    "token": "<jwt-token>"
  }
}
```

### 1.2 用户登录

`POST /api/auth/login`

请求体：
```json
{
  "username": "alice",
  "password": "password"
}
```
响应：
```json
{
  "success": true,
  "message": "OK",
  "data": {
    "userId": 1,
    "token": "<jwt-token>",
    "nickName": "Alice",
    "avatar": ""
  }
}
```

### 1.3 绑定手机号（需登录）

`POST /api/auth/bindPhone`
- 请求头：`Authorization: Bearer <token>`
请求体：
```json
{
  "phone": "13800001234",
  "code": "123456"
}
```
响应：
```json
{ "success": true, "message": "OK", "data": null }
```

### 1.4 管理员登录

管理员初始化的时候创建了一个admin admin123

`POST /api/auth/adminLogin`

- 请求体：
```json
{
  "username": "admin",
  "password": "admin123"
}
```
- 响应：
```json
{
  "success": true,
  "message": "OK",
  "data": { "token": "<admin-jwt-token>" }
}
```

---

## 2. 公共模块（免登录）

### 2.1 引领区简介

`GET /api/public/introduction`

- 响应：
```json
{ "success": true, "message": "OK", "data": "引领区简介占位内容" }
```

### 2.2 景点列表

`GET /api/public/spots`

- 查询参数（可选）：`lat`, `lng`
- 响应（示例项）：
```json
{
  "success": true,
  "message": "OK",
  "data": [
    {
      "id": 101,
      "name": "中心广场",
      "lat": 31.2304,
      "lng": 121.4737,
      "description": "地标广场",
      "image": ""
    }
  ]
}
```

### 2.3 附近推荐

`GET /api/public/nearby`
- 查询参数：`lat`（必填），`lng`（必填），`limit`（默认 5）
- 响应：同“景点列表”返回结构，按距离升序取前 `limit` 条。

### 2.4 打卡排名

`GET /api/public/ranking`
- 查询参数：`page`（默认 0）
- 响应（分页示例，省略部分分页元数据）：
```json
{
  "success": true,
  "message": "OK",
  "data": {
    "content": [
      {
        "id": 1,
        "username": "alice",
        "nickName": "Alice",
        "totalPoints": 120,
        "checkInCount": 5
      }
    ],
    "totalElements": 100,
    "totalPages": 10,
    "size": 10,
    "number": 0
  }
}
```

### 2.5 点评广场（公共）

`GET /api/public/feed`

- 查询参数：`page`（默认 0）
- 响应（分页的打卡记录）：
```json
{
  "success": true,
  "message": "OK",
  "data": {
    "content": [
      {
        "id": 5001,
        "photoFileId": "file_abc",
        "comment": "今天天气好",
        "pointsEarned": 10,
        "checkLat": 31.2304,
        "checkLng": 121.4737,
        "distance": 0.00,
        "status": 1,
        "createdAt": "2025-11-24T15:20:30Z"
      }
    ],
    "totalElements": 30,
    "totalPages": 3,
    "size": 10,
    "number": 0
  }
}
```

---

## 3. 打卡模块

### 3.1 拍照打卡（需登录）

`POST /api/checkin/create`
- 请求头：`Authorization: Bearer <token>`
- 请求体（经纬度可选，不传则使用景点坐标、距离记为 0）：
```json
{
  "spotId": 101,
  "photoFileID": "file_abc",
  "comment": "今天天气好"
}
```
- 或者包含经纬度：
```json
{
  "spotId": 101,
  "photoFileID": "file_abc",
  "comment": "今天天气好",
  "lat": 31.23051,
  "lng": 121.47391
}
```
- 响应：
```json
{ "success": true, "message": "OK", "data": null }
```

### 3.2 点赞（需登录）

`POST /api/checkin/like`
- 请求头：`Authorization: Bearer <token>`
- 请求体：
```json
{ "checkInId": 5001 }
```
- 响应：
```json
{
  "success": true,
  "message": "OK",
  "data": { "checkInId": 5001, "likeCount": 12 }
}
```

### 3.3 我的打卡（需登录）

`GET /api/checkin/my`

- 请求头：`Authorization: Bearer <token>`
- 查询参数：`page`（默认 0）
- 响应：同“点评广场（公共）”分页结构。

### 3.4 点评广场（授权版）

`GET /api/checkin/feed`
- 查询参数：`page`（默认 0）
- 响应：同“点评广场（公共）”分页结构。

---

## 4. 用户模块（需登录）

### 4.1 个人中心

`GET /api/user/profile`
- 请求头：`Authorization: Bearer <token>`
- 响应：
```json
{
  "success": true,
  "message": "OK",
  "data": {
    "userId": 1,
    "nickName": "Alice",
    "avatar": "",
    "phone": "",
    "totalPoints": 120,
    "checkInCount": 5
  }
}
```

### 4.2 更新头像

`POST /api/user/avatar`
- 请求头：`Authorization: Bearer <token>`
- 请求体：
```json
{ "avatarUrl": "cloud://bucket/avatar_1.png" }
```
- 响应：
```json
{ "success": true, "message": "OK", "data": null }
```

---

## 5. 奖品与积分模块

### 5.1 积分商城（免登录）

`GET /api/prize/list`
- 响应（示例项）：
```json
{
  "success": true,
  "message": "OK",
  "data": [
    {
      "id": 201,
      "name": "纪念徽章",
      "points": 50,
      "stock": 100,
      "description": "",
      "image": "",
      "status": 1
    }
  ]
}
```

### 5.2 兑换奖品（需登录）

`POST /api/prize/exchange`
- 请求头：`Authorization: Bearer <token>`
- 请求体：
```json
{ "prizeId": 201 }
```
- 响应：
```json
{
  "success": true,
  "message": "OK",
  "data": { "exchangeCode": "AB12CD34" }
}
```

### 5.3 积分明细（需登录）

`GET /api/points/list`
- 请求头：`Authorization: Bearer <token>`
- 查询参数：`page`（默认 0）
- 响应（分页示例项）：
```json
{
  "success": true,
  "message": "OK",
  "data": {
    "content": [
      {
        "id": 9001,
        "type": "check_in",
        "points": 10,
        "balance": 120,
        "sourceId": 5001,
        "description": "打卡奖励",
        "createdAt": "2025-11-24T15:20:30Z"
      }
    ],
    "totalElements": 12,
    "totalPages": 2,
    "size": 10,
    "number": 0
  }
}
```

---

## 6. 管理端模块（需管理员 Token，ROLE_ADMIN）

### 6.1 用户列表（搜索）

`GET /api/admin/users`
- 请求头：`Authorization: Bearer <admin-token>`
- 查询参数：`page`（默认 0），`keyword`（可选，按用户名/昵称搜索）
- 响应（数组，字段与文档一致）：
```json
{
  "success": true,
  "message": "OK",
  "data": [
    {
      "userId": 1,
      "username": "alice",
      "nickName": "Alice",
      "phone": "",
      "points": 120,
      "checkInCount": 5
    }
  ]
}
```

### 6.2 调整积分
- 路径：`POST /api/admin/adjustPoints`
- 请求头：`Authorization: Bearer <admin-token>`
- 请求体：
```json
{ "userId": 1, "points": -20, "reason": "违规扣分" }
```
- 响应：
```json
{ "success": true, "message": "OK", "data": null }
```

### 6.3 兑奖核销
- 路径：`POST /api/admin/verifyPrize/{code}`
- 请求头：`Authorization: Bearer <admin-token>`
- 示例：`/api/admin/verifyPrize/AB12CD34`
- 响应：
```json
{
  "success": true,
  "message": "OK",
  "data": {
    "userId": 1,
    "userNickName": "Alice",
    "prizeInfo": "纪念徽章"
  }
}
```

### 6.4 景点管理（REST）

- 创建：`POST /api/admin/spots`
  - 头：`Authorization: Bearer <admin-token>`
  - 体：
```json
{ "name": "中心广场", "lat": 31.2304, "lng": 121.4737, "description": "地标广场", "image": "", "status": 1 }
```
- 更新：`PUT /api/admin/spots/{id}`（体同创建）
- 删除：`DELETE /api/admin/spots/{id}`
- 列表：`GET /api/admin/spots`
- 响应：均为统一响应结构，数据为对应的 `Spot` 对象或列表。

---

## 7. 错误响应示例

- 未登录：
```json
{ "success": false, "message": "Unauthorized", "data": null }
```
- 已点赞：
```json
{ "success": false, "message": "已点赞", "data": null }
```
- 资源不存在：
```json
{ "success": false, "message": "景点不存在", "data": null }
```

