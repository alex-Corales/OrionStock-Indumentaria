// Variables globales para el manejo de productos y variantes
let idRopa = null;
let variantesProducto = [];

// Cerrar sesión y redirigir a la página de inicio de sesión
function cerrarSesion() {
    localStorage.removeItem('localId');
    window.location.href = '../index.html';
}

// Cargar el sidebar cuando el documento esté listo
document.addEventListener('DOMContentLoaded', function() {    // Cargar el componente sidebar
    fetch('../components/sidebar.html')
        .then(response => response.text())
        .then(html => {
            document.getElementById('sidebar-container').innerHTML = html;
            // Activar el enlace de inventario en el sidebar
            document.querySelectorAll('.menu-link').forEach(link => link.classList.remove('active'));
            document.querySelector('a[href="inventory.html"]').classList.add('active');
        })
        .catch(error => {
            console.error('Error al cargar el sidebar:', error);
        });

    // Event listeners
    const productoForm = document.getElementById('productoForm');
    const varianteForm = document.getElementById('varianteForm');
    const guardarProductoBtn = document.getElementById('guardarProductoBtn');
    const finalizarProductoBtn = document.getElementById('finalizarProductoBtn');

    // Manejar la creación del producto base
    guardarProductoBtn.addEventListener('click', async function() {
        const localId = localStorage.getItem('localId');
        if (!localId) {
            alert('No se encontró ID del local. Por favor, inicie sesión nuevamente.');
            window.location.href = '../index.html';
            return;
        }

        // Validar el formulario del producto
        if (!productoForm.checkValidity()) {
            productoForm.reportValidity();
            return;
        }

        const productoData = {
            nombre: document.getElementById('nombreProducto').value,
            categoria: document.getElementById('categoriaProducto').value
        };

        try {
            const response = await fetch(`/api/ropa/crear/${localId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(productoData)
            });

            if (!response.ok) {
                throw new Error('Error al crear el producto');
            }

            idRopa = await response.json();

            // Mostrar el formulario de variantes
            document.getElementById('noProductoAlert').style.display = 'none';
            varianteForm.style.display = 'block';
            
            // Cambiar a la pestaña de variantes
            const variantesTab = document.getElementById('variantes-tab');
            const tab = new bootstrap.Tab(variantesTab);
            tab.show();

            // Mostrar mensaje de éxito
            alert('Producto creado exitosamente. Ahora puede agregar variantes.');
        } catch (error) {
            console.error('Error al crear el producto:', error);
            alert('Error al crear el producto. Por favor, intente nuevamente.');
        }
    });

    // Manejar la adición de variantes
    varianteForm.addEventListener('submit', function(e) {
        e.preventDefault();        const varianteData = {
            talle: document.getElementById('talleVariante').value,
            color: document.getElementById('colorVariante').value,
            estado: document.getElementById('estadoVariante').value,
            precioCompra: parseFloat(document.getElementById('precioCompraVariante').value),
            precioVenta: parseFloat(document.getElementById('precioVentaVariante').value),
            cantidad: parseInt(document.getElementById('cantidadVariante').value)
        };

        variantesProducto.push(varianteData);
        actualizarTablaVariantes();
        varianteForm.reset();
    });

    // Manejar la finalización del producto
    finalizarProductoBtn.addEventListener('click', async function() {
        if (variantesProducto.length === 0) {
            alert('Debe agregar al menos una variante al producto.');
            return;
        }

        try {
            // Preparar el array de variantes con el formato correcto
            const variantesData = variantesProducto.map(variante => ({
                talle: variante.talle,
                color: variante.color,
                estado: variante.estado,
                precioUnidadCompra: variante.precioCompra,
                precioUnidadVenta: variante.precioVenta,
                cantidad: variante.cantidad
            }));

            // Enviar todas las variantes en una sola llamada
            const response = await fetch(`/api/variante/crear/${idRopa}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(variantesData)
            });

            if (!response.ok) {
                throw new Error('Error al guardar las variantes');
            }

            // Redirigir al inventario
            alert('Producto y variantes guardados exitosamente.');
            window.location.href = 'inventory.html';
        } catch (error) {
            console.error('Error al guardar las variantes:', error);
            alert('Error al guardar las variantes. Por favor, intente nuevamente.');
        }
    });

    // Agregar evento al botón de cerrar sesión
    document.querySelector('a[href="#"].user-menu-item:last-child').addEventListener('click', function(e) {
        e.preventDefault();
        cerrarSesion();
    });
});

function actualizarTablaVariantes() {
    const tbody = document.querySelector('#variantesTable tbody');
    tbody.innerHTML = '';    variantesProducto.forEach((variante, index) => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${variante.talle}</td>
            <td>${variante.color}</td>
            <td><span class="badge ${getBadgeClass(variante.estado)}">${variante.estado}</span></td>
            <td>$${variante.precioCompra.toLocaleString('es-AR')}</td>
            <td>$${variante.precioVenta.toLocaleString('es-AR')}</td>
            <td>${variante.cantidad}</td>
            <td>
                <button class="btn btn-sm btn-link text-danger" onclick="eliminarVariante(${index})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function eliminarVariante(index) {
    variantesProducto.splice(index, 1);
    actualizarTablaVariantes();
}

function getBadgeClass(estado) {
    switch (estado) {
        case 'Disponible':
            return 'badge-estado disponible';
        case 'Pocas':
            return 'badge-estado pocas';
        case 'Agotado':
            return 'badge-estado agotado';
        default:
            return 'badge-estado';
    }
}

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

// Al cargar el documento, configurar el evento de cerrar sesión
document.addEventListener('DOMContentLoaded', function() {
    const cerrarSesionBtn = document.querySelector('.user-menu-item[href="#"]:last-child');
    if (cerrarSesionBtn) {
        cerrarSesionBtn.addEventListener('click', function(e) {
            e.preventDefault();
            cerrarSesion();
        });
    }
});
