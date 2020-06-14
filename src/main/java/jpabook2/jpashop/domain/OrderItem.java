package jpabook2.jpashop.domain;

import jpabook2.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;      //주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;    //주문

    private int orderPrice; //주문 가격
    private int count;      //주문 수량

    //생성 메서드//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }


    //비지니스 로직//
    public void cancel() {
        //orderItem 객체 기준에서 주문취소는 재고를 원복해준다는 의미
        getItem().addStock(count);
    }

    //조회 로직//
    //주문상품 전체가격 조회//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}