import React, {useEffect, useState} from "react";
import {Link, useNavigate, useLocation} from "react-router-dom";
import TasksService from "../../../services/TasksService";
import DataTable from "../showTasks/DataTable";

const NavagationBar = () => {
  const [name, setName] = useState("");
  const navigate = useNavigate();
  const location = useLocation().pathname;

  const [search, setSearch] = useState("");
  const [searchResult, setSearchResult] = useState([]);

  const saveTask = (e) => {
    e.preventDefault();
    const task = {name};
    TasksService.createTask(task).then((res) => {
        navigate("/show-tasks"); //FIXME:navigate to task page
      })
  };

  // const searchFunc = (rows) => {
  //   return rows.filter((row) => row.name.toLowerCase().indexOf(search) > -1);
  // }

  // const find = (e) => {
  //   e.preventDefault();
  //   TasksService.getShowTasksPage(search).then(() => {
  //
  //   })
  // }

  return (
    <div className="p-3 bg-dark text-white">
      <div className="container-fluid">
        <div className="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
          <ul className="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
            <li className="nav-link px-2 text-secondary">
              <Link to="/" className="text-decoration-none text-secondary">
                Time management
              </Link>
            </li>
            <li>
              <Link to="/create-task" className="nav-link px-2 text-white">
                Create task
              </Link>
            </li>
            <li>
              {location === "/create-task" ?
                <Link to="/create-project" className="nav-link px-2 text-white">
                  Create project
                </Link>
                : ""
              }
            </li>
            <li>
              <Link to="/show-tasks" className="nav-link px-2 text-white">
                Task list
              </Link>
            </li>
            <li>
              <form>
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
          <form className="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
            <input
              type="text"
              name="searchBy"
              className="form-control form-control-dark"
              placeholder="Search..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              // onKeyPress={(e) => e.key === "Enter" && find(e)}
            />

            {/*{name.filter((val) => {
              if(searchTerm === "") {
                return val;
              } else if (val.name.toLowerCase().includes(searchTerm.toLowerCase())) {
                return val;
              }
            }).map((val, key) => {
              return (
                <div key={key}>
                  {val.name}
                </div>
              )
            })}*/}
          </form>
        </div>
      </div>
    </div>
  );
};

export default NavagationBar;