package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectManagement.dto.AddTypeDto;
import projectManagement.dto.BaseResponse;
import projectManagement.dto.ShareBoardDto;
import projectManagement.dto.StatusDto;
import projectManagement.exception.BoardNotFoundException;
import projectManagement.exception.UserNotFoundException;
import projectManagement.service.BoardService;
import projectManagement.service.UserInBoardService;

@Controller
@CrossOrigin
@RequestMapping("api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserInBoardService userInBoardService;

    @PostMapping("create")
    public ResponseEntity<BaseResponse> create(@RequestBody String name, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse("Board created successfully",
                boardService.create(name, userEmail)));
    }

    /**
     * get status dto and add to board
     *
     * @param dto       (StatusDto)
     * @param userEmail
     * @return
     */
    @PatchMapping("status/add")
    public <T> ResponseEntity<BaseResponse> addStatus(@RequestAttribute StatusDto dto, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse("Status added successfully",
                boardService.addStatus(dto)));
    }

    /**
     * add new type to bard
     *
     * @param dto
     * @param userEmail
     * @return the type
     */
    @PatchMapping("type/add")
    public ResponseEntity<BaseResponse> addType(@RequestAttribute AddTypeDto dto, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse("Type added successfully",
                boardService.addType(dto)));
    }

    /**
     * get status and delete it from board
     *
     * @param dto
     * @param userEmail
     * @return
     */
    @PatchMapping("status/delete")
    public ResponseEntity<BaseResponse> deleteStatus(@RequestAttribute StatusDto dto, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse("Type added successfully",
                boardService.deleteStatus(dto)));
    }

    @GetMapping("get/all")
    public ResponseEntity<BaseResponse> getAllBoards(@RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse("Success",
                boardService.getAllBoards(userEmail)));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BaseResponse> getBoard(@PathVariable Long id,
                                                 @RequestAttribute String userEmail) {
        try {
            return ResponseEntity.ok().body(new BaseResponse("Success",
                    boardService.getBoard(userEmail, id)));
        } catch (BoardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new BaseResponse(String.format("Board with id %d not found", id), id));
        }
    }

    @GetMapping("{id}/get/users")
    public ResponseEntity<BaseResponse> getUsers(@PathVariable Long id, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse("Success",
                userInBoardService.findByBoard(id)));
    }

    @PostMapping("share")
    public ResponseEntity<BaseResponse> share(@RequestAttribute ShareBoardDto dto, @RequestAttribute String userEmail) {
        try {
            return ResponseEntity.ok().body(new BaseResponse("Success",
                    userInBoardService.share(dto)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new BaseResponse(String.format("user with id %d not found", dto.getUserEmail()), dto.getUserEmail()));
        }
    }
}
