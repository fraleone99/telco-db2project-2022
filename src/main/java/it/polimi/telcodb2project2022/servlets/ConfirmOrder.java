package it.polimi.telcodb2project2022.servlets;

import it.polimi.telcodb2project2022.entities.*;
import it.polimi.telcodb2project2022.services.OrderService;
import it.polimi.telcodb2project2022.services.ServiceActivationScheduleService;
import it.polimi.telcodb2project2022.services.UserService;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet("/ConfirmOrder")
public class ConfirmOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.telcodb2project2022.services/OrderService")
    private OrderService orderService;

    @EJB(name = "it.polimi.telcodb2project2022.services/UserService")
    private UserService userService;

    @EJB(name = "it.polimi.telcodb2project2022.services/ServiceActivationScheduleService")
    private ServiceActivationScheduleService scheduleService;

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


        Order invalidOrder = (Order) request.getSession().getAttribute("invalidOrder");

        if(invalidOrder == null) {
            int duration =  Integer.parseInt((String) request.getSession().getAttribute("duration"));
            List<OptionalProduct> selectedOptionals = (List<OptionalProduct>) request.getSession().getAttribute("selectedOptionals");
            float totalCost = (Float) request.getSession().getAttribute("totalCost");
            ServicePackage servicePackage = (ServicePackage) request.getSession().getAttribute("packageSelected");
            List<Service> services = servicePackage.getServices();

            ctx.setVariable("duration", duration);
            ctx.setVariable("user", user);
            ctx.setVariable("selectedOptionals", selectedOptionals);
            ctx.setVariable("totalCost", totalCost);
            ctx.setVariable("servicePackage", servicePackage);
            ctx.setVariable("services", services);
        }
        else{
            ctx.setVariable("duration", invalidOrder.getDuration());
            ctx.setVariable("user", user);
            ctx.setVariable("selectedOptionals", invalidOrder.getSelectedOptional());
            ctx.setVariable("totalCost", invalidOrder.getTotalValue());
            ctx.setVariable("servicePackage", invalidOrder.getServicePackage());
            ctx.setVariable("services", invalidOrder.getServicePackage().getServices());
        }
        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Order invalidOrder = (Order) request.getSession().getAttribute("invalidOrder");
        User user = (User) request.getSession().getAttribute("user");


        if (invalidOrder == null) {
            Date startSubscription = (Date) request.getSession().getAttribute("startSubscription");
            List<OptionalProduct> selectedOptionals = (List<OptionalProduct>) request.getSession().getAttribute("selectedOptionals");

            List<Integer> OptionalIds = new ArrayList<>();
            for (OptionalProduct op : selectedOptionals) {
                System.out.println("id to add:" + op.getId());
                OptionalIds.add(op.getId());
            }

            int duration = Integer.parseInt((String) request.getSession().getAttribute("duration"));
            ServicePackage servicePackage = (ServicePackage) request.getSession().getAttribute("packageSelected");


            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());


            Order order;
            if (request.getParameter("Buy").equals("Buy")) {
                order = orderService.insertOrder(duration, true, startSubscription,
                        selectedOptionals, servicePackage, user);
                orderService.insertOptionals(order.getId(), OptionalIds);

                Calendar cal = Calendar.getInstance();
                cal.setTime(startSubscription);

                cal.add(Calendar.MONTH, order.getDuration());
                Date endDate = cal.getTime();

                scheduleService.insertServiceActivationSchedule(startSubscription, endDate, order.getServicePackage().getServices(),
                        order.getServicePackage().getOptionalProducts(), order);

            } else {
                order = orderService.insertOrder(duration, false, startSubscription,
                        selectedOptionals, servicePackage, user);
                userService.setLastFailedOrder(user, order);
                userService.increaseFailedPayment(user);
                orderService.insertOptionals(order.getId(), OptionalIds);
                userService.setInsolvent(user);
            }
        } else {
            if (request.getParameter("Buy").equals("Buy")) {
                orderService.setValid(invalidOrder.getId());
                Date startDate = invalidOrder.getStartDate();

                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);

                cal.add(Calendar.MONTH, invalidOrder.getDuration());
                Date endDate = cal.getTime();

                scheduleService.insertServiceActivationSchedule(startDate, endDate, invalidOrder.getServicePackage().getServices(),
                        invalidOrder.getServicePackage().getOptionalProducts(),invalidOrder);
            }
            else{
                userService.setLastFailedOrder(user, invalidOrder);
                userService.increaseFailedPayment(user);
            }
        }
        request.setAttribute("invalidOrder", null);
        request.getSession().setAttribute("orderCreated", null);

        String confirm = getServletContext().getContextPath() + "/GoToHomePage";
        response.sendRedirect(confirm);

    }
}
