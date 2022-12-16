package projectManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectManagement.entities.Status;
import projectManagement.entities.item.Item;
import projectManagement.repository.ItemRepository;

public class ItemService {

    private final ItemRepository itemRepository;

    private static final Logger logger = LogManager.getLogger(ItemService.class.getName());

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /*
    Item createItem(Status status){
        //TODO
    }

     */
    void deleteItem(Item item){
        //TODO
    }

    void changeType(Item item, String type){
        //TODO
    }

    void updateStatus(Item item, Status status){
        //TODO
    }
    void updateItem(Item item){
        //TODO
    }
}
