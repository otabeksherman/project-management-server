package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.dto.*;
import projectManagement.entities.board.Board;
import projectManagement.entities.item.Comment;
import projectManagement.entities.item.Item;
import projectManagement.entities.user.User;
import projectManagement.repository.*;


@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private static Logger logger = LogManager.getLogger(ItemService.class);

    public Item create(ItemDto dto, String userEmail) throws IllegalArgumentException {
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("board does not exist"));
        User creator = userRepository.findUserByEmail(userEmail);
        Item parentItem = dto.getParentItemId() != null ?
                itemRepository.findById(dto.getParentItemId())
                        .orElseThrow(() -> new IllegalArgumentException("parent item does not exist")) : null;
        User assignedTo = dto.getAssignedToId() != null ?
                userRepository.findById(dto.getAssignedToId())
                        .orElseThrow(() -> new IllegalArgumentException("invalid assignedTo id")) : null;
        if (!board.getTypes().contains(dto.getType())) {
            throw new IllegalArgumentException("illegal item type");
        }
        if (!board.getStatuses().contains(dto.getStatus())) {
            throw new IllegalArgumentException("illegal item status");
        }
        Item item = new Item(dto.getType(), dto.getStatus(), parentItem, board, creator, assignedTo, dto.getDueDate(), dto.getImportance(), dto.getTitle(), dto.getDescription());
        return itemRepository.save(item);
    }

    public Item updateType(UpdateItemTypeDto dto) throws IllegalArgumentException {
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        if (!item.getBoard().getTypes().contains(dto.getType())) {
            throw new IllegalArgumentException("illegal item type");
        }
        item.setType(dto.getType());
        return itemRepository.save(item);
    }

    public Item updateStatus(UpdateItemStatusDto dto) throws IllegalArgumentException {
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        if (!item.getBoard().getStatuses().contains(dto.getStatus())) {
            throw new IllegalArgumentException("illegal item status");
        }
        item.setStatus(dto.getStatus());
        return itemRepository.save(item);
    }

    public Item update(UpdateItemDto dto) throws IllegalArgumentException {
        Item item = itemRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        if (!item.getBoard().getStatuses().contains(dto.getStatus())) {
            throw new IllegalArgumentException("illegal item status");
        }
        if (!item.getBoard().getTypes().contains(dto.getType())) {
            throw new IllegalArgumentException("illegal item type");
        }
        User assignedTo = dto.getAssignedToId() != null ?
                userRepository.findById(dto.getAssignedToId())
                        .orElseThrow(() -> new IllegalArgumentException("invalid assignedTo id")) : item.getAssignedTo();

        item.setAssignedTo(assignedTo);
        item.setStatus(dto.getStatus() != null ? dto.getStatus() : item.getStatus());
        item.setType(dto.getType() != null ? dto.getType() : item.getType());
        item.setDescription(dto.getDescription());
        item.setTitle(dto.getTitle());
        item.setImportance(dto.getImportance());
        item.setDueDate(dto.getDueDate());
        return itemRepository.save(item);
    }

    public Item addComment(AddCommentDto dto, String userEmail) {
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        User user = userRepository.findUserByEmail(userEmail);
        Comment comment = commentRepository.save(new Comment(user, dto.getComment()));
        item.addComment(comment);
        return itemRepository.save(item);
    }

    public void delete(DeleteItemDto dto) {
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        itemRepository.delete(item);
    }
}
