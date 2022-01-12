import axios from "axios";

const INDEX_URL = "http://localhost:8080/";
const CREATETASK_URL = "http://localhost:8080/create-task";
const SHOWTASKS_URL = "http://localhost:8080/show-tasks/page/1";
const GET_ALL_PROJECTS_URL = "http://localhost:8080/get-all-projects";
const CREATEPROJECT_URL = "http://localhost:8080/add-project";

class TasksService {
  getMainPage() {
    return axios.get(INDEX_URL);
  }

  createTask(task) {
    return axios.post(CREATETASK_URL, task);
  }

  getShowTasksPage() {
    return axios.get(SHOWTASKS_URL);
  }

  getAllProjects() {
    return axios.get(GET_ALL_PROJECTS_URL);
  }

  createProject(project) {
    return axios.post(CREATEPROJECT_URL, project);
  }
}

export default new TasksService();