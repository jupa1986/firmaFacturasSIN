package bo.sin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.xml.sax.SAXException;

public class firmaXML {
	public static String firmar(String xml) throws IOException, GeneralSecurityException, ParserConfigurationException, SAXException, XMLSecurityException {
		byte[] facturaBytes = xml.getBytes(StandardCharsets.UTF_8);
		
			//String path = "/home/jupa1986/Downloads";
		    String path = "/home/jupa1986/Code/certificadosAGETIC";
			

			System.out.println("aaaa" + path);
			X509Certificate cert = Firmador.getX509Certificate(path + "/solicitud-cert-agetic_CERT.pem");
			System.out.println("leectura de certificados....");

			PrivateKey privateKey = Firmador.getPrivateKey(path + "/llave-privada-agetic.pem");
			System.out.println("leecturaddddd de clave");


			byte[] xmlFirmado = Firmador.firmarDsig(facturaBytes, privateKey, cert);

			String respuesta = new String(xmlFirmado);
			return respuesta;
	}
}
