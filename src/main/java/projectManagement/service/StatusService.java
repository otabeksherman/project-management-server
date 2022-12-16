package projectManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectManagement.entities.Status;
import projectManagement.entities.board.Board;
import projectManagement.repository.ItemRepository;
import projectManagement.repository.StatusRepository;

public class StatusService {

    private final StatusRepository statusRepository;

    private static final Logger logger = LogManager.getLogger(StatusService.class.getName());

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    /*
    Status createStatus(Board board){
        //TODO
    }
    */

    void deleteStatus(Status status){
        //TODO
    }
    void renameStatus(String newName){
        //TODO
    }

}
