package projectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectManagement.controller.response.BaseResponse;
import projectManagement.entities.Status;
import projectManagement.entities.item.Item;
import projectManagement.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    public ItemController() {
    }

    /*
    ResponseEntity<BaseResponse<Item>> createItem(Status status) {
        //TODO
    }
    */

    void updateeItem(Item item){
        //TODO
    }

    void changeType(Item item, String type){
        //TODO
    }

    void updateStatus(Item item, Status status){
        //TODO
    }
    void deleteItem(Item item){
        //TODO
    }
}
