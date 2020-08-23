package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;

    /**
     * v1 : 엔티티를 직접 노출하는 방법
     * fetch = Lazy로 설정되있기 때문에 프록시를 초기화 해줘야한다.
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }

    /**
     * v2: 엔티티 직접 노출에서 DTO로
     * OrderItem을 전송할 떄도 OrderItem도 엔티티이기 때문에 따로 DTO를 생성해서 보내야 한다.
     * 마찬가지로 지연로딩으로인해 너무 많은 쿼리가 발생해 성능 문제가 발생한다.
     */
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o)).collect(toList());
        return result;
    }

    /**
     * v2,v3에서 발생한 많은 쿼리를 페치 조인으로 문제를 해결할 수 있다.
     * 하지만 Order 2개, OrderItem 4개 일 대 다 관계에서도 중복이 발생한다.(Order가 2번 조회된다->OrderItem에 따라).
     * 이러한 문제는 JPA distinct를 통해 해결할 수 있다.
     * distinct를 추가함으로서 query에 distinct를 추가하고 중복된 엔티가 조회되면, 애플리케이에서 중복도 걸러준다.
     * 단점으로 페이징이 불가능하 컬렉션 페치 조인은 1개 사용할 수 있다.
     */
    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3(
    ) {
        List<Order> orders = orderRepository.findAllwithItem();
        List<OrderDto> result = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(toList());
        return result;
    }

    /**
     * v3-1은 v3에서 페이징이 불가능하는 단점을 보완한다.
     * ToOne의 관계는 페이징에 영향을 주지 않기 때문에 페치 조인으로 가져온다.
     * 이후 컬렉션 (1대다)의 경우 hibernate.default_batch_fetch_size (또는 @BatchSize)를 설정해 단점을 해결한다.
     * 이 옵션을 사용하 컬렉션이나, 프록 객체 한꺼번에 설정 size 만큼 in 쿼리로 한번에 가져온다.
     * (OrderItemDTO 루프를 OrderId에 맞춰 In 쿼리로 한번만 날림)
     * 쿼리를 날리는 수는 v3보다 많으나 그 수가 많지않고 DB 데이터 전송량 감소한다.
     */

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_1(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> result = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(toList());
        return result;
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem)).collect(toList());
        }
    }

    @Data
    static class OrderItemDto {
        private String itemName;//상품 명
        private int orderPrice; //주문 가격
        private int count; //주문 수량

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}


