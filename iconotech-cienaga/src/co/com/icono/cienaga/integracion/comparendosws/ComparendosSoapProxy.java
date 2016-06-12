package co.com.icono.cienaga.integracion.comparendosws;

public class ComparendosSoapProxy implements co.com.icono.cienaga.integracion.comparendosws.ComparendosSoap {
  private String _endpoint = null;
  private co.com.icono.cienaga.integracion.comparendosws.ComparendosSoap comparendosSoap = null;
  
  public ComparendosSoapProxy() {
    _initComparendosSoapProxy();
  }
  
  public ComparendosSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initComparendosSoapProxy();
  }
  
  private void _initComparendosSoapProxy() {
    try {
      comparendosSoap = (new co.com.icono.cienaga.integracion.comparendosws.ComparendosLocator()).getComparendosSoap();
      if (comparendosSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)comparendosSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)comparendosSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (comparendosSoap != null)
      ((javax.xml.rpc.Stub)comparendosSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public co.com.icono.cienaga.integracion.comparendosws.ComparendosSoap getComparendosSoap() {
    if (comparendosSoap == null)
      _initComparendosSoapProxy();
    return comparendosSoap;
  }
  
  public java.lang.String setCompFotoMulta(java.lang.String pin, java.lang.String iDatos, byte[] anexo) throws java.rmi.RemoteException{
    if (comparendosSoap == null)
      _initComparendosSoapProxy();
    return comparendosSoap.setCompFotoMulta(pin, iDatos, anexo);
  }
  
  public java.lang.String getCompFecha(java.lang.String pin, java.lang.String iFecha) throws java.rmi.RemoteException{
    if (comparendosSoap == null)
      _initComparendosSoapProxy();
    return comparendosSoap.getCompFecha(pin, iFecha);
  }
  
  public co.com.icono.cienaga.integracion.comparendosws.MsjPDFComp getPDFComp(java.lang.String pin, java.lang.String iNroComp) throws java.rmi.RemoteException{
    if (comparendosSoap == null)
      _initComparendosSoapProxy();
    return comparendosSoap.getPDFComp(pin, iNroComp);
  }
  
  
}