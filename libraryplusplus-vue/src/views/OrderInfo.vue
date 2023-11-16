<template>
<div class="container justify-content-center d-flex">
  <div class="order">
    <p>{{book.title}}</p>
    <p>{{user.email}}</p>
    <p>{{order.status}}</p>
    <p>{{order.return_date}}</p>
    <p>How long{{days}}</p>
    <select class="form-select" v-model="selectDays">
      <option :value="7">1 week</option>
      <option :value="14">2 week</option>
      <option :value="21">3 week</option>
      <option :value="30">1 month</option>
      <option :value="60">2 month</option>
    </select>
  </div>
<!--{{$route.params.id}}-->
</div>
</template>

<script>
import {sendRequest} from "@/scripts/request";

export default {
  name: "OrderInfo",
  components: {},
  data() {
    return {
      order: {},
      book: {},
      user: {},
      updateStatus: {
        id: this.$route.params.id,
        status: ""
      },
      selectDays: "",
      // orderStatus: "",
      days: 0
    }
  },
  mounted() {
    this.getOrder();
  },
  methods: {
    daysDifference(){
      const start = new Date(this.order.return_date)
      const end = new Date(this.order.orderDate)
      const diff = start.getTime() - end.getTime();
      this.days = Math.floor(diff / (24 * 60 * 60 * 1000))
    },
    async getOrder() {
      try {
        const response = await sendRequest("/order/" + this.$route.params.id, "GET", null);
        if (response.ok){
          const data = await response.json();
          this.order = await data;
          this.book = await data.book;
          this.user = await data.user;
          console.log(this.order)
          this.daysDifference()
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