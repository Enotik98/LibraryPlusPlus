import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Authorization from "@/views/Authorization.vue";
import Profile from "@/views/Profile.vue";
import Orders from "@/views/Orders.vue";
import OrderInfo from "@/views/OrderInfo.vue";
import Product from "@/views/Product.vue";
import Analytics from "@/views/Analytics.vue";
import Users from "@/views/Users.vue";
import store from "@/scripts/store";
import Genres from "@/views/Genres.vue";

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/profile',
        name: 'Profile',
        component: Profile,
        beforeEnter: [isAuthorized, await isAuthorized]
    },
    {
        path: '/orders',
        name: 'Orders',
        component: Orders,
        beforeEnter: [AdminLibrarianMiddleware, await isAuthorized]

    },
    {
        path: '/orders/:id',
        name: 'OrderInfo',
        component: OrderInfo,
        beforeEnter: [AdminLibrarianMiddleware, await isAuthorized]

    },
    {
        path: '/book/:id',
        name: 'Product',
        component: Product
    },
    {
        path: '/analytics',
        name: 'Analytics',
        component: Analytics,
        beforeEnter: [AdminLibrarianMiddleware, await isAuthorized]
    },
    {
        path: '/users',
        name: 'Users',
        component: Users,
        beforeEnter: [AdminLibrarianMiddleware, await isAuthorized]

    },
    {
        path: '/book/_genres',
        name: 'Genres',
        component: Genres,
        // beforeEnter: [await AdminLibrarianMiddleware, await isAuthorized]
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
function AdminLibrarianMiddleware(to) {
    const userRole = store.state.userRole;
    console.log(userRole)
    if (userRole === "USER") {
        to.path
        return "/"
    }
}
async function isAuthorized(to) {
    to.path
        await store.dispatch("fetchUser")
    if (!store.state.isLoggedIn) {
        console.log("get")
    }
    if (!store.state.isLoggedIn || store.state.isBlocked) {
        return "/"
    }
}

export default router
