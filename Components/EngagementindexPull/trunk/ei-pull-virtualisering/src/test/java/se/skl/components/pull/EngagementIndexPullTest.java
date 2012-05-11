package se.skl.components.pull;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mule.util.StringUtils;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import riv.itintegration.engagementindex._1.EngagementType;
import riv.itintegration.engagementindex._1.RegisteredResidentEngagementType;
import se.riv.itintegration.engagementindex.getupdates.v1.rivtabp21.GetUpdatesResponderInterface;
import se.riv.itintegration.engagementindex.getupdatesresponder.v1.GetUpdatesResponseType;
import se.riv.itintegration.engagementindex.getupdatesresponder.v1.GetUpdatesType;
import se.riv.itintegration.engagementindex.update.v1.rivtabp21.UpdateResponderInterface;
import se.riv.itintegration.engagementindex.updateresponder.v1.UpdateType;
import se.riv.itintegration.registry.getlogicaladdresseesbyservicecontract.v1.rivtabp21.GetLogicalAddresseesByServiceContractResponderInterface;
import se.riv.itintegration.registry.getlogicaladdresseesbyservicecontractresponder.v1.GetLogicalAddresseesByServiceContractResponseType;
import se.riv.itintegration.registry.getlogicaladdresseesbyservicecontractresponder.v1.GetLogicalAddresseesByServiceContractType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * Author: Henrik Rostam
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ PropertyResolver.class, DateHelper.class })
public class EngagementIndexPullTest {

    private final String namespacePropertyKey = "ei.push.service.contract.namespace";
    private final String updateDestinationProperty = "ei.push.update.destination";
    private final String consumerHsaIdPropertyKey = "ei.push.service.consumer.hsaid";
    private final String addressServicePropertyKey = "ei.address.service";
    private final String engagementIndexPropertyKey = "ei.push.service.domain.list";
    private final String timeOffsetPropertyKey = "ei.push.time.offset";
    private final String dateFormat = "yyyyMMddHHmmss";

    @Mock
    private GetLogicalAddresseesByServiceContractResponderInterface getAddressesClient;

    @Mock
    private GetUpdatesResponderInterface getUpdatesClient;

    @Mock
    private UpdateResponderInterface updateClient;

    @InjectMocks
    private EngagementIndexPull engagementIndexPull = new EngagementIndexPull();

    @Before
    public void initTests() throws ParseException {
        mockStatic(PropertyResolver.class);
        mockStatic(DateHelper.class);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        final String serviceDomain = "mockito:test:namespace";
        final String consumerHsaId = "Mock170Test1D";
        final String pushAddress = "Mock170T35t4ddr3ss";
        final String serviceDomain1 = "Mockito";
        final String serviceDomain2 = "Testing";
        final String serviceDomain3 = "Rocks";
        final String serviceDomainList = serviceDomain1 + ", " + serviceDomain2 + ", " + serviceDomain3;
        final String timeOffset = "-123";
        final String engagementBookingId = "bookingId";
        final String engagementCategorization = "Booking";
        final Date testDate = simpleDateFormat.parse("20120505150000");
        final String engagementOwner = "HSA-id";
        final String engagementLogicalAddress = "Landstingets hsaid:Vårdgivarens HSA-id:Enhetens hsaid";
        final String engagementSourceSystem = "Systemets HSA-ID";
        final String patientSsn = "012345678901";
        final List<String> testAddresses = new LinkedList<String>();
        testAddresses.add(serviceDomain1);
        testAddresses.add(serviceDomain2);
        testAddresses.add(serviceDomain3);

        when(PropertyResolver.get(eq(namespacePropertyKey))).thenReturn(serviceDomain);
        when(PropertyResolver.get(eq(consumerHsaIdPropertyKey))).thenReturn(consumerHsaId);
        when(PropertyResolver.get(eq(addressServicePropertyKey))).thenReturn(pushAddress);
        when(PropertyResolver.get(eq(engagementIndexPropertyKey))).thenReturn(serviceDomainList);
        when(PropertyResolver.get(eq(timeOffsetPropertyKey))).thenReturn(timeOffset);
        when(DateHelper.now()).thenReturn(testDate);

        EngagementType engagement = new EngagementType();
        engagement.setBusinessObjectInstanceIdentifier(engagementBookingId);
        engagement.setCategorization(engagementCategorization);
        engagement.setClinicalProcessInterestId(UUID.randomUUID().toString());
        engagement.setCreationTime(simpleDateFormat.format(testDate));
        engagement.setLogicalAddress(engagementLogicalAddress);
        engagement.setMostRecentContent(simpleDateFormat.format(testDate));
        engagement.setOwner(engagementOwner);
        engagement.setRegisteredResidentIdentification(patientSsn);
        engagement.setServiceDomain(serviceDomain);
        engagement.setSourceSystem(engagementSourceSystem);
        engagement.setUpdateTime(simpleDateFormat.format(testDate));

        RegisteredResidentEngagementType registeredResidentEngagementType = new RegisteredResidentEngagementType();
        registeredResidentEngagementType.setRegisteredResidentIdentification(patientSsn);
        registeredResidentEngagementType.getEngagement().add(engagement);

        GetUpdatesResponseType getUpdatesResponseType = new GetUpdatesResponseType();
        getUpdatesResponseType.getRegisteredResidentEngagement().add(registeredResidentEngagementType);
        getUpdatesResponseType.setResponseIsComplete(true);

        GetLogicalAddresseesByServiceContractResponseType addressResponse = new GetLogicalAddresseesByServiceContractResponseType();
        addressResponse.getLogicalAddress().addAll(testAddresses);

        when(getUpdatesClient.getUpdates(anyString(), any(GetUpdatesType.class))).thenReturn(getUpdatesResponseType);
        when(getAddressesClient.getLogicalAddresseesByServiceContract(anyString(), any(GetLogicalAddresseesByServiceContractType.class))).thenReturn(addressResponse);
    }

