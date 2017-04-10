<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:templetepage>
	<jsp:attribute name="header">     
    </jsp:attribute>
	<jsp:attribute name="footer">
    </jsp:attribute>
	<jsp:body>
	${home.content}
	<ul>
<li class="rmItem rmLast">
													<a
										href="<c:url value="/edit-webpage-${home.page_id}"/>">
													<span class="rmText">Edit</span>
													</a>
												</li>

</ul>
    
	</jsp:body>

</t:templetepage>