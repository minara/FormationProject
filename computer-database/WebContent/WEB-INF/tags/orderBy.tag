<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags"%>
<%@ attribute name="call" required="true"%>
<%@ attribute name="type" required="true"%>
<%@ attribute name="order" required="true"%>
<%@ attribute name="asc" required="true"%>
<%@ attribute name="search"%>
<%@ attribute name="searchDomain"%>
<%@ attribute name="page"%>
<%@ attribute name="limit"%>
<%@ attribute name="delete"%>
<%@ attribute name="computerId"%>
<%@ attribute name="title"%>
<%@ attribute name="style"%>

<p:link call="${call}" order="${type}" asc="${type==order? !asc : true}" 
img="${type==order?  (asc=='true' ? 'image/downnoir.jpg':'image/upnoir.jpg') : 'image/downgris.jpg'}"
search="${search}" searchDomain="${searchDomain}" page="${page }" limit="${limit}" delete="${delete }" title="${title}" style="${style}"
/>