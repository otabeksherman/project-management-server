package projectManagement.controller;

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
import projectManagement.service.LiveNotificationService;

import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final LiveNotificationService liveNotificationService;

    private static Logger logger = LogManager.getLogger(ItemController.class);

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> create(@RequestBody ItemDto dto, @RequestAttribute String userEmail) {
        logger.info("in create(): ");
        try {
            Item item = itemService.create(dto, userEmail);
            liveNotificationService.sendNotification(userEmail, dto.getBoardId(),
                    new EventDto("CREATE", Map.of("ITEM", item)));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Item created successfully", item));
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
            liveNotificationService.sendNotification(userEmail, dto.getBoardId(),
                    new EventDto("DELETE", Map.of("ITEM", dto.getItemId())));
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
            Item item = itemService.update(dto);
            liveNotificationService.sendNotification(userEmail, dto.getBoardId(),
                    new EventDto("UPDATE", Map.of("ITEM", item)));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Item updated successfully", item));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(e.getMessage(), dto));
        }
    }

    @PatchMapping("/type/update")
    public ResponseEntity<BaseResponse> updateType(@RequestBody UpdateItemTypeDto dto, @RequestAttribute String userEmail) {
        logger.info("in updateType(): ");
        try {
            Item item = itemService.updateType(dto);
            liveNotificationService.sendNotification(userEmail, dto.getBoardId(),
                    new EventDto("UPDATE", Map.of("ITEM_TYPE", item)));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Item type updated successfully", item));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(e.getMessage(), dto));
        }
    }

    @PatchMapping("/status/update")
    public ResponseEntity<BaseResponse> updateStatus(@RequestBody UpdateItemStatusDto dto, @RequestAttribute String userEmail) {
        logger.info("in updateStatus(): ");
        try {
            Item item = itemService.updateStatus(dto);
            liveNotificationService.sendNotification(userEmail, dto.getBoardId(),
                    new EventDto("UPDATE", Map.of("ITEM_STATUS", item)));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Item status updated successfully", item));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(e.getMessage(), dto));
        }
    }

    @PatchMapping("/comment/add")
    public ResponseEntity<BaseResponse> addComment(@RequestBody AddCommentDto dto, @RequestAttribute String userEmail) {
        logger.info("in addComment(): ");
        try {
            Item item = itemService.addComment(dto, userEmail);
            liveNotificationService.sendNotification(userEmail, dto.getBoardId(),
                    new EventDto("ADD", Map.of("COMMENT", item)));
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
