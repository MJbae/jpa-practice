package jpabook2.jpashop.domain.item;

import jpabook2.jpashop.domain.Category;
import jpabook2.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//상속관계의 경우 부모클래스에 아래와 같은 상속 전략을 명시한다.
//SingleTable 전략의 경우 여러 유형의 자식클래스를 통해 생선된 자료들을 한 개의 테이블로 관리한다는 것
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();


    //비지니스 로직 추가
    // 아래 addStock, removeStock과 같이 특정 주요 변수를 다룰 땐, setter를 사용하기 보다
    // 아래와 같이 핵심 메서드를 만드는 것이 에러 핸들링에 유리하다
    /*
        stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /*
        stock 감소
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}