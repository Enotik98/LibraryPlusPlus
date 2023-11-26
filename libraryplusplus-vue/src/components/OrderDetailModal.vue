<template>
<div class="order">
  <div>
    <p>Title: {{order.book.title}}</p>
    <p>Author: {{order.book.author}}</p>
    <p>Genre: {{order.book.genre}}</p>
    <p>Order Date: {{formatDate(order.orderDate)}}</p>
    <p>Return Date: {{formatDate(order.return_date)}}</p>
    <p>Status: {{order.status}}</p>
  </div>
  <div v-if="order.status === 'CHECKOUT'">
    <label>New Return Date
      <span v-if="selectedDay !== 0">{{formatDate(calculateDate(order.return_date, selectedDay))}}</span>
    </label>
    <select class="form-select mt-2" v-model="selectedDay" >
      <option :value="0" selected disabled>Choose</option>
      <option :value="7">more 1 week</option>
      <option :value="14">more 2 week</option>
      <option :value="21">more 3 week</option>
      <option :value="30">more 1 month</option>
    </select>
    <input class="form-control mt-2" placeholder="message" v-model="request.message">
    <button @click="createRequest" class="btn btn-outline-dark">Create Request</button>
  </div>
  <div v-if="order.status === 'AWAITING'">
    <button @click="cancelOrder" class="btn btn-outline-dark">Cancel</button>
  </div>
</div>
</template>

<script>
import {calculateDate, formatDate} from "../scripts/utils";
import {sendRequest} from "@/scripts/request";

export default {
  name: "OrderDetailModal",
  props: {
    order: Object,
    closeModalWindow: Function,
  },
  data() {
    return {
      selectedDay: 0,
      request: {
        order_id: this.order.id,
        new_return_date: "",
        message: ""
      }
    }
  },
  methods: {
    calculateDate,
    formatDate,
    async createRequest() {
      if (this.selectedDay === 0) return;
      this.request.new_return_date = calculateDate(this.order.return_date, this.selectedDay);

      const response = await sendRequest("/extension_request", "POST", this.request);
      if (!response.ok){
        const errorMessage = await response.text();
        this.$Notiflix.Notify.failure(errorMessage);
      }else {
        this.$Notiflix.Notify.success("Successful!")
        this.closeModalWindow();
      }

    },
    async cancelOrder() {
      const response = await sendRequest("/order/cancel", "POST", {id: this.order.id});
      if (!response.ok){
        const errorMessage = await response.text();
        this.$Notiflix.Notify.failure(errorMessage);
      }else {
        this.$Notiflix.Notify.success("Successful!")
        this.closeModalWindow();
      }
    }
  },

}
</script>

<style scoped>
.order {
  width: 30em;
}
</style>