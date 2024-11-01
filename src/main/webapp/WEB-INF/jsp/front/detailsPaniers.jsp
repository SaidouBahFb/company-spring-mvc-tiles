<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div>
    <div class="container mt-5 d-flex justify-content-center" >
        <div class="row w-50">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title mb-3">Détails du panier</h5>
                        <p><span class="fw-bold">Id:</span> ${panier.id}</p>
                        <p><span class="fw-bold">Date de création:</span>
                            <fmt:formatDate value="${panier.date}" pattern="dd-MM-yyyy"/>
                        </p>
                        <p><span class="fw-bold">Client:</span> ${panier.client.firstName} ${panier.client.lastName}</p>
                        <p><span class="fw-bold">Produits:</span> </p>
                        <ul>
                            <c:forEach items="${panier.products}" var="product">
                                <li>${product.name}</li>
                            </c:forEach>
                        </ul>
                        <p class="text-end mt-5" ><a href="/companyspringmvctiles/paniers">Retour à la liste</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>