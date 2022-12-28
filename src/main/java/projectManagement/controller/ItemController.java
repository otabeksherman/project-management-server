package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectManagement.dto.*;
import projectManagement.service.ItemService;

@Controller
@CrossOrigin
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private static Logger logger = LogManager.getLogger(ItemController.class);

    /**
     * creation of an "item" by using an ItemDto object and a userEmail string
     * It returns a ResponseEntity object with a BaseResponse body.
     *
     * @param dto
     * @param userEmail
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<BaseResponse> create(@RequestAttribute ItemDto dto, @RequestAttribute String userEmail) {
        logger.info("in create(): ");

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BaseResponse<>("Item created successfully",
                            itemService.create(dto, userEmail)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponse<>(e.getMessage(), dto));
        }
    }

    /**
     * deletion of an "item" using a DeleteItemDto object and a userEmail string.
     * It returns a ResponseEntity object with a BaseResponse body.
     *
     * @param dto
     * @param userEmail
     * @return item id
     */
    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestAttribute DeleteItemDto dto, @RequestAttribute String userEmail) {
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

    /**
     * handling the updating of an "item" using an UpdateItemDto object and a userEmail string.
     * It returns a ResponseEntity object with a BaseResponse body.
     * The function is decorated with the
     *
     * @param dto
     * @param userEmail
     * @return
     */
    @PatchMapping("/update")
    public ResponseEntity<BaseResponse> update(@RequestAttribute UpdateItemDto dto, @RequestAttribute String userEmail) {
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

    /**
     * updating of the type of an "item" using an UpdateItemTypeDto object and a userEmail string.
     * It returns a ResponseEntity object with a BaseResponse body.
     *
     * @param dto
     * @param userEmail
     */
    @PatchMapping("/type/update")
    public ResponseEntity<BaseResponse> updateType(@RequestAttribute UpdateItemTypeDto dto, @RequestAttribute String userEmail) {
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

    /**
     * updating of the status of an "item" using an UpdateItemStatusDto object and a userEmail string.
     * It returns a ResponseEntity object with a BaseResponse body.
     *
     * @param dto
     * @param userEmail
     * @return
     */
    @PatchMapping("/status/update")
    public ResponseEntity<BaseResponse> updateStatus(@RequestAttribute UpdateItemStatusDto dto, @RequestAttribute String userEmail) {
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

    /**
     * addition of a comment to an "item" using an AddCommentDto object and a userEmail string.
     * It returns a ResponseEntity object with a BaseResponse body.
     *
     * @param dto
     * @param userEmail
     */
    @PatchMapping("/comment/add")
    public ResponseEntity<BaseResponse> addComment(@RequestAttribute AddCommentDto dto, @RequestAttribute String userEmail) {
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

    /**
     * retrieval of sub-items of an "item" identified by an ID.
     * It returns a ResponseEntity object with a BaseResponse body.
     *
     * @param id
     * @param userEmail
     * @return
     */
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
