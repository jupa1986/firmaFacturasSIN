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
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
        		+ "<facturaElectronicaCompraVenta xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"facturaElectronicaCompraVenta.xsd\">\n"
        		+ "  <cabecera>\n"
        		+ "    <nitEmisor>5952016018</nitEmisor>\n"
        		+ "	<razonSocialEmisor>AGENCIA DE GOBIERNO ELECTRONICO AGETIC</razonSocialEmisor>\n"
        		+ "	<municipio>La Paz</municipio>\n"
        		+ "    <telefono>2457896</telefono>\n"
        		+ "    <numeroFactura>100</numeroFactura>\n"
        		+ "    <cuf>8727F63A15F8976591FDDE5B387C5D015A29E06A1A19E23EF34124CD</cuf>\n"
        		+ "    <cufd>BQXlDwqF5ZUFBNkDhGREEzMDFCQkY=QlVaSFZNZEdWVUFNENzkwOTdDQ0FEO</cufd>\n"
        		+ "    <codigoSucursal>0</codigoSucursal>\n"
        		+ "    <direccion>Calle Juan Pablo II #54</direccion>\n"
        		+ "    <fechaEmision>2021-06-28T13:40:12.208</fechaEmision>\n"
        		+ "    <nombreRazonSocial>Pepe Perez</nombreRazonSocial>\n"
        		+ "    <codigoTipoDocumentoIdentidad>1</codigoTipoDocumentoIdentidad>\n"
        		+ "    <numeroDocumento>1548971</numeroDocumento>\n"
        		+ "    <codigoCliente>PPerez</codigoCliente>\n"
        		+ "    <codigoMetodoPago>1</codigoMetodoPago>\n"
        		+ "    <montoTotal>25</montoTotal>\n"
        		+ "	  <montoTotalSujetoIva>25</montoTotalSujetoIva>\n"
        		+ "    <codigoMoneda>1</codigoMoneda>\n"
        		+ "    <tipoCambio>1</tipoCambio>\n"
        		+ "    <montoTotalMoneda>25</montoTotalMoneda>\n"
        		+ "    <leyenda>Ley N° 453: Tienes derecho a recibir información sobre las características y contenidos de los servicios que utilices.</leyenda>\n"
        		+ "    <usuario>pperez</usuario>\n"
        		+ "    <codigoDocumentoSector>1</codigoDocumentoSector>\n"
        		+ "  </cabecera>\n"
        		+ "  <detalle>\n"
        		+ "    <actividadEconomica>620100</actividadEconomica>\n"
        		+ "    <codigoProductoSin>83141</codigoProductoSin>\n"
        		+ "    <codigoProducto>JN-131231</codigoProducto>\n"
        		+ "    <descripcion>JUGO DE NARANJA EN VASO</descripcion>\n"
        		+ "    <cantidad>10</cantidad>\n"
        		+ "    <unidadMedida>58</unidadMedida>\n"
        		+ "    <precioUnitario>2.5</precioUnitario>\n"
        		+ "    <subTotal>25</subTotal>\n"
        		+ "  </detalle>\n"
        		+ "</facturaElectronicaCompraVenta>";

        byte[] datos = xml.getBytes(StandardCharsets.UTF_8);
        try {

            //String path = new File(Firmador.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        	String path = "/home/jupa1986/Downloads";
            
            System.out.println("aaaa"+path);
            PrivateKey privateKey = Firmador.getPrivateKey(path + "/llave-privada-local.pem");
            X509Certificate cert =  Firmador.getX509Certificate(path + "/certificado-siat.crt");

            byte[] xmlFirmado = Firmador.firmarDsig(datos, privateKey, cert);

            String respuesta = new String(xmlFirmado);
            System.out.println("facturaFirmada: ");
            System.out.println(respuesta);

        } catch (IOException | GeneralSecurityException ex) {
        	System.out.println("Error ......"+ ex.getMessage());

        }
    }
}
