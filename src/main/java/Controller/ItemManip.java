package Controller;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.common.hash.Hashing;

import Model.Validator;
import Model.DAO.DBConnector;
import Model.DAO.DBManager;
import Model.Items.ItemType;
import Model.Items.Types;
import Model.Order.PaymentInfo;
import Model.Users.Customer;
import Model.Users.Staff;
import Model.Users.User;

@WebServlet("/itemManip")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 100
)
public class ItemManip extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String successRedirectPage = "itemManagement.jsp";
        String formErrorRedirectPage = "itemManagement.jsp";
        try (DBConnector dbc = new DBConnector()) {
            HttpSession session = request.getSession();
            DBManager dbm = new DBManager(dbc.openConnection());
            // String selectedItemTypeIDStr = request.getParameter("selectedItemTypeID");
            // session.setAttribute("selectedItemTypeID", selectedItemTypeIDStr);
            String selectedItemTypeIDStr = request.getParameter("selectedItemTypeID");
            String formAction = request.getParameter("formAction");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String price = request.getParameter("price");
            String type = request.getParameter("type");
            String imageUrl = "";
            if (formAction.equals("addItem") || formAction.equals("editItem")) {
                Part imagePart = request.getPart("image");
                if (imagePart != null && imagePart.getSize() > 0) {
                    String imageFileName = imagePart.getSubmittedFileName();
                    if (imageFileName != null && !imageFileName.trim().isEmpty()) {
                        String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }
                        imagePart.write(uploadPath + File.separator + imageFileName);
                        imageUrl = "images/" + imageFileName;
                    }
                }
            }
            switch (formAction) {
                case "deleteItem":
                    formErrorRedirectPage = "itemManagement.jsp";
                    successRedirectPage = "itemManagement.jsp";
                    try {
                        int itemTypeIdToDelete = Integer.parseInt(selectedItemTypeIDStr);
                        dbm.deleteItemType(itemTypeIdToDelete);
                        response.sendRedirect(successRedirectPage);
                    }
                    catch (NumberFormatException e) {
                        response.sendRedirect(formErrorRedirectPage);
                    }
                    catch (SQLException e) {
                        session.setAttribute("formError", "Database error during deletion: " + e.getMessage());
                        response.sendRedirect(formErrorRedirectPage);
                    }
                    break;
                case "addItem":
                    formErrorRedirectPage = "addItem.jsp";
                    successRedirectPage = "itemManagement.jsp";
                    try {
                        ItemType it = new ItemType(
                            0,
                            Integer.parseInt(price),
                            name,
                            description,
                            Types.valueOf(type),
                            imageUrl
                        );
                        dbm.createItemType(it);
                        
                        response.sendRedirect(successRedirectPage);
                    }
                    catch (NumberFormatException e) {
                        response.sendRedirect(formErrorRedirectPage);
                    }
                    catch (SQLException e) {
                        session.setAttribute("formError", "Database error during insertion: " + e.getMessage());
                        response.sendRedirect(formErrorRedirectPage);
                    }
                    break;
                case "editItem":
                    formErrorRedirectPage = "editItem.jsp?selectedItemTypeID=" + selectedItemTypeIDStr;
                    successRedirectPage = "itemManagement.jsp";
                    try {
                        int itemTypeId = Integer.parseInt(selectedItemTypeIDStr);
                        ItemType it = dbm.getItemTypeById(itemTypeId);
                        if (name != null) {
                            it.setName(name);
                        }
                        if (description != null) {
                            it.setDescription(description);
                        }
                        if (price != null) {
                            it.setPrice(Integer.parseInt(price));
                        }
                        if (type != null) {
                            it.setType(Types.valueOf(type));
                        }
                        if (imageUrl != null && imageUrl.length() != 0) {
                            it.setImagePath(imageUrl);
                        }
                        dbm.updateItemType(it);
                        response.sendRedirect(successRedirectPage);
                    }
                    catch (NumberFormatException e) {
                        response.sendRedirect(formErrorRedirectPage);
                    }
                    catch (SQLException e) {
                        session.setAttribute("formError", "Database error during insertion: " + e.getMessage());
                        response.sendRedirect(formErrorRedirectPage);
                    }
                    break;
                default:
                    session.setAttribute("formError", "Invalid action specified: " + formAction);
                    response.sendRedirect("itemManagement.jsp");
                    break;
            }
        }
        catch (SQLException e) {
            response.sendRedirect("register.jsp?error=" +  e.getMessage());
        }
        catch (NumberFormatException e) {
            response.sendRedirect("itemManagement.jsp?error=Invalid item type ID format.");
        }
        catch (Exception e) {
            response.sendRedirect("itemManagement.jsp?error=" + e.getMessage());
        }
    }
}
