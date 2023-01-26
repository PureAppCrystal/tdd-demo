package com.hoonyb.tdddemo.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.hoonyb.tdddemo.domain.order.Address;
import com.hoonyb.tdddemo.domain.order.Money;
import com.hoonyb.tdddemo.domain.order.Order;
import com.hoonyb.tdddemo.domain.order.OrderLine;
import com.hoonyb.tdddemo.domain.order.OrderState;
import com.hoonyb.tdddemo.domain.order.Product;
import com.hoonyb.tdddemo.domain.order.ProductType;
import com.hoonyb.tdddemo.domain.order.Receiver;
import com.hoonyb.tdddemo.domain.order.ShippingInfo;

@SpringBootTest
@DisplayName("주문관련 테스트")
public class OrderTests {

    private ProductType[] productTypes = ProductType.values();


    

    @Test
    @DisplayName("주문 상품 선택")
    public void selectOrderLines() {
        List<OrderLine> orderLine = createOrderLines(3);
        assertNotNull(orderLine);
    }

    @Test
    @DisplayName("수령인 정보 - 실패")
    public void createReceiver() {
        Receiver receiver1 = new Receiver("SH", "");
        assertTrue(receiver1.isBlank());
        Receiver receiver2 = new Receiver("", "1234");
        assertTrue(receiver2.isBlank());
    }

    @Test
    @DisplayName("배달 장소 - 실패")
    public void createAddress() {
        Address address1 = new Address("", "addr2", "zipCode");
        assertTrue(address1.isBlank());
        Address address2 = new Address("addr1", "", "zipCode");
        assertTrue(address2.isBlank());
        Address address3 = new Address("addr1", "addr2", "");
        assertTrue(address3.isBlank());
    }

    @Test
    @DisplayName("배송정보 입력 - 실패")
    public void createShippingInfoFail() {
        Receiver receiver = new Receiver("SH", "");
        Address address = new Address("경기도", "하남시", "465711");
        ShippingInfo shippingInfo = new ShippingInfo(receiver, address);
        assertTrue(shippingInfo.isBlank());

        Receiver receiver2 = new Receiver("SH", "1234");
        Address address2 = new Address("경기도", "하남시", "");
        ShippingInfo shippingInfo2 = new ShippingInfo(receiver2, address2);
        assertTrue(shippingInfo2.isBlank());


    }

    @Test
    @DisplayName("배송정보 입력 - 성공")
    public void createShippingInfoSuccess() {
        Receiver receiver = new Receiver("SH", "1234");
        Address address = new Address("경기도", "하남시", "465711");
        ShippingInfo shippingInfo = new ShippingInfo(receiver, address);
        assertNotNull(shippingInfo);
    }


    
    @Test
    @DisplayName("주문 요청 - 실패 - 상품정보 실패")
    public void createOrderFailOrderLines() throws IllegalStateException{
        List<OrderLine> orderLines1 = new ArrayList<OrderLine>();
        Receiver receiver1 = new Receiver("SH", "1234");
        Address address1 = new Address("경기도", "하남시", "465711");
        ShippingInfo shippingInfo1 = new ShippingInfo(receiver1, address1);

  
        Exception exception  = assertThrows(IllegalStateException.class, () -> {
            Order order1 = new Order(orderLines1, shippingInfo1);
        });
        assertEquals(exception.getMessage(), "no OrderLine");
    }

    @Test
    @DisplayName("주문 요청 - 실패 - 배송지정보 실패")
    public void createOrderFailShippingInfo() throws IllegalStateException{
        List<OrderLine> orderLines1 = createOrderLines(1);
        Receiver receiver1 = new Receiver("SH", "1234");
        Address address1 = new Address("경기도", "", "465711");
        ShippingInfo shippingInfo1 = new ShippingInfo(receiver1, address1);

  
        Exception exception  = assertThrows(IllegalStateException.class, () -> {
            Order order1 = new Order(orderLines1, shippingInfo1);
        });
        assertEquals(exception.getMessage(), "no Shipping Information");
    }

