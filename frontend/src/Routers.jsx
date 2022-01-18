import React from "react";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import Home from "./pages/Home";
import CreateTask from "./pages/CreateTask";
import ShowTasks from "./pages/ShowTasks";
import CreateProject from "./pages/CreateProject";
import NavagationBar from "./components/UI/header/NavigationBar";

const Routers = () => {
  return (
    <Router>
      <NavagationBar/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/create-task" element={<CreateTask/>}/>
        <Route path="/create-project" element={<CreateProject/>}/>
        <Route path="/show-tasks" element={<ShowTasks/>}/>
        {/*<Route path="/show-tasks" element={<ShowTasks search={search}/>}/>*/}
      </Routes>
    </Router>
  )
}

export default Routers;