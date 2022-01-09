import React, {useState, useEffect} from "react";
import TasksService from "../services/TasksService";
import {useNavigate} from "react-router-dom";
import {Link} from "react-router-dom";

const CreateTask = () => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [priority, setPriority] = useState('NORMAL');
    const [dueDate, setDueDate] = useState('');
    const [project, setProject] = useState('');

    const [listProjects, setListProjects] = useState([]);
    const history = useNavigate();

    const saveTask = (e) => {
        e.preventDefault();
        let projects = JSON.parse(project);
        console.log(projects);
        const task = {name, description, priority, dueDate, projects}
        console.log(task);
        TasksService.saveTask(task).then((response) => {
            console.log(response.data);
            history("/");
        })
    }

    useEffect(() => {
        TasksService.getAllProjects().then(response => {
            setListProjects(response.data);
            console.log(response.data)
        });
    }, [])

    return (
        <div className="container">
            <div className="col-md-8 order-md-1">
                <nav aria-label="breadcrumb">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item"><Link to="/">Back to task list</Link></li>
                        <li className="breadcrumb-item active" aria-current="page">create task</li>
                    </ol>
                </nav>
                <form>
                    <div className="form-group mb-2">
                        <label className='form-label'>Task name :</label>
                        <input type="text" name="name" className="form-control" placeholder="Task..." value={name}
                               onChange={(e) => setName(e.target.value)}/>
                    </div>
                    <div className="form-group mb-2">
                        <label className="form-label">Description :<span
                            className="text-muted">(Optional)</span></label>
                        <textarea name="description" className="form-control" placeholder="Description.."
                                  style={{height: '150px'}} value={description}
                                  onChange={(e) => setDescription(e.target.value)}/>
                    </div>
                    <div className="form-group mb-2">
                        <label className="form-label">Priority :</label>
                        <select className="form-select" name="priority" value={priority}
                                onChange={(e) => setPriority(e.target.value)}>
                            <option value="NORMAL">Normal</option>
                            <option value="HIGH">High</option>
                            <option value="LOW">Low</option>
                            <option value="PAUSE">Pause</option>
                        </select>
                    </div>
                    <div className="form-group mb-2">
                        <label className="form-label">Due date</label>
                        <input className="form-control" type="datetime-local" name="dueDate" value={dueDate}
                               onChange={(e) => setDueDate(e.target.value)}/>
                    </div>
                    <div className="form-group mb-2">
                        <label>Project</label>
                        <select className="form-select" name="taskProject"
                                onChange={(e) => setProject(e.target.value)}>
                            <option value="">Choose project</option>
                            {listProjects.map((project) =>
                                <option key={project.id} value={JSON.stringify(project)}>
                                    {project.name}
                                </option>
                            )}
                        </select>
                    </div>
                    <button className="btn btn-primary"
                            onClick={(e) => saveTask(e)}>Save
                    </button>
                    <Link to="/" className="btn btn-danger">Cancel</Link>
                </form>
            </div>
        </div>

    )
}

export default CreateTask;
