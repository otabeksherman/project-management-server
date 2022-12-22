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
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("board does not exist"));

        User creator = userRepository.findUserByEmail(userEmail);
        Item parentItem = dto.getParentItemId() != null ?
                itemRepository.findById(dto.getParentItemId())
                        .orElseThrow(() -> new IllegalArgumentException("parent item does not exist")) : null;
        User assignedTo = dto.getAssignedToId() != null ?
                userRepository.findById(dto.getAssignedToId())
                        .orElseThrow(() -> new IllegalArgumentException("invalid assignedTo id")) : null;

        if (!board.getTypes().stream().map(ItemType::getName).collect(Collectors.toList()).contains(dto.getType())) {
            throw new IllegalArgumentException("illegal item type");
        }
        ItemType type = board.getTypes().stream().filter(t -> t.getName().equals(dto.getType())).findFirst().orElse(null);

        if (!board.getStatuses().stream().map(Status::getName).collect(Collectors.toList()).contains(dto.getStatus())) {
            throw new IllegalArgumentException("illegal item status");
        }
        Status status = board.getStatuses().stream().filter(s -> s.getName().equals(dto.getStatus())).findFirst().orElse(null);

        Item item = new Item(type, status, parentItem, board, creator, assignedTo, dto.getDueDate(), dto.getImportance(), dto.getTitle(), dto.getDescription());
        return itemRepository.save(item);
    }

}
