package it.polimi.telcodb2project2022.servlets;

import it.polimi.telcodb2project2022.entities.User;
import it.polimi.telcodb2project2022.exceptions.CredentialsException;
import it.polimi.telcodb2project2022.services.UserService;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "it.polimi.telcodb2project2022.services/UserService")
    private UserService usrService;

    public SignUp() {
        super();
    }

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usrn = null;
        String pwd = null;
        String email = null;
        try {
            usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
            pwd = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
            email = StringEscapeUtils.escapeJava(request.getParameter("email"));
            if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
                throw new Exception("Missing or empty credential value");
            }

        } catch (Exception e) {
            // for debugging only e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
            return;
        }

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        try {
            User user = usrService.insertUser(usrn, email, pwd,false);
            String path = getServletContext().getContextPath() + "/index.html";
            response.sendRedirect(path);
        }
        catch (PersistenceException | IllegalArgumentException | EJBException e) {
            if (e.getCause().getCause().getMessage().contains("Duplicate entry")) {
                ctx.setVariable("errorMsg", "Username already taken");
                String path = "/registration.html";
                templateEngine.process(path, ctx, response.getWriter());
            }
            else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "internal server error");
            }
        }

    }

    public void destroy() {
    }
}
