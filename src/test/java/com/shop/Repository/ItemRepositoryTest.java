package com.shop.Repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
            System.out.println(savedItem.toString());
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println(item.toString());

        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());

        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc() {
        this.createItemList();
        List<Item> ItemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item : ItemList) {
            System.out.println(item.toString());

        }
    }
    //@Param 어노테이션을 이용하여 변수를 JPQL에 전달하는 대신 파라미터의 순서를 이용해 전달해줄수도 있다.
    //그럴 경우 ':itemDetail' 대신 첫 번째 파라미터를 전달하겠다는 ?1이라는 표현을 사용하면 된다.
    //하지만 파라미터의 순서가 달라지면 해당 쿼리문이 제대로 동작하지 않을 수 있기 때문에 좀 더 명시적인 방법인 @Param 어노테이션을 이용하는 방법을 추천한다.
    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
    //만약 기존의 데이터베이스에서 사용하던 쿼리를 그대로 사용해야 할 때는 @Query의 nativeQuery속성을 사용하면 기존 쿼리를 그대로 활용할 수 있다.
    //하지만 특정 데이터베이스에 종속되는 쿼리문을 사용하기 때문에 데이터베이스에 대해 독립적이라는 장점을 잃어버린다.
    //기존에 작성한 통계용 쿼리처럼 복잡한 쿼리를 그대로 사용해야 하는 경우 활용할 수 있다.
    //* nativeQuery는 좀 더 알아보자 *
    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailNative() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
}