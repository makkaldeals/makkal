package com.grepdeals

import groovy.servlet.TemplateServlet.TemplateCacheEntry;
import pdf.PdfService

import com.grepdeals.consts.CouponData

class CouponController {
	PdfService pdfService
	
		  def index = { redirect(action: demo) }
	
		  
		  def pdfLink = {
			  log.info ("Pdf link ");
			  try{
				Long startTime =  System.currentTimeMillis();
				byte[] b
				def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort + grailsAttributes.getApplicationUri(request)
				// TODO: get this working...
				if(params.template){
					Long templateStartTime =  System.currentTimeMillis();
				  def content = g.render(template:params.template, model:[pdf:params])
				  b = pdfService.buildPdfFromString(content.readAsString(), baseUri)
				  log.info ("time for pdf service-pdf template  " + (System.currentTimeMillis()-templateStartTime)/1000);
				}
				if(params.pdfController){
					Long controllerStartTime =  System.currentTimeMillis();
					
				  def content = g.include(controller:params.pdfController, action:params.pdfAction, id:params.pdfId)
				  b = pdfService.buildPdfFromString(content.readAsString(), baseUri)
				  log.info ("time for pdf service -pdfcontroller " + (System.currentTimeMillis()-controllerStartTime)/1000);
				}
				else{
				Long elseStartTime =  System.currentTimeMillis();
				  def url = baseUri + params.url
				  b = pdfService.buildPdf(url)
				  log.info ("time for pdf service -else " + (System.currentTimeMillis()-elseStartTime)/1000);
				}
		//		println  "Total time for pdf generation " + (System.currentTimeMillis()-startTime)/1000;
				log.info ("Total time for pdf generation before send to browser " + (System.currentTimeMillis()-startTime)/1000);
				response.setContentType("application/pdf")
				response.setHeader("Content-disposition", "attachment; filename=" + (params.filename ?: "document.pdf"))
				response.setContentLength(b.length)
				log.info ("pdf write operation starts...");
				response.getOutputStream().write(b)
			  }
			  catch (Throwable e) {
				if(params.pdfController) redirect(controller:params.pdfController, action:params.pdfAction, params:params)
				else redirect(uri:params.url + '?' + request.getQueryString())
			  }
			}
		  
	
		  def pdfForm = {
			  log.info ("Pdf form ");
			try{
			  byte[] b
			  def baseUri = request.scheme + "://" + request.serverName + ":" + request.serverPort + grailsAttributes.getApplicationUri(request)
			  // def baseUri = g.createLink(uri:"/", absolute:"true").toString()
			  if(request.method == "GET") {
				  Long getStartTime =  System.currentTimeMillis();
				def url = baseUri + params.url + '?' + request.getQueryString()
				b = pdfService.buildPdf(url)
				log.info ("time for pdf get service-pdf " + (System.currentTimeMillis()-getStartTime)/1000);
			  }
			  if(request.method == "POST"){
				def content
				Long postStartTime =  System.currentTimeMillis();
				Long postRenderStartTime =  System.currentTimeMillis();
				if(params.template){
				  content = g.render(template:params.template, model:[pdf:params])
				  log.info ("time for pdf post render template  " + (System.currentTimeMillis()-postRenderStartTime)/1000);
				}
				else{
				  content = g.include(controller:params.pdfController, action:params.pdfAction, id:params.id, pdf:params)
				  log.info ("time for pdf POST service-pdf else render  " + (System.currentTimeMillis()-postRenderStartTime)/1000);
				}
				Long postpdfStartTime =  System.currentTimeMillis();
				
				b = pdfService.buildPdfFromString(content.readAsString(), baseUri)
				log.info ("time for POST pdf service-pdf   " + (System.currentTimeMillis()-postpdfStartTime)/1000);
				log.info ("time for pdf service-pdf template   " + (System.currentTimeMillis()-postStartTime)/1000);
			  }
			  Long reponseStartTime =  System.currentTimeMillis();
			  response.setContentType("application/pdf")
			  response.setHeader("Content-disposition", "attachment; filename=" + (params.filename ?: "document.pdf"))
			  response.setContentLength(b.length)
			  response.getOutputStream().write(b)
			  log.info ("time for pdf POST response write into brower" + (System.currentTimeMillis()-reponseStartTime)/1000);
			}
			catch (Throwable e) {
			  if(params.template) render(template:params.template)
			  if(params.url) redirect(uri:params.url + '?' + request.getQueryString())
			  else redirect(controller:params.pdfController, action:params.pdfAction, params:params)
			}
		  }
	
	
	
		  def generateCoupon = {
			  //TODO get the coupon data from service call
			  log.info ("populate generate coupon data object");
			  def post = Post.get(params.pdfId);
			  log.info ("get values for populate generate coupon data object");
			  
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
