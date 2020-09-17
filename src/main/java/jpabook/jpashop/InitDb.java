package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.Blog;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit1() {
            Member member = createMember("userAB","123123","서울", "str1", "12313");
            em.persist(member);

            Book book1 = createBook("Jpa1 Book",10000,100);
            em.persist(book1);

            Book book2 = createBook("JPA2 Book", 20000,100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 3);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        private Member createMember(String name,String pass ,String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setPass(pass);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        public void dbInit2() {

            Member member = createMember("userB","1235","부산", "str2", "1523");
            em.persist(member);

            Book book1 = createBook("Spring1 Book", 10000,100);
            em.persist(book1);

            Book book2 = createBook("Spring2 Book", 20000,100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 3);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }
        public void dbInit3(){
            List<Member> memberList = em.createQuery("select m from Member m").getResultList();
            Blog blog1 = new Blog().builder()
                    .member(memberList.get(0))
                    .title("title1")
                    .content("content1")
                    .blogCategory(BlogCategory.Java)
                    .build();
            em.persist(blog1);

            Blog blog2 = new Blog().builder()
                    .member(memberList.get(1))
                    .title("title2")
                    .content("content2")
                    .blogCategory(BlogCategory.Java)
                    .build();
            em.persist(blog2);


        }
    }
}

