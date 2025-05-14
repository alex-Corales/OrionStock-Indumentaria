// Cargar el sidebar cuando el documento esté listo
document.addEventListener('DOMContentLoaded', function() {
    // Cargar el componente sidebar
    fetch('../components/sidebar.html')
        .then(response => response.text())
        .then(html => {
            document.getElementById('sidebar-container').innerHTML = html;
            // Activar el enlace de inventario en el sidebar
            const inventoryLink = document.querySelector('a[href="inventory.html"]');
            if (inventoryLink) {
                document.querySelectorAll('.menu-link').forEach(link => link.classList.remove('active'));
                inventoryLink.classList.add('active');
            }
        })
        .catch(error => {
            console.error('Error al cargar el sidebar:', error);
        });

    // Inicializar la tabla de inventario y configurar los filtros
    initializeInventoryTable();

    // Sincronizar los campos de búsqueda
    const searchInput = document.getElementById('searchInput');
    const modalSearchInput = document.getElementById('modalSearchInput');
    const modalCategoryFilter = document.getElementById('modalCategoryFilter');
    
    // Configurar el botón de búsqueda rápida
    document.getElementById('searchButton').addEventListener('click', function() {
        modalSearchInput.value = searchInput.value;
        applyFilters();
    });

    // Búsqueda rápida al presionar Enter
    searchInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            modalSearchInput.value = searchInput.value;
            applyFilters();
        }
    });

    // Aplicar filtros desde el modal
    document.getElementById('applyFiltersBtn').addEventListener('click', function() {
        searchInput.value = modalSearchInput.value;
        applyFilters();
        bootstrap.Modal.getInstance(document.getElementById('filtersModal')).hide();
    });

    // Event listeners para el modal de nuevo producto
    const nuevoProductoModal = document.getElementById('nuevoProductoModal');
    const productoForm = document.getElementById('productoForm');
    const varianteForm = document.getElementById('varianteForm');
    const guardarProductoBtn = document.getElementById('guardarProductoBtn');

    // Reiniciar formularios cuando se abre el modal
    nuevoProductoModal.addEventListener('show.bs.modal', function () {
        productoActual = null;
        variantesProducto = [];
        productoForm.reset();
        varianteForm.reset();
        varianteForm.style.display = 'none';
        document.getElementById('noProductoAlert').style.display = 'block';
        actualizarTablaVariantes();
    });

    // Manejar la creación del producto base
    guardarProductoBtn.addEventListener('click', async function() {
        const localId = localStorage.getItem('localId');
        if (!localId) {
            alert('No se encontró ID del local. Por favor, inicie sesión nuevamente.');
            window.location.href = '../index.html';
            return;
        }

        if (!productoActual) {
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

                const idRopa = await response.json();

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
        } else {
            // Si ya existe el producto base, guardamos las variantes
            if (variantesProducto.length === 0) {
                alert('Debe agregar al menos una variante al producto.');
                return;
            }

            try {
                // Guardar todas las variantes
                const promesasVariantes = variantesProducto.map(variante => {
                    const varianteData = {
                        talle: variante.talle,
                        color: variante.color,
                        estado: "Disponible", // Por defecto cuando se crea
                        precioUnidadCompra: variante.precioCompra,
                        precioUnidadVenta: variante.precioVenta,
                        cantidad: variante.cantidad
                    };

                    return fetch(`/api/variante/crear/${idRopa}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(varianteData)
                    });
                });

                await Promise.all(promesasVariantes);

                // Cerrar el modal y actualizar la tabla
                const modal = bootstrap.Modal.getInstance(nuevoProductoModal);
                modal.hide();
                await initializeInventoryTable(); // Recargar la tabla

                // Mostrar mensaje de éxito
                alert('Producto y variantes guardados exitosamente.');
            } catch (error) {
                console.error('Error al guardar las variantes:', error);
                alert('Error al guardar las variantes. Por favor, intente nuevamente.');
            }
        }
    });

    // Manejar la adición de variantes
    varianteForm.addEventListener('submit', function(e) {
        e.preventDefault();

        const varianteData = {
            talle: document.getElementById('talleVariante').value,
            color: document.getElementById('colorVariante').value,
            cantidad: parseInt(document.getElementById('cantidadVariante').value),
            precioCompra: parseFloat(document.getElementById('precioCompraVariante').value),
            precioVenta: parseFloat(document.getElementById('precioVentaVariante').value)
        };

        variantesProducto.push(varianteData);
        actualizarTablaVariantes();
        varianteForm.reset();
    });

    // Agregar evento al botón de cerrar sesión
    document.querySelector('a[href="#"].user-menu-item:last-child').addEventListener('click', function(e) {
        e.preventDefault();
        cerrarSesion();
    });
});

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

function cerrarSesion() {
    localStorage.removeItem('localId');
    window.location.href = '../index.html';
}

async function initializeInventoryTable() {
    const localId = localStorage.getItem('localId');
    if (!localId) {
        alert('No se encontró ID del local. Por favor, inicie sesión nuevamente.');
        window.location.href = '../index.html';
        return;
    }

    try {
        const response = await fetch(`/api/ropa/traer/${localId}`);
        if (!response.ok) {
            throw new Error('Error al obtener los datos del inventario');
        }

        const inventario = await response.json();
        console.log(inventario)
        const tbody = document.querySelector('table tbody');
        tbody.innerHTML = ''; // Limpiar tabla existente

        inventario.forEach((item, index) => {            const getBadgeClass = (estado) => {
                switch (estado) {
                    case 'Disponible':
                        return 'badge-estado disponible';
                    case 'Agotado':
                        return 'badge-estado agotado';
                    default:
                        return 'badge-estado';
                }
            };            
            const row = document.createElement('tr');            
            row.innerHTML = `
                <td class="px-4">${item.nombre}</td>
                <td>${item.categoria}</td>
                <td>${item.talle}</td>
                <td>${item.color}</td>
                <td>${item.cantidad}</td>
                <td>$${item.precioUnidadCompra.toLocaleString('es-AR')}</td>
                <td>$${item.precioUnidadVenta.toLocaleString('es-AR')}</td>
                <td><span class="badge ${getBadgeClass(item.estado)}">${item.estado}</span></td>
                <td class="text-end px-4">                
                        <button class="btn btn-sm btn-link text-danger" onclick="eliminarItem(${item.idVariante})">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            `;
            tbody.appendChild(row);
        });
    } catch (error) {
        console.error('Error:', error);
        alert('Error al cargar el inventario. Por favor, intente nuevamente.');
    }
}

// Función para aplicar los filtros
async function applyFilters() {
    const localId = localStorage.getItem('localId');
    if (!localId) {
        alert('No se encontró ID del local. Por favor, inicie sesión nuevamente.');
        window.location.href = '../index.html';
        return;
    }    const searchTerm = document.getElementById('modalSearchInput').value;
    const category = document.getElementById('modalCategoryFilter').value;

    try {
        // Construir la URL con los parámetros de búsqueda
        let url = `/api/ropa/traer/${localId}/filtro?`;
        if (category) {
            url += `categoria=${encodeURIComponent(category)}&`;
        }
        if (searchTerm) {
            url += `nombre=${encodeURIComponent(searchTerm)}`;
        }

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Error al obtener los productos filtrados');
        }

        const productos = await response.json();
        updateInventoryTable(productos);
    } catch (error) {
        console.error('Error al aplicar los filtros:', error);
        alert('Error al buscar productos. Por favor, intente nuevamente.');
    }
}

// Función para actualizar la tabla con los productos filtrados
function updateInventoryTable(productos) {
    const tbody = document.querySelector('table tbody');
    tbody.innerHTML = ''; // Limpiar la tabla

    productos.forEach((producto, index) => {        const getBadgeClass = (estado) => {
            switch (estado) {
                case 'Disponible':
                    return 'badge-estado disponible';
                case 'Agotado':
                    return 'badge-estado agotado';
                default:
                    return 'badge-estado';
            }
        };

        const row = document.createElement('tr');        row.innerHTML = `            <td class="px-4">${producto.nombre}</td>
            <td>${producto.categoria}</td>
            <td>${producto.talle}</td>
            <td>${producto.color || '-'}</td>
            <td>${producto.cantidad}</td>
            <td>$${producto.precioUnidadCompra.toLocaleString('es-AR')}</td>
            <td>$${producto.precioUnidadVenta.toLocaleString('es-AR')}</td>
            <td>
                <span class="badge ${getBadgeClass(producto.estado)}">
                    ${producto.estado}
                </span>
            </td>            <td class="text-end px-4">
                <button class="btn btn-sm btn-link text-danger" onclick="eliminarItem(${producto.idVariante})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function eliminarItem(idVariante) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "No podrás revertir esta acción",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#1a1a1a',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/api/variante/eliminar-variante/${idVariante}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al eliminar la variante');
                }
                // Actualizar la tabla después de eliminar
                initializeInventoryTable();
                Swal.fire({
                    title: '¡Eliminado!',
                    text: 'La variante ha sido eliminada exitosamente.',
                    icon: 'success',
                    confirmButtonColor: '#1a1a1a'
                });
            })
            .catch(error => {
                console.error('Error:', error);
                Swal.fire({
                    title: 'Error',
                    text: 'Hubo un problema al eliminar la variante. Por favor, intente nuevamente.',
                    icon: 'error',
                    confirmButtonColor: '#1a1a1a'
                });
            });
        }
    });
}

function actualizarTablaVariantes() {
    const tbody = document.querySelector('#variantesTable tbody');
    tbody.innerHTML = '';

    variantesProducto.forEach((variante, index) => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${variante.talle}</td>
            <td>${variante.color}</td>
            <td>${variante.cantidad}</td>
            <td>$${variante.precioVenta.toLocaleString('es-AR')}</td>
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

// Variables globales para el manejo de productos y variantes
let productoActual = null;
let variantesProducto = [];