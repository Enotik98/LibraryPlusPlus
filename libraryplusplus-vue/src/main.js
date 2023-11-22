import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap'
import store from "@/scripts/store";
import Notiflix from "notiflix";

const app = createApp(App)
app.use(router)
app.use(store)
app.config.globalProperties.$Notiflix = Notiflix;
// eslint-disable-next-line vue/multi-word-component-names
app.mount('#app')
Notiflix.Notify.init({
    position: 'right-bottom',
    width: '370px',
    distance: '30px',
    fontSize: '15px',
    timeout: 7000,
    messageMaxLength: 500
    // closeButton: true
})
