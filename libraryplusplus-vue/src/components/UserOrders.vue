<template>
<div>
  <table class="table table-hover">
    <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Book name</th>
      <th scope="col">Author</th>
      <th scope="col">Genre</th>
      <th scope="col">Order date</th>
      <th scope="col">Return date</th>
      <th scope="col">Status</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="order in orders" :key="order.id">
      <td>{{ order.id }}</td>
      <td>{{order.book.title}}</td>
      <td>{{order.book.author}}</td>
      <td>{{order.book.genre}}</td>
      <td>{{formatDate(order.orderDate)}}</td>
      <td>{{formatDate(order.return_date)}}</td>
      <td>{{order.status}}</td>
    </tr>
    </tbody>
  </table>
</div>
</template>

<script>
import {sendRequest} from "@/scripts/request";
import {formatDate} from "../scripts/utils";

export default {
  name: "UserOrders",
  data() {
    return {
      orders: []
    }
  },
  mounted() {
    this.getUserOrders();
  },
  methods: {
    formatDate,
    async getUserOrders(){
      try {
        const response = await sendRequest("/order/user", "GET", null);
        if (response.ok){
          const data = await response.json();
          this.orders = data;
          console.log(this.orders)
        }
      }catch (e){
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>

</style>