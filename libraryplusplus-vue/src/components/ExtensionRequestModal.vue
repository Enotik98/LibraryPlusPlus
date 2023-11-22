<template>
  <div class="request">
    <label class="title">Request {{ extensionRequest.id }}</label>
    <div>
      <p>Title: {{ extensionRequest.order.book.title }}</p>
      <p>Email: {{ extensionRequest.user.email }}</p>
      <p>New return date: {{ extensionRequest.new_return_date }}</p>
      <p>Return date: {{ extensionRequest.order.return_date }}</p>
      <p>Status: {{ extensionRequest.status }}</p>
      <div>
        <span>Message: {{ extensionRequest.message }}</span>
        <input class="form-control mt-2" v-model="mess" placeholder="Response">
      </div>
      <div class="request-control">
        <button @click="updateRequest('Confirm')" class="btn btn-success">Confirm</button>
        <button @click="updateRequest('Cancel')" class="btn btn-outline-danger">Cancel</button>
      </div>

    </div>
  </div>
</template>

<script>
import {sendRequest} from "@/scripts/request";

export default {
  name: "ExtensionRequestModal",
  props: {
    extensionRequest: Object,
    closeModalWindow: Function,
    updateListRequest: Function,

  },
  data() {
    return {
      mess: "",
      editRequest: {
        id: 0,
        order_id: 0,
        status: "",
        message: "",
        new_return_date: ""
      }
    }
  },
  mounted() {
  },
  methods: {
    async updateRequest(status) {
      switch (status) {
        case "Confirm": {
          this.editRequest.status = "APPROVED";
          break;
        }
        case "Cancel": {
          this.editRequest.status = "REJECTED";
          break;
        }
        default:
          return;
      }
      this.editRequest.id = this.extensionRequest.id;
      this.editRequest.new_return_date = this.extensionRequest.new_return_date;
      this.editRequest.order_id = this.extensionRequest.order.id;
      this.editRequest.message = this.extensionRequest.message + " " + this.mess;

      const response = await sendRequest("/extension_request", "PUT", this.editRequest);

      if (!response.ok) {
        const errorMessage = await response.text()
        this.$Notiflix.Notify.success(errorMessage)
        return;
      }
      this.updateListRequest();
      this.$Notiflix.Notify.success("The request has been updated!");
      this.closeModalWindow();

    }
  }
}
</script>

<style scoped>
.request {
  width: 30em;
}

.request > div {
  font-size: 1.1rem;
}

.request p {
  padding: .3rem 0;
  margin: 0;
}

.request-control {
  display: flex;
  justify-content: space-around;
  margin-top: 1em;
}
</style>