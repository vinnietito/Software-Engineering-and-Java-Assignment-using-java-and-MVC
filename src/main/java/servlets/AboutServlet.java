package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bittercode.model.UserRole;
import com.bittercode.util.StoreUtil;

// Http Servlet extended class for showing the about information
public class AboutServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        // If the store is logged in as customer or seller show about info
        if (StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("CustomerHome.html");
            rd.include(req, res);
            StoreUtil.setActiveTab(pw, "about");
            displayAboutContent(pw);

        } else if (StoreUtil.isLoggedIn(UserRole.SELLER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
            rd.include(req, res);
            StoreUtil.setActiveTab(pw, "about");
            displayAboutContent(pw);

        } else {
            // If the user is not logged in, ask to login first
            // Proceed only if logged in or forward to login page
            RequestDispatcher rd = req.getRequestDispatcher("login.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
        }
    }

    private void displayAboutContent(PrintWriter pw) {
        pw.println("<div class=\"about-us\">");
        pw.println("<h1>About Us</h1>");
        pw.println("<p>Welcome to OnlineBookstore, your ultimate destination for a vast selection of books across all genres! Founded in [Year], we are passionate about connecting readers with their favorite titles and introducing them to new authors and stories that inspire, entertain, and educate.</p>");

        pw.println("<h2>Our Mission</h2>");
        pw.println("<p>At OnlineBookstore, our mission is to make reading accessible to everyone. We believe that books have the power to transform lives, spark imagination, and foster a love for learning. Whether you’re a lifelong bibliophile or just starting your reading journey, we aim to provide a seamless shopping experience that caters to your every literary need.</p>");

        pw.println("<h2>What We Offer</h2>");
        pw.println("<ul>");
        pw.println("<li><strong>Extensive Collection:</strong> From bestsellers to hidden gems, our extensive collection includes fiction, non-fiction, textbooks, and more. We carry books for all ages and interests, ensuring there’s something for everyone.</li>");
        pw.println("<li><strong>User-Friendly Experience:</strong> Our website is designed with you in mind. Enjoy easy navigation, personalized recommendations, and a streamlined checkout process to make your shopping experience enjoyable and hassle-free.</li>");
        pw.println("<li><strong>Community Engagement:</strong> We are committed to fostering a vibrant reading community. Join our newsletter for updates on new releases, author interviews, and exclusive promotions. Follow us on social media to participate in book discussions and events.</li>");
        pw.println("<li><strong>Support Independent Authors:</strong> We believe in giving a platform to new voices in literature. Our curated selection features works by independent and emerging authors, giving you the chance to discover new favorites.</li>");
        pw.println("</ul>");

        pw.println("<h2>Our Team</h2>");
        pw.println("<p>Our dedicated team of book lovers is here to assist you every step of the way. From curating our collection to providing customer support, we are committed to delivering the best service possible. Have a question or need a recommendation? Don’t hesitate to reach out!</p>");

        pw.println("<h2>Join Us on Our Journey</h2>");
        pw.println("<p>Thank you for choosing OnlineBookstore. We’re excited to be a part of your reading adventure! Explore our site, discover new titles, and become part of our community of book lovers. Together, let’s celebrate the joy of reading!</p>");
        pw.println("</div>");
    }
}
