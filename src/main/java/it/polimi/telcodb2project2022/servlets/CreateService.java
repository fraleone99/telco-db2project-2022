package it.polimi.telcodb2project2022.servlets;

import it.polimi.telcodb2project2022.entities.Employee;
import it.polimi.telcodb2project2022.entities.Service;
import it.polimi.telcodb2project2022.services.EmployeeService;
import it.polimi.telcodb2project2022.services.ServService;
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
import java.io.IOException;
import java.util.Objects;

@WebServlet("/CreateService")
public class CreateService extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.telcodb2project2022.services/ServService")
    private ServService servService;

    @EJB(name = "it.polimi.telcodb2project2022.services/EmployeeService")
    private EmployeeService eService;

    public CreateService() {
        super();
    }

    @Override
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
        String path = "/createService.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String username = (String) request.getSession().getAttribute("employeeId");
        if(username != null)
            e = eService.findById(username);
        ctx.setVariable("employee", e);
        
        String type = null;
        if (request.getParameterMap().containsKey("serviceId") && !Objects.equals(request.getParameter("serviceId"), "") &&
                !request.getParameter("serviceId").isEmpty()) {
            try {
                type = request.getParameter("serviceId");
                request.getSession().setAttribute("typeSelected", type);
                ctx.setVariable("typeSelected", type);
            } catch (Exception error) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
            }
        }

        templateEngine.process(path, ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = (String) request.getSession().getAttribute("typeSelected");

        int minNum = 0;
        int SMSNum = 0;
        float minFee = 0;
        float SMSFee = 0;
        int gigaNum = 0;
        float gigaFee = 0;

        if(type.equals("mobilePhone")) {
            minNum = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numMinutes")));
            SMSNum = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numSMSs")));
            minFee = Float.parseFloat(StringEscapeUtils.escapeJava(request.getParameter("feeMinutes")));
            SMSFee = Float.parseFloat(StringEscapeUtils.escapeJava(request.getParameter("feeSMSs")));
        } else {
            gigaNum = Integer.parseInt(StringEscapeUtils.escapeJava(request.getParameter("numGiga")));
            gigaFee = Float.parseFloat(StringEscapeUtils.escapeJava(request.getParameter("feeGiga")));
        }

        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        Service service = servService.insertService(type, minNum, SMSNum, minFee, SMSFee, gigaNum, gigaFee);

        String path = getServletContext().getContextPath() + "/GoToEmployeeHomePage";
        response.sendRedirect(path);
    }
}
