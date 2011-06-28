<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Grep Deals Coupon</title>
    <style>
      .coupon_page_layout {
        width:100%;
        
      }
      
      .coupon_layout {
        width:60%;
        margin-left:20%; margin-right:20%;
        border-style:dotted;
		border-color:black black;
        align: center
      }
      
      .sample_table th {
        text-align:center;
        text-decoration:underline;
      }
      .sample_table td {
        padding:7px;
        background-color: #fff;
      }
      .small_text {
        font-size:8pt;
      }
      
      .rounded-corners
		{
		  background-color:lightgrey;
		  width:250px;
		  height:125px;
		  padding:10px;
		
		  border-radius: 10px 10px 10px 10px;
		  -ms-border-radius: 10px 10px 10px 10px;
		  -moz-border-radius: 10px 10px 10px 10px;
		  -webkit-border-radius: 10px 10px 10px 10px;
		  -khtml-border-radius: 10px 10px 10px 10px;
		}
		
		
		.darkshadow
		{
		   background-color: #ddd;
		   width: 300px;
		   padding: 10px;
		   
		   box-shadow: 10px 10px 15px #000;
		   -webkit-box-shadow: 10px 10px 15px #000;
		   -moz-box-shadow: 10px 10px 15px #000;
		}
		
		.lightshadow
		{  
		   background-color: #999;
		   width: 200px;
		   padding: 10px;
		   color: #fff;
		   
		   box-shadow: 4px 4px 2px #ffc;
		   -webkit-box-shadow: 4px 4px 2px #ffc;
		   -moz-box-shadow: 4px 4px 2px #ffc;
		} 
		
		.innershadow
		{  
		   background-color: #fff;
		   width: 200px;
		   padding: 10px;
		
		   -moz-box-shadow: inset 0 0 1em gold;
		   -webkit-box-shadow: inset 0 0 1em gold;
		   box-shadow: inset 0 0 1em gold;
		}
		
		.terms-condition
		{  
		   font-size:6pt;
   
		} 
		.expiredate_text{
			font:6px arial,sans-serif;
        	color:red;
		}
		.deal_text {
			font:bold 12px arial,sans-serif;
        	color:blue;
      	}
      	.address_text{
      		font:8px arial,sans-serif;
        	color:black;
        	align:left;
      	}
    </style>
  </head>
  <body>
    <h3>Grep Deals Coupon</h3>
    <form>
    
    <table class="coupon_page_layout" >
      <tbody>
        <tr>
			<td>
        		<table class="coupon_layout">
	        	 	<tr>
			        	<td align="left" width="10%" valign="top">
			        		<img src="/grepdeals/images/410/GD.jpg" alt="smallsample" title="smallsample" height="55px" width="125px"/>
			    		</td>
			    		
		    			<td width="60%"> 
		    				<table>
		    					
		    					<tr>
		    						<td class="deal_text">
		        						${data.dealDetails}
		        					</td>
		    						
		    					</tr>
		    				</table>
		    				
		    			</td>
		    			<td width="20%"></td>
			          <td align="right"><img src="/grepdeals/images/410/gun.jpg" alt="gun" title="gun" height="55px" width="80px"/></td>
			        </tr>    	
		        	
			        <tr>
			          <td colspan="3" class="small_text" >
			          Redeem at: 
			          
			          </td>
			       </tr>
			       <tr>
			       	  
			          <td colspan="2" class="address_text" >
			          ${data.companyName}, 
			          &#160; 
			          ${data.companyAddrLine1}
			          &#160; 
			          ${data.companyCity},
			          &#160; 
			          ${data.companyState} 
			          </td>
			          <td></td>
			        </tr>

			       <tr>
			       	  
			          <td colspan="2" class="address_text">
			          Phone : ${data.companyPhone} 
			          </td>
			          <td></td>	
			          <td  class="expiredate_text" align="left" wrap="soft">Expires on ${data.couponExpirationDate}
		    						</td>
			        </tr>

			         <!-- 
			        <tr>
			        <td width="40%"></td>
			        <td width="40%"></td>
			          <td align="right"><img src="/grepdeals/images/410/gun.jpg" alt="gun" title="gun" height="40px" width="80px"/></td>
			        </tr>
			         -->
			        
		        </table>
	        </td>
        </tr>
        <tr class="terms-condition">
        	<td>
        	The coupon may not be sold or auctioned or reproduced. Consumer may have to pay all taxes or other charges if applicable
        	</td>
        </tr>
      </tbody>
    </table>
    
    
    
</form>    
 
  </body>
</html>
