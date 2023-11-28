<template>
  <div class="container d-flex mt-4">
    <div class="w-100">
      <div v-if="showRequests">
        <span class="title">Extension Requests</span>
        <table class="table table-hover" >
          <thead>
          <tr>
            <th>#</th>
            <th>Title</th>
            <th>Email</th>
            <th>Status</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="request in filterRequests" :key="request.id" @click="openRequestDetail(request)">
            <td>{{ request.id }}</td>
            <td>{{ request.order.book.title }}</td>
            <td>{{ request.user.email }}</td>
            <td>{{ request.status }}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div>
        <span class="title">Orders</span>
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
          <tr>
            <td class="td-id"><input type="text" v-model="filterOrder.id" placeholder="#" class="form-control"></td>
            <td><input type="text" v-model="filterOrder.email" placeholder="email" class="form-control"></td>
            <td><input type="text" v-model="filterOrder.title" placeholder="title" class="form-control"></td>
            <td colspan="2">
              <select v-model="filterOrder.status" class="form-select">
                <option value="">Choose Status</option>
                <option value="AWAITING">AWAITING</option>
                <option value="CHECKOUT">CHECKOUT</option>
                <option value="RETURNED">RETURNED</option>
                <option value="LOST">LOST</option>
              </select></td>
            <td colspan="2">
              <button class="btn btn-outline-dark " @click="filterOrder = clearFilter(filterOrder)">Clear Filter
              </button>
            </td>
          </tr>
          <tr v-for="order in filterOrders" :key="order.id" @click="goToOrderPage(order.id)"
              :class="{'table-warning' : checkLate(order)}">
            <td>{{ order.id }}</td>
            <td>{{ order.user.email }}</td>
            <td>{{ order.book.title }}</td>
            <td>{{ formatDate(order.orderDate) }}</td>
            <td>{{ formatDate(order.return_date) }}</td>
            <td>{{ order.status }}</td>
            <td>
              <div v-if="order.status === 'RETURNED' ">
                <i v-if="order.returnedLate" class="fa-regular fa-calendar-xmark"
                   style="color: #ff4013; font-size: 1.4rem"></i>
                <i v-else class="fa-regular fa-calendar-check" style="color: #96d35f; font-size: 1.4rem"></i>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="panel">
      <div class="form-check form-check-inline ">
        <input type="checkbox" class="form-check-input" v-model="showRequests">
        <label class="form-check-label">Show requests</label>
      </div>
      <div class="form-check form-check-inline " v-if="showRequests">
        <input type="checkbox" class="form-check-input" v-model="showPending">
        <label class="form-check-label">Show Pending Requests</label>
      </div>
    </div>
  </div>
  <ModalWindow ref="ModalWindow">
    <ExtensionRequestModal :extension-request="chooseRequest" :close-modal-window="()=>{this.$refs.ModalWindow.closeModal()}" :update-list-request="getExtensionRequests"/>
  </ModalWindow>
</template>

<script>
import {sendRequest} from "@/scripts/request";
import {checkLate, formatDate} from "../scripts/utils";
import ModalWindow from "@/components/ModalWindow.vue";
import ExtensionRequestModal from "@/components/ExtensionRequestModal.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Orders",
  components: {ExtensionRequestModal, ModalWindow},
  data() {
    return {
      orders: [],
      extensionRequests: [],
      chooseRequest: {},
      filterOrder: {
        id: '',
        email: "",
        title: "",
        status: "",
      },
      showRequests: false,
      showPending: true,
    }
  },
  mounted() {
    this.getOrders();
    this.getExtensionRequests();
  },
  computed: {
    filterOrders() {
      let result = this.orders;
      if (this.filterOrder.id || this.filterOrder.email || this.filterOrder.title || this.filterOrder.status) {
        result = result.filter(order => {
          return (
              (!this.filterOrder.id || order.id.toString().includes(this.filterOrder.id)) &&
              (!this.filterOrder.email || order.user.email.toLowerCase().includes(this.filterOrder.email.toLowerCase())) &&
              (!this.filterOrder.title || order.book.title.toLowerCase().includes(this.filterOrder.title.toLowerCase())) &&
              (!this.filterOrder.status || order.status.includes(this.filterOrder.status))
          )
        })
      }
      return result.sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));
    },
    filterRequests() {
      let result = this.extensionRequests;
      if (this.showPending) {
        result = result.filter(request => {
          return (
              request.status.includes("PENDING")
          )
        })
      }
      return result.sort((a, b) => new Date(b.request_date) - new Date(a.request_date));
    }
  },

  methods: {
    clearFilter(obj) {
      for (let key in obj) {
        obj[key] = "";
      }
      return obj;
    },
    checkLate,
    formatDate,
    goToOrderPage(orderId) {
      this.$router.push({name: 'OrderInfo', params: {id: orderId}})
    },
    openRequestDetail(val) {
      this.chooseRequest = val;
      this.$refs.ModalWindow.openModal();
    },
    async getOrders() {
      try {
        const response = await sendRequest("/order", "GET", null);
        if (response.ok) {
          this.orders = await response.json();
          console.log(this.orders)
        }
      } catch (e) {
        console.error(e)
      }
    },
    async getExtensionRequests() {
      const response = await sendRequest("/extension_request", "GET", null);
      if (!response.ok) {
        console.log(await response.text());
        return;
      }
      this.extensionRequests = await response.json();
      console.log(this.extensionRequests)
    }
  }
}
</script>

<style scoped>
.panel {
  width: 10em;
}
.title {
  color: var(--blue-opacity);
  font-size: 1.5rem;
  font-style: normal;
  font-weight: 500;
  line-height: normal;
}
.td-id {
  width: 6em;
}


/*:style="{backgroundColor: checkLate(order) ? '#FFA500' : 'inherit'}*/
</style>