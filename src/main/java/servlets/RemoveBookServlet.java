package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bittercode.constant.ResponseCode;
import com.bittercode.model.UserRole;
import com.bittercode.service.BookService;
import com.bittercode.service.impl.BookServiceImpl;
import com.bittercode.util.StoreUtil;

public class RemoveBookServlet extends HttpServlet {

    BookService bookService = new BookServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        if (!StoreUtil.isLoggedIn(UserRole.SELLER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }

        try {
            String bookId = req.getParameter("bookId");
            RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
            rd.include(req, res);
            StoreUtil.setActiveTab(pw, "removebook");
            pw.println("<div class='container'>");
            if (bookId == null || bookId.isBlank()) {
                // render the remove book form;
                showRemoveBookForm(pw);
                return;
            } // else continue

            String responseCode = bookService.deleteBookById(bookId.trim());
            if (ResponseCode.SUCCESS.name().equalsIgnoreCase(responseCode)) {
                pw.println("<table class=\"tab my-5\"><tr><td>Book Removed Successfully</td></tr></table>");
                pw.println(
                        "<table class=\"tab\"><tr><td><a href=\"removebook\">Remove more Books</a></td></tr></table>");

            } else {
                pw.println("<table class=\"tab my-5\"><tr><td>Book Not Available In The Store</td></tr></table>");
                pw.println(
                        "<table class=\"tab\"><tr><td><a href=\"removebook\">Remove more Books</a></td></tr></table>");
            }
            pw.println("</div>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<table class=\"tab\"><tr><td>Failed to Remove Books! Try Again</td></tr></table>");
        }
    }

    private static void showRemoveBookForm(PrintWriter pw) {
        String form = 
                "<form action=\"removebook\" method=\"post\" class=\"my-5\">" +
                "    <table class=\"tab\" style=\"width: 40%; background-color: #f2f2f2; border: 2px solid #e74c3c; border-radius: 8px;\">" +
                "        <tr>" +
                "            <td style=\"padding: 20px;\">" +
                "                <label for=\"bookCode\" style=\"color: #333; font-weight: bold;\">Enter Book ID to Remove:</label>" +
                "                <input type=\"text\" name=\"bookId\" placeholder=\"Enter Book ID\" id=\"bookCode\" required " +
                "                       style=\"width: 100%; padding: 8px; margin-top: 4px; margin-bottom: 12px; border: 1px solid #ccc; border-radius: 4px;\"><br/>" +
                "                <input class=\"btn btn-danger my-2\" type=\"submit\" value=\"Remove Book\" " +
                "                       style=\"background-color: #e74c3c; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer;\">" +
                "            </td>" +
                "        </tr>" +
                "    </table>" +
                "</form>";

        pw.println(form);
    }

}
