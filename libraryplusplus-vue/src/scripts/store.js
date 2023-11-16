import {createStore} from "vuex";
import {sendRequest} from "@/scripts/request";

const store = createStore({
    state: {
        isLoggedIn: !!localStorage.getItem("Token"),
        isUser: true
    },
    mutations: {
        login(state) {
            state.isLoggedIn = !!localStorage.getItem("Token");
            if (state.isLoggedIn) state.isUser = true;
        },
        setUser(state, value) {
            state.isUser = value === "USER"
            console.log("User " + state.isUser)
        },
        logout(state) {
            localStorage.clear();
            state.isLoggedIn = false;
            state.isUser = true;
        }
    },
    actions: {
        async fetchUser({commit}) {
            // if (isLoggedIn) console.log(true)
            const response = await sendRequest("/user/profile", "GET", null);
            if (response.ok) {
                const data = await response.json();
                commit('setUser', data['role'])
            }
        }
    }
})

export default store