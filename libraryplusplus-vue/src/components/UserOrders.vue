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
    <tr v-for="order in orders" :key="order.id" @click="openOrderDetail(order)" :class="{'table-danger' : checkLate(order)}">
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
  <label v-if="requests.length" class="title">My Requests</label>
  <table v-if="requests.length" class="table table-hover">
    <thead>
    <tr>
      <th>#</th>
      <th>Title</th>
      <th>Author</th>
      <th>New Date</th>
      <th>Status</th>
      <th>Message</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="requestUser in requests" :key="requestUser">
      <td>{{requestUser.id}}</td>
      <td>{{ requestUser.order.book.title }}</td>
      <td>{{ requestUser.order.book.author }}</td>
      <td>{{ formatDate(requestUser.new_return_date) }}</td>
      <td>{{ requestUser.status }}</td>
      <td>{{requestUser.message}}</td>
    </tr>
    </tbody>
  </table>
</div>
  <ModalWindow ref="ModalWindow">
    <OrderDetailModal :order="chooseOrder" :close-modal-window="() => { this.$refs.ModalWindow.closeModal() }" :update-orders="getUserOrders" :update-requests="getUserRequests"/>
  </ModalWindow>
</template>

<script>
import {sendRequest} from "@/scripts/request";
import {checkLate, formatDate} from "../scripts/utils";
import ModalWindow from "@/components/ModalWindow.vue";
import OrderDetailModal from "@/components/OrderDetailModal.vue";

export default {
  name: "UserOrders",
  components: {OrderDetailModal, ModalWindow},
  data() {
    return {
      orders: [],
      requests: [],
      chooseOrder: {}
    }
  },
  mounted() {
    this.getUserOrders();
    this.getUserRequests();
  },
  methods: {
    checkLate,
    formatDate,
    openOrderDetail(val){
      this.chooseOrder = val;
      this.$refs.ModalWindow.openModal();
    },
    async getUserOrders(){
      try {
        const response = await sendRequest("/order/user", "GET", null);
        if (response.ok){
          const data = await response.json();
          this.orders = data.sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));
          console.log(this.orders)
        }
      }catch (e){
        console.error(e)
      }
    },
    async getUserRequests(){
      const response = await sendRequest("/extension_request/user", "GET", null);
      if (!response.ok){
        console.error(await response.text());
        return;
      }
      const data = await response.json()
      this.requests = data.sort((a, b) => new Date(b.request_date) - new Date(a.request_date));
      console.log(this.requests);
    }
  }
}
</script>

<style scoped>
.table {
  max-height: 40em;
  overflow-y: auto;
}
</style>