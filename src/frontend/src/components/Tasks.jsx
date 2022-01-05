import React, {useEffect, useState} from "react";
import TasksService from "../services/TasksService";
import {Link} from "react-router-dom";

const Tasks = () => {

    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        TasksService.getMainPage().then(response => {
            setTasks(response.data);
            console.log(response.data)
        });
    },[])

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
                        tasks.map(task =>
                            <tr key={task.id}>
                                <td><a href={task.id}>{task.name}</a></td>
                                <td>{task.description}</td>
                                <td>{task.created}</td>
                                <td>{task.status}</td>
                                <td>
                                    <div className="progress">
                                        <div className="progress-bar" role="progressbar"
                                             style={{width: task.progress + '%'}}
                                        >{task.progress}%
                                        </div>
                                    </div>
                                </td>
                                <td>{task.dueDate}</td>
                                <td>{task.priority}</td>
                                <td>{task.projects}</td>
                            </tr>
                        )
                    }
                    </tbody>
                </table>
                : <h2>you have no tasks to do</h2>
            }
        </div>
    )
}
export default Tasks;