import React, {useState} from "react";
import {Link} from "react-router-dom";
import {useNavigate} from "react-router-dom";
import TasksService from "../services/TasksService";


const MyHeader = () => {

    const [name, setName] = useState('');
    const history = useNavigate();

    const saveTask = (e) => {
        e.preventDefault();
        setName('');
        let taskId;
        const task = {name};
        TasksService.saveTask(task).then((response) => {
            taskId = response.data.id;
            history(`/task/${taskId}`);
        })
    };

    return (
        <header className="p-3 bg-dark text-white">

            <div className="container-fluid">
                <div className="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <ul className="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <li><Link to="/" className="nav-link px-2 text-secondary">Time management</Link></li>
                        <li><Link to="/create-task" className="nav-link px-2 text-white">Create task</Link></li>
                        <li><Link to="/create-new-project" className="nav-link px-2 text-white">Create project</Link></li>
                        <li><Link to="/show-tasks/page/1" className="nav-link px-2 text-white">Task list</Link></li>
                        <li>
                            <form className="d-inline-flex">
                                <div>
                                    <input
                                        required
                                        type="text"
                                        name="name"
                                        className="form-control form-control-dark"
                                        placeholder="Quick create task..."
                                        style={{marginLeft: "15px"}}
                                        value={name}
                                        onChange={(e) => setName(e.target.value)}
                                        onKeyPress={(e) => e.key === "Enter" && saveTask(e)}
                                    />
                                </div>
                            </form>
                        </li>
                    </ul>
                    <form method="get"
                          className="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                        <input type="text" name="searchBy" className="form-control form-control-dark"
                               placeholder="Search..."
                               aria-label="Search"/>
                    </form>
                </div>
            </div>
        </header>
    )
}

export default MyHeader;