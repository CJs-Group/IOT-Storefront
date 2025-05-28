<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.DAO.DBConnector"%>
<%@page import="Model.DAO.DBManager"%>
<%@page import="Model.Users.User"%>
<%@page import="Model.Users.Customer"%>
<%@page import="Model.Users.Staff"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="Model.Users.AccountType"%>
<%@page import="Model.Users.StaffRole"%>
<%
    DBConnector dbc = new DBConnector();
    DBManager dbm = new DBManager(dbc.openConnection());
    List<Customer> customers = new ArrayList<>();
    List<Staff> staff = new ArrayList<>();

    String activeTab = (String) session.getAttribute("activeTab");
    if (activeTab == null) {
        String paramActiveTab = request.getParameter("activeTab");
        if (paramActiveTab != null && !paramActiveTab.isEmpty()){
            activeTab = paramActiveTab;
        }
        else {
            activeTab = "";
        }
    }
    String currentCustomerNameSearch = request.getParameter("customerNameSearch");
    if (currentCustomerNameSearch == null) {
        currentCustomerNameSearch = (String) session.getAttribute("customerNameSearch");
        if (currentCustomerNameSearch == null) currentCustomerNameSearch = "";
    } else {
        session.setAttribute("customerNameSearch", currentCustomerNameSearch);
    }

    String currentCustomerAccountTypeSearch = request.getParameter("customerAccountTypeSearch");
    if (currentCustomerAccountTypeSearch == null) {
        currentCustomerAccountTypeSearch = (String) session.getAttribute("customerAccountTypeSearch");
        if (currentCustomerAccountTypeSearch == null) currentCustomerAccountTypeSearch = "";
    }
    else {
        session.setAttribute("customerAccountTypeSearch", currentCustomerAccountTypeSearch);
    }

    String currentStaffNameSearch = request.getParameter("staffNameSearch");
    if (currentStaffNameSearch == null) {
        currentStaffNameSearch = (String) session.getAttribute("staffNameSearch");
        if (currentStaffNameSearch == null) currentStaffNameSearch = "";
    }
    else {
        session.setAttribute("staffNameSearch", currentStaffNameSearch);
    }

    String currentStaffRoleSearch = request.getParameter("staffRoleSearch");
    if (currentStaffRoleSearch == null) {
        currentStaffRoleSearch = (String) session.getAttribute("staffRoleSearch");
        if (currentStaffRoleSearch == null) currentStaffRoleSearch = "";
    }
    else{
        session.setAttribute("staffRoleSearch", currentStaffRoleSearch);
    }
    String customerNameQuery = currentCustomerNameSearch;
    String customerAccountTypeQuery = currentCustomerAccountTypeSearch;
    String staffNameQuery = currentStaffNameSearch;
    String staffRoleQuery = currentStaffRoleSearch;
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

            function removeNoTransitionFromElements() {
                const customerButton = document.querySelector('.customerButton');
                const staffButton = document.querySelector('.staffButton');
                const container = document.querySelector('.container');
                const smallbuttons = document.querySelectorAll('.smallbutton');
                const elements = [document.body, container, customerButton, staffButton, ...Array.from(smallbuttons)];
                
                elements.forEach(el => {
                    if (el && el.classList.contains('no-transition')) {
                         el.classList.remove('no-transition');
                    }
                });
            }

            function expandCustomer(isInitialCall = false) {
                if (!isInitialCall) {
                    removeNoTransitionFromElements();
                }
                let editButton = document.querySelector('.editButton');
                let deleteButton = document.querySelector('.deleteButton');
                let activateButton = document.querySelector('.activateButton');
                let deactivateButton = document.querySelector('.deactivateButton');
                if (editButton) editButton.disabled = true;
                if (deleteButton) deleteButton.disabled = true;
                if (activateButton) activateButton.disabled = true;
                if (deactivateButton) deactivateButton.disabled = true;
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
                document.getElementById('activateButton').disabled = true;
                document.getElementById('deactivateButton').disabled = true;
            }

            function expandStaff(isInitialCall = false) {
                if (!isInitialCall) {
                    removeNoTransitionFromElements();
                }
                let editButton = document.querySelector('.editButton');
                let deleteButton = document.querySelector('.deleteButton');
                let activateButton = document.querySelector('.activateButton');
                let deactivateButton = document.querySelector('.deactivateButton');
                if (editButton) editButton.disabled = true;
                if (deleteButton) deleteButton.disabled = true;
                if (activateButton) activateButton.disabled = true;
                if (deactivateButton) deactivateButton.disabled = true;
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
                document.getElementById('activateButton').disabled = true;
                document.getElementById('deactivateButton').disabled = true;
            }

            function selectUser(id, rowElement) {
                let editButton = document.querySelector('.editButton');
                let deleteButton = document.querySelector('.deleteButton');
                let activateButton = document.querySelector('.activateButton');
                let deactivateButton = document.querySelector('.deactivateButton');
                if (selectedRow) {
                    selectedRow.classList.remove('selected-row');
                }
                rowElement.classList.add('selected-row');
                selectedRow = rowElement;
                document.getElementById('selectedUserID').value = id;
                if (editButton) editButton.disabled = false;
                if (deleteButton) deleteButton.disabled = false;
                if (activateButton) activateButton.disabled = false;
                if (deactivateButton) deactivateButton.disabled = false;
            }

            window.onload = function() {
                let activeTabFromJSP = '<%= activeTab %>'; 
                const customerButton = document.querySelector('.customerButton');
                const staffButton = document.querySelector('.staffButton');
                const container = document.querySelector('.container');
                const smallbuttons = document.querySelectorAll('.smallbutton');

                const elementsToMakeInstant = [document.body, container, customerButton, staffButton, ...Array.from(smallbuttons)];
                elementsToMakeInstant.forEach(el => {
                    if (el) el.classList.add('no-transition');
                });
                if (activeTabFromJSP === 'Customer') {
                    expandCustomer(true);
                }
                else if (activeTabFromJSP === 'Staff') {
                    expandStaff(true);
                }
                if (container) void container.offsetWidth;
                setTimeout(() => {
                    removeNoTransitionFromElements();
                }, 0);
                if (document.getElementById('customerContent').style.display === 'block') {
                    document.getElementById('activeTab').value = 'Customer';
                }
                else if (document.getElementById('staffContent').style.display === 'block') {
                    document.getElementById('activeTab').value = 'Staff';
                }
            };
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
                        <input type="hidden" name="activeTab" id="activeTab" value="<%= activeTab %>"/>
                    </form>
                    <div id="customerContent" style="display:none; width:100%;">
                        <h2>Customers</h2>
                        <form method="GET" action="userManagement" class="search-form">
                            <input type="hidden" name="activeTab" value="Customer"/>
                            Name: <input type="text" name="customerNameSearch" id="customerNameSearchInput" value="<%= currentCustomerNameSearch %>"/>
                            Type: 
                            <select name="customerAccountTypeSearch" id="customerAccountTypeSearchSelect">
                                <option value="">All</option>
                                <% for (AccountType type : AccountType.values()) { %>
                                    <option value="<%= type.name() %>" <%= type.name().equals(currentCustomerAccountTypeSearch) ? "selected" : "" %>>
                                        <%= type.name() %>
                                    </option>
                                <% } %>
                            </select>
                            <button type="submit" class="smallbutton">Search</button>
                        </form>
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th><th>Username</th><th>Email</th><th>Phone</th><th>Address</th><th>Type</th><th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% 
                            customers = (List<Customer>)dbm.getCustomers(customerNameQuery, customerAccountTypeQuery);
                            if (customers != null) {
                                for(Customer c: customers) { 
                                    boolean isActivated = dbm.isUserActivated(c.getUserID());
                            %>
                            <tr onclick="selectUser(<%=c.getUserID()%>, this)" style="cursor:pointer;" class="<%= isActivated ? "" : "deactivated-user" %>">
                                <td><%=c.getUserID()%></td>
                                <td><%=c.getUsername()%></td>
                                <td><%=c.getEmail()%></td>
                                <td><%=c.getPhoneNumber() == null ? "" : c.getPhoneNumber()%></td>
                                <td><%=c.getAddress() == null ? "" : c.getAddress()%></td>
                                <td><%=c.getAccountType() == null ? "" : c.getAccountType().name() %></td>
                                <td class="status-cell">
                                    <span class="status-badge <%= isActivated ? "status-active" : "status-inactive" %>">
                                        <%= isActivated ? "Active" : "Inactive" %>
                                    </span>
                                </td>
                            </tr>
                            <%   }
                            }
                            %>
                            </tbody>
                        </table>
                    </div>

                    <div id="staffContent" style="display:none; width:100%;">
                        <h2>Staff</h2>
                        <form method="GET" action="userManagement" class="search-form">
                            <input type="hidden" name="activeTab" value="Staff"/>
                            Name: <input type="text" name="staffNameSearch" id="staffNameSearchInput" value="<%= currentStaffNameSearch %>"/>
                            Role: 
                            <select name="staffRoleSearch" id="staffRoleSearchSelect">
                                <option value="">All</option>
                                <% for (StaffRole role : StaffRole.values()) { %>
                                    <option value="<%= role.name() %>" <%= role.name().equals(currentStaffRoleSearch) ? "selected" : "" %>>
                                        <%= role.name() %>
                                    </option>
                                <% } %>
                            </select>
                            <button type="submit" class="smallbutton">Search</button>
                        </form>
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th><th>Username</th><th>Email</th><th>Phone</th><th>Role</th><th>Admin?</th><th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                            <% 
                            staff = (List<Staff>)dbm.getStaff(staffNameQuery, staffRoleQuery);
                            if (staff != null) {
                                for(Staff s: staff) { 
                                    boolean isActivated = dbm.isUserActivated(s.getUserID());
                            %>
                            <tr onclick="selectUser(<%=s.getUserID()%>, this)" style="cursor:pointer;" class="<%= isActivated ? "" : "deactivated-user" %>">
                                <td><%=s.getUserID()%></td>
                                <td><%=s.getUsername()%></td>
                                <td><%=s.getEmail()%></td>
                                <td><%=s.getPhoneNumber() == null ? "" : s.getPhoneNumber()%></td>
                                <td><%=s.getStaffRole() == null ? "" : s.getStaffRole().name() %></td>
                                <td><%=s.isAdmin()? "Yes":"No"%></td>
                                <td class="status-cell">
                                    <span class="status-badge <%= isActivated ? "status-active" : "status-inactive" %>">
                                        <%= isActivated ? "Active" : "Inactive" %>
                                    </span>
                                </td>
                            </tr>
                            <%   }
                            }
                            %>
                            </tbody>
                        </table>
                    </div>
                    <div class="buttons">
                        <button class="addButton smallbutton" type="submit" form="selectForm" name="addUser">Add</button>
                        <button class="editButton smallbutton" type="submit" form="selectForm" name="editUser" disabled>Edit</button>
                        <button class="deleteButton smallbutton" type="submit" form="selectForm" name="deleteUser" disabled>Delete</button>
                        <button class="activateButton smallbutton" type="submit" form="selectForm" name="activateUser" disabled>Activate</button>
                        <button class="deactivateButton smallbutton" type="submit" form="selectForm" name="deactivateUser" disabled>Deactivate</button>
                        <button class="backButton smallbutton" type="button" onclick="location.href='index.jsp'">Back</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>