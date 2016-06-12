/**
 * ComparendosLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package co.com.icono.cienaga.integracion.comparendosws;

public class ComparendosLocator extends org.apache.axis.client.Service implements co.com.icono.cienaga.integracion.comparendosws.Comparendos {

    public ComparendosLocator() {
    }


    public ComparendosLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ComparendosLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ComparendosSoap
    private java.lang.String ComparendosSoap_address = "http://186.147.244.152:5014/wsComparendos.asmx";

    public java.lang.String getComparendosSoapAddress() {
        return ComparendosSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ComparendosSoapWSDDServiceName = "ComparendosSoap";

    public java.lang.String getComparendosSoapWSDDServiceName() {
        return ComparendosSoapWSDDServiceName;
    }

    public void setComparendosSoapWSDDServiceName(java.lang.String name) {
        ComparendosSoapWSDDServiceName = name;
    }

    public co.com.icono.cienaga.integracion.comparendosws.ComparendosSoap getComparendosSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ComparendosSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getComparendosSoap(endpoint);
    }

    public co.com.icono.cienaga.integracion.comparendosws.ComparendosSoap getComparendosSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            co.com.icono.cienaga.integracion.comparendosws.ComparendosSoapStub _stub = new co.com.icono.cienaga.integracion.comparendosws.ComparendosSoapStub(portAddress, this);
            _stub.setPortName(getComparendosSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setComparendosSoapEndpointAddress(java.lang.String address) {
        ComparendosSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (co.com.icono.cienaga.integracion.comparendosws.ComparendosSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                co.com.icono.cienaga.integracion.comparendosws.ComparendosSoapStub _stub = new co.com.icono.cienaga.integracion.comparendosws.ComparendosSoapStub(new java.net.URL(ComparendosSoap_address), this);
                _stub.setPortName(getComparendosSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ComparendosSoap".equals(inputPortName)) {
            return getComparendosSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "Comparendos");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "ComparendosSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ComparendosSoap".equals(portName)) {
            setComparendosSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
