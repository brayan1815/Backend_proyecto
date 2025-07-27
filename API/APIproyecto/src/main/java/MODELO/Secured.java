
package MODELO;


import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@NameBinding// Indica que esta anotación puede usarse para asociarse con filtros
@Retention(RetentionPolicy.RUNTIME)// La anotación estará disponible en tiempo de ejecución (para que el filtro la detecte)
@Target({ElementType.TYPE, ElementType.METHOD})// Puede usarse en clases o métodos
public @interface Secured {}// Se define una anotación vacía llamada @Secured
