const BASE_URL = 'http://localhost:8080';
import store from "@/scripts/store";

export async function sendRequest(url, method, data){
    console.log(method + " " + url)
    let headers = ""
    let token = localStorage.getItem("Token")
    if (token) headers = "Bearer " + token;

    const request = {
        method : method,
        headers: {
            'Content-Type' : "application/json",
            'Authorization' : `${headers}`
        }
    };
    if (data) request.body = JSON.stringify(data)

    const response = await fetch(BASE_URL + url, request);
    if (response.status === 401){
        localStorage.clear();
        console.log(store.isLoggedIn);
        if (store.isLoggedIn) store.logout();
        return response;
    }
    return response;
}