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
package se.riv.se_apotekensservice.or.hamtaaktuellaordinationer.v1;

import javax.jws.WebService;

import org.w3.wsaddressing10.AttributedURIType;

import se.riv.se_apotekensservice.or.hamtaaktuellaordinationer.v1.HamtaAktuellaOrdinationerResponderInterface;
import se.riv.se_apotekensservice.or.hamtaaktuellaordinationer.v1.HamtaAktuellaOrdinationerResponseType;
import se.riv.se_apotekensservice.or.hamtaaktuellaordinationer.v1.HamtaAktuellaOrdinationerType;

@WebService(
		serviceName = "HamtaAktuellaOrdinationerResponderService", 
		endpointInterface="se.riv.se_apotekensservice.or.hamtaaktuellaordinationer.v1.HamtaAktuellaOrdinationerResponderInterface", 
		portName = "HamtaAktuellaOrdinationerResponderPort", 
		targetNamespace = "urn:riv:se_apotekensservice:or:HamtaAktuellaOrdinationer:1:rivtabp20",
		wsdlLocation = "schemas/interactions/HamtaAktuellaOrdinationerInteraction/HamtaAktuellaOrdinationerInteraction_1.0_rivtabp20.wsdl")
public class ProducerImpl implements HamtaAktuellaOrdinationerResponderInterface {

	public HamtaAktuellaOrdinationerResponseType hamtaAktuellaOrdinationer(final AttributedURIType logicalAddress, final HamtaAktuellaOrdinationerType parameters) {
		HamtaAktuellaOrdinationerResponseType response = new HamtaAktuellaOrdinationerResponseType();
		return response;
	}
}
