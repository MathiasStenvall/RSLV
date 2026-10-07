# API Documentation

| Method | URL                          | Request Body (JSON) | Response (JSON)  | Error (e)                                |
|:-------|:-----------------------------|:--------------------|:-----------------|:-----------------------------------------|
| GET    | /api/v1/counter              |                     | requests counter |                                          |
| GET    | /api/v1/stats                |                     | API statistics   | InterruptedException, ExecutionException |
| GET    | /api/v1/exercise?page={page} |                     | Exercise[20]     | NotFoundResponse: 404                    |
| POST   | /api/v1/exercise             | Exercise without id |                  | BadRequestResponse: 400                  |