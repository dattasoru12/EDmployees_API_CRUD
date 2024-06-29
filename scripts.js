document.addEventListener("DOMContentLoaded", function() {
    const addEmployeeForm = document.getElementById("addEmployeeForm");
    const employeeListDiv = document.getElementById("employeeList");

    addEmployeeForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const formData = new FormData(addEmployeeForm);
        const employee = {
            emp_name: formData.get("name"),
            emp_age: formData.get("age"),
            emp_city: formData.get("city"),
            emp_salary: formData.get("salary")
        };
        addEmployee(employee);
    });

    function addEmployee(employee) {
        fetch('/api/employees', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(employee)
        })
        .then(response => response.text())
        .then(data => {
            alert(data);
            fetchEmployees();
            addEmployeeForm.reset();
        })
        .catch(error => console.error('Error adding employee:', error));
    }

    function fetchEmployees() {
        fetch('/api/employees')
            .then(response => response.json())
            .then(data => {
                let html = '<table><tr><th>ID</th><th>Name</th><th>Age</th><th>City</th><th>Salary</th></tr>';
                data.forEach(employee => {
                    html += `<tr><td>${employee.id}</td><td>${employee.emp_name}</td><td>${employee.emp_age}</td><td>${employee.emp_city}</td><td>${employee.emp_salary}</td></tr>`;
                });
                html += '</table>';
                employeeListDiv.innerHTML = html;
            })
            .catch(error => console.error('Error fetching employees:', error));
    }

    fetchEmployees();
});
