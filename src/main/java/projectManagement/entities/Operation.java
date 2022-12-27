package projectManagement.entities;

import projectManagement.entities.user.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Operation {
    //Board operations:
    SHARE_BOARD(Arrays.asList(UserRole.ADMIN)),
    CREATE_BOARD(Arrays.asList(UserRole.ADMIN, UserRole.LEADER, UserRole.USER)),
    ADD_STATUS(Arrays.asList(UserRole.ADMIN)),
    REMOVE_STATUS(Arrays.asList(UserRole.ADMIN)),


    //Item operations:
    CREATE_ITEM(Arrays.asList(UserRole.ADMIN)),
    DELETE_ITEM(Arrays.asList(UserRole.ADMIN)),
    UPDATE_ITEM_TYPE(Arrays.asList(UserRole.ADMIN, UserRole.LEADER)),
    UPDATE_ITEM_STATUS(Arrays.asList(UserRole.ADMIN, UserRole.LEADER, UserRole.USER)),
    UPDATE_ITEM_TITLE(Arrays.asList(UserRole.ADMIN)),
    UPDATE_ITEM_DESCRIPTION(Arrays.asList(UserRole.ADMIN)),
    UPDATE_DUE_DATE_ITEMS(Arrays.asList(UserRole.ADMIN, UserRole.LEADER)),
    UPDATE_IMPORTANCE_ITEMS(Arrays.asList( UserRole.LEADER)),
    ASSIGN_ITEM(Arrays.asList(UserRole.ADMIN)),
    ADD_ITEM_TYPE(Arrays.asList(UserRole.ADMIN)),
    REMOVE_ITEM_TYPE(Arrays.asList(UserRole.ADMIN)),
    ADD_COMMENT(Arrays.asList(UserRole.USER));

    private List<UserRole> userRole;

    Operation(List<UserRole> userRoleList) {
        this.userRole = userRoleList;
    }

    public List<UserRole> getUserRole(){
        return userRole;
    }
}
