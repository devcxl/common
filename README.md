# SpringBoot 通用开发包

- `cn.devcxl.common.base.CommonResp<T>` 通用返回结构体
- `cn.devcxl.common.base.PageRequest`分页请求
- `cn.devcxl.common.base.PageResponse<T>`分页返回数据
- `@EnableDateConvertAndFormat` 开启LocalDate入参转换
- `@EnableRedis` 开启Redis自动配置
- `@EnableRequestDebugLog` 开启被`@RequestDebug`注解的请求web日志
- `@EnableSecurityConfig` 开启SpringSecurity
- `@DistributedLock` 基于Redis的分布式锁
- `@Limit` 基于Redis的接口限流


## LocalDateTime 序列化为 TImeStamp

配置文件`application.yml`中添加配置

```yml
spring:
  common:
    date2timestamp: true
```

## token 认证

application.yml 配置

```yml
spring:
  security:
    jwt:
      secret: xxxxxxxxxxxxxxxxxxxxxx
  redis:
    host: 127.0.0.1
    port: 6379
```