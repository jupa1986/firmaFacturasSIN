package bo.sin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.xml.sax.SAXException;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws URISyntaxException, ParserConfigurationException, SAXException, XMLSecurityException
    {
        System.out.println( "iniciando!" );
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><facturaElectronicaProductosNacionales xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"facturaElectronicaProductosNacionales.xsd\">  <cabecera>    <nitEmisor>456489012</nitEmisor>	<razonSocialEmisor>PRUEBA</razonSocialEmisor>	<municipio>La Paz</municipio>    <telefono>2457896</telefono>    <numeroFactura>100</numeroFactura>    <cuf>B2AFA11610013351564D658EE50D2D2A4AA6B685</cuf>    <cufd>F00F840D939A5512913A06FC88ADEA84</cufd>    <codigoSucursal>0</codigoSucursal>    <direccion>Calle Juan Pablo II #54</direccion>    <codigoPuntoVenta xsi:nil=\"true\" />    <fechaEmision>2019-07-26T11:00:12.208</fechaEmision>    <nombreRazonSocial>Pablo Mamani</nombreRazonSocial>    <codigoTipoDocumentoIdentidad>1</codigoTipoDocumentoIdentidad>    <numeroDocumento>1548971</numeroDocumento>    <complemento xsi:nil=\"true\" />    <codigoCliente>PMamani</codigoCliente>    <codigoMetodoPago>2</codigoMetodoPago>    <numeroTarjeta xsi:nil=\"true\"></numeroTarjeta>    <montoTotal>25</montoTotal>	<montoTotalSujetoIva>25</montoTotalSujetoIva>    <codigoMoneda>1</codigoMoneda>    <tipoCambio>1</tipoCambio>    <montoTotalMoneda>25</montoTotalMoneda>    <leyenda>Ley N° 453: Tienes derecho a recibir información sobre las características y contenidos de los servicios que utilices.</leyenda>	<leyendaDS4298>D.S. 4298 - Importe de Crédito Fiscal del 15.6 % aplica solo para sujetos pasivos sujetos al RC-IVA</leyendaDS4298>    <usuario>pperez</usuario>    <codigoDocumentoSector>25</codigoDocumentoSector>  </cabecera>  <detalle>    <actividadEconomica>620100</actividadEconomica>    <codigoProductoSin>83141</codigoProductoSin>    <codigoProducto>JN-131231</codigoProducto>    <descripcion>JUGO DE NARANJA EN VASO</descripcion>    <cantidad>10</cantidad>    <unidadMedida>58</unidadMedida>    <precioUnitario>2.5</precioUnitario>    <montoDescuento xsi:nil=\"true\" />    <subTotal>25</subTotal>	<numeroSerie>djeojde</numeroSerie>		<numeroImei>ajsdasdas</numeroImei>	</detalle> 	</facturaElectronicaProductosNacionales>";

        byte[] datos = xml.getBytes(StandardCharsets.UTF_8);
        try {

            String path = new File(Firmador.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            
            System.out.println("aaaa"+path);
            PrivateKey privateKey = Firmador.getPrivateKey(path + "/privatekey.pem");
            X509Certificate cert =  Firmador.getX509Certificate(path + "/certificadoAgetic.crt");

            byte[] xmlFirmado = Firmador.firmarDsig(datos, privateKey, cert);

            String respuesta = new String(xmlFirmado);

            System.out.println("facturaFirmada: "+respuesta);

        } catch (IOException | GeneralSecurityException ex) {
        	System.out.println("Error ......"+ ex.getMessage());

        }
    }
}
