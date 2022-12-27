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
import projectManagement.dto.ItemDto;
import projectManagement.entities.Operation;
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

    private final PermissionService permissionService;
    private static Gson gson = new GsonBuilder().create();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("in doFilter():");

        if (((HttpServletRequest) request).getServletPath().contains("/registration")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (((HttpServletRequest) request).getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }


        MutableHttpServletRequest req = new MutableHttpServletRequest((HttpServletRequest) request);
        HttpServletResponse res = (HttpServletResponse) response;

        final String userEmail = req.getAttribute("userEmail").toString();

        //check permission to create item
        if (((HttpServletRequest) request).getServletPath().endsWith("/item/create")) {
            Operation operation = Operation.CREATE_ITEM;
            logger.info("user: " + userEmail + "  to: " + operation);

            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            try {
                JSONObject jsonObject = new JSONObject(body);
                ItemDto dto = gson.fromJson(String.valueOf(jsonObject), ItemDto.class);
                request.setAttribute("dto", dto);

                Long boardId = jsonObject.getLong("boardId");
                if (permissionService.isAuthorized(boardId, userEmail, operation)) {
                    filterChain.doFilter(request, response);
                } else {
                    res.sendError(401, "User does not have permission to create item");
                }
            } catch (JSONException e) {
                res.sendError(401, e.getMessage());
            }
        }
    }
}



