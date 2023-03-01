This is my rest api backend application for working with To-Do list applicattions.


Links to use this api:

    Registration - /api/auth/register
    
    authorization - /api/auth/authenticate

    Boards:
    GetAllBoards - /api/{userid}/boards
    
    GetBoardByUuid - /api/{userid}/boards/{uuid}
    
    CreateBoard - /api/{userid}/boards/create
    
    UpdateBoardByUuid - /api/{userid}/boards/update/{uuid}
    
    DeleteBoardByUuid - /api/{userid}/boards/delete/{uuid}
    
    Tasks:
    GetAllTasks - /api/{userid}/tasks
    
    GetTaskByUuid - /api/{userid}/tasks/{uuid}
    
    CreateTask - /api/{userid}/tasks/create
    
    UpdateTaskByUuid - /api/{userid}/tasks/update/{uuid}
    
    DeleteTaskByUuid - /api/{userid}/tasks/delete/{uuid}