    @Test
    public void testOneAddressFetchCall() {
        // Test
        engagementIndexPull.doFetchUpdates();
        // Verify
        verify(getAddressesClient, times(1)).getLogicalAddresseesByServiceContract(anyString(), any(GetLogicalAddresseesByServiceContractType.class));
    }

    @Test
    public void testAddressCorrectAddressCall() {
        // Setup
        ArgumentCaptor<String> addressServiceLogicalAddressCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<GetLogicalAddresseesByServiceContractType> getLogicalAddresseesByServiceContractTypeArgumentCaptor = ArgumentCaptor.forClass(GetLogicalAddresseesByServiceContractType.class);
        // Test
        engagementIndexPull.doFetchUpdates();
        // Verify
        verifyStatic(times(1));
        PropertyResolver.get(eq(addressServicePropertyKey));
        verifyStatic(times(1));
        PropertyResolver.get(eq(namespacePropertyKey));
        verify(getAddressesClient, times(1)).getLogicalAddresseesByServiceContract(addressServiceLogicalAddressCaptor.capture(), getLogicalAddresseesByServiceContractTypeArgumentCaptor.capture());
        assertEquals("The contacted address did not match the expected one!", PropertyResolver.get(addressServicePropertyKey), addressServiceLogicalAddressCaptor.getValue());
        String contactedNameSpace = getLogicalAddresseesByServiceContractTypeArgumentCaptor.getValue().getServiceContractNameSpace().getServiceContractNamespace();
        assertEquals("The contacted namespace did not match the expected one!", PropertyResolver.get(namespacePropertyKey), contactedNameSpace);
    }

    @Test
    public void testPushThenPull() {
        // Setup
        InOrder inOrder = inOrder(getUpdatesClient, updateClient);
        // Test
        engagementIndexPull.doFetchUpdates();
        // Verify
        inOrder.verify(getUpdatesClient).getUpdates(anyString(), any(GetUpdatesType.class));
        inOrder.verify(updateClient).update(anyString(), any(UpdateType.class));
    }

    @Test
    public void testAddressServiceCall() {
        // Setup
        ArgumentCaptor<String> engagementIndexLogicalAddressCaptor = ArgumentCaptor.forClass(String.class);
        // Test
        engagementIndexPull.doFetchUpdates();
        // Verify
        verifyStatic(times(1));
        PropertyResolver.get(eq(consumerHsaIdPropertyKey));
        verify(updateClient, times(1)).update(engagementIndexLogicalAddressCaptor.capture(), any(UpdateType.class));
        assertEquals("The contacted address did not match the expected one!", PropertyResolver.get(engagementIndexPropertyKey), engagementIndexLogicalAddressCaptor.getValue());
    }

    @Test
    public void testTimeOffset() throws ParseException {
        // Setup
        Date testDate = DateHelper.now();
        int timeOffset = -NumberUtils.toInt(PropertyResolver.get(timeOffsetPropertyKey));
        String expectedDate = EngagementIndexHelper.getFormattedOffsetTime(testDate, timeOffset, dateFormat);
        ArgumentCaptor<GetUpdatesType> getUpdatesTypeArgumentCaptor = ArgumentCaptor.forClass(GetUpdatesType.class);
        // Test
        engagementIndexPull.doFetchUpdates();
        // Verify
        // 2 method calls - one for the actual call and one is used in this test
        verifyStatic(times(2));
        PropertyResolver.get(eq(timeOffsetPropertyKey));
        verify(getUpdatesClient, atLeastOnce()).getUpdates(anyString(), getUpdatesTypeArgumentCaptor.capture());
        int i = 1;
        for (GetUpdatesType actualUpdateType : getUpdatesTypeArgumentCaptor.getAllValues()) {
            String actualDate = actualUpdateType.getTimeStamp();
            assertEquals("The time used in number " + i + " of the getUpdates call differ from the expected one!", expectedDate, actualDate);
            i++;
        }
    }

