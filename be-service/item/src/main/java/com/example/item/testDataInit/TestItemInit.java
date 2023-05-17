package com.example.item.testDataInit;

import com.example.item.DTO.ItemDTO;
import com.example.item.DTO.ItemTypeDTO;
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
    public TestItemInit(ItemService itemService, ItemTypeService itemTypeService) {
        this.itemService = itemService;
        this.itemTypeService = itemTypeService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ItemTypeDTO kitchen = new ItemTypeDTO("kitchen", "items for kitchen");
        kitchen = itemTypeService.create(kitchen);
//        System.out.println(kitchen);
        ItemTypeDTO food = new ItemTypeDTO("food", "items for eat");
        food = itemTypeService.create(food);
//        System.out.println(food);

        ItemDTO pan = new ItemDTO("pan", new BigDecimal(55.50), "pan for cooking", "4184e5fd-6fd1-40cf-a57b-3167fd19137f", kitchen.getId());
        itemService.create(pan);
        ItemDTO pizza = new ItemDTO("pizza", new BigDecimal(10.99), "nice pizza", "4184e5fd-6fd1-40cf-a57b-3167fd19137f", food.getId());
        itemService.create(pizza);
        ItemDTO bread = new ItemDTO("bread", new BigDecimal(1.9), "nice bread", "4184e5fd-6fd1-40cf-a57b-3167fd19137f", food.getId());
        itemService.create(bread);

//        Item item = itemService.findByName("pizza");
//        System.out.println(item);
//        System.out.println(item.getItemType().getName());
//        System.out.println(itemService.findByNameAndUserUuid("pizza", "4184e5fd-6fd1-40cf-a57b-3167fd19137f"));
//        System.out.println(itemService.findByNameAndUserUuid("invalid", "4184e5fd-6fd1-40cf-a57b-3167fd19137f"));
//        List<Item> items = itemService.findByItemTypeName("food");
//        for (Item item: items){
//            System.out.println(item.getItemType());
//        }
    }
}
