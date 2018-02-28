/**
 * 
 */
$(document).ready(function() {
	
	var orderID = /*[[${session.orderID}]]*/;
	var lotNo = /*[[${session.lotNo}]]*/;
	
	var action = "/add";
	$('#orderID').val(orderID);
	$('#lotNo').val(lotNo);
	
	$('#Confirm').click(function(e){
		add();
	});
	
	$('#addProduct').submit(
		function(event) {
		
			 
var str = $("#addProduct").serialize();
alert(str);


$.ajax({
	url : "/save",
	data : str,
	type : "POST",
	success : function(response) {
		
		alert("success");
	
		var htmlrow ="<tr>"+
		"<td>" + "</td>"+
		"<td>" + $("#prodName").find(":selected").text() + "</td>"+
		"<td>" + $("#Quantity").val() + "</td>"+
		"<td>" + $("#Color").find(":selected").text() + "</td>"+
		"<td>" + $("#Construction").val() + "</td>"+
		"</tr>";         
        $('#addedProducts').append(htmlrow);
		
        $("#addProduct")[0].reset();
		
	},
	error : function(xhr, status, error) {
		alert("ERROR:"+xhr.responseText);
	}	
});
		})
});

function add(){
	var orderObject = /*[[${session.orderDetails}]]*/;
	orderObject.Price = "124";
	orderObject.Remarks="remarks";
	orderObject.Construction = "consts";
	var list = [];
	 list.push({orderObject : orderObject});
	 
	 var x;
	 var txt;
	 for (x in list) {
	     txt += list[x] + " ";
	 }
	 alert(txt);

	
}
function confirm(){
	alert("confirm");
}


function onProductChange() {
	var prodName=$('#prodName').val();
	alert(prodName);	

	 $.ajax({
		    type: "POST",
		    data:prodName,
		    url: "/getProdType?",
		    success : function(prodType) {
		    	$.each(prodType, function(key, value) {
		    		 
		    	     $('#prodType')
		    	         .append($("<option></option>")
		    	                    .attr("value",key)
		    	                    .text(value)); 
		    	});
	          
	        }
	});
}
