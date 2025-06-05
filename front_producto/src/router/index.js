import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import GestionProductos from '../views/GestionProductos.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/gestion',
    name: 'GestionProductos',
    component: GestionProductos
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
