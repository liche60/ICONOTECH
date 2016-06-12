<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<portlet:defineObjects />
<!DOCTYPE html>
<html>
<head>
<title>jQuery jTable Setup in java</title>
<!-- jTable Metro styles. -->
<link href="<%=request.getContextPath()%>/css/metro/blue/jtable.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/jquery-ui.css" rel="stylesheet" type="text/css" />
<!-- jTable script file. -->
<script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery-ui.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.jtable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/localization/jquery.jtable.es.js" type="text/javascript"></script>

<script type="text/javascript">
        $(document).ready(function() {
                $('#StudentTableContainer').jtable({
                        title : 'Lista de Personas',
                        paging: true, //Enable paging
                        pageSize: 5, //Set page size (default: 10)     
                        actions : {
                                listAction : '<%=request.getContextPath()%>/Controller?action=list'
                        },
                        fields : {
	                        	ComparendoPdf: {
	                        	    title: '',
	                        	    width: '3%',
	                        	    sorting: false,
	                        	    edit: false,
	                        	    create: false,
	                        	    display: function (studentData) {
	                        	    	console.info("PersonData: "+studentData.record.name);
	                        	        var $link = $('<a href="#" ><img src="<%=request.getContextPath()%>/css/images/pdf-blue-icon.png" title="Abrir Comparendo" /></a>');
	                        	        return $link;
	                        	    }
	                        	},
                        	
	                        	procesoId : {
                                        title : 'Identificacion',
                                        width : '20%',
                                        key : true,
                                        list : true,
                                        create : true
                                },
                                name : {
                                        title : 'Tipo Identificacion',
                                        width : '30%',
                                        edit : false
                                },
                                department : {
                                        title : 'Nombres',
                                        width : '25%',
                                        edit : true
                                },
                                emailId : {
                                        title : 'Apellidos',
                                        width : '25%',
                                        edit : true
                                }
                        }
                });
                //$('#StudentTableContainer').jtable('load');
                
                //Re-load records when user click 'load records' button.
                $('#LoadRecordsButton').click(function (e) {
                    e.preventDefault();
                    $('#StudentTableContainer').jtable('load', {
                    	studentId: $('#studentId').val()
                    });
                });
         
                //Load all records when page is first shown
                $('#LoadRecordsButton').click();
                
        });
</script>

</head>
<body>

<div class="filtering">
    <form>
    	<div>
    	<label for="studentId" id="lbl_studentId">Comparendo</label>
        <input type="text" name="studentId" id="studentId" />
       </div>
		<div>
        <button type="submit" id="LoadRecordsButton" class="btn btn-primary">
  				<i class="icon-search icon-white"></i> Buscar
		</button>
		</div>
    </form>
</div>

<div style="text-align: center;">
        <div id="StudentTableContainer"></div>
</div>
</body>
</html>