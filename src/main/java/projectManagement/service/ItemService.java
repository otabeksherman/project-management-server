package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.dto.*;
import projectManagement.entities.Status;
import projectManagement.entities.board.Board;
import projectManagement.entities.item.Comment;
import projectManagement.entities.item.Item;
import projectManagement.entities.item.ItemType;
import projectManagement.entities.user.User;
import projectManagement.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Item updateType(UpdateItemTypeDto dto) throws IllegalArgumentException {
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        if (!item.getBoard().getTypes().stream().map(ItemType::getName).collect(Collectors.toList()).contains(dto.getType())) {
            throw new IllegalArgumentException("illegal item type");
        }
        ItemType type = item.getBoard().getTypes().stream().filter(t -> t.getName().equals(dto.getType())).findFirst().orElse(null);
        item.setType(type);
        return itemRepository.save(item);
    }

    public Item updateStatus(UpdateItemStatusDto dto) throws IllegalArgumentException {
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        if (!item.getBoard().getStatuses().stream().map(Status::getName).collect(Collectors.toList()).contains(dto.getStatus())) {
            throw new IllegalArgumentException("illegal item status");
        }
        Status status = item.getBoard().getStatuses().stream().filter(t -> t.getName().equals(dto.getStatus())).findFirst().orElse(null);
        item.setStatus(status);
        return itemRepository.save(item);
    }

    public Item update(UpdateItemDto dto) throws IllegalArgumentException {
        Item item = itemRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("item does not exist"));

        Status status = dto.getStatus() != null ?
                item.getBoard().getStatuses().stream()
                        .filter(s -> s.getName().equals(dto.getStatus()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("illegal item status")) : item.getStatus();
        ItemType type = dto.getType() != null ?
                item.getBoard().getTypes().stream()
                        .filter(t -> t.getName().equals(dto.getType()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("illegal item type")) : item.getType();
        User assignedTo = dto.getAssignedToId() != null ?
                userRepository.findById(dto.getAssignedToId())
                        .orElseThrow(() -> new IllegalArgumentException("invalid assignedTo id")) : item.getAssignedTo();

        item.setAssignedTo(assignedTo);
        item.setStatus(status);
        item.setType(type);
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

}