    @Test
    @DisplayName("주문 요청 - 성공")
    public void createOrderSuccess() throws IllegalStateException{
        List<OrderLine> orderLines1 = createOrderLines(1);
        Receiver receiver1 = new Receiver("SH", "1234");
        assertTrue(receiver1.isBlank() == false);

        Address address1 = new Address("경기도", "하남시", "465711");
        assertTrue(address1.isBlank() == false);

        ShippingInfo shippingInfo1 = new ShippingInfo(receiver1, address1);
        assertTrue(shippingInfo1.isBlank() == false);

        Order order1 = new Order(orderLines1, shippingInfo1);
        assertNotNull(order1);
    }


    @Test
    @DisplayName("주문-결제-출고-배송시작-배송완료")
    public void OrderSuccessProcess() {
        // 주문
        Order order = createOrder();
        assertEquals(order.getState(), OrderState.PAYMENT_WAITING);

        // 결제
        order.payment();
        assertEquals(order.getState(), OrderState.PREPARING);

        // 출고
        order.shipped();
        assertEquals(order.getState(), OrderState.SHIPPED);

        // 배송 시작
        order.startDelivery();
        assertEquals(order.getState(), OrderState.DELIVERING);


        // 배송 완료
        order.completeDelivery();
        assertEquals(order.getState(), OrderState.DELIVERING_COMPLETED);

    }

    @Test
    @DisplayName("주문 후 결제대기에서 주문 취소")
    public void cancelBeforePayment() {
        Order order = createOrder();
        assertEquals(order.getState(), OrderState.PAYMENT_WAITING);

        order.cancel();
        assertEquals(order.getState(), OrderState.CANCELED);
    }


    @Test
    @DisplayName("결제 후 상품 준비에서 주문 취소")
    public void cancelWhenPreparing() {
        Order order = createOrder();
        assertEquals(order.getState(), OrderState.PAYMENT_WAITING);

        order.payment();
        assertEquals(order.getState(), OrderState.PREPARING);

        order.cancel();
        assertEquals(order.getState(), OrderState.CANCELED);
    }


    @Test
    @DisplayName("출고 후 주문 취소")
    public void cancelAfterShipped() throws IllegalStateException {
        Order order = createOrder();
        assertEquals(order.getState(), OrderState.PAYMENT_WAITING);

        order.payment();
        assertEquals(order.getState(), OrderState.PREPARING);

        order.shipped();
        assertEquals(order.getState(), OrderState.SHIPPED);

        Exception exception  = assertThrows(IllegalStateException.class, () -> {
            order.cancel();
        });
        assertEquals(exception.getMessage(), "already shipped");
    }

    
    @Test
    @DisplayName("출고 전 배송정보 변경")
    public void changeShippingInfoBeforeShipped() {
        //
    }

    @Test
    @DisplayName("출고 후 배송정보 변경")
    public void changeShippingInfoAfterShipped() {
        //
    }


    

















    public List<OrderLine> createOrderLines(int orderLineCount) {
        List<OrderLine> orderLines = new ArrayList<>();

        for (int i = 0; i < orderLineCount; i++) {
            Product product = new Product(UUID.randomUUID().toString(), productTypes[i % productTypes.length]);
            Money price = new Money(10000 * i);

            orderLines.add(new OrderLine(product, price, i));
        }

        return orderLines;
    }

    public ShippingInfo createShippingInfo() {
        return ShippingInfo.builder()
                .receiver(new Receiver("SH", "010-1234-5678"))
                .address(new Address("Kyungido", "Korea", "12345"))
                .build();
    }
    
    public Order createOrder() {
        List<OrderLine> oerLine = createOrderLines(3)        ;
        ShippingInfo shippinInfo = createShippingInfo();
        return new Order(oerLine, shippinInfo);
    }

}
