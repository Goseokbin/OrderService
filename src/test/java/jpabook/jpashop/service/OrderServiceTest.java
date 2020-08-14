package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;


    @Test
    public void 주문_조회() {
        //given
        Member member = createMember();
        Book book = createBook("JAP BOOK", 10000, 10);
        int orderCount = 8;

        //when
        Long order = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order findOrder = orderRepository.findOne(order);

        assertEquals("주문 상태가 같아야한다", OrderStatus.ORDER, findOrder.getStatus());
        assertEquals("주문상품수 같아야한다", findOrder.getOrderItems().size(), 1);
        assertEquals("총 주문 가격이 같아야한다",findOrder.getTotalprice(),10000*orderCount);
        assertEquals("주문 수량 만큼 재고가 줄어야한다",book.getStockQuantity(),2);
    }
    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("JPA",10000,10);

        int orderCount = 11;
        orderService.order(member.getId(),item.getId(),orderCount);

        fail("재고 수량 부족 예외가 발생해야됨 ");
    }
    @Test
    public void 주문취소(){
        //given
        Member member=createMember();
        Item item = createBook("JPA", 10000, 10);
        Long order = orderService.order(member.getId(), item.getId(), 8);
        //when
        orderService.cancleOrder(order);
        //then
        Order getOrder = orderRepository.findOne(order);

        assertEquals("주문상태가 바뀌어야함",OrderStatus.CANCEL,getOrder.getStatus());
        assertEquals("재고가 늘어나야함",item.getStockQuantity(),10);

    }


    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강남", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantitiy) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantitiy);
        System.out.println(book.getClass());
        em.persist(book);
        System.out.println("booK:" + book.getClass());
        return book;
    }


}