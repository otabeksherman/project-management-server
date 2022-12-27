//package projectManagement.filter;
//
//import lombok.RequiredArgsConstructor;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.stereotype.Component;
//import projectManagement.entities.Operation;
//import projectManagement.service.PermissionService;
//import projectManagement.service.UserService;
//import projectManagement.util.JwtUtils;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class RolesFilter2 implements Filter {
//    public static final Logger logger = LogManager.getLogger(RolesFilter2.class);
//    private final JwtUtils jwtUtils;
//    private final UserService userService;
//    private final PermissionService permissionService;
//
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        logger.info("in doFilter():");
//
//        MutableHttpServletRequest req = new MutableHttpServletRequest((HttpServletRequest) servletRequest);
//        HttpServletResponse res = (HttpServletResponse) servletResponse;
//
//        final String userEmail=req.getAttribute("userEmail").toString();
//
//
//        //check permission to create item
//        if (((HttpServletRequest) servletRequest).getServletPath().endsWith("/item/create")) {
//            Operation operation=Operation.CREATE_ITEM;
//            logger.info("user: "+userEmail+"  to: "+operation);
//
//            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//            try {
//                JSONObject jsonObject = new JSONObject(body);
//                Long boardId=jsonObject.getLong("boardId");
//                if(permissionService.isAuthorized(boardId,userEmail,operation)){
//                    filterChain.doFilter(servletRequest, servletResponse);
//                } else {
//                    res.sendError(401,"User does not have permission to create item");
//                }
//            } catch (JSONException e) {
//                res.sendError(401, e.getMessage());
//            }
//        }
//    }
//
//}
//
//
//
