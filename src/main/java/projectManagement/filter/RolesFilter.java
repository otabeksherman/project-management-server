package projectManagement.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import projectManagement.dto.*;
import projectManagement.entities.Operation;
import projectManagement.service.ItemService;
import projectManagement.service.PermissionService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RolesFilter extends OncePerRequestFilter {
    public static final Logger logger = LogManager.getLogger(RolesFilter.class);
    private final ItemService itemService;

    private final PermissionService permissionService;
    private static Gson gson = new GsonBuilder().create();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("in doFilter():");
        String path = ((HttpServletRequest) request).getServletPath();
        if ((path.contains("/item")) || path.contains("/board")) {

            MutableHttpServletRequest req = new MutableHttpServletRequest((HttpServletRequest) request);
            HttpServletResponse res = (HttpServletResponse) response;

            if (path.contains("board/create")) {//create is allow to all users
                filterChain.doFilter(request, response);
                return;
            }

            final String userEmail = req.getAttribute("userEmail").toString();
            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Long boardId = null;

            if (body.equals("")) {//pass get request
                if (path.contains("get")) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }


            try {
                JSONObject jsonObject = new JSONObject(body);

                Operation operation = getOperationFromPath(path, jsonObject, request);

                boardId = jsonObject.getLong("boardId");
                if (permissionService.isAuthorized(boardId, userEmail, operation)) {
                    filterChain.doFilter(request, response);
                } else {
                    res.sendError(401, "User does not have permission to create item");
                }

            } catch (JSONException e) {
                res.sendError(401, e.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }

    }

    /**
     * get path and return the appropriate operation
     * save the body on request attribute
     *
     * @param path
     * @return Operation
     */
    private Operation getOperationFromPath(String path, JSONObject jsonObject, HttpServletRequest request) {
        Operation operation = null;

        //Item operations:
        if (path.endsWith("/item/create")) {
            operation = Operation.CREATE_ITEM;
            ItemDto dto = gson.fromJson(String.valueOf(jsonObject), ItemDto.class);
            request.setAttribute("dto", dto);
            return operation;
        }
        if (path.endsWith("/item/delete")) {
            operation = Operation.DELETE_ITEM;
            DeleteItemDto dto = gson.fromJson(String.valueOf(jsonObject), DeleteItemDto.class);
            request.setAttribute("dto", dto);
            return operation;
        }
        if (path.endsWith("/item/type/update")) {
            operation = Operation.UPDATE_ITEM;
            UpdateItemTypeDto dto = gson.fromJson(String.valueOf(jsonObject), UpdateItemTypeDto.class);
            request.setAttribute("dto", dto);
            return operation;
        }
        if (path.endsWith("/item/update")) {
            operation = Operation.UPDATE_ITEM_TYPE;
            UpdateItemDto dto = gson.fromJson(String.valueOf(jsonObject), UpdateItemDto.class);
            request.setAttribute("dto", dto);
            return operation;
        }
        if (path.endsWith("/item/status/update")) {
            operation = Operation.UPDATE_ITEM_STATUS;
            UpdateItemStatusDto dto = gson.fromJson(String.valueOf(jsonObject), UpdateItemStatusDto.class);
            request.setAttribute("dto", dto);
            return operation;
        }
        if (path.endsWith("/item/comment/add")) {
            operation = Operation.ADD_COMMENT;
            AddCommentDto dto = gson.fromJson(String.valueOf(jsonObject), AddCommentDto.class);
            request.setAttribute("dto", dto);
            return operation;
        }

        //Board operations:
        if (path.endsWith("/board/status/add")) {
            operation = Operation.ADD_STATUS;
            StatusDto dto = gson.fromJson(String.valueOf(jsonObject), StatusDto.class);
            request.setAttribute("dto", dto);
            return operation;
        }
        if (path.endsWith("/board/type/add")) {
            operation = Operation.ADD_BOARD_TYPE;
            AddTypeDto dto = gson.fromJson(String.valueOf(jsonObject), AddTypeDto.class);
            request.setAttribute("dto", dto);
            return operation;
        }
        if (path.endsWith("/board/status/delete")) {
            operation = Operation.REMOVE_STATUS;
            StatusDto dto = gson.fromJson(String.valueOf(jsonObject), StatusDto.class);
            request.setAttribute("dto", dto);
            return operation;
        }
        if (path.endsWith("/board/share")) {
            operation = Operation.SHARE_BOARD;
            ShareBoardDto dto = gson.fromJson(String.valueOf(jsonObject), ShareBoardDto.class);
            request.setAttribute("dto", dto);
            return operation;
        }

        return operation;
    }

}



