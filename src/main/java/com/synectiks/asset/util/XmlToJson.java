package com.synectiks.asset.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class XmlToJson {
	
	public static void main(String a[]) throws JsonMappingException, JsonProcessingException {
		final String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \r\n"
				+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\r\n"
				+ "    <soapenv:Body>\r\n"
				+ "        <getApprovedPortChangeRequestsResponse xmlns=\"http://transferobjects.ipms.tcf.org.nz\">\r\n"
				+ "            <return xmlns=\"\">\r\n"
				+ "                <success>true</success>\r\n"
				+ "                <approvedPortChangeRequests>\r\n"
				+ "                    <lspOverride>false</lspOverride>\r\n"
				+ "                    <numbers>\r\n"
				+ "                        <complete>false</complete>\r\n"
				+ "                        <gainingCarrierId>11647</gainingCarrierId>\r\n"
				+ "                        <losingCarrierId>11647</losingCarrierId>\r\n"
				+ "                        <notRequired>false</notRequired>\r\n"
				+ "                        <phoneNumber>\r\n"
				+ "                            <phoneNumber>0278893603</phoneNumber>\r\n"
				+ "                        </phoneNumber>\r\n"
				+ "                    </numbers>\r\n"
				+ "                    <rfsDateTimeStart>2022-09-12T08:00:00+12:00</rfsDateTimeStart>\r\n"
				+ "                    <som>8602045</som>\r\n"
				+ "                    <category>Simple</category>\r\n"
				+ "                    <requestDateTime>2022-08-17T14:09:40+12:00</requestDateTime>\r\n"
				+ "                    <requesterServiceProviderId>152</requesterServiceProviderId>\r\n"
				+ "                    <responderServiceProviderId>2</responderServiceProviderId>\r\n"
				+ "                    <status>\r\n"
				+ "                        <status>Accepted</status>\r\n"
				+ "                    </status>\r\n"
				+ "                    <version>2</version>\r\n"
				+ "                </approvedPortChangeRequests>\r\n"
				+ "                <approvedPortChangeRequests>\r\n"
				+ "                    <lspOverride>false</lspOverride>\r\n"
				+ "                    <numbers>\r\n"
				+ "                        <complete>false</complete>\r\n"
				+ "                        <gainingCarrierId>11937</gainingCarrierId>\r\n"
				+ "                        <losingCarrierId>11647</losingCarrierId>\r\n"
				+ "                        <notRequired>false</notRequired>\r\n"
				+ "                        <phoneNumber>\r\n"
				+ "                            <phoneNumber>0277004880</phoneNumber>\r\n"
				+ "                        </phoneNumber>\r\n"
				+ "                    </numbers>\r\n"
				+ "                    <rfsDateTimeStart>2022-09-05T08:00:00+12:00</rfsDateTimeStart>\r\n"
				+ "                    <som>8602960</som>\r\n"
				+ "                    <category>Simple</category>\r\n"
				+ "                    <requestDateTime>2022-08-16T13:54:51+12:00</requestDateTime>\r\n"
				+ "                    <requesterServiceProviderId>5</requesterServiceProviderId>\r\n"
				+ "                    <responderServiceProviderId>152</responderServiceProviderId>\r\n"
				+ "                    <status>\r\n"
				+ "                        <status>Accepted</status>\r\n"
				+ "                    </status>\r\n"
				+ "                    <version>2</version>\r\n"
				+ "                </approvedPortChangeRequests>\r\n"
				+ "                <approvedPortChangeRequests>\r\n"
				+ "                    <lspOverride>false</lspOverride>\r\n"
				+ "                    <numbers>\r\n"
				+ "                        <complete>false</complete>\r\n"
				+ "                        <gainingCarrierId>11937</gainingCarrierId>\r\n"
				+ "                        <losingCarrierId>11647</losingCarrierId>\r\n"
				+ "                        <notRequired>false</notRequired>\r\n"
				+ "                        <phoneNumber>\r\n"
				+ "                            <phoneNumber>0277004985</phoneNumber>\r\n"
				+ "                        </phoneNumber>\r\n"
				+ "                    </numbers>\r\n"
				+ "                    <rfsDateTimeStart>2022-09-05T08:00:00+12:00</rfsDateTimeStart>\r\n"
				+ "                    <som>8602963</som>\r\n"
				+ "                    <category>Simple</category>\r\n"
				+ "                    <requestDateTime>2022-08-16T13:55:18+12:00</requestDateTime>\r\n"
				+ "                    <requesterServiceProviderId>5</requesterServiceProviderId>\r\n"
				+ "                    <responderServiceProviderId>152</responderServiceProviderId>\r\n"
				+ "                    <status>\r\n"
				+ "                        <status>Accepted</status>\r\n"
				+ "                    </status>\r\n"
				+ "                    <version>2</version>\r\n"
				+ "                </approvedPortChangeRequests>\r\n"
				+ "                <approvedPortChangeRequests>\r\n"
				+ "                    <lspOverride>false</lspOverride>\r\n"
				+ "                    <numbers>\r\n"
				+ "                        <complete>false</complete>\r\n"
				+ "                        <gainingCarrierId>11937</gainingCarrierId>\r\n"
				+ "                        <losingCarrierId>11647</losingCarrierId>\r\n"
				+ "                        <notRequired>false</notRequired>\r\n"
				+ "                        <phoneNumber>\r\n"
				+ "                            <phoneNumber>0272747445</phoneNumber>\r\n"
				+ "                        </phoneNumber>\r\n"
				+ "                    </numbers>\r\n"
				+ "                    <rfsDateTimeStart>2022-08-17T11:45:00+12:00</rfsDateTimeStart>\r\n"
				+ "                    <som>8604975</som>\r\n"
				+ "                    <category>Simple</category>\r\n"
				+ "                    <requestDateTime>2022-08-17T10:31:17+12:00</requestDateTime>\r\n"
				+ "                    <requesterServiceProviderId>5</requesterServiceProviderId>\r\n"
				+ "                    <responderServiceProviderId>152</responderServiceProviderId>\r\n"
				+ "                    <status>\r\n"
				+ "                        <status>Accepted</status>\r\n"
				+ "                    </status>\r\n"
				+ "                    <version>2</version>\r\n"
				+ "                </approvedPortChangeRequests>\r\n"
				+ "                <approvedPortChangeRequests>\r\n"
				+ "                    <lspOverride>false</lspOverride>\r\n"
				+ "                    <numbers>\r\n"
				+ "                        <complete>false</complete>\r\n"
				+ "                        <gainingCarrierId>11647</gainingCarrierId>\r\n"
				+ "                        <losingCarrierId>11647</losingCarrierId>\r\n"
				+ "                        <notRequired>false</notRequired>\r\n"
				+ "                        <phoneNumber>\r\n"
				+ "                            <phoneNumber>0274759236</phoneNumber>\r\n"
				+ "                        </phoneNumber>\r\n"
				+ "                    </numbers>\r\n"
				+ "                    <rfsDateTimeStart>2022-08-22T10:00:00+12:00</rfsDateTimeStart>\r\n"
				+ "                    <som>8602047</som>\r\n"
				+ "                    <category>Simple</category>\r\n"
				+ "                    <requestDateTime>2022-08-17T14:09:19+12:00</requestDateTime>\r\n"
				+ "                    <requesterServiceProviderId>152</requesterServiceProviderId>\r\n"
				+ "                    <responderServiceProviderId>2</responderServiceProviderId>\r\n"
				+ "                    <status>\r\n"
				+ "                        <status>Accepted</status>\r\n"
				+ "                    </status>\r\n"
				+ "                    <version>2</version>\r\n"
				+ "                </approvedPortChangeRequests>\r\n"
				+ "            </return>\r\n"
				+ "        </getApprovedPortChangeRequestsResponse>\r\n"
				+ "    </soapenv:Body>\r\n"
				+ "</soapenv:Envelope>\r\n"
				+ "";
//		Document doc = convertStringToDocument(xmlStr);
//		String str = convertDocumentToString(doc);
		
		JSONObject json=XML.toJSONObject(xmlStr); 
	    JSONArray x = json.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body")
	    		.getJSONObject("getApprovedPortChangeRequestsResponse").getJSONObject("return")
	    		.getJSONArray("approvedPortChangeRequests");
	    Iterator<Object> itr = x.iterator();
	    while(itr.hasNext()) {
	    	JSONObject obj = (JSONObject)itr.next();
	    	String ph = obj.getJSONObject("numbers").getJSONObject("phoneNumber").getString("phoneNumber");
	    	System.out.println(ph); 
	    }
	    
	}
	
	 private static Document convertStringToDocument(String xmlStr) {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder builder;  
	        try  
	        {  
	            builder = factory.newDocumentBuilder();  
	            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
	            return doc;
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	        return null;
	    }
	 
	 private static String convertDocumentToString(Document doc) {
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer;
	        try {
	            transformer = tf.newTransformer();
	            // below code to remove XML declaration
	            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	            StringWriter writer = new StringWriter();
	            transformer.transform(new DOMSource(doc), new StreamResult(writer));
	            String output = writer.getBuffer().toString();
	            return output;
	        } catch (TransformerException e) {
	            e.printStackTrace();
	        }
	        
	        return null;
	    }
}
