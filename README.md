# firmaFacturasSIN

Para la gestion de facturas digitales Impuestos nacionales actualizo su generador de facturas a uno que utiliza firma digital

Este es un ejemplo basado en su documentacion

https://siatinfo.impuestos.gob.bo/index.php/firma-digital/firmado-de-xml

## Datos tecnicos
- Es necesario una firma digital valida en Bolivia (ADSIB, DIGICERT)
- el profile de la firma xades es 
    - ALGO_ID_SIGNATURE_RSA_SHA256(Message Digest - SHA256 (Note: Defined by XML Encryption)).
    - TRANSFORM_ENVELOPED_SIGNATURE(Transform - Required Enveloped Signature).
    - TRANSFORM_C14N_WITH_COMMENTS(Transform - Recommended Inclusive c14n 1.0 WITH comments.)

## Certificados de ejemplo



