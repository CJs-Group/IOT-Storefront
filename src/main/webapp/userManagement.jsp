<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.Users.Staff"%>
<%@page import="java.util.List"%>
<%
    DBConnector dbc = new DBConnector();
    DBManager dbm = new DBManager(dbc.openConnection());
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Management</title>
        <link rel="stylesheet" type="text/css" href="css/userManagement.css">
        <script>
        let selectedTab = 'none';
        let selectedRow = null;

        window.onload = function() {
            document.getElementById('editButton').disabled = true;
            document.getElementById('deleteButton').disabled = true;
        }

            function expandCustomer() {
                selectedTab = 'Customer';
                document.getElementById('activeTab').value = 'Customer';
                const customerButton = document.querySelector('.customerButton');
                const staffButton = document.querySelector('.staffButton');
                const container = document.querySelector('.container');
                const smallbuttons = document.querySelectorAll('.smallbutton');
                const buttonColor = getComputedStyle(customerButton).backgroundColor;
                container.classList.add('shown');
                staffButton.classList.remove('movedownButton');
                customerButton.classList.add('movedownButton');
                staffButton.classList.add('moveupButton');
                customerButton.classList.remove('moveupButton');
                document.body.style.backgroundColor = buttonColor;
                smallbuttons.forEach(button => {
                    button.style.backgroundColor = buttonColor;
                });
                document.getElementById('customerContent').style.display = 'block';
                document.getElementById('staffContent').style.display = 'none';

                document.getElementById('editButton').disabled = true;
                document.getElementById('deleteButton').disabled = true;
            }

            function expandStaff() {
                selectedTab = 'Staff';
                document.getElementById('activeTab').value = 'Staff';
                const staffButton = document.querySelector('.staffButton');
                const customerButton = document.querySelector('.customerButton');
                const container = document.querySelector('.container');
                const smallbuttons = document.querySelectorAll('.smallbutton');
                const buttonColor = getComputedStyle(staffButton).backgroundColor;
                container.classList.add('shown');
                customerButton.classList.remove('movedownButton');
                staffButton.classList.add('movedownButton');
                customerButton.classList.add('moveupButton');
                staffButton.classList.remove('moveupButton');
                document.body.style.backgroundColor = buttonColor;
                smallbuttons.forEach(button => {
                    button.style.backgroundColor = buttonColor;
                });
                document.getElementById('staffContent').style.display = 'block';
                document.getElementById('customerContent').style.display = 'none';

                document.getElementById('editButton').disabled = true;
                document.getElementById('deleteButton').disabled = true;
            }
            function selectUser(id, rowElement) {
                if (selectedRow) {
                    selectedRow.classList.remove('selected-row');
                }
                rowElement.classList.add('selected-row');
                selectedRow = rowElement;
                document.getElementById('selectedUserID').value = id;

                document.getElementById('editButton').disabled = false;
                document.getElementById('deleteButton').disabled = false;

                // document.getElementById('selectForm').submit();
            }
        </script>
    </head>
    <body>
        <div class="content-wrapper">
            <h1>User Management</h1>
            <div class="buttons">
                <button class="customerButton bigbutton" onmouseup="expandCustomer()">Customer</button>
                <button class="staffButton bigbutton" onmouseup="expandStaff()">Staff</button>
            </div>
                <div class="container">
                    <form id="selectForm" action="userManagement" method="post">
                        <input type="hidden" name="selectedUserID" id="selectedUserID"/>
                        <input type="hidden" name="activeTab" id="activeTab" value=""/>
                    </form>
                    <div id="customerContent" style="display:none; width:100%;">
                        <h2>Customers</h2>
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th><th>Username</th><th>Email</th><th>Phone</th><th>Address</th><th>Type</th><th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% 
                            List<Customer> customers = (List<Customer>)dbm.getCustomers();
                            if (customers != null) {
                                for(Customer c: customers) { 
                            %>
                            <tr onclick="selectUser(<%=c.getUserID()%>, this)" style="cursor:pointer;">
                                <td><%=c.getUserID()%></td>
                                <td><%=c.getUsername()%></td>
                                <td><%=c.getEmail()%></td>
                                <td><%=c.getPhoneNumber()%></td>
                                <td><%=c.getAddress()%></td>
                                <td><%=c.getAccountType()%></td>
                            </tr>
                            <%   }
                            }
                            %>
                            </tbody>
                        </table>
                    </div>

                    <div id="staffContent" style="display:none; width:100%;">
                        <h2>Staff</h2>
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th><th>Username</th><th>Email</th><th>Phone</th><th>Staff Role</th><th>Admin?</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% 
                            List<Staff> staff = (List<Staff>)dbm.getStaff();
                            if (staff != null) {
                                for(Staff s: staff) { 
                            %>
                            <tr onclick="selectUser(<%=s.getUserID()%>, this)" style="cursor:pointer;">
                                <td><%=s.getUserID()%></td>
                                <td><%=s.getUsername()%></td>
                                <td><%=s.getEmail()%></td>
                                <td><%=s.getPhoneNumber()%></td>
                                <td><%=s.getStaffRole()%></td>
                                <td><%=s.isAdmin()? "Yes":"No"%></td>
                            </tr>
                            <%   }
                            }
                            %>
                            </tbody>
                        </table>
                    </div>
                    <div class="buttons">
                        <button class="addButton smallbutton" type="submit" form="selectForm" name="addUser" id="addButton">Add</button>
                        <button class="editButton smallbutton" type="submit" form="selectForm" name="editUser" id="editButton">Edit</button>
                        <button class="deleteButton smallbutton" type="submit" form="selectForm" name="deleteUser" id="deleteButton">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>