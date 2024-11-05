package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bittercode.constant.BookStoreConstants;
import com.bittercode.constant.db.UsersDBConstants;
import com.bittercode.model.User;
import com.bittercode.model.UserRole;
import com.bittercode.service.UserService;
import com.bittercode.service.impl.UserServiceImpl;

public class CustomerLoginServlet extends HttpServlet {

    UserService authService = new UserServiceImpl();

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(BookStoreConstants.CONTENT_TYPE_TEXT_HTML);
        String uName = req.getParameter(UsersDBConstants.COLUMN_USERNAME);
        String pWord = req.getParameter(UsersDBConstants.COLUMN_PASSWORD);
        User user = authService.login(UserRole.CUSTOMER, uName, pWord, req.getSession());

        try {
            if (user != null) {
                RequestDispatcher rd = req.getRequestDispatcher("CustomerHome.html");
                rd.include(req, res);
                pw.println("<div id=\"topmid\" style=\"text-align: center; color: #2e4a21; margin-top: 20px; font-family: 'Georgia', serif;\">" +
                           "    <h1>Welcome to the Ebook Store</h1>" +
                           "</div><br>" +
                           "<table class=\"tab\" style=\"width: 50%; margin: 0 auto; background-color: #fdf6e3; border: 2px solid #2e4a21; border-radius: 10px; box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.2);\">" +
                           "    <tr>" +
                           "        <td style=\"padding: 20px; font-family: 'Georgia', serif; color: #5c4033; font-size: 1.1em;\">" +
                           "            <p>Welcome " + user.getFirstName() + ", Enjoy Your Reading Journey!</p>" +
                           "        </td>" +
                           "    </tr>" +
                           "</table>");
            } else {
                RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
                rd.include(req, res);
                pw.println("<table class=\"tab\" style=\"width: 50%; margin: 0 auto; background-color: #fdf6e3; border: 2px solid #8b0000; color: #8b0000; border-radius: 10px; box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.2);\">" +
                           "    <tr>" +
                           "        <td style=\"padding: 20px; font-family: 'Georgia', serif; font-size: 1.1em;\">" +
                           "            <p>Incorrect credentials. Please try again.</p>" +
                           "        </td>" +
                           "    </tr>" +
                           "</table>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}