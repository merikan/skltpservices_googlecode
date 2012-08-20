
package se.sll.engagemangsindex.integration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "GetLogicalAddresseesByServiceContractResponderService", targetNamespace = "urn:riv:itintegration:registry:GetLogicalAddresseesByServiceContract:1:rivtabp21", wsdlLocation = "file:/Users/Peter/Insync/peter.larsson@callistaenterprise.se/Project/SLL/EI/GetLogicalAddresseesByServiceContractInteraction_1.0_RIVTABP21.wsdl")
public class GetLogicalAddresseesByServiceContractResponderService
    extends Service
{

    private final static URL GETLOGICALADDRESSEESBYSERVICECONTRACTRESPONDERSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(se.sll.engagemangsindex.integration.GetLogicalAddresseesByServiceContractResponderService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = se.sll.engagemangsindex.integration.GetLogicalAddresseesByServiceContractResponderService.class.getResource(".");
            url = new URL(baseUrl, "file:/Users/Peter/Insync/peter.larsson@callistaenterprise.se/Project/SLL/EI/GetLogicalAddresseesByServiceContractInteraction_1.0_RIVTABP21.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/Users/Peter/Insync/peter.larsson@callistaenterprise.se/Project/SLL/EI/GetLogicalAddresseesByServiceContractInteraction_1.0_RIVTABP21.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        GETLOGICALADDRESSEESBYSERVICECONTRACTRESPONDERSERVICE_WSDL_LOCATION = url;
    }

    public GetLogicalAddresseesByServiceContractResponderService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GetLogicalAddresseesByServiceContractResponderService() {
        super(GETLOGICALADDRESSEESBYSERVICECONTRACTRESPONDERSERVICE_WSDL_LOCATION, new QName("urn:riv:itintegration:registry:GetLogicalAddresseesByServiceContract:1:rivtabp21", "GetLogicalAddresseesByServiceContractResponderService"));
    }

    /**
     * 
     * @return
     *     returns GetLogicalAddresseesByServiceContractResponderInterface
     */
    @WebEndpoint(name = "GetLogicalAddresseesByServiceContractResponderPort")
    public GetLogicalAddresseesByServiceContractResponderInterface getGetLogicalAddresseesByServiceContractResponderPort() {
        return super.getPort(new QName("urn:riv:itintegration:registry:GetLogicalAddresseesByServiceContract:1:rivtabp21", "GetLogicalAddresseesByServiceContractResponderPort"), GetLogicalAddresseesByServiceContractResponderInterface.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GetLogicalAddresseesByServiceContractResponderInterface
     */
    @WebEndpoint(name = "GetLogicalAddresseesByServiceContractResponderPort")
    public GetLogicalAddresseesByServiceContractResponderInterface getGetLogicalAddresseesByServiceContractResponderPort(WebServiceFeature... features) {
        return super.getPort(new QName("urn:riv:itintegration:registry:GetLogicalAddresseesByServiceContract:1:rivtabp21", "GetLogicalAddresseesByServiceContractResponderPort"), GetLogicalAddresseesByServiceContractResponderInterface.class, features);
    }

}
