package it.polimi.telcodb2project2022.servlets;

import it.polimi.telcodb2project2022.entities.*;
import it.polimi.telcodb2project2022.exceptions.CredentialsException;
import it.polimi.telcodb2project2022.services.*;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@WebServlet("/GoToEmployeeHomePage")
public class GoToEmployeeHomePage extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.telcodb2project2022.services/EmployeeService")
    private EmployeeService eService;

    @EJB(name = "it.polimi.telcodb2project2022.services/ServService")
    private ServService servService;

    @EJB(name = "it.polimi.telcodb2project2022.services/OptionalProductService")
    private OptionalProductService optionalProductService;

    @EJB(name = "it.polimi.telcodb2project2022.services/ServicePackageService")
    private ServicePackageService servicePackageService;

    public GoToEmployeeHomePage() {
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
            throws IOException, ServletException {
        Employee e = null;
        String path = "/employeeHomepage.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String username = (String) request.getSession().getAttribute("employeeId");
        if(username != null)
            e = eService.findById(username);
        ctx.setVariable("employee", e);

        List<Service> mobilePhone = servService.findServiceByType("mobilePhone");
        List<Service> fixedInternet = servService.findServiceByType("fixedInternet");
        List<Service> mobileInternet = servService.findServiceByType("mobileInternet");
        List<OptionalProduct> optionalProducts = optionalProductService.getOptionalProducts();

        ctx.setVariable("mobilePhone", mobilePhone);
        ctx.setVariable("fixedInternet", fixedInternet);
        ctx.setVariable("mobileInternet", mobileInternet);
        ctx.setVariable("optionalProducts", optionalProducts);

        templateEngine.process(path, ctx, response.getWriter());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Employee e = null;
        String path = "/employeeHomepage.html";
        ServletContext servletContext = getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        String username = (String) request.getSession().getAttribute("employeeId");
        if(username != null)
            e = eService.findById(username);
        ctx.setVariable("employee", e);

        List<Service> mobilePhone = servService.findServiceByType("mobilePhone");
        List<Service> fixedInternet = servService.findServiceByType("fixedInternet");
        List<Service> mobileInternet = servService.findServiceByType("mobileInternet");
        List<OptionalProduct> optionalProducts = optionalProductService.getOptionalProducts();

        ctx.setVariable("mobilePhone", mobilePhone);
        ctx.setVariable("fixedInternet", fixedInternet);
        ctx.setVariable("mobileInternet", mobileInternet);
        ctx.setVariable("optionalProducts", optionalProducts);

        String[] fixedPhoneButton = request.getParameterValues("fixedPhoneButton");
        String[] mobilePhoneButton = request.getParameterValues("mobilePhoneButton");
        String[] fixedInternetButton = request.getParameterValues("fixedInternetButton");
        String[] mobileInternetButton = request.getParameterValues("mobileInternetButton");
        String[] optionalButton = request.getParameterValues("optionalButton");

        if(mobilePhoneButton == null && fixedInternetButton == null && mobileInternetButton == null && fixedPhoneButton == null) {
            ctx.setVariable("errorMsg1", "[ERROR] You have to select at least one service");
        } else if((mobilePhoneButton != null && mobilePhoneButton.length>1) || (fixedInternetButton != null && fixedInternetButton.length>1) || (mobileInternetButton != null && mobileInternetButton.length>1)) {
            ctx.setVariable("errorMsg1", "[ERROR] You have selected more than one service of the same type");
        } else if(optionalButton == null) {
            ctx.setVariable("errorMsg2","[ERROR] You have to select at least one optional product");
        } else {
            String packageName = request.getParameter("packageName");
            float months12 = Float.parseFloat(request.getParameter("prize12"));
            float months24 = Float.parseFloat(request.getParameter("prize24"));
            float months36 = Float.parseFloat(request.getParameter("prize36"));

            List<Integer> servicesId = new ArrayList<>();
            List<Integer> optionalIds = new ArrayList<>();

            if(fixedPhoneButton != null){
                servicesId.add(1);
            }
            if(mobilePhoneButton != null) {
                for(String s : mobilePhoneButton) {
                    servicesId.add(Integer.parseInt(s));
                }
            }
            if(fixedInternetButton != null) {
                for (String s : fixedInternetButton) {
                    servicesId.add(Integer.parseInt(s));
                }
            }
            if(mobileInternetButton != null) {
                for (String s : mobileInternetButton) {
                    servicesId.add(Integer.parseInt(s));
                }
            }
            for(String s : optionalButton) {
                optionalIds.add(Integer.parseInt(s));
            }

            ServicePackage servicePackage;
            servicePackage = servicePackageService.insertServicePackage(packageName, months12, months24, months36, e);
            servicePackageService.insertServices(servicePackage.getId(), servicesId);
            servicePackageService.insertOptionalsAssociated(servicePackage.getId(), optionalIds);
            ctx.setVariable("confirmMsg","[OK] The service package '" + packageName + "' was created");
        }

        templateEngine.process(path, ctx, response.getWriter());
    }

}
