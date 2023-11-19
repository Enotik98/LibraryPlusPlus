<template>
  <div class="container justify-content-center d-flex">
    <div class="order">
      <p>Book: {{ book.title }}</p>
      <p>Email: {{ user.email }}</p>
      <p>Status: {{ order.status }}</p>
      <p v-if="order.status === 'AWAITING'">Return Date {{ calculateDate(new Date(), selectDays) }}</p>
      <p v-else>Return Date: {{order.return_date}}</p>
      <div class="order_return-date">
        <span>How long take: {{ days }} days</span>
        <select class="form-select" v-model="selectDays" v-if="order.status === 'AWAITING'">
          <option :value="days" selected>Change period</option>
          <option :value="7">1 week</option>
          <option :value="14">2 week</option>
          <option :value="21">3 week</option>
          <option :value="30">1 month</option>
        </select>
      </div>
      <div class="order_control" v-if="order.status !== 'RETURNED' && order.status !== 'LOST'">
        <button class="btn btn-outline-dark" @click="updateStatusOrder">Change Status</button>
        <button v-if="order.status === 'AWAITING'" @click="cancelOrder" class="btn btn-outline-dark">Cancel order
        </button>
        <button @click="addLostBook" class="btn btn-outline-dark" >Lost Book</button>
      </div>
    </div>
    <!--{{$route.params.id}}-->
  </div>
</template>

<script>
import {sendRequest} from "@/scripts/request";
import {calculateDate} from "@/scripts/utils";

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
        status: "",
        return_date: "",
      },
      selectDays: 0,
      // orderStatus: "",
      days: 0
    }
  },
  mounted() {
    this.getOrder();
  },
  methods: {
    calculateDate,
    daysDifference() {
      const start = new Date(this.order.return_date)
      const end = new Date(this.order.orderDate)
      const diff = start.getTime() - end.getTime();
      this.days = Math.floor(diff / (24 * 60 * 60 * 1000))
      this.selectDays = this.days;
    },
    async getOrder() {
      try {
        const response = await sendRequest("/order/" + this.$route.params.id, "GET", null);
        if (response.ok) {
          const data = await response.json();
          this.order = await data;
          this.book = await data.book;
          this.user = await data.user;
          console.log(this.order)
          this.daysDifference()
        }
      } catch (e) {
        console.error(e)
      }
    },
    async updateStatusOrder() {
      switch (this.order.status) {
        case "AWAITING" : {
          this.updateStatus.status = "CHECKOUT";
          if (this.selectDays !== this.days) {
            console.log(this.selectDays)
            this.updateStatus.return_date = calculateDate(this.order.orderDate, this.selectDays);
          } else {
            console.log(this.days)
            this.updateStatus.return_date = calculateDate(this.order.orderDate, this.days);
          }
          break;
        }
        case "CHECKOUT" : {
          this.updateStatus.status = "RETURNED";
          this.updateStatus.return_date = this.order.return_date;
          break;
        }
        default :
          return;
      }

      const response = await sendRequest("/order/status", "POST", this.updateStatus);

      if (!response.ok) {
        console.log(await response.text())
        return;
      }
      await this.getOrder();
    },
    async cancelOrder() {
      const response = await sendRequest("/order/cancel", "POST", {id: this.$route.params.id});

      if (!response.ok) {
        console.error(await response.text())
        return;
      }
      this.$router.go(-1);

    },
    async addLostBook() {
      this.updateStatus.status = "LOST";
      this.updateStatus.return_date = this.order.return_date;
      const response = await sendRequest("/order/status", "POST", this.updateStatus);

      if (!response.ok){
        console.log(await response.text());
      }
    }
  }
}
</script>

<style scoped>
.order {
  width: 40em;
  margin-top: 2em;
}

.order_return-date {
  display: flex;
  justify-content: space-between;
}

.order_return-date .form-select {
  width: auto;
}

.order_control {
  display: flex;
  justify-content: space-between;
}
</style>