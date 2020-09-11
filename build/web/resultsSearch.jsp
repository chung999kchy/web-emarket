<%-- 
    Document   : category
    Created on : Aug 12, 2020, 9:50:52 PM
    Author     : CHUNG
--%>

<%@page import="entity.Category" %>
<%@page import="java.util.List" %>
<%@page import="entity.Product" %>

<%
    session.setAttribute("view", "/resultsSearch");
    List <Product> products =(List<Product>) session.getAttribute("result-search");
    String key = (String)session.getAttribute("key-search");
%>

<div id="container" style="height: 100%">
    <div class="one">
        <div class="heading_bg">
            <h2>Result search for "<%=key%>" </h2>                    
        </div>
    </div>
    <div id="portfolio">
        <div class="portfolio-container" id="columns">
            <ul>
                <%
                    
                    for (Product p : products) {

                %>
                <li class="one-third">
                    <p>
                        <a title="<%=p.getName()%>" href="product?<%=p.getProductId()%>" class="portfolio-item-preview">
                            <img src="img/demo/<%=p.getImage()%>" alt="" width="210px" height="145px" class="portfolio-img pretty-box">
                        </a>
                    </p>
                    <h4>
                        <a href="product?<%=p.getProductId()%>"><%=p.getName()%></a>
                    </h4>
                    <p><%=p.getDescription()%></p>
                    <p style="text-align: left">
                        <a href="product?<%=p.getProductId()%>" class="button_small white"> See Details & raquo; </a>
                    </p>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</div>
<div style="clear: both;"></div>
