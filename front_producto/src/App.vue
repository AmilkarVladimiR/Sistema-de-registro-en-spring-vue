<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const producto = ref({
  nombreProducto: '',
  descripcion: '',
  precio: '',
  categoria: '',
  imagenUrl: ''
})
const productos = ref([])
const editando = ref(false)
let productoId = null

const URL = 'http://localhost:8080/productos'

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
}

onMounted(cargarProductos)
</script>

<template>
  <div class="p-6 max-w-4xl mx-auto">
    <h1 class="text-2xl font-bold mb-4 text-center">📦 Gestión de Productos</h1>

    <form @submit.prevent="guardarProducto" class="formulario grid gap-4">
      <input v-model="producto.nombreProducto" placeholder="Nombre del producto" class="input" required />
      <textarea v-model="producto.descripcion" placeholder="Descripción" class="input" required></textarea>
      <input type="number" step="0.01" v-model="producto.precio" placeholder="Precio" class="input" required />
      <select v-model="producto.categoria" class="input" required>
        <option disabled value="">Selecciona una categoría</option>
        <option>Desayuno</option>
        <option>Comida</option>
        <option>Refresco</option>
        <option>Postre</option>
      </select>
      <input v-model="producto.imagenUrl" placeholder="URL de la imagen" class="input" />

      <button type="submit" class="bg-blue-500 hover:bg-blue-600 text-black py-2 rounded">
  {{ editando ? 'Actualizar' : 'Registrar' }} Producto
</button>

    </form>

    <!-- Tabla centrada y responsive -->
    <div class="tabla-container overflow-x-auto">
      <table class="tabla w-full border-collapse bg-white shadow rounded-lg">
        <thead class="bg-gray-200">
          <tr>
            <th class="border p-2">Nombre</th>
            <th class="border p-2">Descripción</th>
            <th class="border p-2">Precio</th>
            <th class="border p-2">Categoría</th>
            <th class="border p-2">Imagen</th>
            <th class="border p-2">Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="p in productos" :key="p.id" class="text-center">
            <td class="border p-2">{{ p.nombreProducto }}</td>
            <td class="border p-2">{{ p.descripcion }}</td>
            <td class="border p-2">$ {{ p.precio.toFixed(2) }}</td>
            <td class="border p-2">{{ p.categoria }}</td>
            <td class="border p-2">
              <img :src="p.imagenUrl" alt="Imagen" class="w-16 h-16 object-cover mx-auto" v-if="p.imagenUrl" />
            </td>
            <td class="border p-2 flex gap-2 justify-center">
              <button @click="editarProducto(p)" class="bg-yellow-400 px-2 py-1 rounded">✏️</button>
              <button @click="eliminarProducto(p.id)" class="bg-red-500 text-white px-2 py-1 rounded">🗑️</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
:root {
  --fondo-url: url('https://images.unsplash.com/photo-1600891964599-f61ba0e24092?auto=format&fit=crop&w=1470&q=80');
}

body {
  background-image: var(--fondo-url);
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
  margin: 0;
  font-family: sans-serif;
}

.p-6 {
  background-color: rgba(255, 255, 255, 0.92);
  border-radius: 1rem;
  padding: 2rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  margin-top: 2rem;
}

.input {
  border: 1px solid #ccc;
  padding: 0.5rem;
  border-radius: 0.375rem;
  background-color: #fff;
  width: 100%;
}

.formulario {
  max-width: 400px;
  margin: 0 auto 2rem auto;
  background-color: rgba(255, 255, 255, 0.92);
  padding: 1.5rem;
  border-radius: 1rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
}

.tabla-container {
  max-width: 800px;
  margin: 0 auto;
  border-radius: 1rem;
  overflow-x: auto;
}

.tabla {
  min-width: 600px;
  background-color: rgba(255, 255, 255, 0.95);
}

button {
  cursor: pointer;
  transition: transform 0.2s ease;
}

button:hover {
  transform: scale(1.05);
}

/* Responsive para pantallas pequeñas */
@media (max-width: 768px) {
  .formulario, .tabla-container {
    width: 95%;
    padding: 1rem;
  }

  .tabla {
    font-size: 0.875rem;
  }

  th, td {
    padding: 0.5rem;
  }
}
</style>
