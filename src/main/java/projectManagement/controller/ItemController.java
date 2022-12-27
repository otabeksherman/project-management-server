package projectManagement.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectManagement.dto.*;
import projectManagement.entities.item.Item;
import projectManagement.service.ItemService;

import java.sql.SQLDataException;

@Controller
@CrossOrigin
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private static Logger logger = LogManager.getLogger(ItemController.class);
    //private static Gson gson = new GsonBuilder().create();


    @PostMapping("/create")
    public ResponseEntity<BaseResponse> create(@RequestAttribute ItemDto dto, @RequestAttribute String userEmail) {
        logger.info("in create(): ");
        //ItemDto dto=gson.fromJson(String.valueOf(body),ItemDto.class);

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Item created successfully",
                            itemService.create(dto, userEmail)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(e.getMessage(), dto));
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestBody DeleteItemDto dto, @RequestAttribute String userEmail) {
        logger.info("in delete(): ");
        try {
            itemService.delete(dto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new BaseResponse<>("item deleted successfully",
                            dto.getItemId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(e.getMessage(), dto));
        }
    }
    @PatchMapping("/update")
    public ResponseEntity<BaseResponse> update(@RequestBody UpdateItemDto dto, @RequestAttribute String userEmail) {
        logger.info("in update(): ");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Item updated successfully",
                            itemService.update(dto)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(e.getMessage(), dto));
        }
    }
    @PatchMapping("/update/type")
    public ResponseEntity<BaseResponse> updateType(@RequestBody UpdateItemTypeDto dto, @RequestAttribute String userEmail) {
        logger.info("in updateType(): ");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Item type updated successfully",
                            itemService.updateType(dto)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(e.getMessage(), dto));
        }
    }
    @PatchMapping("/update/status")
    public ResponseEntity<BaseResponse> updateStatus(@RequestBody UpdateItemStatusDto dto, @RequestAttribute String userEmail) {
        logger.info("in updateStatus(): ");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Item status updated successfully",
                            itemService.updateStatus(dto)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(e.getMessage(), dto));
        }
    }
    @PatchMapping("/comment/add")
    public ResponseEntity<BaseResponse> addComment(@RequestBody AddCommentDto dto, @RequestAttribute String userEmail) {
        logger.info("in addComment(): ");
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Comment added successfully",
                            itemService.addComment(dto, userEmail)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(e.getMessage(), dto));
        }
    }
    @GetMapping("/{id}/get/subitems")
    public ResponseEntity<BaseResponse> getSubItems(@PathVariable Long id, @RequestAttribute String userEmail) {
        logger.info("in getSubItems(): ");
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new BaseResponse<>("Success",
                            itemService.getSubItems(id)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(e.getMessage(), id));
        }
    }
}
