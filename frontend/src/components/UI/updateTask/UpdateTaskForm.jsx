import React, {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import TasksService from "../../../services/TasksService";
import moment from "moment";

const UpdateTaskForm = () => {
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
    }
  });
  let {id} = useParams(); //TODO question

  let m = moment(task.dueDate, "YYYY-MM-DDTkk:mm").format("YYYY-MM-DDTkk:mm");
  console.log(m);
  task.dueDate = m;

  // let m = moment(task.dueDate, "YYYY-MM-DDTkk:mm");
  // console.log(m);
  // task.dueDate = m.format("YYYY-MM-DDTkk:mm").toString();

  // let q = moment(task.dueDate, moment.HTML5_FMT.DATETIME_LOCAL);
  // console.log(q);
  // task.dueDate = q.format("YYYY-MM-DDTkk:mm").toString();

  // task.dueDate = m;
  // let m = moment.HTML5_FMT.DATETIME_LOCAL;
  // moment(task.dueDate).toISOString();
  // let m = moment().toISOString(task.dueDate);
  // console.log(m.toISOString(task.dueDate));
  const [listProjects, setListProjects] = useState([]);
  const navigate = useNavigate();

  const updateTask = (e) => {
    e.preventDefault();
    console.log(task, typeof(task));
    console.log(task.dueDate, typeof(task.dueDate));
    TasksService.updateTask(id, task).then((res) => {
      console.log("WORK");
      console.log(res.data);
      navigate(`/task/${id}`);
    })
  }

  useEffect(() => {
    getTask(id);
    getProjects();
  }, [id])

  const getTask = (id) => {
    TasksService.getTask(id).then((res) => {
      setTask(res.data);
      console.log(task.dueDate);
      console.log(task.name);
    });
  }

  const getProjects = () => {
    TasksService.getAllProjects().then((res) => {
      setListProjects(res.data);
    });
  }

  return (
    <div className="container">
      <div className="col-md-8 order-md-1">
        <nav aria-label="breadcrumb">
          <ol className="breadcrumb">
            <li className="breadcrumb-item"><Link to='/show-tasks'>Back to task list</Link></li>
            <li className="breadcrumb-item"><Link to={`/task/${id}`}>{task.name}</Link></li>
            <li className="breadcrumb-item active">{task.name}</li>
          </ol>
        </nav>
        <form>
          <div className="col-md-5 mb-3">
            <label className="form-label">Name</label>
            <input
              type="text"
              name="name"
              className="form-control"
              placeholder="Name..."
              value={task.name}
              onChange={(e) =>
                setTask((state) => ({...state, name: e.target.value}))}
              // setTask((state)=> ({...state, [name]: newValue}))
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
              style={{height: "150px"}}
              value={task.description}
              onChange={(e) =>
                setTask((state) => ({...state, description: e.target.value}))}
            >Description</textarea>
          </div>
          <div className="col-md-5 mb-3">
            <label>Status</label>
            <select
              className="form-control"
              name="status"
              value={task.status}
              onChange={(e) =>
                setTask((state) => ({...state, status: e.target.value}))}
            >
              <option value="TODO">Todo</option>
              <option value="IN_PROGRESS">In progress</option>
              <option value="COMPLETE">Complete</option>
              <option value="PAUSE">Pause</option>
            </select>
          </div>
          <div className="col-md-5 mb-3">
            <label>Priority</label>
            <select
              className="form-select"
              name="priority"
              value={task.priority}
              onChange={(e) =>
                setTask((state) => ({...state, priority: e.target.value}))}
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
              type="datetime-local"
              className="form-control"
              name="dueDate"
              value={task.dueDate}
              onChange={(e) =>
                setTask((state) => ({...state, dueDate: e.target.value}))}
            />
          </div>
          <div className="col-md-5 mb-3">
            <label>Progress</label>
            <select
              className="form-control"
              name="progress"
              value={task.progress}
              onChange={(e) =>
                setTask((state) => ({...state, progress: e.target.value}))}
            >
              <option value="0">0%</option>
              <option value="10">10%</option>
              <option value="20">20%</option>
              <option value="30">30%</option>
              <option value="40">40%</option>
              <option value="50">50%</option>
              <option value="60">60%</option>
              <option value="70">70%</option>
              <option value="80">80%</option>
              <option value="90">90%</option>
              <option value="100">100%</option>
            </select>
          </div>
          <div className="col-md-5 mb-3">
            <label>Project</label>
            <select className="form-select" name="taskProject"
                    onChange={(e) =>
                      setTask((state) => ({...state, projectName: e.target.value}))}>
              <option value="">Choose project</option>
              {listProjects.map((project) =>
                <option key={project.id} value={JSON.stringify(project)}>
                  {project.name}
                </option>
              )}
            </select>
          </div>
          <button
            className="btn btn-primary"
            onClick={(e) => updateTask(e)}>Update
          </button>
        </form>
      </div>
    </div>
  )
}

export default UpdateTaskForm;