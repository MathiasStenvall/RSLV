# API Documentation

| Method | URL                          | Request Body (JSON) | Response (JSON)  | Error (e)                        |
|:-------|:-----------------------------|:--------------------|:-----------------|:---------------------------------|
| GET    | /api/v1/counter              |                     | requests counter |                                  |
| GET    | /api/v1/stats                |                     | API statistics   | 500: Internal Server Error       |
| GET    | /api/v1/exercise/{id}        |                     | Exercise as JSON | 400: Bad Request, 404: Not Found |
| GET    | /api/v1/exercise?page={page} |                     | Exercise[20]     | 400: Bad Request                 |
| POST   | /api/v1/exercise             | Exercise without id | Exercise as JSON | 400: Bad Request                 |
| PUT    | /api/v1/exercise/{id}        | Exercise without id | Exercise as JSON | 400: Bad Request, 404: Not Found |
| DELETE | /api/v1/exercise/{id}        |                     |                  | 400: Bad Request, 404: Not Found |