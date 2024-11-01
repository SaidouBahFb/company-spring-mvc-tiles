<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div>
    <div class="container mt-5">
        <div class="row">
            <div class="col-sm-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Liste des paniers</h5>
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Date</th>
                                <th>Client</th>
                                <th>Détails</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${paniersList}" var="panier">
                                <tr>
                                    <td>${panier.id}</td>
                                    <td>
                                        <fmt:formatDate value="${panier.date}" pattern="dd-MM-yyyy"/>
                                    </td>
                                    <td>${panier.client.firstName} ${panier.client.lastName}</td>
                                    <td>
                                        <a href="paniers/details/${panier.id}">
                                            Voir
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Ajout de panier</h5>
                        <form action="paniers" method="post">
                            <div class="mb-3">
                                <label for="inputClient" class="form-label">Client</label>
                                <select name="client_id" class="form-control" id="inputClient" required>
                                    <c:forEach items="${clientList}" var="client">
                                        <option value="${client.id}">${client.firstName} ${client.lastName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="inputProduct" class="form-label">Produit(s)</label>
                                <select name="products" multiple class="form-control" id="inputProduct" required>
                                    <c:forEach items="${productList}" var="product">
                                        <option value="${product.ref}">${product.ref} - ${product.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <button type="submit" class="btn btn-primary">Enregistrer</button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>