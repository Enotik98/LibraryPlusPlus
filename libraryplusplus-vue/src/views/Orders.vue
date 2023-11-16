<template>
<div class="container">
  <table class="table table-hover">
    <thead>
    <tr>
      <th>#</th>
      <th>Email</th>
      <th>Title</th>
      <th>Order Date</th>
      <th>Returned Date</th>
      <th>Status</th>
      <th>Returned Late</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="order in orders" :key="order.id" @click="goToOrderPage(order.id)">
      <td>{{ order.id }}</td>
      <td>{{ order.user.email }}</td>
      <td>{{ order.book.title }}</td>
      <td>{{ formatDate(order.orderDate) }}</td>
      <td>{{ formatDate(order.return_date) }}</td>
      <td>{{order.status}}</td>
      <td><i v-if="order.returnedLate" class="fa-regular fa-calendar-xmark" style="color: #ff4013; font-size: 1.4rem"></i>
        <i v-else class="fa-regular fa-calendar-check" style="color: #96d35f; font-size: 1.4rem"></i>
      </td>
<!--      <td><router-link :to="{name: 'OrderInfo', params: {id: order.id}}">order</router-link></td>-->
    </tr>
    </tbody>
  </table>
</div>
</template>

<script>
import {sendRequest} from "@/scripts/request";
import {formatDate} from "../scripts/utils";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Orders",
  components: {},
  data() {
    return {
      orders: []
    }
  },
  mounted() {
    this.getOrders()
  },
  methods: {
    formatDate,
    goToOrderPage(orderId){
      this.$router.push({name: 'OrderInfo', params: {id: orderId}})
    },
    async getOrders() {
      try {
        const response = await sendRequest("/order", "GET", null);
        if (response.ok){
          this.orders = await response.json();
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
.table {
  margin: 2em 0;
}
</style>