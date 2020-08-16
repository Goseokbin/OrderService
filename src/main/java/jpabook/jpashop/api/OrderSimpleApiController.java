package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**XtoOne(OneToOne, ManyToOne)
 * Order
 * Order->Member
 * Order->Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    /**
     * 아래와 같이 엔티티를 직접 넘길 경우 Order를 조회하면 Member로 가고 Member에서 다시 Order로 무한루프에 빠지게 된다.
     * 위 문제는 JsonIgnore로 한쪽의 Json 생성을 끊어서 해결할 수 있지만 다른 문제가 발생한다.
     * Order에서 Member를 조회할 경우 Fetch=Lazy이기 때문에 porxy 객체를 넣어놓으면서 문제가 발생한다.
     * Hibernate5Module을 통해 member와 delivery의 지연로딩을 무시하고 member만 조회하게 하게한다.(meber,delivery는 Null값이 들어감)
     * 모듈 옵션을 통해 member,delivery 데이터를 가져와도 오더의 전체 데이터를 가져오는 문제가 발생하다.
     */
    @GetMapping("/api/v1/simple-order")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();            //Lazy가 강제로 초기화 된다.
            order.getDelivery().getAddress();
        }
        return all;
    }

    /**
     *
     * DTO로 Api를 만들경우 v1와 같이 발생하는 문제로 lazy 로딩으로 인한 쿼리가 너무 많이 호출되는 문제가 있다.
     * orders를 생성하는 과정에서 1번 쿼리를 날리고
     * stream을 통해 DTO를 생성하는 과정에서 DTO 생성자에서 member 2번 delivery 2번 조회한다.
     * 1 +N(2번) + N(2번) 최악의 경우 / id가 같으면 영속성 콘텍스트가 확인해서 한번만 조회한다.
     */
    @GetMapping("/api/v2/simple-order")
    public List<SimpleOrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;

    }

    @Data
    private class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();     //Lazy초기화 영속성 콘텍스트가 아이디가지고 찾는다 없으면 디비에서 조회한다.
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
