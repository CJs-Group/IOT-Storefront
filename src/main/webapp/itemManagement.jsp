<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Items.ItemType"%>
<%@page import="java.util.List"%>
<%
    DBConnector dbc = new DBConnector();
    DBManager dbm = new DBManager(dbc.openConnection());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Item Management</title>
        <%-- <link rel="stylesheet" type="text/css" href="css/userManagement.css"> --%>
        <script>
        
        </script>
    </head>
    <body>
        <div>
            <h1>Item Management</h1>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Type</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% 
                    List<ItemType> itemTypes = (List<ItemType>)dbm.getAllItemTypes();
                    if (itemTypes != null) {
                        for(ItemType i: itemTypes) { 
                    %>
                    <tr>
                        <td><%=i.getItemID()%></td>
                        <td><%=i.getName()%></td>
                        <td><%=i.getDescription()%></td>
                        <td>$<%=i.getPrice()%></td>
                        <td><%=i.getType()%></td>
                        <td>
                            <form action="/itemManip" method="POST">
                                <input type="hidden" name="formAction" value="deleteItem" />
                                <input type="hidden" name="selectedItemTypeID" value="<%=i.getItemID()%>" />
                                <button type="submit">Delete</button>
                            </form>
                            <form action="/editItem.jsp" method="GET">
                                <input type="hidden" name="selectedItemTypeID" value="<%=i.getItemID()%>" />
                                <button type="submit">Edit</button>
                            </form>
                        </td>
                    </tr>
                    <%   }
                    }
                    %>
                    </tbody>
                </table>
            </div>
            <div>
                <a href="/addItem.jsp">Add Item</a>
            </div>
        </div>
    </body>
</html>