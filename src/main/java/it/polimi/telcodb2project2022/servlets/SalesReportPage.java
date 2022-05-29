package it.polimi.telcodb2project2022.servlets;

import it.polimi.telcodb2project2022.entities.Employee;
import it.polimi.telcodb2project2022.entities.Order;
import it.polimi.telcodb2project2022.entities.ServicePackage;
import it.polimi.telcodb2project2022.entities.User;
import it.polimi.telcodb2project2022.entities.materializedViewTable.*;
import it.polimi.telcodb2project2022.services.*;
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
import java.util.List;
import java.util.Objects;

@WebServlet("/SalesReport")
public class SalesReportPage extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;
    @EJB(name = "it.polimi.telcodb2project2022.services/EmployeeService")
    private EmployeeService eService;

    @EJB(name = "it.polimi.telcodb2project2022.services/ServicePackageService")
    private ServicePackageService servicePackageService;

    @EJB(name = "it.polimi.telcodb2project2022.services/User")
    private UserService userService;

    @EJB(name = "it.polimi.telcodb2project2022.services/Order")
    private OrderService orderService;

    public SalesReportPage() {
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
        String path = "/salesReportPage.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String username = (String) request.getSession().getAttribute("employeeId");
        if(username != null)
            e = eService.findById(username);
        ctx.setVariable("employee", e);

        List<ServicePackage> servicePackages = servicePackageService.getAllServicePackages();
        List<PackagePerValidityPeriod> packagePerValidityPeriods = servicePackageService.getAllPackagePerValidityPeriod();
        List<ValueOfSales> valueOfSales = servicePackageService.getAllValueOfSales();
        List<AverageOptional> averageOptionals = servicePackageService.getAllAverageOptional();
        BestSeller bestSoldNumber = servicePackageService.getBestSoldNumber();
        BestSeller bestValue = servicePackageService.getBestValue();
        List<User> insolventUsers = userService.getInsolventUsers();
        List<AuditingTable> alerts = userService.getAlerts();
        List<Order> orders = orderService.getSuspendedOrders();

        System.out.println(bestSoldNumber.getName());
        System.out.println(bestValue.getName());

        ctx.setVariable("packages", servicePackages);
        ctx.setVariable("packagePerValidityPeriod", packagePerValidityPeriods);
        ctx.setVariable("valueOfSales", valueOfSales);
        ctx.setVariable("averageOptionals", averageOptionals);
        ctx.setVariable("bestSoldNumber", bestSoldNumber);
        ctx.setVariable("bestValue", bestValue);
        ctx.setVariable("insolvent", insolventUsers);
        ctx.setVariable("alerts", alerts);
        ctx.setVariable("suspended", orders);

        templateEngine.process(path, ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
