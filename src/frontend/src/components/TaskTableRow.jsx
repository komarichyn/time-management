import React from "react";
import {Badge} from "react-bootstrap";
import SetBadge from "../scripts/SetBadge.js";
import {Link} from "react-router-dom";

const TaskTableRow = ({task: {id, name, description, created, status, progress, dueDate, priority, projectName}}) => {

    
    return (
        <tr>
            <td><Link to={`/task/${id}`}>{name}</Link></td>
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
            <td><Badge bg={SetBadge(priority)}>{priority}</Badge></td>
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

export default TaskTableRow;