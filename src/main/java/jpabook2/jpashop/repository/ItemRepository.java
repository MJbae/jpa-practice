package jpabook2.jpashop.repository;

import jpabook2.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        //item의 id를 새로 생성하는 경우 jpa에 저장되기 전까지 id가 생성되지 않았으로 null
        if(item.getId() == null){
            em.persist(item);
            //아래의 경우는 기존에 있었던 것을 update 하는 경우
        }else{
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }




}
