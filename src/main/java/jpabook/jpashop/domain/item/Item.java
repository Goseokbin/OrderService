package jpabook.jpashop.domain.item;

import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import jpabook.jpashop.domain.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //==생성 메서드 ==//
    public void createItem(int price, int stockQuantity){
        this.price=price;
        this.stockQuantity=stockQuantity;
    }

    //==비즈니스 로직==//

    /**
     * stock 증가.
     */
    public void addStock(int quantity){
        stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity){
        int result = stockQuantity-quantity;
        if(result<0){
            throw new NotEnoughStockException("need to stock");
        }
        stockQuantity = result;
    }
}
