package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.dto.ItemDto;
import projectManagement.entities.Status;
import projectManagement.entities.board.Board;
import projectManagement.entities.item.Item;
import projectManagement.entities.item.ItemType;
import projectManagement.entities.user.User;
import projectManagement.repository.BoardRepository;
import projectManagement.repository.ItemRepository;
import projectManagement.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private static Logger logger = LogManager.getLogger(ItemService.class);

    public Item create(ItemDto dto, String userEmail) throws IllegalArgumentException {
        Optional<Board> board = boardRepository.findById(dto.getBoardId());
        Optional<Item> parentItem = null;
        Optional<User> assignedTo = null;
        if (dto.getParentItemId() != null) {
            parentItem = itemRepository.findById(dto.getParentItemId());
        }
        User creator = userRepository.findUserByEmail(userEmail);
        if (dto.getAssignedToId() != null) {
            assignedTo = userRepository.findById(dto.getAssignedToId());
        }
        if (!board.isPresent()) {
            throw new IllegalArgumentException("board does not exist");
        }
        List<String> itemTypesNames = board.get().getTypes().stream().map(type -> type.getName()).collect(Collectors.toList());
        if (!itemTypesNames.contains(dto.getType())) {
            throw new IllegalArgumentException("illegal item type");
        }
        List<String> statusesNames = board.get().getStatuses().stream().map(status -> status.getName()).collect(Collectors.toList());
        if (!statusesNames.contains(dto.getStatus())) {
            throw new IllegalArgumentException("illegal item status");
        }

        if (parentItem != null && !parentItem.isPresent()) {
            throw new IllegalArgumentException("parent item does not exist");
        }

        if (assignedTo != null && !assignedTo.isPresent()) {
            throw new IllegalArgumentException("invalid assignedTo id");
        }
        ItemType type = board.get().getTypes().stream().filter(t -> t.getName().equals(dto.getType())).findFirst().orElse(null);
        Status status = board.get().getStatuses().stream().filter(s -> s.getName().equals(dto.getStatus())).findFirst().orElse(null);
        Item item = new Item(type, status, parentItem != null ? parentItem.get() : null, board.get(), creator, assignedTo != null ? assignedTo.get() : null, dto.getDueDate(), dto.getImportance(), dto.getTitle(), dto.getDescription());
        return itemRepository.save(item);
    }
}
