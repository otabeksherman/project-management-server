package projectManagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectManagement.entities.Status;
import projectManagement.entities.board.Board;
import projectManagement.service.StatusService;

@RestController
@RequestMapping("/status")
public class StatusController {
    @Autowired
    StatusService statusService;

     public StatusController(){};

    /*
    Status createStatus(Board board){
        //TODO
    }
    */

    void updateStatus(Status status){
        //TODO
    }

    void deleteStatus(Status status){
        //TODO
    }

}
