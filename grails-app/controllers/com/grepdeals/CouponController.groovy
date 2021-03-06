package com.grepdeals

import pdf.PdfService

import com.grepdeals.consts.CouponData

class CouponController {
	PdfService pdfService
	
		  def index = { redirect(action: demo) }
	
		  
		  def pdfLink = {
			  try{
				byte[] b
				def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort + grailsAttributes.getApplicationUri(request)
				// TODO: get this working...
				if(params.template){
				  def content = g.render(template:params.template, model:[pdf:params])
				  b = pdfService.buildPdfFromString(content.readAsString(), baseUri)
				}
				if(params.pdfController){
					
				  def content = g.include(controller:params.pdfController, action:params.pdfAction, id:params.pdfId)
				  b = pdfService.buildPdfFromString(content.readAsString(), baseUri)
				}
				else{
				  def url = baseUri + params.url
				  b = pdfService.buildPdf(url)
				}
				response.setContentType("application/pdf")
				response.setHeader("Content-disposition", "attachment; filename=" + (params.filename ?: "document.pdf"))
				response.setContentLength(b.length)
				response.getOutputStream().write(b)
			  }
			  catch (Throwable e) {
				if(params.pdfController) redirect(controller:params.pdfController, action:params.pdfAction, params:params)
				else redirect(uri:params.url + '?' + request.getQueryString())
			  }
			}
		  
	
		  def pdfForm = {
			try{
			  byte[] b
			  def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort + grailsAttributes.getApplicationUri(request)
			  // def baseUri = g.createLink(uri:"/", absolute:"true").toString()
			  if(request.method == "GET") {
				def url = baseUri + params.url + '?' + request.getQueryString()
				b = pdfService.buildPdf(url)
			  }
			  if(request.method == "POST"){
				def content
				if(params.template){
				  content = g.render(template:params.template, model:[pdf:params])
				}
				else{
				  content = g.include(controller:params.pdfController, action:params.pdfAction, id:params.id, pdf:params)
				}
				b = pdfService.buildPdfFromString(content.readAsString(), baseUri)
			  }
			  response.setContentType("application/pdf")
			  response.setHeader("Content-disposition", "attachment; filename=" + (params.filename ?: "document.pdf"))
			  response.setContentLength(b.length)
			  response.getOutputStream().write(b)
			}
			catch (Throwable e) {
			  if(params.template) render(template:params.template)
			  if(params.url) redirect(uri:params.url + '?' + request.getQueryString())
			  else redirect(controller:params.pdfController, action:params.pdfAction, params:params)
			}
		  }
	
	
	
		  def generateCoupon = {
			  //TODO get the coupon data from service call
			  
			  def post = Post.get(params.pdfId);
			  
			  
			  def couponData = new CouponData();
			  couponData.companyName = post.author.business.name
			  couponData.companyAddrLine1=post.author.business.address
			  couponData.companyCity=post.author.business.city
			  couponData.companyState=post.author.business.state
			  couponData.companyPin=post.author.business.areaCode
			  //TODO
			  // Generated coupon id has to be here
			  couponData.couponId="ABDCCoupone123"
			  couponData.customerFirstName=post.author.firstName
			  couponData.customerLastName=post.author.lastName
			  couponData.companyPhone=post.author.phone
			  // TODO
			  // expiration data has to be here
			  //couponData.couponExpirationDate = "12/12/2011"
			  if (post.expiresOn != null){
				  couponData.couponExpirationDate = post.expiresOn.format("MM/dd/yyyy")
			  }
			  
			  couponData.dealDetails = post.title
			  return ['data':couponData]
			
		  }
		  
	
}
