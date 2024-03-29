package it.polimi.telcodb2project2022.servlets;

import it.polimi.telcodb2project2022.entities.Employee;
import it.polimi.telcodb2project2022.entities.OptionalProduct;
import it.polimi.telcodb2project2022.services.EmployeeService;
import it.polimi.telcodb2project2022.services.OptionalProductService;
import it.polimi.telcodb2project2022.services.ServService;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CreateOptional")
public class CreateOptionalProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.telcodb2project2022.services/OptionalProductService")
    private OptionalProductService optionalService;

    @EJB(name = "it.polimi.telcodb2project2022.services/EmployeeService")
    private EmployeeService eService;

    public CreateOptionalProduct() {
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee e = null;
        String path = "/createOptionalProduct.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String username = (String) request.getSession().getAttribute("employeeId");
        if(username != null)
            e = eService.findById(username);
        ctx.setVariable("employee", e);

        templateEngine.process(path, ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = StringEscapeUtils.escapeJava(request.getParameter("optionalProductName"));
        float monthlyFee = Float.parseFloat(StringEscapeUtils.escapeJava(request.getParameter("optionalProductFee")));
        Employee emp = null;
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String username = (String) request.getSession().getAttribute("employeeId");
        if(username != null)
            emp = eService.findById(username);

        ctx.setVariable("employee", emp);

        try {
            OptionalProduct optionalProduct = optionalService.insertOptionalProduct(name, monthlyFee, emp);
            String path = getServletContext().getContextPath() + "/GoToEmployeeHomePage";
            response.sendRedirect(path);
        } catch (PersistenceException | IllegalArgumentException | EJBException e) {
            if(e.getCause().getCause().getMessage().contains("Duplicate entry")) {
                ctx.setVariable("errorMsg", "Optional product name already taken");
                String path = "/createOptionalProduct.html";
                templateEngine.process(path, ctx, response.getWriter());
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Internal server error");
            }
        }
    }
}