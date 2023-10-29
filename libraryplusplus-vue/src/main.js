import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
// import {BootstrapVue} from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap'
const app = createApp(App)
app.use(router)
// app.use(BootstrapVue)
app.mount('#app')
