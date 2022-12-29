package projectManagment.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagement.dto.ItemDto;
import projectManagement.entities.board.Board;
import projectManagement.entities.item.Item;
import projectManagement.entities.user.User;
import projectManagement.repository.BoardRepository;
import projectManagement.repository.CommentRepository;
import projectManagement.repository.ItemRepository;
import projectManagement.repository.UserRepository;
import projectManagement.service.ItemService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    public void setUp() {
        itemService = new ItemService(itemRepository, boardRepository, userRepository, commentRepository);

        // Create mock instances of ItemDto and user email
        Object itemDto = Mockito.mock(ItemDto.class);
        String userEmail = "test@example.com";

        // Specify the behavior of the mock dependencies
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(Mockito.mock(Board.class)));
        when(userRepository.findUserByEmail(anyString())).thenReturn(Mockito.mock(User.class));
    }

    @Test
    void testCreate_whenBoardDoesNotExist_shouldThrowIllegalArgumentException() {
        // Set up mock behavior
        when(boardRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Create a test DTO
        ItemDto dto = new ItemDto();
        dto.setBoardId(1L);

        // Verify that the expected exception is thrown
        assertThrows(IllegalArgumentException.class, () -> itemService.create(dto, "user@example.com"));
    }


}

