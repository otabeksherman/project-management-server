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
import projectManagement.entities.board.Board;
import projectManagement.entities.user.UserInBoard;
import projectManagement.exception.UserNotFoundException;
import projectManagement.service.BoardService;
import projectManagement.service.UserInBoardService;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@CrossOrigin
@RequestMapping("api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserInBoardService userInBoardService;

    @PostMapping("create")
    public ResponseEntity<BaseResponse<Board>> create(@RequestBody String name, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse<>("Board created successfully",
                boardService.create(name, userEmail)));
    }

    /**
     * get status dto and add to board
     * @param dto       (StatusDto)
     * @param userEmail
     * @return Board
     */
    @PatchMapping("status/add")
    public ResponseEntity<BaseResponse<Board>> addStatus(@RequestAttribute StatusDto dto, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse<>("Status added successfully",
                boardService.addStatus(dto)));
    }

    /**
     * add new type to bard
     *
     * @param dto
     * @param userEmail
     * @return Board
     */
    @PatchMapping("type/add")
    public ResponseEntity<BaseResponse<Board>> addType(@RequestAttribute AddTypeDto dto, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse<>("Type added successfully",
                boardService.addType(dto)));
    }

    /**
     * get status and delete it from board
     *
     * @param dto
     * @param userEmail
     * @return Board
     */
    @PatchMapping("status/delete")
    public ResponseEntity<BaseResponse<Board>> deleteStatus(@RequestAttribute StatusDto dto, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse<>("Type added successfully",
                boardService.deleteStatus(dto)));
    }

    /**
     * get user email and return list of all his boards
     *
     * @param userEmail
     * @return List<Board>
     */
    @GetMapping("get/all")
    public ResponseEntity<BaseResponse<List<Board>>> getAllBoards(@RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse<>("Success",
                boardService.getAllBoards(userEmail)));
    }

    /**
     * get userEmail to authentication , and board id- and return the board
     * @param id
     * @param userEmail
     * @return Board
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<BaseResponse<Board>> getBoard(@PathVariable Long id,
                                                 @RequestAttribute String userEmail) {
        try {
            return ResponseEntity.ok().body(new BaseResponse("Success",
                    boardService.getBoard(id)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new BaseResponse<>(String.format("Board with id %d not found", id), null));
        }
    }

    /**
     * get board id and return all the user's board
     * @param id
     * @param userEmail
     * @return List<String>
     */
    @GetMapping("{id}/get/users")
    public ResponseEntity<BaseResponse<List<String>>> getUsers(@PathVariable Long id, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse<>("Success",
                userInBoardService.findByBoard(id)));
    }

    /**
     *  get board user and role and return userInBoard entity.
     *  share the board with other user.
     * @param dto
     * @param userEmail
     * @return UserInBoard
     */
    @PostMapping("share")
    public ResponseEntity<BaseResponse<UserInBoard>> share(@RequestAttribute ShareBoardDto dto, @RequestAttribute String userEmail) {
        try {
            return ResponseEntity.ok().body(new BaseResponse("Success",
                    userInBoardService.share(dto)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new BaseResponse<>(String.format("user with id %d not found", dto.getUserEmail()),null));
        }
    }
}
