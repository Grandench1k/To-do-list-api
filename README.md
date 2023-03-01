
<h1>Introduction</h1>

This is my REST api backend application for working with To-Do list applicattions.


<h2> Links to work with API</h2>


<h3>Links for boards</h3>

#### GET all boards

```http
  GET /api/{userid}/boards
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userid`      | `string` | **Required**. User id |

#### GET board by uuid

```http
  GET /api/{userid}/boards/{uuid}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userid`      | `string` | **Required**. User id |
| `uuid`      | `string` | **Required**. Board's uuid  |

#### POST new board

```http
  POST /api/{userid}/boards/create
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userid`      | `string` | **Required**. User id |
| `name`      | `string` | **Required**. Board's name |

#### UPDATE board by uuid

```http
  PUT /api/{userid}/boards/{uuid}/update
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userid`      | `string` | **Required**. User id |
| `uuid`      | `string` | **Required**. Board's uuid  |
| `name`      | `string` | **Required**. Board's new name  |

#### DELETE board by uuid

```http
  DELETE /api/{userid}/boards/{uuid}/delete
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userid`      | `string` | **Required**. User id |
| `uuid`      | `string` | **Required**. Board's uuid |


<h3>Links for tasks</h3>

#### GET all tasks

```http
  GET /api/{userid}/tasks
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userid`      | `string` | **Required**. User id |

#### GET task by uuid

```http
  GET /api/{userid}/tasks/{uuid}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userid`      | `string` | **Required**. User id |
| `uuid`      | `string` | **Required**. Task uuid  |

#### POST new task

```http
  POST /api/{userid}/tasks/create
```
| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userid`      | `string` | **Required**. User id |
| `name`      | `string` | **Required**. Task's name |

#### UPDATE task by uuid

```http
  PUT /api/{userid}/tasks/{uuid}/update
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userid`      | `string` | **Required**. User id |
| `uuid`      | `string` | **Required**. Task uuid  |
| `name`      | `string` | **Required**. Task's new name  |

#### DELETE task by uuid

```http
  DELETE /api/{userid}/tasks/{uuid}/delete
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userid`      | `string` | **Required**. User id |
| `uuid`      | `string` | **Required**. Task uuid |
