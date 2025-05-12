// Función para manejar el menú de usuario
function toggleUserMenu() {
    const dropdown = document.getElementById('userMenuDropdown');
    dropdown.classList.toggle('show');

    // Cerrar el menú al hacer clic fuera de él
    document.addEventListener('click', function closeMenu(e) {
        if (!e.target.closest('.user-menu')) {
            dropdown.classList.remove('show');
            document.removeEventListener('click', closeMenu);
        }
    });
}

// Función para cerrar sesión
function cerrarSesion() {
    localStorage.removeItem('localId');
    window.location.href = '../index.html';
}

// Cargar el sidebar cuando el documento esté listo
document.addEventListener('DOMContentLoaded', function() {
    // Cargar el componente sidebar
    fetch('../components/sidebar.html')
        .then(response => response.text())
        .then(html => {
            document.getElementById('sidebar-container').innerHTML = html;
        })
        .catch(error => {
            console.error('Error al cargar el sidebar:', error);
        });

    // Configurar el evento de cerrar sesión
    const cerrarSesionBtn = document.querySelector('.user-menu-item[href="#"]:last-child');
    if (cerrarSesionBtn) {
        cerrarSesionBtn.addEventListener('click', function(e) {
            e.preventDefault();
            cerrarSesion();
        });
    }
});