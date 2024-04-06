function registerForm() {
    const firstname = document.getElementById('regFirstname').value;
    const lastname = document.getElementById('regLastname').value;
    const email = document.getElementById('regEmail').value;
    const password = document.getElementById('regPassword').value;
    const role = document.getElementById('regRole').value;

    fetch('/api/v1/auth/register', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            firstname: firstname,
            lastname: lastname,
            email: email,
            password: password,
            role: role
        })
    })
    .then(response => response.json())
    .then(data => alert('Registro exitoso: ' + JSON.stringify(data)))
    .catch(error => console.error('Error en registro:', error));
}
