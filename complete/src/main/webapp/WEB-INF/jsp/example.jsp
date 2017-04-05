<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<script src="//cdn.ckeditor.com/4.6.2/standard/ckeditor.js"></script>
<t:templetepage>
	<jsp:attribute name="header">     
    </jsp:attribute>
	<jsp:attribute name="footer">
      
    </jsp:attribute>
	<jsp:body>
	<div>
    	<form>
            <textarea name="editor1" id="editor1" rows="10" cols="80" ">
               
            </textarea>
            <script>
                // Replace the <textarea id="editor1"> with a CKEditor
                // instance, using default configuration.
                CKEDITOR.replace( 'editor1' );
            </script>
        </form>
        </div>
    </jsp:body>
</t:templetepage>