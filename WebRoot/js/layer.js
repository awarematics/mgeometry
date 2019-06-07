function onOpen(evt) { 
            console.log("Connected to WebSocket server."); 
        } 
        function onClose(evt) { 
            console.log("Disconnected"); 
        } 
        function onMessage(evt) { 
            console.log('Retrieved data from server: ' + evt.data); 
        } 
        function onError(evt) { 
            console.log('Error occured: ' + evt.data); 
        }
        window.onbeforeunload = function(){  
            onClose(evt);  
        }      
        
        
        var xmlHttp;
        function createXMLHttpRequest() {
            if (window.ActiveXObject) {
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            else if (window.XMLHttpRequest) {
                xmlHttp = new XMLHttpRequest();
            }
        }
       function createQueryString() {
            var firstName = document.getElementById("submit").value;
            index = firstName.lastIndexOf("\\");
            firstName = firstName.substring(index+1);
            var queryString = "submit=" + firstName ;
            return queryString;
        }

        function doRequestUsingGET() {
            createXMLHttpRequest();
            var queryString = "GetAndPostExample?";
            queryString = queryString + createQueryString()
            + "&timeStamp=" + new Date().getTime();
           // alert(queryString);
            xmlHttp.open("GET", queryString, true);
            xmlHttp.onreadystatechange = handleStateChange;   
            xmlHttp.send();
        }


        function doRequestUsingPOST() {
            createXMLHttpRequest();
            var url = "GetAndPostExample?timeStamp=" + new Date().getTime();
            var queryString = createQueryString();
            xmlHttp.open("POST", url, true);
            xmlHttp.onreadystatechange = handleStateChange;
            xmlHttp.setRequestHeader("Content-Type",
            "application/x-www-form-urlencoded;");
            xmlHttp.send(queryString);
        }
       
        function handleStateChange() {
            if(xmlHttp.readyState == 4) {
                if(xmlHttp.status == 200) {
                parseResults();
                }
                parseResults();
            }
        } 
   
       function parseResults() {
    	   document.getElementById('serverResponse').innerHTML = xmlHttp.responseText;
    	  // var texts = xmlHttp.responseText;
    	 //  dealData(texts);
    	   alsert("llllll");
    	   alert("后台返回的返回值： "+ document.getElementById('serverResponse').innerHTML);  
           var responseDiv = document.getElementById("serverResponse");
           if(responseDiv.hasChildNodes()) {
           responseDiv.removeChild(responseDiv.childNodes[0]);
        }
        var responseText = document.createTextNode(xmlHttp.responseText);
        alert("后台返回的返回值： "+xmlHttp.responseText);  
       // dealData(xmlHttp.responseText);
        responseDiv.appendChild(responseText);
        }
       function dealData(data)
       {
    	   var dataSplit = new Array();
    		dataSplit = data.split("#");
    		for (i = 0; i < rangeSplit.length-1; i++) {
    			var obj = new Array();
    			obj = rangeSplit[i].split("@");
    			var table = document.getElementById("tbdata");
    			var newRow = table.insertRow();
				for(j = 0; j< obj.length-1;j++)
				{
    				var newCell = newRow.insertCell(j);
    				newCell.innerHTML = "<td>" + obj[j] + "</td>";
    			}
    		}
       }