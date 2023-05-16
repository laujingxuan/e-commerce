package com.example.item.testDataInit;

import com.example.item.entity.Item;
import com.example.item.entity.ItemType;
import com.example.item.service.ItemService;
import com.example.item.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestItemInit implements ApplicationRunner {

    private ItemService itemService;

    private ItemTypeService itemTypeService;

    @Autowired
    public TestItemInit(ItemService itemService, ItemTypeService itemTypeService){
        this.itemService = itemService;
        this.itemTypeService = itemTypeService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ItemType kitchen = new ItemType("kitchen", "items for kitchen", new ArrayList<>());
        itemTypeService.save(kitchen);
        ItemType food = new ItemType("food", "items for eat", new ArrayList<>());
        itemTypeService.save(food);

        Item pan = new Item("pan", new BigDecimal(55.50), "pan for cooking", "4184e5fd-6fd1-40cf-a57b-3167fd19137f", kitchen);
        itemService.save(pan);
        Item pizza = new Item("pizza", new BigDecimal(10.99), "nice pizza", "4184e5fd-6fd1-40cf-a57b-3167fd19137f", food);
        itemService.save(pizza);
        Item bread = new Item("bread", new BigDecimal(1.9), "nice bread", "4184e5fd-6fd1-40cf-a57b-3167fd19137f", food);
        itemService.save(bread);

//        Item item = itemService.findByName("pizza");
//        System.out.println(item);
//        System.out.println(item.getItemType().getName());
//        System.out.println(itemService.findByNameAndUserUuid("pizza", "4184e5fd-6fd1-40cf-a57b-3167fd19137f"));
//        System.out.println(itemService.findByNameAndUserUuid("invalid", "4184e5fd-6fd1-40cf-a57b-3167fd19137f"));
        List<Item> items = itemService.findByItemTypeName("food");
        for (Item item: items){
            System.out.println(item.getItemType());
        }
    }
}
