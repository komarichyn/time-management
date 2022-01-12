import React from "react";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import Home from "./pages/Home";
import CreateTask from "./pages/CreateTask";
import ShowTasks from "./pages/ShowTasks";
import CreateProject from "./pages/CreateProject";

const Routers = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/create-task" element={<CreateTask/>}/>
        <Route path="/create-project" element={<CreateProject/>}/>
        <Route path="/show-tasks" element={<ShowTasks/>}/>
      </Routes>
    </Router>
  )
}

export default Routers;