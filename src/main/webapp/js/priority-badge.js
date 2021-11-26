(function () {
    const priority = document.querySelectorAll('.badge');
    priority.forEach(function (element) {
        if (element.textContent === "NORMAL") {
            element.classList.add("bg-warning");
        }
        if (element.textContent === "LOW") {
            element.classList.add("bg-success");
        }
        if (element.textContent === "HIGH") {
            element.classList.add("bg-danger");
        }
        if (element.textContent === "PAUSE") {
            element.classList.add("bg-secondary");
        }
    })
})()