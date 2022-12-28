package projectManagment.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagement.entities.Operation;
import projectManagement.entities.user.UserInBoard;
import projectManagement.entities.user.UserRole;
import projectManagement.repository.BoardRepository;
import projectManagement.repository.UserInBoardRepository;
import projectManagement.repository.UserRepository;
import projectManagement.service.PermissionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceTest {
    @Mock
    private UserInBoardRepository userInBoardRepository;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserRepository userRepository;

    private PermissionService permissionService;

    @BeforeEach
    public void setUp() {
        permissionService = new PermissionService(userRepository, boardRepository, userInBoardRepository);
    }

    @Test
    void testIsAuthorized() {
        // Given
        long boardId = 1L;
        String userEmail = "user@example.com";
        Operation operation = Operation.CREATE_BOARD;
        UserInBoard boardUser = new UserInBoard();
        boardUser.setRole(UserRole.ADMIN);
        when(userInBoardRepository.findByBoardAndUser(boardId, userEmail)).thenReturn(boardUser);

        // When
        boolean result = permissionService.isAuthorized(boardId, userEmail, operation);

        // Then
        assertTrue(result);
    }


}
