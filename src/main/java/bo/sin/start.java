package bo.sin;

import static spark.Spark.*;

import java.util.Base64;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import bo.sin.*;

public class start {

	public static void main(String[] args) {

		get("/status", (req, res) -> "Funcionando Correctamente v0.0.1");

		post("/descomprimir", (req, res) -> {
			System.out.println("Descomprimiendo");

			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(req.body());
			String base64 = (String) obj.get("base64");
			String hashEnviado = (String) obj.get("hash");
			String limpio = base64.replace("\n", "");
			try {
				byte[] decoded = Base64.getDecoder().decode(limpio);
				String hashFacturaGZIP = PaqueteFactura.hash256(decoded);

				String contenido = PaqueteFactura.decompress(decoded);
				System.out.println(hashEnviado+" "+hashFacturaGZIP);
				if (hashEnviado.equals(hashFacturaGZIP)) {
					return contenido + "  true";
				}
				
				return contenido + "   false";
			} catch (Exception e) {
				System.out.println("pp" + e.getMessage());
				return e.getMessage();

			}
		});

		post("/comprimir", (req, res) -> {
			System.out.println("Comprimiendo");

			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(req.body());
			String facturaRAW = (String) obj.get("data");

			try {
				byte[] facturaGZIP = PaqueteFactura.compress(facturaRAW);
				String hashFacturaGZIP = PaqueteFactura.hash256(facturaGZIP);
				String facturaGZIPBase64 = Base64.getEncoder().encodeToString(facturaGZIP);
				JSONObject json = (JSONObject) parser
						.parse("{\"base64\":\"" + facturaGZIPBase64 + "\", \"hash\":\"" + hashFacturaGZIP + "\"}");
				res.type("application/json");
				return json;

			} catch (Exception e) {
				System.out.println("pp" + e.getMessage());
				return e.getMessage();
			}
		});

		post("/firmar", (req, res) -> {
			System.out.println("firmando");
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(req.body());
			String facturaBase64 = (String) obj.get("base64");

			String facturaXML = new String(Base64.getDecoder().decode(facturaBase64));

			try {
				String facturaFirmadaXML = firmaXML.firmar(facturaXML);
				
				
				
				
				String encodedString = Base64.getEncoder().encodeToString(facturaFirmadaXML.getBytes());

				JSONObject json = (JSONObject) parser.parse("{\"datos\":\"" + encodedString + "\"}");
				res.type("application/json");
				return json;

			} catch (Exception e2) {
				return e2.getMessage();
			}
		});
		
		
		post("/empaquetar", (req, res) -> {
			System.out.println("Empaquetando");
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(req.body());
			String facturaBase64 = (String) obj.get("base64");
			String facturaXML = new String(Base64.getDecoder().decode(facturaBase64));
			try {
				String facturaFirmadaXML = firmaXML.firmar(facturaXML);
				byte[] facturaGZIP = PaqueteFactura.compress(facturaFirmadaXML);
				String hashFacturaGZIP = PaqueteFactura.hash256(facturaGZIP);
				String facturaGZIPBase64 = Base64.getEncoder().encodeToString(facturaGZIP);
				JSONObject json = (JSONObject) parser
						.parse("{\"base64\":\"" + facturaGZIPBase64 + "\", \"hash\":\"" + hashFacturaGZIP + "\"}");
				res.type("application/json");
				return json;
			} catch (Exception e2) {
				return e2.getMessage();
			}
		});

	}
}
