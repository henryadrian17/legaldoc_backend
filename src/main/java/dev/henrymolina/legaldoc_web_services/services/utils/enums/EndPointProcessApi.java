package dev.henrymolina.legaldoc_web_services.services.utils.enums;

public interface EndPointProcessApi {
    String ENDPOINT_RAIZ = "/legaldoc/api";
    String ENDPOINT_LOGIN = "/v1/login";
    String ENDPOINT_REGISTER = "/v1/register";
    String ENDPOINT_LOGOUT = "/v1/logout";
    String ENDPOINT_CLIENTES = "/v1/clientes";

    String ENDPOINT_ASESORES = "/v1/asesores";

    String VERIFY_TOKEN = "/v1/verifytoken";
    String ENDPOINT_CLIENTE = "/v1/cliente";
    String ENDPOINT_ASESORES_ID_SERVICES = "/v1/asesores/{id}";
    String CARRITO_DATE = "/v1/carrito";
    String STORE_ORDER = "/v1/storeorder";
    String ENDPOINT_ORDER_ID = "/v1/order/{id}";
    String ENDPOINT_ORDER_BY_USER_ID = "/v1/order/user/";
    String ENDPOINT_CLIENT_BY_AUTHORIZATION = "/v1/cliente/authorization";
    String ENDPOINT_CLIENTE_BY_AUTHORIZATION_PASSWORD = "/v1/cliente/authorization/password";
}
