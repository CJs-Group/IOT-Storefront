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
        <form action="/itemManip" method="POST">
            <input type="hidden" name="formAction" value="addItem" />

            <label for="name">Name:</label><br />
            <input required id="name" name="name" type="text" />
            <br />

            <label>Description:</label><br />
            <textarea id="description" name="description"></textarea>
            <br />

            <label>Price:</label><br />
            <input required id="price" name="price" type="number" value="1" min="1" step="1" />
            <br />

            <label>Type:</label><br />
            <input required type="radio" id="networking" name="type" value="Networking">
            <label for="networking">Networking</label><br />
            <input type="radio" id="security" name="type" value="Security">
            <label for="security">Security</label><br />
            <input type="radio" id="smarthome" name="type" value="Smart_home">
            <label for="smarthome">Smart Home</label><br />
            <input type="radio" id="assistants" name="type" value="Assistants">
            <label for="assistants">Assistants</label><br />

            <button type="submit">Add Item</button>
        </form>
        <div><%=session.getAttribute("formError") == null ? "" : session.getAttribute("formError")%></div>
    </body>
</html>