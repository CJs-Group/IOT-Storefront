<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Items.ItemType"%>
<%@page import="Model.Items.Types"%>
<%@page import="java.util.List"%>
<%
    DBConnector dbc = new DBConnector();
    DBManager dbm = new DBManager(dbc.openConnection());
    String selectedItemTypeID = request.getParameter("selectedItemTypeID");
    ItemType itemType = dbm.getItemTypeById(Integer.parseInt(selectedItemTypeID));
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Item</title>
        <%-- <link rel="stylesheet" type="text/css" href="css/userManagement.css"> --%>
        <script>
        
        </script>
    </head>
    <body>
        <h1>
            Add Item
        </h1>
        <form action="/itemManip" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="formAction" value="editItem" />

            <label for="selectedItemTypeID">Item Type ID: <%=selectedItemTypeID%></label>
            <input type="hidden" id="selectedItemTypeID" name="selectedItemTypeID" value="<%=selectedItemTypeID%>" /><br />

            <label for="name">Name:</label><br />
            <input required id="name" name="name" type="text" value="<%=itemType.getName()%>" />
            <br />

            <label>Description:</label><br />
            <textarea id="description" name="description"><%=itemType.getDescription()%></textarea>
            <br />

            <label>Price:</label><br />
            $<input required id="price" name="price" type="number" value="1" min="1" step="1" value="<%=itemType.getPrice()%>" />
            <br />

            <label>Type:</label><br />
            <input required type="radio" id="networking" name="type" value="Networking" <%=itemType.getType() == Types.Networking ? "checked" : ""%>>
            <label for="networking">Networking</label><br />
            <input type="radio" id="security" name="type" value="Security" <%=itemType.getType() == Types.Security ? "checked" : ""%>>
            <label for="security">Security</label><br />
            <input type="radio" id="smarthome" name="type" value="Smart_Home" <%=itemType.getType() == Types.Smart_Home ? "checked" : ""%>>
            <label for="smarthome">Smart Home</label><br />
            <input type="radio" id="assistants" name="type" value="Assistants" <%=itemType.getType() == Types.Assistants ? "checked" : ""%>>
            <label for="assistants">Assistants</label><br />

            <label>Image:</label><br />
            <input id="image" name="image" type="file" accept="image/png, image/jpeg, image/webp, image/gif" /><br />

            <button type="submit">Edit Item</button>
        </form>
        <div><%=session.getAttribute("formError") == null ? "" : session.getAttribute("formError")%></div>
    </body>
</html>