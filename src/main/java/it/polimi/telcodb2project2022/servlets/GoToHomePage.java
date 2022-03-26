package it.polimi.telcodb2project2022.servlets;

import it.polimi.telcodb2project2022.entities.OptionalProduct;
import it.polimi.telcodb2project2022.entities.Service;
import it.polimi.telcodb2project2022.entities.ServicePackage;
import it.polimi.telcodb2project2022.entities.User;
import it.polimi.telcodb2project2022.exceptions.CredentialsException;
import it.polimi.telcodb2project2022.services.OptionalProductService;
import it.polimi.telcodb2project2022.services.ServService;
import it.polimi.telcodb2project2022.services.ServicePackageService;
import it.polimi.telcodb2project2022.services.UserService;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.IconUIResource;
import java.io.IOException;
import java.util.List;

@WebServlet("/GoToHomePage")
public class GoToHomePage extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.telcodb2project2022.services/UserService")
    private UserService uService;

    @EJB(name = "it.polimi.telcodb2project2022.services/ServicePackageService")
    private ServicePackageService spService;

    @EJB(name = "it.polimi.telcodb2project2022.services/ServService")
    private ServService servService;

    @EJB(name = "it.polimi.telcodb2project2022.services/OptionalProductServices")
    private OptionalProductService optionalProductService;


    public GoToHomePage() {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User u = null;
        List<ServicePackage> packages  = null;
        List<Service> services = null;
        ServicePackage servicePackage = null;
        List<OptionalProduct> optionalProducts = null;

        Integer chosen = null;
        if (request.getParameterMap().containsKey("packageId") && request.getParameter("packageId") != ""
                && !request.getParameter("packageId").isEmpty()) {
            try {
                chosen = Integer.parseInt(request.getParameter("packageId"));
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid package parameters");
                return;
            }
        }

        if (chosen != null)
            servicePackage = spService.findById(chosen);

        String path = "/userHomepage.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String username = (String) request.getSession().getAttribute("userId");
        if(username != null)
            u = uService.findById(username);
        if(servicePackage != null) {
            ctx.setVariable("packageSelected", servicePackage);
            services = servService.findByPackageId(servicePackage.getId());
            ctx.setVariable("services", services);
            optionalProducts = optionalProductService.findByPackageId(servicePackage.getId());
            ctx.setVariable("optionalProducts" ,optionalProducts);
        }
        ctx.setVariable("user", u);
        packages = spService.getAllServicePackages();
        ctx.setVariable("packages", packages);
        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }


}

