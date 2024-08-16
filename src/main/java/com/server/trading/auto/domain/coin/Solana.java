package com.server.trading.auto.domain.coin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "tb_solana")
public class Solana {
    @Id @UuidGenerator
    private String id;
    private String market;                  // 마켓명
    private String candleDateTimeUtc;       // 캔들 기준 시각(UTC 기준)
    private String candleDateTimeKst;       // 캔들 기준 시각(KST 기준)
    private Double openingPrice;            // 시가
    private Double highPrice;               // 고가
    private Double lowPrice;                // 저가
    private Double tradePrice;              // 종가
    private Long timestamp;                 // 해당 캔들에서 마지막 틱이 저장된 시각
    private Double candleAccTradePrice;     // 누적 거래 금액
    private Double candleAccTradeVolume;    // 누적 거래량
    private Integer unit;                   // 분 단위(유닛)
}
