const BASE_URL = 'http://localhost:8080';

export async function sendRequest(url, method, data){
    console.log(method + " " + url)
    // let headers = ""

    const request = {
        method : method,
        headers: {
            'Content-Type' : "application/json"
        }
    }
    if (data) request.body = JSON.stringify(data)

    const response = await fetch(BASE_URL + url, request);
    return response;
}