document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    
    loginForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        
        try {
            const response = await fetch('http://localhost:80/api/usuario/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    correo: username,
                    contraseña: password
                })
            });

            const responseText = await response.text();
            console.log('Respuesta del servidor (texto):', responseText);

            if (response.ok) {
                try {
                    // Intentar parsear la respuesta como JSON
                    const data = JSON.parse(responseText);
                    console.log('Datos parseados:', data);
                    
                    // Asumiendo que el servidor devuelve directamente el ID como un número o string
                    const localId = data;
                    
                    if (localId) {
                        console.log('ID del local a guardar:', localId);
                        localStorage.setItem('localId', localId.toString());
                        
                        // Verificar que se guardó correctamente
                        const savedId = localStorage.getItem('localId');
                        console.log('ID guardado en localStorage:', savedId);
                        
                        if (savedId) {
                            window.location.href = 'pages/dashboard.html';
                        } else {
                            throw new Error('No se pudo guardar el ID en localStorage');
                        }
                    } else {
                        throw new Error('La respuesta no contiene un ID válido');
                    }
                } catch (parseError) {
                    console.error('Error al procesar la respuesta:', parseError);
                    console.error('Respuesta recibida:', responseText);
                    alert('Error al procesar la respuesta del servidor');
                }
            } else {
                console.error('Error en la respuesta:', response.status, responseText);
                alert('Usuario o contraseña incorrectos');
            }
        } catch (error) {
            console.error('Error de conexión:', error);
            alert('Error al conectar con el servidor');
        }
    });
});