# API Documentation

| Method | URL                          | Request Body (JSON) | Response (JSON)  | Error (e)                                                            |
|:-------|:-----------------------------|:--------------------|:-----------------|:---------------------------------------------------------------------|
| POST   | /api/v1/register             | User                | Success message  | 400: Bad Request, 409: UserAlreadyExists, 500: Internal Server error |
| POST   | /api/v1/login                | User                | JWT              | 400: Bad Request, 401: Unauthorized                                  |
| GET    | /api/v1/counter              |                     | requests counter |                                                                      |
| GET    | /api/v1/stats                |                     | API statistics   | 500: Internal Server Error                                           |
| GET    | /api/v1/exercise/{id}        |                     | Exercise as JSON | 400: Bad Request, 404: Not Found                                     |
| GET    | /api/v1/exercise?page={page} |                     | Exercise[20]     | 400: Bad Request                                                     |
| POST   | /api/v1/exercise             | Exercise without id | Exercise as JSON | 400: Bad Request, 403: Forbidden                                     |
| PUT    | /api/v1/exercise/{id}        | Exercise without id | Exercise as JSON | 400: Bad Request, 403: Forbidden, 404: Not Found                     |
| DELETE | /api/v1/exercise/{id}        |                     |                  | 400: Bad Request, 403: Forbidden, 404: Not Found                     |