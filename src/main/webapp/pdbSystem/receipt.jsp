<html>
<head>
    <title>Receipt</title>
</head>
<h1>Checkout</h1>

<h2>Thank you for your purchase!</h2>

<%
  String deliveryType = request.getParameter("deliveryType");
  %>
<body>

<%
  if (deliveryType.equals("delivery")){
    String country = request.getParameter("country");
    String address = request.getParameter("address");
    String city = request.getParameter("city");
    String state = request.getParameter("state");
    String finalAddress = address + ", " + city + ", " + state + ", " + country;
%>

<a>You have selected a delivery to your address</a></br>
<a>  Your order will be delivered to:</a></br>
<%= finalAddress %></br>

<%
  } else if (deliveryType.equals("click")){
    String collectionPoint = request.getParameter("selectedStore");
%>

<a>You have selected a Click&Collect</a></br>
<a>  Your order will be ready for collection at:</a></br>
  <%= collectionPoint %></br>

<%

  } else if (deliveryType.equals("collection")){
    String collectionPoint = request.getParameter("collectionPoint");
%>

<a>You have selected a delivery to a collection point</a></br>
<a>  Your order will be delivered to: </a>
  <%= collectionPoint %></br>

<%
  }
%>
Click <a href="../userHome.jsp">here </a>to proceed to the main page. <br/>
</body>
</html>
