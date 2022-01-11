import React, {useEffect, useState} from "react";
import TasksService from "../services/TasksService";
import Task from "./Task";

const TasksTable = () => {

    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        TasksService.getMainPage().then(response => {
            setTasks(response.data);
        });
    }, [])

    return (
        <div className="container">
            <h3>
                Five tasks -
                <small className="text-muted"> sorted by due date</small>
            </h3>
            {tasks.length ?
                <table className="table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Created</th>
                        <th>Status</th>
                        <th>Progress</th>
                        <th>Due date</th>
                        <th>Priority</th>
                        <th>Project</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        tasks.map((task) =>
                            <Task key={task.id} task={task}/>
                        )
                    }
                    </tbody>
                </table>
                : <h2>you have no tasks to do</h2>
            }
        </div>
    )
}

export default TasksTable;