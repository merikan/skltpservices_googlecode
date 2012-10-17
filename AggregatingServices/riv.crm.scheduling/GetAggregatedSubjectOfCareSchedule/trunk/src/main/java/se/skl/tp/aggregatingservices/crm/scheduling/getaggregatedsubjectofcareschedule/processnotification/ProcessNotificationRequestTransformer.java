package se.skl.tp.aggregatingservices.crm.scheduling.getaggregatedsubjectofcareschedule.processnotification;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessNotificationRequestTransformer extends AbstractMessageTransformer {

	private static final Logger log = LoggerFactory.getLogger(ProcessNotificationRequestTransformer.class);

    /**
     * Message aware transformer that ...
     */
    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

        // Perform any message aware processing here, otherwise delegate as much as possible to pojoTransform() for easier unit testing

        return pojoTransform(message.getPayload(), outputEncoding);
    }

	/**
     * Simple pojo transformer method that can be tested with plain unit testing...
	 */
	protected Object pojoTransform(Object src, String encoding) throws TransformerException {

		log.debug("Transforming xml payload: {}", src);
		
		Object[] oArr = (Object[])src;

		// Return the second argument that corresponds to the ProcessNotification-Request (as an inputStream)
		return oArr[1];
	}
}