package app.mapper;

import app.DTO.ErrorResponse;
import io.javalin.config.JavalinConfig;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionMapper {

    private static final Logger log = LoggerFactory.getLogger(ExceptionMapper.class);

    public static void exceptionMapping (JavalinConfig config){

        config.routes.exception(BadRequestResponse.class, (e, ctx) -> {
            log.warn("Bad request: {}", e.getMessage());

            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.json(new ErrorResponse(400, e.getMessage()));
        });

        config.routes.exception(NotFoundResponse.class, (e, ctx) -> {
            log.warn("Resource not found: {}", e.getMessage());

            ctx.status(HttpStatus.NOT_FOUND);
            ctx.json(new ErrorResponse(404, e.getMessage()));
        });

        config.routes.exception(Exception.class, (e, ctx) -> {
            log.error("Unexpected exception: {}", e.getMessage());

            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.json((new ErrorResponse(500, e.getMessage())));
        });



    }

}
