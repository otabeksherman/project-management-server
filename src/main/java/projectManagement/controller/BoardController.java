package projectManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectManagement.dto.BaseResponse;
import projectManagement.service.BoardService;
import projectManagement.util.JwtUtils;

import java.sql.SQLDataException;

@Controller
@CrossOrigin
@RequestMapping("api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final JwtUtils jwtUtils;

    @GetMapping("all")
    public ResponseEntity<BaseResponse> getAllBoards(@RequestHeader("Authorization") String token) {
        String tokenVal = token.substring(7);
        String email = jwtUtils.extractUsername(tokenVal);
        return  ResponseEntity.ok().body(new BaseResponse("Success",
               boardService.getAllBoards(email)));
    }
    @GetMapping("{id}")
    public ResponseEntity<BaseResponse> getBoard(@PathVariable Long id,
                                                 @RequestHeader("Authorization") String token) {
        String tokenVal = token.substring(7);
        String email = jwtUtils.extractUsername(tokenVal);
        try {
            return  ResponseEntity.ok().body(new BaseResponse("Success",
                   boardService.getBoard(email, id)));
        } catch (SQLDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new BaseResponse(String.format("Board with id %d not found", id), id));
        }
    }
}
