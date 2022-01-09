import axios from "axios";

const INDEX_URL = "http://localhost:8080/";
// const GETPROJECTS_URL = "http://localhost:8080/create-task";
const CREATETASK_URL = "http://localhost:8080/add-task";
const SHOWTASKS_URL = "http://localhost:8080/show-tasks/page/1";
const GET_ALL_PROJECTS_URL = "http://localhost:8080/get-all-projects";
const CREATEPROJECT_URL = "";

class TasksService {
    getMainPage() {
        return axios.get(INDEX_URL);
    }
    createTask(task) {
        return axios.post(CREATETASK_URL, task);
    }
    // getProjects() {
    //     return axios.get(GETPROJECTS_URL);
    // }
    getShowTasksPage() {
        return axios.get(SHOWTASKS_URL);
    }
    getAllProjects() {
        return axios.get(GET_ALL_PROJECTS_URL);
    }
    createProject(project) {
        return axios.get(CREATEPROJECT_URL);
    }
}

export default new TasksService();