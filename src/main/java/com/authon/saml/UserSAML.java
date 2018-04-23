package com.authon.saml;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.schema.impl.XSAnyImpl;
import org.springframework.security.core.Authentication;

public class UserSAML {

    public static SamlUserInfo extractUserInfoIn(Authentication authentication) {
        try {
            org.springframework.security.saml.SAMLCredential credential = (org.springframework.security.saml.SAMLCredential) authentication.getCredentials();
            String email = getAttributeValue(credential.getAttribute("EMAIL").getAttributeValues().get(0));
            String name = getAttributeValue(credential.getAttribute("NAME").getAttributeValues().get(0));
            return new SamlUserInfo(email, name);
        }catch (Exception e){
            return new SamlUserInfo("", "");
        }
    }

    private static String getAttributeValue(XMLObject attributeValue) {
        return attributeValue == null ?
                null :
                attributeValue instanceof XSString ?
                        getStringAttributeValue((XSString) attributeValue) :
                        attributeValue instanceof XSAnyImpl ?
                                getAnyAttributeValue((XSAnyImpl) attributeValue) :
                                attributeValue.toString();
    }

    private static String getStringAttributeValue(XSString attributeValue) {
        return attributeValue.getValue();
    }

    private static String getAnyAttributeValue(XSAnyImpl attributeValue) {
        return attributeValue.getTextContent();
    }
}
