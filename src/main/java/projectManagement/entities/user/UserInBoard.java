package projectManagement.entities.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class UserInBoard {
    private static final Logger logger = LogManager.getLogger(UserInBoard.class.getName());

    Map<Long, UserRole> userMap;
    Long boardId;
    Long creatorId;

    void changeRoles(Long creatorId, Long userId,UserRole role){
        if(creatorId== this.creatorId){
            userMap.put(userId,role);
        }
        else{
            logger.error("Invalid creator id");
        }
    }
}
