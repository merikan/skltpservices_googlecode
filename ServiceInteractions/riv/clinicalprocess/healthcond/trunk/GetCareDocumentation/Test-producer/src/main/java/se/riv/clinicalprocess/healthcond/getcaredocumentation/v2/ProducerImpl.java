/**
 * Copyright 2009 Sjukvardsradgivningen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public

 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the

 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,

 *   Boston, MA 02111-1307  USA
 */
package se.riv.clinicalprocess.healthcond.getcaredocumentation.v2;

import riv.ehr.patientsummary.getcaredocumentation._2.rivtabp21.GetCareDocumentationResponderInterface;
import riv.ehr.patientsummary.getcaredocumentationresponder._2.GetCareDocumentationResponseType;
import riv.ehr.patientsummary.getcaredocumentationresponder._2.GetCareDocumentationType;

import javax.jws.WebParam;
import javax.jws.WebService;


@WebService(
		serviceName = "GetCareDocumentationResponderService",
        endpointInterface= "se.riv.clinicalprocess.healthcond.getcaredocumentation.v2.rivtabp21.GetCareDocumentationResponderInterface",
        portName = "GetCareDocumentationResponderPort",
		targetNamespace = "urn:riv:clinicalprocess:healthcond:GetCareDocumentation:2:rivtabp21",
		wsdlLocation = "schemas/interactions/GetCareDocumentationInteraction/GetCareDocumentationInteraction_2_RIVTABP21.wsdl")
public class ProducerImpl implements GetCareDocumentationResponderInterface {
	
    @Override
    public GetCareDocumentationResponseType getCareDocumentation(@WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "urn:riv:itintegration:registry:1", header = true) String s, @WebParam(partName = "parameters", name = "GetCareDocumentation", targetNamespace = "urn:riv:ehr:patientsummary:GetCareDocumentationResponder:2") GetCareDocumentationType getCareDocumentationType) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
