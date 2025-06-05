<template>
  <div>
    <!-- Navbar estilo restaurante con colores mejorados -->
    <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: 	#0F52BA;">
      <div class="container">
        <!-- Logo o nombre -->
        <router-link class="navbar-brand fw-bold fs-3" to="/">
          🍽️ Restaurant Paris
        </router-link>

        <!-- Botón responsive -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarRestaurante" aria-controls="navbarRestaurante" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Enlaces -->
        <div class="collapse navbar-collapse justify-content-center" id="navbarRestaurante">
          <ul class="navbar-nav mb-2 mb-lg-0">
            <li class="nav-item mx-3">
              <router-link class="nav-link fs-5" to="/">🏠 Productos</router-link>
            </li>
            <li class="nav-item mx-3">
              <router-link class="nav-link fs-5" to="/gestion">🛠️ Gestión</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- Contenido principal -->
    <div class="container my-4">
      <router-view />
    </div>
  </div>
</template>
