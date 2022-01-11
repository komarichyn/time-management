import React, {useState} from "react";

const Task = ({task: {id, name, description, created, status, progress, dueDate, priority, projectName}}) => {

    return (
        <tr>
            <td><a href={id}>{name}</a></td>
            <td>{description}</td>
            <td>{created}</td>
            <td>{status}</td>
            <td>
                <div className="progress">
                    <div className="progress-bar" role="progressbar"
                         style={{width: progress + '%'}}
                    >{progress}%
                    </div>
                </div>
            </td>
            <td>{dueDate}</td>
            <td>{priority}</td>
            {
                projectName !== null
                    ?
                    <td>{projectName['name']}</td>
                    :
                    <td></td>
            }
        </tr>
    )
}

export default Task;