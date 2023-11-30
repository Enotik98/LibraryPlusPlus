<template>
  <div class="user">
    <div>
      <label class="title">User {{ user.id }}</label>
      <p>Email: {{ user.email }}</p>
      <p>First Name: {{ user.first_name }}</p>
      <p>Last Name: {{ user.last_name }}</p>
      <p>Phone: {{ user.phone }}</p>
      <p>Address: {{ address.city }}, St. {{ address.street }} {{ address.houseNumber }}, {{ address.apartmentNumber }}</p>
      <p>Role: {{ user.role }}</p>
    </div>
    <div class="user-control">
      <div>
        <p class="title">Restrictions</p>
        <div class="form-check">
          <input type="checkbox" class="form-check-input" v-model="this.restriction.isSanctions"
                 :disabled="userRole === 'LIBRARIAN'">
          <label>Sanctions</label>
        </div>
        <div class="form-check">
          <input type="checkbox" class="form-check-input" v-model="this.restriction.isBlocked"
                 :disabled="userRole === 'LIBRARIAN' && this.user.isBlocked">
          <label>Blocked</label>
        </div>
        <div>
          <button @click="updateRestriction" class="btn btn-outline-primary">Save Restrictions</button>
        </div>
      </div>
      <div v-if="userRole === 'ADMIN'">
        <label class="title">Make Role</label>
        <div>
          <div class="form-check form-check-inline">
            <input type="radio" class="form-check-input" value="USER" v-model="role">
            <label class="form-check-label">User</label>
          </div>
          <div class="form-check form-check-inline">
            <input type="radio" class="form-check-input" value="LIBRARIAN" v-model="role">
            <label class="form-check-label">Librarian</label>
          </div>
          <div class="form-check form-check-inline">
            <input type="radio" class="form-check-input" value="ADMIN" v-model="role">
            <label class="form-check-label">Admin</label>
          </div>
        </div>
        <button @click="updateRole" class="btn btn-outline-primary">Save Role</button>
        <button @click="deleteUser" class="btn btn-outline-danger">Delete</button>
      </div>
    </div>

  </div>
</template>

<script>
import {mapState} from "vuex";
import {sendRequest} from "@/scripts/request";

export default {
  name: "UserModal",
  props: {
    userInfo: Object,
    closeModalWindow: Function,
    getUsers: Function,
  },
  data() {
    return {
      restriction: {
        id: this.userInfo.id,
        isSanctions: this.userInfo.isSanctions,
        isBlocked: this.userInfo.isBlocked,
      },
      role: this.userInfo.role,
      user: {},
      address: {}
    }
  },
  computed: {
    ...mapState(['userRole'])
  },
  mounted() {
    this.user = this.userInfo
    this.address = this.user.address ? JSON.parse(this.user.address) : "";
  },
  methods: {
    async updateRestriction() {
      const response = await sendRequest("/user/restriction", "POST", this.restriction);
      if (!response.ok) {
        const errorMessage = await response.text();
        this.$Notiflix.Notify.failure(errorMessage);
        return;
      } else {
        this.$Notiflix.Notify.success("Update successful!");
      }
      await this.getUserInfo();
      await this.getUsers();
    },
    async updateRole() {
      if (this.role === this.userInfo.role) {
        return;
      }
      const response = await sendRequest("/user/role", "POST", {id: this.userInfo.id, role: this.role})
      if (!response.ok) {
        const errorMessage = await response.text();
        this.$Notiflix.Notify.failure(errorMessage);
        return;
      } else {
        this.$Notiflix.Notify.success("Update successful!");
      }
      await this.getUserInfo();
      await this.getUsers();

    },
    async getUserInfo() {
      const response = await sendRequest("/user/" + this.userInfo.id, "GET", null)
      if (!response.ok) {
        console.error(await response.text())
        return;
      }
      this.user = await response.json();
      this.address = this.user.address ? JSON.parse(this.user.address) : "";
      console.log(this.address)
    },
    async deleteUser() {
      const response = await sendRequest("/user", "DELETE", {id: this.user.id})
      if (!response.ok) {
        const errorMessage = await response.text();
        this.$Notiflix.Notify.failure(errorMessage);
      } else {
        this.$Notiflix.Notify.success("deleted successful!");
        await this.getUsers();
        this.closeModalWindow();
      }
    }
  }

}
</script>

<style scoped>
.user {
  min-width: 40em;
  padding: 0 1.5em;

  display: flex;
  /*justify-content: space-between;*/
}

.user p {
  padding: .5em 0 0;
  margin: 0;
}

.user-control {
  width: 60%;
  padding-left: 10em;
}

.user-control .title {
  padding: 2em 0 .5em;
  font-size: 16pt;
}

.user-control button {
  width: 100%;
  margin: .5em 0;
  /*background: var(--blue-opacity);*/
  /*color: #ffffff;*/
}
</style>