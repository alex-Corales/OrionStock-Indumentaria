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
            Swal.fire({
                title: 'Error',
                text: 'No se encontró ID del local. Por favor, inicie sesión nuevamente.',
                icon: 'error',
                confirmButtonColor: '#1a1a1a'
            }).then(() => {
                window.location.href = '../index.html';
            });
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
            Swal.fire({
                title: '¡Producto Creado!',
                text: 'Ahora puedes agregar las variantes del producto',
                icon: 'success',
                confirmButtonColor: '#1a1a1a'
            });
        } catch (error) {
            console.error('Error al crear el producto:', error);
            Swal.fire({
                title: 'Error',
                text: 'Error al crear el producto. Por favor, intente nuevamente.',
                icon: 'error',
                confirmButtonColor: '#1a1a1a'
            });
        }
    });

    // Evento de adición de variantes
    varianteForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const cantidad = parseInt(document.getElementById('cantidadVariante').value);
        const varianteData = {
            talle: document.getElementById('talleVariante').value,
            color: document.getElementById('colorVariante').value,
            estado: cantidad > 0 ? "Disponible" : "Agotado",
            precioCompra: parseFloat(document.getElementById('precioCompraVariante').value),
            precioVenta: parseFloat(document.getElementById('precioVentaVariante').value),
            cantidad: cantidad
        };

        variantesProducto.push(varianteData);
        actualizarTablaVariantes();
        varianteForm.reset();

        // Mostrar mensaje de éxito al agregar variante
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true
        });
        Toast.fire({
            icon: 'success',
            title: 'Variante agregada correctamente'
        });
    });

    // Manejar la finalización del producto
    finalizarProductoBtn.addEventListener('click', async function() {
        if (variantesProducto.length === 0) {
            Swal.fire({
                title: 'Error',
                text: 'Debe agregar al menos una variante al producto.',
                icon: 'warning',
                confirmButtonColor: '#1a1a1a'
            });
            return;
        }

        // Mostrar confirmación antes de guardar
        const confirmarGuardado = await Swal.fire({
            title: '¿Guardar producto?',
            text: "¿Deseas guardar el producto con todas sus variantes?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#1a1a1a',
            cancelButtonColor: '#6c757d',
            confirmButtonText: 'Sí, guardar',
            cancelButtonText: 'Cancelar'
        });

        if (confirmarGuardado.isConfirmed) {
            try {
                const variantesData = variantesProducto.map(variante => ({
                    talle: variante.talle,
                    color: variante.color,
                    estado: variante.cantidad > 0 ? "Disponible" : "Agotado",
                    precioUnidadCompra: variante.precioCompra,
                    precioUnidadVenta: variante.precioVenta,
                    cantidad: variante.cantidad
                }));

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

                // Mostrar mensaje de éxito y redirigir
                Swal.fire({
                    title: '¡Guardado Exitoso!',
                    text: 'El producto y sus variantes se han guardado correctamente.',
                    icon: 'success',
                    confirmButtonColor: '#1a1a1a'
                }).then(() => {
                    window.location.href = 'inventory.html';
                });
            } catch (error) {
                console.error('Error al guardar las variantes:', error);
                Swal.fire({
                    title: 'Error',
                    text: 'Error al guardar las variantes. Por favor, intente nuevamente.',
                    icon: 'error',
                    confirmButtonColor: '#1a1a1a'
                });
            }
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
    tbody.innerHTML = '';

    variantesProducto.forEach((variante, index) => {
        const estado = variante.cantidad > 0 ? "Disponible" : "Agotado";
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${variante.talle}</td>
            <td>${variante.color}</td>
            <td><span class="badge ${getBadgeClass(estado)}">${estado}</span></td>
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
    Swal.fire({
        title: '¿Eliminar variante?',
        text: '¿Estás seguro de que deseas eliminar esta variante?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#1a1a1a',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            variantesProducto.splice(index, 1);
            actualizarTablaVariantes();
            
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true
            });
            Toast.fire({
                icon: 'success',
                title: 'Variante eliminada correctamente'
            });
        }
    });
}

function getBadgeClass(estado) {
    switch (estado) {
        case 'Disponible':
            return 'badge-estado disponible';
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
