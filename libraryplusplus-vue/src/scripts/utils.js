import moment from "moment/moment";

export function formatDate(val){
    return moment(val).format("DD/MM/YYYY")
}
export function calculateDate(days){
    const currDate = new Date();
    return currDate.setDate(currDate.getDate() + days);
}
