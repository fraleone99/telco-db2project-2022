package it.polimi.telcodb2project2022.servlets;

import it.polimi.telcodb2project2022.entities.OptionalProduct;
import it.polimi.telcodb2project2022.entities.Order;
import it.polimi.telcodb2project2022.entities.ServicePackage;
import it.polimi.telcodb2project2022.entities.User;
import it.polimi.telcodb2project2022.services.OptionalProductService;
import it.polimi.telcodb2project2022.services.ServicePackageService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet("/BuyService")
public class BuyService extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.telcodb2project2022.services/UserService")
    private UserService usrService;

    @EJB(name = "it.polimi.telcodb2project2022.services/ServicePackageService")
    private ServicePackageService spService;

    @EJB(name = "it.polimi.telcodb2project2022.services/OptionalProductServices")
    private OptionalProductService optionalProductService;


    public BuyService() {
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

        List<ServicePackage> packagesList  = null;
        ServicePackage packageToBuy = null;
        Integer packageId = null;
        List<OptionalProduct> optionalProducts = null;

        HttpSession session = request.getSession(true);

        User user = (User) session.getAttribute("user");

        String path = "/buy.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        packagesList = spService.getAllServicePackages();
        ctx.setVariable("packagesList", packagesList);


        if (request.getParameterMap().containsKey("toBuy") && request.getParameter("toBuy") != ""
                && !request.getParameter("toBuy").isEmpty()) {
            try {
                packageId = Integer.parseInt(request.getParameter("toBuy"));
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid package parameters");
                return;
            }
        }

        if (packageId != null) {
            packageToBuy = spService.findById(packageId);
            optionalProducts = optionalProductService.findByPackageId(packageToBuy.getId());
        }
        ctx.setVariable("packageToBuy", packageToBuy);
        ctx.setVariable("optionalProducts", optionalProducts);
        ctx.setVariable("user", user);

        session.setAttribute("packageSelected", packageToBuy);
        session.setAttribute("optionalProducts", optionalProducts);

        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ServicePackage servicePackage = (ServicePackage) session.getAttribute("packageSelected");
        List<OptionalProduct> optionalProducts = (List<OptionalProduct>) session.getAttribute("optionalProducts");


        List<OptionalProduct> selectedOptional = new ArrayList<>();
        String[] optionals = request.getParameterValues("optional");
        String startDate = request.getParameter("startDate");
        User user = (User) session.getAttribute("user");


        String duration = request.getParameter("duration");
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        System.out.println(duration);
        System.out.println("The package to buy is : " + servicePackage.getId() + "for the duration " + duration);
        for(OptionalProduct p : optionalProducts){
            System.out.println("optional: " + p.getName());
        }

        if(optionals != null) {
            for (String optional : optionals) {
                System.out.println("selected : " + optional);
                selectedOptional.add(optionalProductService.findById(Integer.parseInt(optional)));

            }
        }
        System.out.println("Start Date: " + startDate);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        Date startSubscription = null;
        try {
            startSubscription = dateFormat.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Order order = new Order(date, startSubscription, Integer.parseInt(duration), selectedOptional, servicePackage, user);
        session.setAttribute("startSubscription", startSubscription);
        session.setAttribute("selectedOptionals", selectedOptional);
        session.setAttribute("order", order);
        session.setAttribute("duration", duration);

        String confirm = getServletContext().getContextPath() + "/ConfirmOrder";
        response.sendRedirect(confirm);
    }
}
