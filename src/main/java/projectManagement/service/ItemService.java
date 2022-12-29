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
import projectManagement.repository.BoardRepository;
import projectManagement.repository.CommentRepository;
import projectManagement.repository.ItemRepository;
import projectManagement.repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private static Logger logger = LogManager.getLogger(ItemService.class);

    /**
     * This method creates a new item with the information provided in the given DTO.
     *
     * @param dto       An object containing the details of the item to be created
     * @param userEmail The email of the user who is creating the item
     * @return The newly created item
     * @throws IllegalArgumentException If the board, parent item (if specified), or assigned to user (if specified) do not exist, or if the type or status are not valid for the board
     */
    public Item create(ItemDto dto, String userEmail) throws IllegalArgumentException {
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("board does not exist"));
        User creator = userRepository.findUserByEmail(userEmail);
        Item parentItem = dto.getParentItemId() != null ?
                itemRepository.findById(dto.getParentItemId())
                        .orElseThrow(() -> new IllegalArgumentException("parent item does not exist")) : null;
        User assignedTo = dto.getAssignedToEmail() != null ?
                userRepository.findUserByEmail(dto.getAssignedToEmail()) : null;
        if (dto.getAssignedToEmail() != null && assignedTo == null) {
            throw new IllegalArgumentException("assign to user does not exist");
        }
        if (!board.getTypes().contains(dto.getType())) {
            throw new IllegalArgumentException("illegal item type");
        }
        if (!board.getStatuses().contains(dto.getStatus())) {
            throw new IllegalArgumentException("illegal item status");
        }
        Item item=new Item.Builder()
                .type(dto.getType()).status(dto.getStatus()).parent(parentItem).board(board).creator(creator)
                .assignedTo(assignedTo).dueDate(dto.getDueDate()).importance(dto.getImportance())
                .title(dto.getTitle()).description(dto.getDescription()).build();

        Item savedItem = itemRepository.save(item);
        if (!dto.getSubItems().isEmpty()) {
            addSubItems(savedItem, dto.getSubItems());
        }
        return savedItem;
    }

    /**
     * This method updates the type of the item with the given ID to the new type provided in the DTO.
     *
     * @param dto An object containing the ID of the item to be updated and the new type for the item
     * @return The updated item
     * @throws IllegalArgumentException If the item does not exist or if the new type is not valid for the board the item belongs to
     */
    public Item updateType(UpdateItemTypeDto dto) throws IllegalArgumentException {
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        if (!item.getBoard().getTypes().contains(dto.getType())) {
            throw new IllegalArgumentException("illegal item type");
        }
        item.setType(dto.getType());
        return itemRepository.save(item);
    }

    /**
     * This method updates the status of the item with the given ID to the new status provided in the DTO.
     *
     * @param dto An object containing the ID of the item to be updated and the new status for the item
     * @return The updated item
     * @throws IllegalArgumentException If the item does not exist or if the new status is not valid for the board the item belongs to
     */
    public Item updateStatus(UpdateItemStatusDto dto) throws IllegalArgumentException {
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        if (!item.getBoard().getStatuses().contains(dto.getStatus())) {
            throw new IllegalArgumentException("illegal item status");
        }
        item.setStatus(dto.getStatus());
        return itemRepository.save(item);
    }

    /**
     * This method updates the item with the given ID with the information provided in the DTO.
     *
     * @param dto An object containing the details of the item to be updated
     * @return The updated item
     * @throws IllegalArgumentException If the item does not exist, the assigned to user (if specified) does not exist, or if the type or status are not valid for the board the item belongs to
     */
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
        if (!dto.getSubItems().isEmpty()) {
            addSubItems(item, dto.getSubItems());
        }
        item.setAssignedTo(assignedTo);
        item.setStatus(dto.getStatus() != null ? dto.getStatus() : item.getStatus());
        item.setType(dto.getType() != null ? dto.getType() : item.getType());
        item.setDescription(dto.getDescription());
        item.setTitle(dto.getTitle());
        item.setImportance(dto.getImportance());
        item.setDueDate(dto.getDueDate());
        return itemRepository.save(item);
    }

    /**
     * This function adds a list of sub-items to the specified item.
     *
     * @param i          the item to which the sub-items will be added
     * @param subItemsId the list of ids of the sub-items to be added
     * @throws IllegalArgumentException if the item does not exist or if the sub-item is the same as the parent item
     */
    private void addSubItems(Item i, List<Long> subItemsId) {
        for (Long id : subItemsId) {
            if (i.getId() == id)
                throw new IllegalArgumentException("sub item should be different from the parent item");
            Item item = itemRepository.findById(i.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Item does not exist"));
            Item subItem = itemRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("subItem does not exist"));
            subItem.setParent(item);
            itemRepository.save(subItem);
        }
    }

    /**
     * This function adds a new comment to a given item.
     *
     * @param dto       An object containing the id of the item to add the comment to and the content of the comment.
     * @param userEmail The email of the user adding the comment.
     * @return The updated item with the new comment added.
     * @throws IllegalArgumentException If the item does not exist.
     */
    public Item addComment(AddCommentDto dto, String userEmail) {
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        User user = userRepository.findUserByEmail(userEmail);
        Comment comment = commentRepository.save(new Comment(user, dto.getComment()));
        item.addComment(comment);
        return itemRepository.save(item);
    }

    /**
     * Deletes an item with the specified id.
     *
     * @param dto an object containing the id of the item to delete
     * @throws IllegalArgumentException if the item with the specified id does not exist
     */
    public void delete(DeleteItemDto dto) {
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        itemRepository.delete(item);
    }

    /**
     * Retrieves the subitems of the item with the specified id.
     *
     * @param itemId the id of the item to get the subitems for
     * @return a list of subitems for the item with the specified id
     * @throws IllegalArgumentException if the item with the specified id does not exist
     */
    public List<Item> getSubItems(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("item does not exist"));
        return itemRepository.findAllSubItems(item);
    }

    /**
     * send itemId and return its board id
     *
     * @param itemId
     * @return board id
     */
    public Long getItemBoardId(Long itemId) {
        Item item = itemRepository.getReferenceById(itemId);
        return item.getBoard().getId();
    }
}
