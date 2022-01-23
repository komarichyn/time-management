import React, {useState} from "react";
import TasksService from "../../../services/TasksService";
import {Link, useNavigate} from "react-router-dom";

const CreateProjectForm = () => {
  const [name, setName] = useState("");

  const navigate = useNavigate();

  const saveProject = (e) => {
    e.preventDefault();
    const project = {name};
    TasksService.createProject(project).then((res) => {
      navigate("/create-task");
    })
  }

  return (
    <div className="container">
      <div className="col-md-8 order-md-1">
        <nav aria-label="breadcrumb">
          <ol className="breadcrumb">
            <li className="breadcrumb-item"><Link to="/create-task">Back to create task</Link></li>
            <li className="breadcrumb-item active" aria-current="page">create project</li>
          </ol>
        </nav>
        <form className="needs-validation" method="post" action="/add-project" noValidate>
          <div className="col-md-5 mb-3">
            <label>Project name</label>
            <input type="text"
                   name="name"
                   id="projectName"
                   className="form-control"
                   placeholder="Project..."
                   required
                   value={name}
                   onChange={(e) => setName(e.target.value)}
            />
            <div className="invalid-feedback">
              Please enter project name.
            </div>
          </div>
          <div>
            <input type="submit" className="btn btn-primary" onClick={(e) => saveProject(e)}/>
          </div>
        </form>
      </div>
    </div>
  )
}

export default CreateProjectForm;