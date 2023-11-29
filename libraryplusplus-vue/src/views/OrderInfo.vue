<template>
  <div class="container justify-content-center d-flex">
    <div class="back-link" @click="$router.go(-1)"><i class="fa-solid fa-chevron-left" style="color: #000000;"></i><span>Back</span></div>
    <div class="order card">
      <label class="title">Order {{order.id}}</label>
      <p>Book: {{ book.title }}</p>
      <p>Email: {{ user.email }}</p>
      <p>Status: {{ order.status }}</p>
      <p v-if="order.status === 'AWAITING'">Return Date {{ formatDate(calculateDate(new Date(), selectDays) )}}</p>
      <p v-else>Return Date: {{formatDate(order.return_date)}}</p>
      <div class="order_return-date" v-if="order.status === 'AWAITING' && userRole !== 'ADMIN'">
        <span>The user wants to take the book for {{ days }} days</span>
        <select class="form-select" v-model="selectDays">
          <option :value="days" selected >Change period</option>
          <option :value="7">1 week</option>
          <option :value="14">2 week</option>
          <option :value="21">3 week</option>
          <option :value="30">1 month</option>
        </select>
      </div>
      <div v-if="order.status !== 'RETURNED' && order.status !== 'LOST'" class="order_control">
        <button @click="updateStatusOrder" v-if="userRole !== 'ADMIN'" class="btn btn-dark">Change Status</button>
        <button v-if="order.status === 'AWAITING' && userRole !== 'ADMIN'" @click="cancelOrder" class="btn btn-outline-danger">Cancel Order</button>
        <button @click="addLostBook" class="btn btn-outline-dark" >Add Lost Book</button>
      </div>
    </div>
  </div>
</template>

<script>
import {sendRequest} from "@/scripts/request";
import {calculateDate, formatDate} from "@/scripts/utils";
import {mapState} from "vuex";

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
      days: 0
    }
  },
  computed: {
    ...mapState(['userRole'])
  },
  mounted() {
    this.getOrder();
  },
  methods: {
    formatDate,
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
        const errorMessage = await response.text()
        this.$Notiflix.Notify.failure(errorMessage)
        return;
      }
      await this.getOrder();
      this.$Notiflix.Notify.success("The status has been successfully updated!")
    },
    async cancelOrder() {
      const response = await sendRequest("/order/cancel", "POST", {id: this.$route.params.id});

      if (!response.ok) {
        const errorMessage = await response.text()
        this.$Notiflix.Notify.failure(errorMessage)
        return;
      }
      this.$Notiflix.Notify.success("The order has been cancelled!")
      this.$router.go(-1);


    },
    async addLostBook() {
      this.updateStatus.status = "LOST";
      this.updateStatus.return_date = this.order.return_date;
      const response = await sendRequest("/order/status", "POST", this.updateStatus);

      if (!response.ok) {
        const errorMessage = await response.text()
        this.$Notiflix.Notify.failure(errorMessage)
        return;
      }
      this.$Notiflix.Notify.success("The copy book has been added to the list lost books!")
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>
.back-link {
  margin-top: 2em;
  padding: 0 2em 1em;
}
.order {
  width: 40em;
  margin-top: 2em;
  padding: 1em;
}
.title {
  color: var(--blue-opacity);
  font-size: 1.5rem;
  font-style: normal;
  font-weight: 500;
  line-height: normal;
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
  padding-top: 1em;
}
</style>