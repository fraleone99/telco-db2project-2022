package it.polimi.telcodb2project2022.servlets;

import it.polimi.telcodb2project2022.entities.OptionalProduct;
import it.polimi.telcodb2project2022.entities.Order;
import it.polimi.telcodb2project2022.entities.ServicePackage;
import it.polimi.telcodb2project2022.entities.User;
import it.polimi.telcodb2project2022.services.OrderService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/ConfirmOrder")
public class ConfirmOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.telcodb2project2022.services/OrderService")
    private OrderService orderService;

    public ConfirmOrder(){super();}

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
        String path = "/confirmation.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        User user = (User) request.getSession().getAttribute("user");
        Order order = (Order) request.getSession().getAttribute("order");
        ctx.setVariable("user", user);
        ctx.setVariable("order", order);
        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println( "Entrato in post");


        Date startSubscription = (Date) request.getSession().getAttribute("startSubscription");
        List<OptionalProduct> selectedOptional = (List<OptionalProduct>) request.getSession().getAttribute("selectedOptionals");

        List<Integer> OptionalIds = new ArrayList<>();
        for(OptionalProduct op : selectedOptional) {
            System.out.println("id to add:" + op.getId());
            OptionalIds.add(op.getId());
        }

        int duration =  Integer.parseInt((String) request.getSession().getAttribute("duration"));
        ServicePackage servicePackage = (ServicePackage) request.getSession().getAttribute("packageSelected");
        User user = (User) request.getSession().getAttribute("user");


        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());


        Order order;
        order =  orderService.insertOrder(duration,request.getParameter("Buy").equals("Buy"), startSubscription,
                selectedOptional, servicePackage, user);
        orderService.insertOptionals(order.getId(), OptionalIds);

        String confirm = getServletContext().getContextPath() + "/GoToHomePage";
        response.sendRedirect(confirm);
    }
}
