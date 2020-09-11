<%@page import="java.util.List"%>
<%@page import="java.util.Random"%>
<%@page import="entity.ProductDetail"%>
<%@page import="entity.Product"%>
<%
    session.setAttribute("view", "/product");
    Product selectedProduct = (Product) session.getAttribute("selectedProduct");
    ProductDetail selectedProductDetail = (ProductDetail) session.getAttribute("selectedProductDetail");
%>
<div id="container">
    <div class="one">
        <div class="heading_bg">
            <h2>
                <%=selectedProduct.getName()%>
            </h2>
        </div>
    </div>
    <div class="one-half">
        <div id="amazingslider-wrapper-1"
             style="display:block;position:relative;max-width:450px;margin:0px auto 98px;">
            
            <br/>
            <div id="amazingslider-1" style="display:block;position:relative;margin:0 auto;">
                <ul class="amazingslider-slides" style="display:block">
                    <%
                        Random generator = new Random();
                        List<String> arr = selectedProductDetail.getAllImages();
                        int a = arr.size();
                        int r = generator.nextInt(a);
                        String img = arr.get(r);
                        
                    %>
                    <li><img src="images/<%=img%>" alt="" title="" /></li>
                </ul>
                <br/>
                <ul class="amazingslider-thumbnails" style="display: inline-block">
                    <%
                        for (String im : arr) {
                    %>
                    <li style="float: left"><img src="images/tn-<%=im%>" alt="" title="" style="width: 70px;"/></li>
                        <%
                            }
                        %>
                </ul>
            </div>
        </div>
    </div>
    <div class="one-half last">
        <ul id="tabify_menu" class="menu_tab" style="margin: 0;">
            <li><a href="#fane1">Product Details</a></li>
        </ul>
        <div id="fane1" class="tab_content">
            <h3>Technical Details</h3>
            <p>
                <%=selectedProductDetail.getInformation()%>
            </p>
            <h3>Accessories</h3>
            <p><%=selectedProductDetail.getAccessories()%></p>
            <h3>Warranty Strategy</h3>
            <p><%=selectedProductDetail.getGuaranty()%></p>
            <h3>Price</h3>
            <p><%=selectedProduct.getPrice()%> $</p>
            <p style="text-align:left; margin-right: 16px">
                <a href="<c:url value='addToCart?${selectedProduct.getProductId()}'/>" class="button">Add to cart</a>
            </p>
        </div>
    </div>
    <div style="clear:both; height: 40px"></div>
</div>