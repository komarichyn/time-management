const ChangeStatus = (id, status, setStatus) => {
  const newStatus = {status: ""};

  // let newStatus = status === "TODO" ? setStatus(id, newStatus) : newStatus === "IN_PROGRESS";
  if (status === "TODO") {
    newStatus["status"] = "IN_PROGRESS";
    setStatus(id, newStatus);
  } else if (status === "IN_PROGRESS") {
    newStatus["status"] = "COMPLETE";
    setStatus(id, newStatus);
  } else {
    newStatus["status"] = "TODO";
    setStatus(id, newStatus);
  }
}

export default ChangeStatus;