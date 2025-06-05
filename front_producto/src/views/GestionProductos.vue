<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'

const URL = 'http://localhost:8080/productos'

const categoriaSeleccionada = ref('Todos')
const productos = ref([])
const producto = ref({
  nombreProducto: '',
  descripcion: '',
  precio: '',
  categoria: '',
  imagenUrl: ''
})

const editando = ref(false)
const imagenInvalida = ref(false)
let productoId = null

const cargarProductos = async () => {
  const res = await axios.get(`${URL}/traer-productos`)
  productos.value = res.data
}

const guardarProducto = async () => {
  if (editando.value) {
    await axios.put(`${URL}/editar-producto/${productoId}`, producto.value)
  } else {
    await axios.post(`${URL}/insertar-producto`, producto.value)
  }
  resetFormulario()
  await cargarProductos()
}

const editarProducto = (p) => {
  producto.value = { ...p }
  productoId = p.id
  editando.value = true
  imagenInvalida.value = false
}

const eliminarProducto = async (id) => {
  if (confirm('¿Seguro que deseas eliminar este producto?')) {
    await axios.delete(`${URL}/eliminar-producto/${id}`)
    await cargarProductos()
  }
}

const resetFormulario = () => {
  producto.value = {
    nombreProducto: '',
    descripcion: '',
    precio: '',
    categoria: '',
    imagenUrl: ''
  }
  editando.value = false
  productoId = null
  imagenInvalida.value = false
}

const validarImagen = () => {
  imagenInvalida.value = false // se reseteará si no carga la imagen
}

const productosFiltrados = computed(() => {
  return categoriaSeleccionada.value === 'Todos'
    ? productos.value
    : productos.value.filter(p => p.categoria === categoriaSeleccionada.value)
})

onMounted(cargarProductos)
</script>

<template>
  <div class="container py-4">
    <h1 class="mb-4 text-center text-primary">📦 Gestión de Productos</h1>

    <!-- Formulario -->
    <form @submit.prevent="guardarProducto" class="row g-3 mb-4 border p-4 rounded bg-light">
      <div class="col-md-6">
        <input v-model="producto.nombreProducto" class="form-control" placeholder="Nombre del producto" required />
      </div>
      <div class="col-md-6">
        <input v-model="producto.descripcion" class="form-control" placeholder="Descripción" required />
      </div>
      <div class="col-md-4">
        <input v-model="producto.precio" type="number" step="0.01" class="form-control" placeholder="Precio" required />
      </div>
      <div class="col-md-4">
        <select v-model="producto.categoria" class="form-select" required>
          <option disabled value="">Seleccione una categoría</option>
          <option value="Desayuno">Desayuno</option>
          <option value="Comida">Comida</option>
          <option value="Refresco">Refresco</option>
          <option value="Postre">Postre</option>
        </select>
      </div>
      <div class="col-md-4">
        <input v-model="producto.imagenUrl" class="form-control" placeholder="URL de la imagen" @input="validarImagen" />
      </div>

      <!-- Vista previa -->
      <div v-if="producto.imagenUrl" class="col-12 text-center">
        <img
          :src="producto.imagenUrl"
          alt="Vista previa de la imagen"
          class="img-thumbnail mt-2"
          style="max-width: 200px; max-height: 200px; object-fit: contain;"
          @error="imagenInvalida = true"
          @load="imagenInvalida = false"
        />
        <p v-if="imagenInvalida" class="text-danger">⚠️ La URL no apunta a una imagen válida.</p>
      </div>

      <div class="col-12 d-flex justify-content-end gap-2">
        <button type="submit" class="btn btn-success" :disabled="imagenInvalida">
          {{ editando ? 'Actualizar' : 'Agregar' }}
        </button>
        <button type="button" @click="resetFormulario" class="btn btn-secondary">Cancelar</button>
      </div>
    </form>

    <!-- Filtro de categorías -->
    <div class="mb-3 d-flex align-items-center justify-content-center flex-wrap gap-2">
      <button
        v-for="cat in ['Todos', 'Desayuno', 'Comida', 'Refresco', 'Postre']"
        :key="cat"
        @click="categoriaSeleccionada = cat"
        class="btn"
        :class="categoriaSeleccionada === cat ? 'btn-primary' : 'btn-outline-secondary'"
      >
        {{ cat }}
      </button>
    </div>

    <!-- Tabla de productos -->
    <div class="table-responsive">
      <table class="table table-bordered table-hover align-middle text-center">
        <thead class="table-light">
          <tr>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>Categoría</th>
            <th>Imagen</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="p in productosFiltrados" :key="p.id">
            <td>{{ p.nombreProducto }}</td>
            <td>{{ p.descripcion }}</td>
            <td>$ {{ parseFloat(p.precio).toFixed(2) }}</td>
            <td>{{ p.categoria }}</td>
            <td>
              <img v-if="p.imagenUrl" :src="p.imagenUrl" alt="Imagen" class="img-thumbnail" style="max-width: 60px; max-height: 60px;" />
            </td>
            <td>
              <button @click="editarProducto(p)" class="btn btn-warning btn-sm me-2">✏️</button>
              <button @click="eliminarProducto(p.id)" class="btn btn-danger btn-sm">🗑️</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
body {
  background-image: url('https://images.unsplash.com/photo-1600891964599-f61ba0e24092?auto=format&fit=crop&w=1470&q=80');
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
  margin: 0;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
</style>
