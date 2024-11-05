package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bittercode.constant.BookStoreConstants;
import com.bittercode.constant.db.BooksDBConstants;
import com.bittercode.model.Book;
import com.bittercode.model.UserRole;
import com.bittercode.service.BookService;
import com.bittercode.service.impl.BookServiceImpl;
import com.bittercode.util.StoreUtil;

public class AddBookServlet extends HttpServlet {
    BookService bookService = new BookServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(BookStoreConstants.CONTENT_TYPE_TEXT_HTML);

        if (!StoreUtil.isLoggedIn(UserRole.SELLER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }

        String bName = req.getParameter(BooksDBConstants.COLUMN_NAME);
        RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
        rd.include(req, res);
        StoreUtil.setActiveTab(pw, "addbook");
        pw.println("<div class='container my-2'>");
        if(bName == null || bName.isBlank()) {
            //render the add book form;
            showAddBookForm(pw);
            return;
        } //else process the add book
        
 
        try {
            String uniqueID = UUID.randomUUID().toString();
            String bCode = uniqueID;
            String bAuthor = req.getParameter(BooksDBConstants.COLUMN_AUTHOR);
            double bPrice = Integer.parseInt(req.getParameter(BooksDBConstants.COLUMN_PRICE));
            int bQty = Integer.parseInt(req.getParameter(BooksDBConstants.COLUMN_QUANTITY));

            Book book = new Book(bCode, bName, bAuthor, bPrice, bQty);
            String message = bookService.addBook(book);
            if ("SUCCESS".equalsIgnoreCase(message)) {
                pw.println(
                        "<table class=\"tab\"><tr><td>Book Detail Updated Successfully!<br/>Add More Books</td></tr></table>");
            } else {
                pw.println("<table class=\"tab\"><tr><td>Failed to Add Books! Fill up CareFully</td></tr></table>");
                //rd.include(req, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<table class=\"tab\"><tr><td>Failed to Add Books! Fill up CareFully</td></tr></table>");
        }
    }
    
    private static void showAddBookForm(PrintWriter pw) {
        String form = 
                "<table class=\"tab my-5\" style=\"width: 40%; background-color: #f9f9f9; border: 2px solid #4a90e2; border-radius: 8px;\">" +
                "    <tr>" +
                "        <td style=\"padding: 20px;\">" +
                "            <form action=\"addbook\" method=\"post\">" +
                "                <label for=\"bookName\" style=\"color: #333; font-weight: bold;\">Book Name:</label>" +
                "                <input type=\"text\" name=\"name\" id=\"bookName\" placeholder=\"Enter Book's name\" required " +
                "                       style=\"width: 100%; padding: 8px; margin-top: 4px; margin-bottom: 12px; border: 1px solid #ccc; border-radius: 4px;\"><br/>" +
                                
                "                <label for=\"bookAuthor\" style=\"color: #333; font-weight: bold;\">Book Author:</label>" +
                "                <input type=\"text\" name=\"author\" id=\"bookAuthor\" placeholder=\"Enter Author's Name\" required " +
                "                       style=\"width: 100%; padding: 8px; margin-top: 4px; margin-bottom: 12px; border: 1px solid #ccc; border-radius: 4px;\"><br/>" +

                "                <label for=\"bookPrice\" style=\"color: #333; font-weight: bold;\">Book Price:</label>" +
                "                <input type=\"number\" name=\"price\" placeholder=\"Enter the Price\" required " +
                "                       style=\"width: 100%; padding: 8px; margin-top: 4px; margin-bottom: 12px; border: 1px solid #ccc; border-radius: 4px;\"><br/>" +

                "                <label for=\"bookQuantity\" style=\"color: #333; font-weight: bold;\">Book Quantity:</label>" +
                "                <input type=\"number\" name=\"quantity\" id=\"bookQuantity\" placeholder=\"Enter the quantity\" required " +
                "                       style=\"width: 100%; padding: 8px; margin-top: 4px; margin-bottom: 12px; border: 1px solid #ccc; border-radius: 4px;\"><br/>" +

                "                <input class=\"btn btn-success my-2\" type=\"submit\" value=\"Add Book\" " +
                "                       style=\"background-color: #4a90e2; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer;\">" +
                "            </form>" +
                "        </td>" +
                "    </tr>" +
                "    <!-- Uncomment the following line if you want to include a link back to the home page -->" +
                "    <!-- <tr><td><a href=\"index.html\" style=\"color: #4a90e2;\">Go Back To Home Page</a></td></tr> --> " +
                "</table>";

        pw.println(form);
    }
}
