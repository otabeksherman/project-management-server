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
import projectManagement.service.ItemService;
import projectManagement.service.PermissionService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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
        String path=((HttpServletRequest) request).getServletPath();
        if ((path.contains("/item"))|| path.contains("/board")) {

            MutableHttpServletRequest req = new MutableHttpServletRequest((HttpServletRequest) request);
            HttpServletResponse res = (HttpServletResponse) response;


            final String userEmail = req.getAttribute("userEmail").toString();
            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Long boardId =null;

            if(body.equals("")){//pass get request
                if(path.contains("get")){
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            try {
                JSONObject jsonObject = new JSONObject(body);
                ItemDto dto = gson.fromJson(String.valueOf(jsonObject), ItemDto.class);
                request.setAttribute("dto", dto);

                boardId = jsonObject.getLong("boardId");
                if (permissionService.isAuthorized(boardId, userEmail, path)) {
                    filterChain.doFilter(request, response);
                } else {
                    res.sendError(401, "User does not have permission to create item");
                }

            } catch (JSONException e) {
                res.sendError(401, e.getMessage());
            }
        }
        else{
            filterChain.doFilter(request, response);
            return;
        }

    }
}



