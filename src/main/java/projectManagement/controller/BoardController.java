package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectManagement.dto.BaseResponse;
import projectManagement.service.BoardService;

import java.sql.SQLDataException;

@Controller
@CrossOrigin
@RequestMapping("api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

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
}
