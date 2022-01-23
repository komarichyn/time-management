const SetBadge = (priority) => {
  let badge = '';
  if (priority === "NORMAL") {
    badge = 'warning';
  }
  if (priority === "LOW") {
    badge = 'success';
  }
  if (priority === "HIGH") {
    badge = 'danger';
  }
  if (priority === "PAUSE") {
    badge = 'secondary';
  }
  return badge;
}

export default SetBadge;