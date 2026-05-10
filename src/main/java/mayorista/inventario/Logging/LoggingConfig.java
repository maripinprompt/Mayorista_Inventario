package mayorista.inventario.Logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect // indica que esta clase intercepta métodos de la aplicación
@Component // Spring la gestiona automáticamente
public class LoggingConfig {

    // crea el logger que escribe los mensajes en la consola
    private static final Logger logger = LoggerFactory.getLogger(LoggingConfig.class);

    // se ejecuta ANTES de cualquier método del Service
    @Before("execution(* mayorista.inventario.Service.*.*(..))")
    public void logAntes(JoinPoint joinPoint) {
        logger.info("Ejecutando método: " + joinPoint.getSignature().getName());
    }

    // se ejecuta DESPUÉS de cualquier método del Service
    @AfterReturning(pointcut = "execution(* mayorista.inventario.Service.*.*(..))", returning = "resultado")
    public void logDespues(JoinPoint joinPoint, Object resultado) {
        logger.info("Método finalizado: " + joinPoint.getSignature().getName());
    }
}