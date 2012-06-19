package org.picketlink.test.cdi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
 
import org.junit.Test;
import org.picketlink.identity.federation.core.config.IDPType;
import org.picketlink.identity.federation.core.config.PicketLinkType;
import org.picketlink.identity.federation.core.config.SPType;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Use Spring to construct the object model for picketlink.xml
 * @author anil saldhana
 */
public class PicketLinkXMLSpringObjectModelTestCase {
    
    /**
     * Test the construction of {@link PicketLinkType} using Spring bean definitions
     * @throws Exception
     */
    @Test
    public void testIDPConfiguration() throws Exception{ 
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "META-INF/picketlink-idp-beans.xml");
        BeanFactory factory = context;
        PicketLinkType picketlink = (PicketLinkType) factory.getBean("picketlink");
        assertNotNull(picketlink);
        assertTrue(picketlink.isEnableAudit());
        
        IDPType idp = (IDPType) picketlink.getIdpOrSP();
        assertNotNull(idp);
        
        // asserts the StrictPostBinding attribute. Default is true, but for this test it was changed to true in the configuration file. 
        assertFalse(idp.isStrictPostBinding());
        
        assertEquals("testFQN", idp.getIdentityParticipantStack());
    }
    
    /**
     * Test the construction of {@link PicketLinkType} using Spring bean definitions
     * @throws Exception
     */
    @Test
    public void testSPConfiguration() throws Exception{ 
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "META-INF/picketlink-sp-beans.xml");
        BeanFactory factory = context;
        PicketLinkType picketlink = (PicketLinkType) factory.getBean("picketlink");
        assertNotNull(picketlink);
        SPType sp = (SPType) picketlink.getIdpOrSP();
        assertNotNull(sp);
        assertEquals("REDIRECT", sp.getBindingType());
        assertEquals("tomcat", sp.getServerEnvironment());
        assertEquals("someURL", sp.getRelayState());
        assertEquals("/someerror.jsp", sp.getErrorPage());
        assertTrue(sp.isSupportsSignature());
        assertTrue(picketlink.isEnableAudit());
    }
    
    
    
    
}