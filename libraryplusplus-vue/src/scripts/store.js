import {createStore} from "vuex";
import {sendRequest} from "@/scripts/request";

const store = createStore({
    state: {
        isLoggedIn: !!localStorage.getItem("Token"),
        isUser: true,
        userRole: "USER",
        isSanctions: true,
        isBlocked: true
    },
    mutations: {
        login(state) {
            state.isLoggedIn = !!localStorage.getItem("Token");
            if (state.isLoggedIn) state.isUser = true;
        },
        setUser(state, values) {
            if (values["blocked"]) {
                localStorage.clear();
                state.isLoggedIn = false;
                state.isUser = true;
                state.isSanctions = true;
                state.isBlocked = true;
            } else {
                console.log("state")
                state.isUser = values["role"] === "USER"
                state.userRole = values["role"];
                state.isSanctions = values["sanctions"];
                state.isBlocked = values["blocked"];
                console.log("User " + state.isUser + " s " + state.isSanctions)
            }
        },
        logout(state) {
            localStorage.clear();
            state.isLoggedIn = false;
            state.isUser = true;
            state.isSanctions = true;
            state.isBlocked = true;
        }
    },
    actions: {
        async fetchUser({commit}) {
            console.log(this.state.isLoggedIn)
            if (this.state.isLoggedIn) {
                const response = await sendRequest("/user/profile", "GET", null);
                if (response.ok) {
                    const data = await response.json();
                    commit('setUser', {role: data['role'], sanctions: data['isSanctions'], blocked: data['isBlocked']})
                }else {
                    // commit('setUser', {role: "USER", sanctions: true, blocked: true})
                    commit("logout");
                }
            }
        }
    }
})

export default store