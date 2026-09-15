package lectoapp_backend.application.service;

public interface PasswordEncoderService {

    /**
     * Encripta una contraseña utilizando el algoritmo configurado.
     */
    String encriptar(String password);

    /**
     * Compara una contraseña en texto plano contra una contraseña encriptada.
     */
    boolean coincide(String password, String passwordEncriptado);

}