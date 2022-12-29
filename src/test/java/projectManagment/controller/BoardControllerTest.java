package projectManagment.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagement.controller.BoardController;
import projectManagement.service.BoardService;
import projectManagement.service.UserInBoardService;

@ExtendWith(MockitoExtension.class)
public class BoardControllerTest {
    @Mock
    private BoardService boardService;

    @Mock
    private UserInBoardService userInBoardService;

    @InjectMocks
    private BoardController boardController;

}
