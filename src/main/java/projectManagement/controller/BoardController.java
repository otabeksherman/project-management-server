package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectManagement.dto.ShareBoardDto;
import projectManagement.dto.StatusDto;
import projectManagement.dto.AddTypeDto;
import projectManagement.dto.BaseResponse;
import projectManagement.service.BoardService;
import projectManagement.service.UserInBoardService;

import java.sql.SQLDataException;

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

    @PatchMapping("status/add")
    public ResponseEntity<BaseResponse> addStatus(@RequestBody StatusDto dto, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse("Status added successfully",
                boardService.addStatus(dto)));
    }

    @PatchMapping("type/add")
    public ResponseEntity<BaseResponse> addType(@RequestBody AddTypeDto dto, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse("Type added successfully",
                boardService.addType(dto)));
    }

    @PatchMapping("status/delete")
    public ResponseEntity<BaseResponse> deleteStatus(@RequestBody StatusDto dto, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse("Type added successfully",
                boardService.deleteStatus(dto)));
    }

    @GetMapping("all")
    public ResponseEntity<BaseResponse> getAllBoards(@RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse("Success",
                boardService.getAllBoards(userEmail)));
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponse> getBoard(@PathVariable Long id,
                                                 @RequestAttribute String userEmail) {

        try {
            return ResponseEntity.ok().body(new BaseResponse("Success",
                    boardService.getBoard(userEmail, id)));
        } catch (SQLDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new BaseResponse(String.format("Board with id %d not found", id), id));
        }
    }

    @GetMapping("{id}/users")
    public ResponseEntity<BaseResponse> getUsers(@PathVariable Long id, @RequestAttribute String userEmail) {
        return ResponseEntity.ok().body(new BaseResponse("Success",
                userInBoardService.findByBoard(id)));
    }

    @PostMapping("share")
    public ResponseEntity<BaseResponse> share(@RequestBody ShareBoardDto dto, @RequestAttribute String userEmail) {
        try {
            return ResponseEntity.ok().body(new BaseResponse("Success",
                    userInBoardService.share(dto)));
        } catch (SQLDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new BaseResponse(String.format("Board with id %d not found", dto.getUserEmail()), dto.getUserEmail()));
        }
    }
}
