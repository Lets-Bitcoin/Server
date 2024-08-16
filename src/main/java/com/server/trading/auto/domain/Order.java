package com.server.trading.auto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "tb_order")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;                // 주문의 고유 아이디
    private String side;                // 주문 종류
    private String ordType;             // 주문 방식
    private String price;               // 주문 당시 화폐 가격
    private String state;               // 주문 상태
    private String market;              // 마켓의 유일키
    private String createdAt;           // 주문 생성 시간
    private String volume;              // 사용자가 입력한 주문 양
    private String remainingVolume;     // 체결 후 남은 주문 양
    private String reservedFee;         // 수수료로 예약된 비용
    private String remainingFee;        // 남은 수수료
    private String paidFee;             // 사용된 수수료
    private String locked;              // 거래에 사용중인 비용
    private String executedVolume;      // 체결된 양
    private Integer tradesCount;        // 해당 주문에 걸린 체결 수
}
