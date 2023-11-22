<template>
  <form @submit.prevent="updateUser">
    <div class="personal_body">
      <div class="body_column">
        <div class="body_column-item">
          <label class="">First Name:</label>
          <input v-model="editUser.first_name" type="text" class="form-control" placeholder="First Name">
        </div>
        <div class="body_column-item">
          <label class="">Last Name:</label>
          <input v-model="editUser.last_name" type="text" class="form-control" placeholder="Last Name">
        </div>
        <div class="body_column-item">
          <label class="">Email:</label>
          <input v-model="editUser.email" type="text" class="form-control" placeholder="Email">
        </div>
        <div class="body_column-item">
          <label class="">Phone Number:</label>
          <input v-model="editUser.phone" type="text" class="form-control" placeholder="Phone Number">
        </div>
      </div>
      <div class="body_column">
        <div class="body_column-item">
          <label class="">Apartment Number:</label>
          <input type="text" class="form-control" placeholder="Apartment Number">
        </div>
        <div class="body_column-item">
          <label class="">House Number:</label>
          <input type="text" class="form-control" placeholder="House Number">
        </div>
        <div class="body_column-item">
          <label class="">Street:</label>
          <input v-model="editUser.address" type="text" class="form-control" placeholder="Street">
        </div>
        <div class="body_column-item">
          <label class="">City:</label>
          <input type="text" class="form-control" placeholder="City">
        </div>
      </div>
    </div>
    <div class="personal_btn">
      <button class="btn btn-outline-primary" type="submit">Save Change</button>
    </div>
  </form>
</template>

<script>
import {sendRequest} from "@/scripts/request";

export default {
  name: "UserData",
  props: {
    user: Object,
  },
  data() {
    return {
      editUser: {
        first_name: "",
        last_name: "",
        email: "",
        phone: "",
        address: "",
      }
    }
  },
  watch: {
    user: {
      handler() {
        this.updateEditUser();
      },
      deep: true,
    },
  },
  methods: {
    updateEditUser() {
      console.log(this.editUser)
      let checkTicket = true;
      for (const key in this.editUser) {
        if (this.user[key] === null || this.user[key] === undefined || this.user[key] === "") {
          checkTicket = false
        }
        this.editUser[key] = this.user[key];
      }

      this.$emit('checkTicket', checkTicket);

    },
    async updateUser() {
      const response = await sendRequest("/user/ticket", "PUT", this.editUser);
      if (response.ok) {
        this.updateEditUser();
        this.$emit('updateUserInfo');
      }
      if (response.status === 400){
        const error = await response.text();
        this.$Notiflix.Notify.failure(error);
      }
    }
  }
}
</script>

<style scoped>
.personal_body {
  display: flex;
  flex-wrap: wrap;

}

.body_column {
  width: 30em;
}

.body_column-item {
  display: flex;
  justify-content: end;
  align-items: center;
  padding: .5rem;
}

.body_column-item > input {
  width: 60%;
}

.body_column-item > label {
  padding-right: .5rem;
}

.personal_btn {
  display: flex;
  justify-content: end;
}

.personal_btn > button {
  color: var(--blue);
  width: 15em;
}


</style>