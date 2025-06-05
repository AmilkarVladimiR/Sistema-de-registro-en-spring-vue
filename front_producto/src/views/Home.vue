<template>
  <div class="container py-5">
    <h1 class="text-center mb-5 display-5 fw-bold text-primary">🍽 Bienvenido a nuestro Menú</h1>

    <div class="row g-4">
      <!-- Sección Desayunos -->
      <div class="col-md-6">
        <section class="p-4 border rounded shadow-sm bg-white h-100">
          <h2 class="text-center mb-4 text-danger bg-light py-2 rounded">🥐 Menú de Desayunos</h2>
          <div class="row g-4">
            <div 
              v-for="p in filtrarPorCategoria('Desayuno')" 
              :key="p.id" 
              class="col-sm-6 col-md-6">
              <div class="card h-100 text-center shadow-sm">
                <img v-if="p.imagenUrl" :src="p.imagenUrl" class="card-img-top object-fit-contain" style="height: 200px;" alt="Producto">
                <div class="card-body">
                  <h5 class="card-title">{{ p.nombreProducto }}</h5>
                  <p class="card-text">{{ p.descripcion }}</p>
                  <p class="text-success fw-semibold">$ {{ p.precio.toFixed(2) }}</p>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>

      <!-- Sección Comidas -->
      <div class="col-md-6">
        <section class="p-4 border rounded shadow-sm bg-white h-100">
          <h2 class="text-center mb-4 text-warning bg-light py-2 rounded">🍛 Menú de Comidas</h2>
          <div class="row g-4">
            <div 
              v-for="p in filtrarPorCategoria('Comida')" 
              :key="p.id" 
              class="col-sm-6 col-md-6">
              <div class="card h-100 text-center shadow-sm">
                <img v-if="p.imagenUrl" :src="p.imagenUrl" class="card-img-top object-fit-contain" style="height: 200px;" alt="Producto">
                <div class="card-body">
                  <h5 class="card-title">{{ p.nombreProducto }}</h5>
                  <p class="card-text">{{ p.descripcion }}</p>
                  <p class="text-success fw-semibold">$ {{ p.precio.toFixed(2) }}</p>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>

      <!-- Sección Refrescos -->
      <div class="col-md-6">
        <section class="p-4 border rounded shadow-sm bg-white h-100">
          <h2 class="text-center mb-4 text-info bg-light py-2 rounded">🥤 Menú de Refrescos</h2>
          <div class="row g-4">
            <div 
              v-for="p in filtrarPorCategoria('Refresco')" 
              :key="p.id" 
              class="col-sm-6 col-md-6">
              <div class="card h-100 text-center shadow-sm">
                <img v-if="p.imagenUrl" :src="p.imagenUrl" class="card-img-top object-fit-contain" style="height: 200px;" alt="Producto">
                <div class="card-body">
                  <h5 class="card-title">{{ p.nombreProducto }}</h5>
                  <p class="card-text">{{ p.descripcion }}</p>
                  <p class="text-success fw-semibold">$ {{ p.precio.toFixed(2) }}</p>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>

      <!-- Sección Postres -->
      <div class="col-md-6">
        <section class="p-4 border rounded shadow-sm bg-white h-100">
          <h2 class="text-center mb-4 text-pink bg-light py-2 rounded">🍰 Menú de Postres</h2>
          <div class="row g-4">
            <div 
              v-for="p in filtrarPorCategoria('Postre')" 
              :key="p.id" 
              class="col-sm-6 col-md-6">
              <div class="card h-100 text-center shadow-sm">
                <img v-if="p.imagenUrl" :src="p.imagenUrl" class="card-img-top object-fit-contain" style="height: 200px;" alt="Producto">
                <div class="card-body">
                  <h5 class="card-title">{{ p.nombreProducto }}</h5>
                  <p class="card-text">{{ p.descripcion }}</p>
                  <p class="text-success fw-semibold">$ {{ p.precio.toFixed(2) }}</p>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const productos = ref([])

const URL = 'http://localhost:8080/productos'

const cargarProductos = async () => {
 const res = await axios.get(`${URL}/traer-productos`)

  productos.value = res.data
}

const filtrarPorCategoria = (cat) => {
  return productos.value.filter(p => p.categoria === cat)
}

onMounted(cargarProductos)
</script>

<style scoped>
.object-fit-contain {
  object-fit: contain;
}
.text-pink {
  color: #ce1f76;
}
</style>