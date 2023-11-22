import {createStore} from "vuex";
import {sendRequest} from "@/scripts/request";

const store = createStore({
    state: {
        isLoggedIn: !!localStorage.getItem("Token"),
        isUser: true,
        userRole: "USER",
        isSanctions: false,
    },
    mutations: {
        login(state) {
            state.isLoggedIn = !!localStorage.getItem("Token");
            if (state.isLoggedIn) state.isUser = true;
        },
        setUser(state, values) {
            state.isUser = values["role"] === "USER"
            state.userRole = values["role"];
            state.isSanctions = values["sanctions"];
            console.log("User " + state.isUser + " s " + state.isSanctions)
        },
        logout(state) {
            localStorage.clear();
            state.isLoggedIn = false;
            state.isUser = true;
            state.isSanctions = false;
        }
    },
    actions: {
        async fetchUser({commit}) {
            console.log(this.state.isLoggedIn)
            if (this.state.isLoggedIn) {
                const response = await sendRequest("/user/profile", "GET", null);
                if (response.ok) {
                    const data = await response.json();
                    commit('setUser', {role: data['role'], sanctions: data['isSanctions']})
                }
            }
        }
    }
})

export default store