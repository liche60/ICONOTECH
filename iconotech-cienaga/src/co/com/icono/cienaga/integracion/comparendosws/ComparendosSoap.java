/**
 * ComparendosSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package co.com.icono.cienaga.integracion.comparendosws;

public interface ComparendosSoap extends java.rmi.Remote {
    public java.lang.String setCompFotoMulta(java.lang.String pin, java.lang.String iDatos, byte[] anexo) throws java.rmi.RemoteException;
    public java.lang.String getCompFecha(java.lang.String pin, java.lang.String iFecha) throws java.rmi.RemoteException;
    public co.com.icono.cienaga.integracion.comparendosws.MsjPDFComp getPDFComp(java.lang.String pin, java.lang.String iNroComp) throws java.rmi.RemoteException;
}
