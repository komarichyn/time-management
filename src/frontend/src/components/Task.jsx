import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {Link} from "react-router-dom";
import {useNavigate} from "react-router-dom";
import TasksService from "../services/TasksService";
import SetBadge from "../scripts/SetBadge";
import {Badge} from "react-bootstrap";

const Task = () => {

    const [task, setTask] = useState({
        id: 0,
        name: '',
        description: '',
        created: '',
        comments: [],
        status: '',
        dueDate: '',
        priority: '',
        progress: 0,
        projectName: {
            id: 0,
            name: '',
        },

    });
    const history = useNavigate();

    const dt = {
        background: '#056c8d',
        color: '#fff',
        padding: '10px',
        borderRadius: '5px',
        border: 'solid 2px',
    }

    let {id} = useParams();

    useEffect(() => {
        TasksService.getTask(id).then(response => {
            setTask(response.data);
        });
    }, [id])

    const deleteTask = (e) => {
        e.preventDefault();
        TasksService.deleteTask(id).then((response) => {
            history('/');
        })
    }


    return (
        <>
            <div className="container">
                <nav aria-label="breadcrumb">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item"><Link to='/'>Back to task list</Link></li>
                        <li className="breadcrumb-item active">{task.name}</li>
                    </ol>
                </nav>
            </div>
            <div className="col-6" style={dt}>
                <div className="block">
                    <div>
                        <h2>{task.name}</h2>
                    </div>
                    <hr/>
                    <h6><u>Description</u></h6>
                    <p>{task.description}</p>
                    <div className="container">
                        <dl className="row">
                            <dt className="col-6">Due date :</dt>
                            <dd className="col-6">{task.dueDate}</dd>
                            <dt className="col-6">Status :</dt>
                            <dd className="col-6">{task.status}</dd>
                            <dt className="col-6">Progress :</dt>
                            <dd className="col-6">{task.progress}</dd>
                            <dt className="col-6">Priority :</dt>
                            <dd className="col-6"><Badge bg={SetBadge(task.priority)}>{task.priority}</Badge>
                            </dd>
                            <dt className="col-6">Project :</dt>
                            {task.projectName !== null
                                ?
                                <dd className="col-6">{task.projectName.name}</dd>
                                :
                                <dd></dd>
                            }

                        </dl>
                    </div>
                </div>
            </div>
            <Link className="btn btn-primary" to="/">Update</Link>
            <button className="btn btn-danger"
                    onClick={(e) => deleteTask(e)}>Delete
            </button>

        </>
    )
}

export default Task;