    @Test
    public void testUpdateAmountOfCalls() {
        // Setup
        int amountOfServiceDomains = StringUtils.countMatches(PropertyResolver.get(engagementIndexPropertyKey), ",") + 1;
        int amountOfAddresses = getAddressesClient.getLogicalAddresseesByServiceContract(null, null).getLogicalAddress().size();
        int expectedAmountOfMethodCalls = amountOfServiceDomains * amountOfAddresses;
        // Test
        engagementIndexPull.doFetchUpdates();
        // Verify
        verify(updateClient, times(expectedAmountOfMethodCalls)).update(anyString(), any(UpdateType.class));
    }

    @Test
    public void testCheckForUpdatesAddresses() {
        // Setup
        List<String> expectedAddresses = getAddressesClient.getLogicalAddresseesByServiceContract(null, null).getLogicalAddress();
        ArgumentCaptor<String> logicalAddressArgumentCaptor = ArgumentCaptor.forClass(String.class);
        // Test
        engagementIndexPull.doFetchUpdates();
        // Verify
        verify(getUpdatesClient, atLeastOnce()).getUpdates(logicalAddressArgumentCaptor.capture(), any(GetUpdatesType.class));
        List<String> actualAddresses = logicalAddressArgumentCaptor.getAllValues();
        // Verify that all called addresses were expected.
        for (String actualAddress : actualAddresses) {
            assertTrue("The actual called address " + actualAddress + " was not expected!", expectedAddresses.contains(actualAddress));
        }
        // Verify that all expected addresses were actually called.
        for (String expectedAddress : expectedAddresses) {
            assertTrue("The expected called address " + expectedAddress + " was not called!", actualAddresses.contains(expectedAddress));
        }
    }

    @Test
    public void testCheckForUpdatesAddressesAndServiceDomain() {
        // Setup
        List<String> expectedServiceDomains = EngagementIndexHelper.stringToList(PropertyResolver.get(engagementIndexPropertyKey));
        List<String> expectedAddresses = getAddressesClient.getLogicalAddresseesByServiceContract(null, null).getLogicalAddress();
        ArgumentCaptor<String> logicalAddressArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<GetUpdatesType> getUpdatesTypeArgumentCaptor = ArgumentCaptor.forClass(GetUpdatesType.class);
        // Test
        engagementIndexPull.doFetchUpdates();
        // Verify
        verify(getUpdatesClient, atLeastOnce()).getUpdates(logicalAddressArgumentCaptor.capture(), getUpdatesTypeArgumentCaptor.capture());
        List<String> actualAddresses = logicalAddressArgumentCaptor.getAllValues();
        List<GetUpdatesType> getUpdatesTypes = getUpdatesTypeArgumentCaptor.getAllValues();
        // Verify that all called addresses were expected.
        // There is a glitch here, because the combination of actual addresses and service domains is not verified, however it seems that Mockito does not support verification of parameter combinations.
        // To accomplish this test, the combinations need to be hard coded.
        for (GetUpdatesType getUpdatesType : getUpdatesTypes) {
            String actualServiceDomain = getUpdatesType.getServiceDomain();
            for (String actualAddress : actualAddresses) {
                assertTrue("The actual called address " + actualAddress + " using service domain " + actualServiceDomain + " was not expected!", expectedAddresses.contains(actualAddress));
                assertTrue("The actual called address " + actualAddress + " using service domain " + actualServiceDomain + " was not expected!", expectedServiceDomains.contains(actualServiceDomain));
            }
        }
        // Verify that all expected addresses were actually called.
        for (String expectedServiceDomain : expectedServiceDomains) {
            for (String expectedAddress : expectedAddresses) {
                assertTrue("The expected called address " + expectedAddress + " using service domain " + expectedServiceDomain + " was not called!", actualAddresses.contains(expectedAddress));
                assertTrue("The expected called address " + expectedAddress + " using service domain " + expectedServiceDomain + " was not called!", actualAddresses.contains(expectedServiceDomain));
            }
        }
    }

    @Test
    public void testCorrectEngagementIndexAddress() {
        // Setup
        int amountOfServiceDomains = StringUtils.countMatches(PropertyResolver.get(engagementIndexPropertyKey), ",") + 1;
        int amountOfAddresses = getAddressesClient.getLogicalAddresseesByServiceContract(null, null).getLogicalAddress().size();
        int expectedAmountOfMethodCalls = amountOfServiceDomains * amountOfAddresses;
        // Test
        engagementIndexPull.doFetchUpdates();
        // Verify
        verify(updateClient, Mockito.times(expectedAmountOfMethodCalls)).update(eq(PropertyResolver.get("updateDestinationProperty")), any(UpdateType.class));
    }

}
