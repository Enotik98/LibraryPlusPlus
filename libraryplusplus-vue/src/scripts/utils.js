import moment from "moment/moment";

export function formatDate(val){
    return moment(val).format("DD/MM/YYYY")
}
export function calculateDate(date, days){

    let currDate = new Date(date);
    const newDate = new Date(currDate.setDate(currDate.getDate() + parseInt(days)));
    return moment(newDate).format("YYYY-MM-DD");
}
