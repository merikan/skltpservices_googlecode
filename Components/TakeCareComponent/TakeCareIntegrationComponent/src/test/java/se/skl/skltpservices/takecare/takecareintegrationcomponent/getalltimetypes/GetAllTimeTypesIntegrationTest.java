package se.skl.skltpservices.takecare.takecareintegrationcomponent.getalltimetypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static se.skl.skltpservices.takecare.takecareintegrationcomponent.TakeCareIntegrationComponentMuleServer.getAddress;
import static se.skl.skltpservices.takecare.takecareintegrationcomponent.getalltimetypes.GetAllTimeTypesTestProducer.TEST_HEALTHCAREFACILITY_INVALID_ID;
import static se.skl.skltpservices.takecare.takecareintegrationcomponent.getalltimetypes.GetAllTimeTypesTestProducer.TEST_HEALTHCAREFACILITY_OK;
import static se.skl.skltpservices.takecare.takecareintegrationcomponent.getalltimetypes.GetAllTimeTypesTestProducer.TEST_ID_FAULT_TIMEOUT;

import javax.xml.ws.soap.SOAPFaultException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.test.AbstractJmsTestUtil;
import org.soitoolkit.commons.mule.test.ActiveMqJmsTestUtil;
import org.soitoolkit.commons.mule.test.junit4.AbstractTestCase;
import org.soitoolkit.refapps.sd.sample.wsdl.v1.Fault;

import se.riv.crm.scheduling_1.GetAllTimeTypesResponseType;

public class GetAllTimeTypesIntegrationTest extends AbstractTestCase {

	private static final Logger log = LoggerFactory.getLogger(GetAllTimeTypesIntegrationTest.class);

	private static final String EXPECTED_ERR_TIMEOUT_MSG = "Read timed out";

	private static final String DEFAULT_SERVICE_ADDRESS = getAddress("GETALLTIMETYPES_INBOUND_URL");

	private static final String ERROR_LOG_QUEUE = "SOITOOLKIT.LOG.ERROR";
	private AbstractJmsTestUtil jmsUtil = null;

	public GetAllTimeTypesIntegrationTest() {

		// Only start up Mule once to make the tests run faster...
		// Set to false if tests interfere with each other when Mule is started
		// only once.
		setDisposeContextPerClass(true);
	}

	protected String getConfigResources() {
		return "soitoolkit-mule-jms-connector-activemq-embedded.xml," +

		"TakeCareIntegrationComponent-common.xml," + "TakeCareIntegrationComponent-integrationtests-common.xml," +
		// FIXME. MULE STUDIO.
		// "services/GetAllTimeTypes-service.xml," +
				"GetAllTimeTypes-service.xml," + "teststub-services/GetAllTimeTypes-teststub-service.xml";
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();

		doSetUpJms();
	}

	private void doSetUpJms() {
		// TODO: Fix lazy init of JMS connection et al so that we can create
		// jmsutil in the declaration
		// (The embedded ActiveMQ queue manager is not yet started by Mule when
		// jmsutil is delcared...)
		if (jmsUtil == null)
			jmsUtil = new ActiveMqJmsTestUtil();

		// Clear queues used for error handling
		jmsUtil.clearQueues(ERROR_LOG_QUEUE);
	}

	@Test
	public void test_ok() throws Fault {
		String healthcareFacility = TEST_HEALTHCAREFACILITY_OK;

		GetAllTimeTypesTestConsumer consumer = new GetAllTimeTypesTestConsumer(DEFAULT_SERVICE_ADDRESS);
		GetAllTimeTypesResponseType response = consumer.callService(healthcareFacility);
		assertEquals("0", response.getListOfTimeTypes().get(0).getTimeTypeId());
		assertEquals("OPEN", response.getListOfTimeTypes().get(0).getTimeTypeName());
	}

	@Test
	public void test_fault_invalidInput() throws Exception {
		try {
			String healthcareFacility = TEST_HEALTHCAREFACILITY_INVALID_ID;

			GetAllTimeTypesTestConsumer consumer = new GetAllTimeTypesTestConsumer(DEFAULT_SERVICE_ADDRESS);
			GetAllTimeTypesResponseType response = consumer.callService(healthcareFacility);

			fail("expected fault, but got a response of type: "
					+ ((response == null) ? "NULL" : response.getClass().getName()));

		} catch (SOAPFaultException e) {

			assertEquals("Invalid status code: 500", e.getMessage());

		}
	}

	@Test
	public void test_fault_timeout() throws Fault {
		try {
			String healthcareFacility = TEST_ID_FAULT_TIMEOUT;

			GetAllTimeTypesTestConsumer consumer = new GetAllTimeTypesTestConsumer(DEFAULT_SERVICE_ADDRESS);
			GetAllTimeTypesResponseType response = consumer.callService(healthcareFacility);

			fail("expected fault, but got a response of type: "
					+ ((response == null) ? "NULL" : response.getClass().getName()));
		} catch (SOAPFaultException e) {
			assertTrue("Unexpected error message: " + e.getMessage(),
					e.getMessage().startsWith(EXPECTED_ERR_TIMEOUT_MSG));
		}

		// Sleep for a short time period to allow the JMS response message to be
		// delivered, otherwise ActiveMQ data store seems to be corrupt
		// afterwards...
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

}
