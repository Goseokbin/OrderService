package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="order_id")
    private Order order;


    private int orderPrice; //주문가격
    private  int count;     //주문수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(orderPrice);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직 ==//

    /**
     * 주문 취소
     *
     */
    public void cancle() {
        getItem().addStock(count);
    }

    /**
     * 전체가격 조회
     */

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
