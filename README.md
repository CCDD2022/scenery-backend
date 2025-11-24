# Scenery Backend

示例项目：实现游客小程序与管理端核心 API（简化版）。

## 技术栈

- Spring Boot 3.3
- Spring Security + JWT（自定义过滤器）
- Spring Data JPA (MySQL)
- Validation
- Lombok
- OpenAPI (springdoc)

## 运行步骤

```cmd
mvn spring-boot:run
```

或打包：

```cmd
mvn clean package -DskipTests
java -jar target/scenery-backend-0.1.0-SNAPSHOT.jar
```

## 配置

编辑 `src/main/resources/application.yml` 设置数据库连接与 jwt.secret。

## API 分组

- /api/auth/*: 注册登录、绑定手机号
- /api/public/*: 引领区简介、景点列表、排行榜
- /api/checkin/*: 打卡、点赞、我的打卡、动态广场
- /api/user/*: 个人资料、头像更新
- /api/prize/*: 积分商城列表、兑换奖品
- /api/points/*: 积分流水
- /api/admin/*: 管理端（需 ROLE_ADMIN）

## 待完善

- 距离计算逻辑、打卡有效性校验
- 手机验证码真实校验流程
- 奖品核销返回格式可扩展
- 异步处理与缓存优化

## 安全

请求需在 Header 中附加：`Authorization: Bearer <token>`（公共接口除外）。

## 说明

当前代码为最小可运行骨架，未做复杂业务与异常细化，请根据实际需求继续增强。

