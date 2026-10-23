package app.exceptions;

import app.DTO.ErrorResponse;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionMapper {

    private static final Logger log = LoggerFactory.getLogger(ExceptionMapper.class);

    public static void exceptionMapping(JavalinConfig config) {

        config.routes.exception(ValidationException.class, (e, ctx) -> {
            log.warn("Validation error: {}", e.getMessage());

            String message = e.getErrors().values().iterator().next().get(0).getMessage();

            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorResponse(400, message));
        });

        config.routes.exception(UnauthorizedResponse.class, (e, ctx) -> {
           log.warn("Unauthorized request: {}", e.getMessage());

           ctx.status(HttpStatus.UNAUTHORIZED);
           ctx.json(new ErrorResponse(401, e.getMessage()));
        });

        config.routes.exception(NotFoundResponse.class, (e, ctx) -> {
            log.warn("Resource not found: {}", e.getMessage());

            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(new ErrorResponse(404, e.getMessage()));
        });

        config.routes.exception(UserAlreadyExistsException.class, (e, ctx) -> {
            log.warn("User registration error: {}", e.getMessage());

            ctx.status(HttpStatus.CONFLICT);
            ctx.json(new ErrorResponse(409, e.getMessage()));
        });

        config.routes.exception(Exception.class, (e, ctx) -> {
            log.error("Unexpected exception: {}", e.getMessage());

            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json((new ErrorResponse(500, "Internal server error.")));
        });


    }

}
