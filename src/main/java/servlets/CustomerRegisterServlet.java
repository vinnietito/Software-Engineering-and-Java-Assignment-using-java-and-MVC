package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bittercode.constant.BookStoreConstants;
import com.bittercode.constant.ResponseCode;
import com.bittercode.constant.db.UsersDBConstants;
import com.bittercode.model.User;
import com.bittercode.model.UserRole;
import com.bittercode.service.UserService;
import com.bittercode.service.impl.UserServiceImpl;

public class CustomerRegisterServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(BookStoreConstants.CONTENT_TYPE_TEXT_HTML);

        String pWord = req.getParameter(UsersDBConstants.COLUMN_PASSWORD);
        String fName = req.getParameter(UsersDBConstants.COLUMN_FIRSTNAME);
        String lName = req.getParameter(UsersDBConstants.COLUMN_LASTNAME);
        String addr = req.getParameter(UsersDBConstants.COLUMN_ADDRESS);
        String phNo = req.getParameter(UsersDBConstants.COLUMN_PHONE);
        String mailId = req.getParameter(UsersDBConstants.COLUMN_MAILID);
        
        User user = new User();
        user.setEmailId(mailId);
        user.setFirstName(fName);
        user.setLastName(lName);
        user.setPassword(pWord);
        user.setPhone(Long.parseLong(phNo));
        user.setAddress(addr);

        try {
            String respCode = userService.register(UserRole.CUSTOMER, user);
            System.out.println(respCode);
            
            if (ResponseCode.SUCCESS.name().equalsIgnoreCase(respCode)) {
                RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
                rd.include(req, res);
                pw.println(
                    "<div style=\"margin: 20px auto; width: 60%; text-align: center; color: #2e4a21;\">" +
                    "    <h2>Welcome to Our Ebook store!</h2>" +
                    "</div>" +
                    "<table class=\"tab\" style=\"width: 60%; margin: 0 auto; background-color: #fdf6e3; border: 2px solid #2e4a21; border-radius: 10px; box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.2);\">" +
                    "    <tr style=\"background-color: #e7e3d4;\">" +
                    "        <th colspan=\"2\" style=\"padding: 10px; font-family: 'Georgia', serif; color: #2e4a21; font-size: 1.2em;\">User Registration Summary</th>" +
                    "    </tr>" +
                    "    <tr><td style=\"padding: 10px; font-weight: bold; color: #5c4033;\">First Name:</td><td style=\"padding: 10px;\">" + fName + "</td></tr>" +
                    "    <tr><td style=\"padding: 10px; font-weight: bold; color: #5c4033;\">Last Name:</td><td style=\"padding: 10px;\">" + lName + "</td></tr>" +
                    "    <tr><td style=\"padding: 10px; font-weight: bold; color: #5c4033;\">Email ID:</td><td style=\"padding: 10px;\">" + mailId + "</td></tr>" +
                    "    <tr><td style=\"padding: 10px; font-weight: bold; color: #5c4033;\">Phone:</td><td style=\"padding: 10px;\">" + phNo + "</td></tr>" +
                    "    <tr><td style=\"padding: 10px; font-weight: bold; color: #5c4033;\">Address:</td><td style=\"padding: 10px;\">" + addr + "</td></tr>" +
                    "    <tr><td colspan=\"2\" style=\"text-align: center; padding: 15px; font-family: 'Georgia', serif; color: #2e4a21;\"><strong>User Registered Successfully!</strong></td></tr>" +
                    "</table>"
                );
            } else {
                RequestDispatcher rd = req.getRequestDispatcher("CustomerRegister.html");
                rd.include(req, res);
                pw.println(
                    "<table class=\"tab\" style=\"width: 60%; margin: 0 auto; background-color: #fdf6e3; border: 2px solid #8b0000; color: #8b0000; border-radius: 10px; box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.2);\">" +
                    "    <tr>" +
                    "        <td style=\"padding: 20px; font-family: 'Georgia', serif; font-size: 1.1em; color: #8b0000;\">" +
                    "            " + respCode + "<br>Sorry for the interruption! Please try again." +
                    "        </td>" +
                    "    </tr>" +
                    "</table>"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}