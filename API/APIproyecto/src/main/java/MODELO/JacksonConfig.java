/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper; //se declara l avariable llamada objectMapper

    public JacksonConfig() {
        //en el constructor de confirgutra el objectMapper
        objectMapper = new ObjectMapper();//Crea una nueva instancia de ObjectMapper.
        objectMapper.registerModule(new JavaTimeModule());//Registra el módulo JavaTimeModule en el ObjectMapper
        //esto permite manejar bien las fechas
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //Desactiva la opción que haría que las fechas se escriban como timestamps (números).
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }
}
