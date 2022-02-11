import React, {useState} from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Home from "./pages/Home";
import CreateTask from "./pages/CreateTask";
import ShowTasks from "./pages/ShowTasks";
import CreateProject from "./pages/CreateProject";
import NavigationBar from "./components/UI/header/NavigationBar";
import Task from "./pages/Task";
import UpdateTask from "./pages/UpdateTask";

const Routers = () => {
  const [searchWord, setSearchWord] = useState("");

  return (
    <Router>
      <NavigationBar search={searchWord} onChange={setSearchWord}/>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/create-task" element={<CreateTask/>}/>
        <Route path="/create-project" element={<CreateProject/>}/>
        <Route path="/show-tasks" element={<ShowTasks search={searchWord}/>}/>
        <Route path="/task/:id" element={<Task/>}/>
        <Route path="/task/update/:id" element={<UpdateTask/>}/>
      </Routes>
    </Router>
  )
}

export default Routers;