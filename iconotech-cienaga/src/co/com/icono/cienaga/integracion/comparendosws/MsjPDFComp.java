/**
 * MsjPDFComp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package co.com.icono.cienaga.integracion.comparendosws;

public class MsjPDFComp  implements java.io.Serializable {
    private java.lang.String msj;

    private byte[] pdf;

    public MsjPDFComp() {
    }

    public MsjPDFComp(
           java.lang.String msj,
           byte[] pdf) {
           this.msj = msj;
           this.pdf = pdf;
    }


    /**
     * Gets the msj value for this MsjPDFComp.
     * 
     * @return msj
     */
    public java.lang.String getMsj() {
        return msj;
    }


    /**
     * Sets the msj value for this MsjPDFComp.
     * 
     * @param msj
     */
    public void setMsj(java.lang.String msj) {
        this.msj = msj;
    }


    /**
     * Gets the pdf value for this MsjPDFComp.
     * 
     * @return pdf
     */
    public byte[] getPdf() {
        return pdf;
    }


    /**
     * Sets the pdf value for this MsjPDFComp.
     * 
     * @param pdf
     */
    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MsjPDFComp)) return false;
        MsjPDFComp other = (MsjPDFComp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.msj==null && other.getMsj()==null) || 
             (this.msj!=null &&
              this.msj.equals(other.getMsj()))) &&
            ((this.pdf==null && other.getPdf()==null) || 
             (this.pdf!=null &&
              java.util.Arrays.equals(this.pdf, other.getPdf())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMsj() != null) {
            _hashCode += getMsj().hashCode();
        }
        if (getPdf() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPdf());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPdf(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MsjPDFComp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "MsjPDFComp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("msj");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Msj"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pdf");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Pdf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
