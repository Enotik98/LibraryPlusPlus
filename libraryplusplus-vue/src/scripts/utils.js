import moment from "moment/moment";

export const optionsGenre = ['Comedy', 'Horror', 'Fantasy', 'Romance', 'Contemporary', 'Mystery', 'Thriller', 'Paranormal', 'Historical fiction', 'Science Fiction', 'History', 'Travel', 'Guide', 'Motivational', "Children's"];
export function getGenreName (genres, id) {
    const genre = genres.find(genre => genre.id === id);
    return genre ? genre.name : "";

}
export function formatDate(val){
    return moment(val).format("DD/MM/YYYY")
}
export function calculateDate(date, days){

    let currDate = new Date(date);
    const newDate = new Date(currDate.setDate(currDate.getDate() + parseInt(days)));
    return moment(newDate).format("YYYY-MM-DD");
}
export function checkLate(order) {
    return (order.status === 'CHECKOUT' && new Date(order.return_date) < new Date())
}