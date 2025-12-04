# 코프링으로 GraphQL + RabbitMQ 간단한 MSA 프로젝트 구현

## MQ 장애 대비 아키텍처

#### 방법 1: Transaction Outbox Pattern

```
1. DB에 User 저장
2. DB에 Outbox 테이블에 이벤트 저장 (같은 트랜잭션)
3. 별도 프로세스가 Outbox를 읽어서 RabbitMQ에 발행
4. 발행 성공 시 Outbox에서 삭제
```

#### 방법 2: RabbitMQ Publisher Confirms

```kotlin
rabbitTemplate.setConfirmCallback { correlationData, ack, cause ->
    if (!ack) {
        // 발행 실패 시 재시도 로직
    }
}
```

#### 방법 3: 메시지 영속성 설정

```kotlin
rabbitTemplate.convertAndSend(
    exchange,
    routingKey,
    message,
    MessagePostProcessor { message ->
        message.messageProperties.isPersistent = true
        message
    }
)
```
