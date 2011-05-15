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
				// def baseUri = g.createLink(uri:"/", absolute:"true").toString()
				// TODO: get this working...
				if(params.template){
				  println "*******************Template: $params.template"
				  def content = g.render(template:params.template, model:[pdf:params])
				  b = pdfService.buildPdfFromString(content.readAsString(), baseUri)
				}
				if(params.pdfController){
					
					println "Controller --------------- "
				  //println "GSP - Controller: $params.pdfController , Action: $params.pdfAction, Id: $params.pdfId"
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
				println "there was a problem with PDF generation ${e}"
				//if(params.template) render(template:params.template)
				if(params.pdfController) redirect(controller:params.pdfController, action:params.pdfAction, params:params)
				else redirect(uri:params.url + '?' + request.getQueryString())
			  }
			}
		  
	/*	  def pdfLink = {
			try{
			  byte[] b
			  def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort + grailsAttributes.getApplicationUri(request)
			  // def baseUri = g.createLink(uri:"/", absolute:"true").toString()
			  // TODO: get this working...
			  //if(params.template){
				//println "Template: $params.template"
				//def content = g.render(template:params.template, model:[pdf:params])
				//b = pdfService.buildPdfFromString(content.readAsString(), baseUri)
			  //}
			  if(params.pdfController){
				//println "GSP - Controller: $params.pdfController , Action: $params.pdfAction, Id: $params.pdfId"
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
			  println "there was a problem with PDF generation ${e}"
			  //if(params.template) render(template:params.template)
			  if(params.pdfController) redirect(controller:params.pdfController, action:params.pdfAction, params:params)
			  else redirect(uri:params.url + '?' + request.getQueryString())
			}
		  }
	*/
		  def pdfForm = {
			try{
			  byte[] b
			  def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort + grailsAttributes.getApplicationUri(request)
			  // def baseUri = g.createLink(uri:"/", absolute:"true").toString()
			  if(request.method == "GET") {
				def url = baseUri + params.url + '?' + request.getQueryString()
				//println "BaseUri is $baseUri"
				//println "Fetching url $url"
				b = pdfService.buildPdf(url)
			  }
			  if(request.method == "POST"){
				def content
				if(params.template){
				  //println "Template: $params.template"
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
			  println "there was a problem with PDF generation ${e}"
			  if(params.template) render(template:params.template)
			  if(params.url) redirect(uri:params.url + '?' + request.getQueryString())
			  else redirect(controller:params.pdfController, action:params.pdfAction, params:params)
			}
		  }
	
	
	
		  def generateCoupon = {
			  //TODO get the coupon data from service call
			  def couponData = new CouponData();
			  couponData.companyName = "DealCompany"
			  couponData.companyAddrLine1="ABC apartment"
			  couponData.companyAddrLine2= "#232"
			  couponData.companyCity="Omaha"
			  couponData.companyState="NE"
			  couponData.companyPin="68117"
			  couponData.couponId="ABDCCoupone123"
			  couponData.customerFirstName="Gang of "
			  couponData.customerLastName="Five"
			  couponData.companyPhone="123-123-4345"
			  couponData.couponExpirationDate = "12/12/2011"
			  return ['data':couponData]
			
		  }
		  
	
}
