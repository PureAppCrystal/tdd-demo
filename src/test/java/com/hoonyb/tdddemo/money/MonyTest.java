package com.hoonyb.tdddemo.money;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hoonyb.tdddemo.domain.Money;

@DisplayName("통화별 계산 테스트")
public class MonyTest {


    // Test 1: 어떤 금액(주가)을 어떤 수(주식의 수)에 곱한 금액을 결과로 얻을 수 있어야 한다.
    @Test
    @DisplayName("달러 생성")
    public void constroctor() {
        Money five = Money.dollar(5);
        assertNotNull(five);
    }


    @Test
    @DisplayName("달러 곱셈")
    public void multipleDollar() {
        Money five = Money.dollar(5);
        assertEquals(Money.dollar(10), five.times(2));
        assertFalse(Money.dollar(11).equals(five.times(2)));

        Money dollarFive = Money.dollar(5);
        assertEquals(Money.dollar(10), dollarFive.times(2));
    }
    // Test 2: 통화가 다른 두 금액을 더해서 주어진 환율에 맞게 변한 금액을 결과로 얻을 수 있어야 한다.


    @Test
    @DisplayName("프랑 곱셈")
    public void multipleFranc() {
        Money five = Money.france(5);
        assertEquals(Money.france(10), five.times(2));
        assertFalse(Money.france(11).equals(five.times(2)));
    }


    @Test
    @DisplayName("일치 테스트")
    public void testEquality() {
        assertTrue(Money.dollar(11).equals(Money.dollar(11)));
        assertFalse(Money.dollar(11).equals(Money.dollar(12)));
        assertTrue(Money.france(11).equals(Money.france(11)));
        assertFalse(Money.france(11).equals(Money.france(12)));
        
        assertFalse(Money.dollar(11).equals(Money.france(11)));
        assertTrue(Money.dollar(11).equals(Money.dollar(11)));
        assertFalse(Money.dollar(11).equals(Money.dollar(12)));
    }

    @Test
    @DisplayName("통화 테스트")
    public void testCurrency() {
        assertEquals("USD", Money.dollar(1).currency());
        assertEquals("CHF", Money.france(1).currency());
    }

    @Test
    @DisplayName("달러 통화 덧셈")
    public void testDollarSum() {
        assertEquals(Money.dollar(10).plus(Money.dollar(5)), Money.dollar(15));
        assertTrue(Money.dollar(10).plus(Money.dollar(5)).equals(Money.dollar(15)));
        assertFalse(Money.dollar(10).plus(Money.dollar(5)).equals(Money.dollar(16)));
    }

    @Test
    @DisplayName("프랑스 통화 덧셈")
    public void testFranceSum() {
        assertEquals(Money.france(10).plus(Money.france(5)), Money.france(15));
        assertTrue(Money.france(10).plus(Money.france(5)).equals(Money.france(15)));
        assertFalse(Money.france(10).plus(Money.france(5)).equals(Money.france(16)));
    }

    @Test
    @DisplayName("다른 통화 덧셈")
    public void testDifCurrencySum() {
        // 5프랑 + 5프랑
        assertEquals(Money.dollar(10).plus(Money.france(10)), Money.dollar(15));
        assertTrue(Money.dollar(10).plus(Money.france(5)).equals(Money.dollar(25/2)));
        assertFalse(Money.dollar(10).plus(Money.france(5)).equals(Money.dollar(16)));
    }


}
