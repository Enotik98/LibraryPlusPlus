import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Authorization from "@/views/Authorization.vue";
import Profile from "@/views/Profile.vue";
import Orders from "@/views/Orders.vue";
import OrderInfo from "@/views/OrderInfo.vue";
import Product from "@/views/Product.vue";

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile
  },
  {
    path: '/orders',
    name: 'Orders',
    component: Orders
  },
  {
    path: '/orders/:id',
    name: 'OrderInfo',
    component: OrderInfo
  },
  {
    path: '/book/:id',
    name: 'Product',
    component: Product
  },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // },
  {
    path: '/login',
    name: 'authorization',
    component: Authorization
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
