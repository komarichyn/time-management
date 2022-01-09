import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import TasksService from "../../../services/TasksService";

const CreateTaskForm = () => {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [priority, setPriority] = useState("");
    const [dueDate, setDueDate] = useState("");
    const [projects, setProjects] = useState("");

    const [ListProjects, setListProjects] = useState([]);
    const navigate = useNavigate();

    const saveTask = (e) => { //TODO:Add this method to new seperate component
        e.preventDefault();
        const task = { name, description, priority, dueDate, projects };
        TasksService.createTask(task).then((res) => {
            console.log(res.data);
            navigate("/show-tasks");
        })/*.catch(error => {
            console.log(error);
        })*/
        console.log(task);
    };

    // const [project, setProject] = useState([]);
    useEffect(() => {
        TasksService.getAllProjects().then((res) => {
            setListProjects(res.data);
            console.log(res);
        });
    }, [])

    return (
        <div className="container">
            <div className="col-md-8 order-md-1">
                <form>
                    <div className="col-md-5 mb-3">
                        <label>Task Name</label>
                        <input
                            type="text"
                            name="name"
                            id="taskName"
                            className="form-control"
                            placeholder="Task..."
                            required
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />
                        <div className="invalid-feedback">Please enter task name.</div>
                    </div>
                    <div className="col-md-5 mb-3">
                        <label>
                            Description<span className="text-muted">(Optional)</span>
                        </label>
                        <textarea
                            name="description"
                            className="form-control"
                            placeholder="Description.."
                            style={{ height: "150px" }}
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                        >Description</textarea>
                    </div>

                    <div className="col-md-5 mb-3">
                        <label>Priority</label>
                        <select
                            className="form-select"
                            name="priority"
                            value={priority}
                            onChange={(e) => setPriority(e.target.value)}
                        >
                            <option value="NORMAL">Normal</option>
                            <option value="HIGH">High</option>
                            <option value="LOW">Low</option>
                            <option value="PAUSE">Pause</option>
                        </select>
                    </div>
                    <div className="col-md-5 mb-3">
                        <label>Due date</label>
                        <input
                            className="form-control"
                            type="datetime-local"
                            name="dueDate"
                            value={dueDate}
                            onChange={(e) => setDueDate(e.target.value)}
                        />
                    </div>
                    <div className="col-md-5 mb-3">
                        <label>Project</label>
                        <select className="form-select" name="taskProject"
                                onChange={(e) => setProjects(e.target.value)}>
                            <option value="">Choose project</option>
                            {ListProjects.map((project) =>
                                <option key={project.id} value={JSON.stringify(project)}>
                                    {project.name}
                                </option>
                            )}
                        </select>
                    </div>
                    <button
                        type="submit"
                        className="btn btn-primary"
                        onClick={(e) => saveTask(e)}
                    >
                        Save
                    </button>
                </form>
            </div>
        </div>
    );
};

export default CreateTaskForm;